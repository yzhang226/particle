package org.lightning.particle.core.model;

import com.google.common.collect.Lists;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.lightning.particle.core.jdbc.meta.Column;

import java.util.Collections;
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
     * 属性类型类全称 - example: java.lang.Integer, java.util.List
     */
    private String propertyTypeClass;

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

    /**
     * 方法修饰符(s), for example - public static final
     */
    @Getter(AccessLevel.NONE)
    private List<String> modifiers = null;

    // for jdbc
    private Column column;

    private String jdbcTypeName;

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

    /**
     *
     * @param modifier
     */
    public void addModifier(String modifier) {
        if (modifiers == null) {
            modifiers = Lists.newArrayList();
        }
        modifiers.add(modifier);
    }

    /**
     *
     * @return
     */
    public List<String> getModifiers() {
        return modifiers == null ? Collections.singletonList("private") : modifiers;
    }

}
