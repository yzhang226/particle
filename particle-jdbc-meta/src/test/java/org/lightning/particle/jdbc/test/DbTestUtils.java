package org.lightning.particle.jdbc.test;


import org.lightning.particle.jdbc.ds.DataSourceParam;
import org.lightning.particle.jdbc.ds.DatasourceLoader;

import java.util.ResourceBundle;

/**
 * Created by cook on 2018/2/24
 */
public abstract class DbTestUtils {

    public static DataSourceParam createSQLServerDemoParam() {
        return DatasourceLoader.buildJdbcParam("sql-server.demo");
    }

    public static DataSourceParam createMySQLDemoParam() {
        return DatasourceLoader.buildJdbcParam("mysql.demo");
    }

}
