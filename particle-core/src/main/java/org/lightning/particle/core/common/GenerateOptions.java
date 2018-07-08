package org.lightning.particle.core.common;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by cook at 2018/7/8
 */
@Getter
@Setter
public class GenerateOptions {

    /**
     * bean需要继承的类
     */
    private String beanExtendClass;

    /**
     * 默认Json使用的类
     */
    private String defaultJsonClass;

    /**
     * 是否启用lombok简写
     */
    private boolean enableLombok;

    /**
     *
     */
    private List<Class<?>> externalClasses;

    @Getter(AccessLevel.NONE)
    private String currentDate;

    @Getter(AccessLevel.NONE)
    private String currentUserName;

    public String getCurrentDate() {
        return currentDate == null ? LocalDate.now().toString() : currentDate;
    }

    public String getCurrentUserName() {
        return currentUserName == null ? System.getProperty("user.name") : currentUserName;
    }

}
