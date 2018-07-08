package org.lightning.particle.jdbc.meta;

import org.lightning.particle.jdbc.ds.DSFactory;
import org.lightning.particle.jdbc.ds.DataSourceParam;

import javax.sql.DataSource;

/**
 * Created by cook at 2018/7/8
 */
public final class MetadataFetcherFacotry {

    /**
     * 构建fetcher
     * @param param
     * @return
     */
    public static MetadataFetcher createFetcher(DataSourceParam param) {
        DataSource ds = DSFactory.createDataSource(param);
        if (param.getDriverClassName().contains("sqlserver")) {
            return new SQLServerJdbcMetadataFetcher(ds);
        }

        return new JdbcMetadataFetcher(ds);
    }

}
