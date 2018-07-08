package org.lightning.particle.jdbc.meta;

import org.apache.commons.collections4.CollectionUtils;
import org.lightning.particle.jdbc.common.meta.Column;
import org.lightning.particle.jdbc.common.meta.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

/**
 * sql-server适配
 * Created by cook at 2018/7/8
 */
public class SQLServerJdbcMetadataFetcher extends JdbcMetadataFetcher {

    private static final Logger logger = LoggerFactory.getLogger(SQLServerJdbcMetadataFetcher.class);

    private static String remarks_sql = "SELECT objname, cast(value as varchar(128)) as value " +
            " FROM fn_listextendedproperty ('MS_DESCRIPTION','schema', 'dbo', 'table', '{table_name}', 'column', null)";

    SQLServerJdbcMetadataFetcher(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public Table fetchTable(String tableName) {
        Table table = super.fetchTable(tableName);
        if (CollectionUtils.isNotEmpty(table.getColumns())) {
            Map<String, String> remarkMap = queryColumnRemarks(tableName);
            for (Column column : table.getColumns()) {
                column.setRemarks(remarkMap.get(column.getColumnName()));
            }
        }
        return table;
    }


    private Map<String, String> queryColumnRemarks(String tableName) {
        Map<String, String> columnRemarks = new HashMap<>();
        String sql = remarks_sql.replaceAll("\\{table_name}", tableName);
        try (Connection conn = dataSource.getConnection();
             Statement statement = conn.createStatement()) {
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                columnRemarks.put(rs.getString(1), rs.getString(2));
            }
            rs.close();
        } catch (SQLException e) {
            logger.error("query column remarks error", e);
        }

        return columnRemarks;
    }

}
