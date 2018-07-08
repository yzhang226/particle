package org.lightning.particle.jdbc.utils;

import org.apache.commons.dbcp2.PoolableConnection;
import org.apache.commons.pool2.impl.AbandonedConfig;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.lightning.particle.jdbc.ds.DataSourceParam;

import java.util.Properties;

/**
 * Created by cook at 2018/7/8
 */
public abstract class DsUtils {

    /**
     * 转化连接参数 为 Properties
     * @param param
     * @return
     */
    public static Properties convertParamToProperties(DataSourceParam param) {
        Properties info = new Properties();
        info.put("driverClassName", param.getDriverClassName());
        info.put("url", param.getUrl());
        info.put("user", param.getUsername());
        info.put("password", param.getPassword());
        if (param.getConnectionProperties() != null ) {
            info.put("connectionProperties", param.getConnectionProperties());
        }
        if (param.getInitialSize() != null) {
            info.put("initialSize", param.getInitialSize());
        }
        // 获取
        info.setProperty("useInformationSchema", "true");
        info.setProperty("nullNamePatternMatchesAll", "true");
        info.setProperty("allowMultiQueries", "true");
        return info;
    }


    public static void populatePoolConfig(DataSourceParam param, GenericObjectPool<PoolableConnection> pool) {
        if (param.getMaxTotal() != null) {
            pool.setMaxTotal(param.getMaxTotal());
        }
        if (param.getMaxIdle() != null) {
            pool.setMaxIdle(param.getMaxIdle());
        }
        if (param.getMinIdle() != null) {
            pool.setMinIdle(param.getMinIdle());
        }
        if (param.getMaxWaitMillis() != null) {
            pool.setMaxWaitMillis(param.getMaxWaitMillis());
        }
        // TODO: change to config
        pool.setTimeBetweenEvictionRunsMillis(60 * 1000);
        pool.setMinEvictableIdleTimeMillis(120 * 1000);
        pool.setBlockWhenExhausted(false);
        AbandonedConfig abandonedConfig = new AbandonedConfig();
        abandonedConfig.setLogAbandoned(true);
        abandonedConfig.setRemoveAbandonedOnBorrow(true);
        abandonedConfig.setRemoveAbandonedTimeout(120);
        pool.setAbandonedConfig(abandonedConfig);
    }

}
