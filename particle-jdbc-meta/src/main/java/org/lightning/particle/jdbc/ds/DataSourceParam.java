package org.lightning.particle.jdbc.ds;


import lombok.Getter;
import lombok.Setter;

/**
 * Created by cook at 2018/7/8
 */
@Getter
@Setter
public class DataSourceParam {

    private String driverClassName;
    private String url;
    private String username;
    private String password;
    /** Format of the string must be [propertyName=property;]* */
    private String connectionProperties;

    /**
     * 初始连接数
     */
    private Integer initialSize;
    /**
     * 最大活动连接数
     */
    private Integer maxTotal;
    /**
     * 最大空闲连接数
     */
    private Integer maxIdle;
    /**
     * 最小空闲连接数
     */
    private Integer minIdle;
    /**
     * 从连接池获取一个连接时，最大的等待时间
     */
    private Long maxWaitMillis;

}
