package org.lightning.particle.jdbc.ds;

import java.util.ResourceBundle;

/**
 * Created by cook on 2018/11/24
 */
public abstract class DatasourceLoader {

    public static DataSourceParam buildJdbcParam(String resourceName, String prefix) {
        ResourceBundle resource = ResourceBundle.getBundle(resourceName);
        DataSourceParam param = new DataSourceParam();
        param.setDriverClassName(resource.getString(prefix + ".jdbc.driverClassName"));
        param.setUrl(resource.getString(prefix + ".jdbc.url"));
        param.setUsername(resource.getString(prefix + ".jdbc.username"));
        param.setPassword(resource.getString(prefix + ".jdbc.password"));
        param.setDatabaseName(resource.getString(prefix + ".jdbc.database"));
        if (param.getUrl().contains("{database}")) {
            param.setUrl(param.getUrl().replace("{database}", param.getDatabaseName()));
        }
        param.setMaxTotal(16);
        param.setMaxWaitMillis(2 * 1000L);

        param.setDatabaseMappings(resource.getString(prefix + ".database.mappings"));
        param.setTableNames(resource.getString(prefix + ".generate.table-names"));

        return param;
    }

    public static DataSourceParam buildJdbcParam(String prefix) {
        return buildJdbcParam("dev_jdbc", prefix);
    }

}
