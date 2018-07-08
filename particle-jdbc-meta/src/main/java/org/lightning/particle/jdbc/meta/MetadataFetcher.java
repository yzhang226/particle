package org.lightning.particle.jdbc.meta;

import org.lightning.particle.jdbc.common.meta.Column;
import org.lightning.particle.jdbc.common.meta.PrimaryKey;
import org.lightning.particle.jdbc.common.meta.Table;

import javax.sql.DataSource;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by cook at 2018/7/8
 */
public interface MetadataFetcher {

    /**
     * 获取
     * @param tableName
     * @return
     */
    Table fetchTable(String tableName);

    /**
     *
     * @param dbmd
     * @param tableName
     * @return
     * @throws SQLException
     */
    List<PrimaryKey> fetchPrimaryKeys(DatabaseMetaData dbmd, String tableName) throws SQLException;

    /**
     *
     * @param dbmd
     * @param tableName
     * @return
     * @throws SQLException
     */
    List<Column> fetchColumns(DatabaseMetaData dbmd, String tableName) throws SQLException;

}
