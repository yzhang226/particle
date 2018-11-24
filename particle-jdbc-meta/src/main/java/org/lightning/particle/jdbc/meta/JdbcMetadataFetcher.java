package org.lightning.particle.jdbc.meta;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.lightning.particle.core.jdbc.meta.Column;
import org.lightning.particle.core.jdbc.meta.PrimaryKey;
import org.lightning.particle.core.jdbc.meta.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Created by cook at 2018/7/8
 */
public class JdbcMetadataFetcher implements MetadataFetcher {

    private static final Logger logger = LoggerFactory.getLogger(JdbcMetadataFetcher.class);

    protected DataSource dataSource;

    JdbcMetadataFetcher(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Table fetchTable(String tableName) {
        try (Connection conn = this.dataSource.getConnection()) {
            DatabaseMetaData dbmd = conn.getMetaData();

            Table table = fetchTableInfo(dbmd, tableName);

            List<PrimaryKey> pks = this.fetchPrimaryKeys(dbmd, tableName);
            List<Column> columns = this.fetchColumns(dbmd, tableName);

            table.setPrimaryKeys(pks);
            table.setColumns(columns);

            table.buildColumnOfPrimaryKey();

            return table;
        } catch (SQLException e) {
            logger.error("fetch table error", e);
            throw new RuntimeException("fetch table error", e);
        }
    }

    protected Table fetchTableInfo(DatabaseMetaData dbmd, String tableName) throws SQLException {
        Table table = new Table();
        ResultSet rs = dbmd.getTables(null, null, tableName, null);
        List<Map<String, Object>> rows = toListMap(rs);
        rows.forEach(row -> {
            table.setTableCategory((String) row.get("TABLE_CAT"));
            table.setTableSchema((String) row.get("TABLE_SCHEM"));
            table.setTableName((String) row.get("TABLE_NAME"));
            table.setTableType((String) row.get("TABLE_TYPE"));
            table.setRemarks((String) row.get("REMARKS"));
            table.setTypeCategory((String) row.get("TYPE_CAT"));
            table.setTypeSchema((String) row.get("TYPE_SCHEM"));
            table.setTypeName((String) row.get("TYPE_NAME"));
            table.setSelfReferencingColName((String) row.get("SELF_REFERENCING_COL_NAME"));
            table.setRefGeneration((String) row.get("REF_GENERATION"));
        });
        return table;
    }

    private List<Map<String, Object>> toListMap(ResultSet rs) {
        try {
            ResultSetMetaData meta = rs.getMetaData();
            List<Map<String, Object>> res = Lists.newArrayList();
            int count = meta.getColumnCount();
            while (rs.next()) {
                Map<String, Object> map = Maps.newHashMap();
                for (int i = 1; i <= count; i++) {
                    map.put(meta.getColumnName(i), rs.getObject(i));
                }
                res.add(map);
            }
            return res;
        } catch (SQLException e) {
            throw new RuntimeException("result set to map error", e);
        }
    }

    public List<PrimaryKey> fetchPrimaryKeys(DatabaseMetaData dbmd, String tableName) throws SQLException {
        ResultSet rs = dbmd.getPrimaryKeys(null, null, tableName);
        List<Map<String, Object>> rows = toListMap(rs);

        List<PrimaryKey> pks = Lists.newArrayList();
        rows.forEach(row -> {
            PrimaryKey pk = new PrimaryKey();
            pk.setTableCategory((String) row.get("TABLE_CAT"));
            pk.setTableSchema((String) row.get("TABLE_SCHEM"));
            pk.setTableName((String) row.get("TABLE_NAME"));
            pk.setColumnName((String) row.get("COLUMN_NAME"));
            pk.setKeySequence(getInt(row, "KEY_SEQ"));
            pk.setPkName((String) row.get("PK_NAME"));
            pks.add(pk);
        });

        rs.close();
        return pks;
    }

    public List<Column> fetchColumns(DatabaseMetaData dbmd, String tableName) throws SQLException {
        List<Column> columns = Lists.newArrayList();

        ResultSet rs = dbmd.getColumns(null, null, tableName, null);
        List<Map<String, Object>> rows = toListMap(rs);

        rows.forEach(row -> {
            Column col = new Column();
            col.setTableCategory((String) row.get("TABLE_CAT"));
            col.setTableSchema((String) row.get("TABLE_SCHEM"));
            col.setTableName((String) row.get("TABLE_NAME"));
            col.setColumnName((String) row.get("COLUMN_NAME"));

            col.setDataType(getInt(row, "DATA_TYPE"));
            col.setTypeName((String) row.get("TYPE_NAME"));
            col.setColumnSize(getInt(row, "DATA_TYPE"));
            col.setDecimalDigits(getInt(row, "DECIMAL_DIGITS"));
            col.setNumPrecRadix(getInt(row, "NUM_PREC_RADIX"));
            col.setNullable(getInt(row, "NULLABLE"));

            col.setRemarks((String) row.get("REMARKS"));
            col.setColumnDefault((String) row.get("COLUMN_DEF"));
            col.setCharOctetLength(getInt(row, "CHAR_OCTET_LENGTH"));
            col.setOrdinalPosition(getInt(row, "ORDINAL_POSITION"));
            col.setIsNullable((String) row.get("IS_NULLABLE"));
            col.setScopeCatalog((String) row.get("SCOPE_CATALOG"));
            col.setScopeSchema((String) row.get("SCOPE_SCHEMA"));
            col.setScopeTable((String) row.get("SCOPE_TABLE"));
            col.setSourceDataType((String) row.get("SOURCE_DATA_TYPE"));
            col.setIsAutoIncrement((String) row.get("IS_AUTOINCREMENT"));
            col.setIsGeneratedColumn((String) row.get("IS_GENERATEDCOLUMN"));

            columns.add(col);
        });
        while (rs.next()) {

        }
        rs.close();

        columns.sort(Comparator.comparingInt(Column::getOrdinalPosition));

        return columns;
    }

    private int getInt(Map<String, Object> map, String key) {
        Number n = (Number) map.get(key);
        return n == null ? 0 : n.intValue();
    }


}
