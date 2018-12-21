package org.lightning.particle.plugin.javabean.common;

import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by cook on 2018/11/24
 */
@Getter
public class GenerateOption {

    /**
     * 是否强制覆盖DtoRequest文件, 默认: false
     */
    public boolean forceOverrideDtoRequest = false;

    /**
     * 是否强制覆盖DtoResponse文件, 默认: false
     */
    public boolean forceOverrideDtoResponse = false;

    /**
     * 是否强制覆盖Bean文件, 默认: true
     */
    public boolean forceOverridePo = true;

    /**
     * 是否强制覆盖Criteria文件, 默认: true
     */
    public boolean forceOverrideCriteria = true;

    /**
     * 是否强制覆盖Dao文件, 默认: false
     */
    public boolean forceOverrideDao = false;

    /**
     * 是否强制覆盖Service文件, 默认: false
     */
    public boolean forceOverrideService = false;

    /**
     * 是否强制覆盖BaseMapper文件, 默认: true
     */
    public boolean forceOverrideBaseMapper = true;

    /**
     * 是否生成Controller文件, 默认: false
     */
    public boolean enableController = false;

    /**
     * 是否强制覆盖Controller文件, 默认: false
     */
    public boolean forceOverrideController = false;

    /**
     * 是否启用使用LocalDateTime, 默认: false
     */
    public boolean enableLocalDateTime = true;

    /**
     * 移除的前缀
     */
    public List<String> removePrefixes = Lists.newArrayList();

    /**
     * 根项目id
     */
    public String rootArtifactId;

    /**
     * 基础包名
     */
    public String basePackageName;

    /**
     * 数据库包名映射
     */
    public List<String> databaseMappings;

    /**
     * 等待生成的表名
     */
    public List<String> generateTableNames;

    public String getCurrentDate() {
        return LocalDate.now().toString();
    }

    public String getCurrentUserName() {
        return System.getProperty("user.name");
    }


}
