package org.lightning.particle.jdbc.test;


import org.lightning.particle.jdbc.ds.DataSourceParam;

import java.util.ResourceBundle;

/**
 * Created by cook on 2018/2/24
 */
public abstract class DbTestUtils {

    private static ResourceBundle resource = null;

    static {
        resource = ResourceBundle.getBundle("local");
        System.out.println("resource is " + resource);
    }

    public static DataSourceParam createSQLServerDemoParam() {
        return createJdbcParam("sql-server.demo");
    }

    public static DataSourceParam createMySQLDemoParam() {
        return createJdbcParam("mysql.demo");
    }

    public static DataSourceParam createJdbcParam(String prefix) {
        DataSourceParam param = new DataSourceParam();
        param.setDriverClassName(resource.getString(prefix + ".driver"));
        param.setUrl(resource.getString(prefix + ".url"));
        param.setUsername(resource.getString(prefix + ".username"));
        param.setPassword(resource.getString(prefix + ".password"));
        param.setMaxTotal(16);
        param.setMaxWaitMillis(2 * 1000L);
        return param;
    }

}
