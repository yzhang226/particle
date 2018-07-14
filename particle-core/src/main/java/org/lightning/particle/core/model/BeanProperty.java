package org.lightning.particle.core.model;

import com.google.common.collect.Lists;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * Created by cook at 2018/7/8
 */
@Getter
@Setter
public class BeanProperty {

    /**
     *
     */
    private String propertyName;

    /**
     * 类型 - example: int, String
     */
    private String propertyTypeName;

    /**
     * 注释
     */
    private String comment;

    /**
     *
     */
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private String defaultValue;

    /**
     * 是否允许为空
     */
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private boolean allowNull;

    /**
     * 属性的注解(s)
     */
    private List<String> annotationNames = Lists.newArrayList();

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public boolean isAllowNull() {
        return allowNull;
    }

    public void setAllowNull(boolean allowNull) {
        this.allowNull = allowNull;
    }

    /**
     * 属性名-首字母大写
     * @return
     */
    public String getPropertyNameUpperCamelCase() {
        return StringUtils.capitalize(getPropertyName());
    }

    /**
     *
     * @param annoName
     */
    public void addAnnotationName(String annoName) {
        annotationNames.add(annoName);
    }

}
