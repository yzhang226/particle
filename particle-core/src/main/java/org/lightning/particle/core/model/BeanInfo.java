package org.lightning.particle.core.model;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.Singular;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by cook at 2018/7/8
 */
@Getter
@Setter
public class BeanInfo {

    /**
     * 包
     */
    private String packageName;

    /**
     * CamelCase, 首字母大写
     */
    private String beanName;

    /**
     * 注释
     */
    private String comment;

    /**
     *
     */
    private List<BeanProperty> properties;

    /**
     * 导入需要的依赖Classes
     */
    private Set<String> requiredClassNames = Sets.newHashSet();

    /**
     * 继承的类名
     */
    private String extendClassName;

    /**
     * 继承的类-泛型(s),
     */
    private List<String> extendClassGenericTypes = Lists.newArrayList();

    /**
     * 实现的接口(s)
     */
    private List<String> interfaceNames;

    /**
     * 类的注解(s)
     */
    private List<String> annotationNames = Lists.newArrayList();

    /**
     * 类的自定义方法(s)
     */
    private List<BeanMethod> methods = Lists.newArrayList();

    /**
     * 类修饰符(s), for example - public static final
     */
    @Getter(AccessLevel.NONE)
    private List<String> modifiers = null;

    /**
     * 是否启用lombok简写
     */
    private boolean enableLombok;

    /**
     * 启用Getter Setter
     */
    private boolean enableGetterSetter;

    // for jdbc
    private BeanProperty pkProperty;

    private String tableName;

    private String entityName;

    /**
     * beanName-首字母小写
     * @return
     */
    public String getUncapitalizeBeanName() {
        return StringUtils.uncapitalize(getBeanName());
    }

    /**
     *
     * @param annoClass
     */
    public void addClassAnnotationName(Class<?> annoClass) {
        annotationNames.add(annoClass.getSimpleName());
        requiredClassNames.add(annoClass.getName());
    }

    /**
     *
     * @param className
     */
    public void addRequiredClassName(String className) {
        requiredClassNames.add(className);
    }

    public void addProperty(BeanProperty property) {
        if (properties == null) {
            properties = Lists.newArrayList();
        }

        properties.add(property);
    }

    /**
     *
     * @param method
     */
    public void addMethod(BeanMethod method) {
        methods.add(method);
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
        if (modifiers == null) {
            modifiers = Lists.newArrayList();
            modifiers.add("public");
            modifiers.add("class");
        }
        return modifiers;
    }

    /**
     * 全称 - 包名 + 类名
     * @return
     */
    public String getFullBeanName() {
        return packageName + "." + beanName;
    }

    public String getUriName() {
        StringBuilder sb = new StringBuilder();
        String uriName = tableName.replace("_", "-");
        char[] arr = uriName.toCharArray();
        for (char c : arr) {
            if (Character.isUpperCase(c)) {
                sb.append("-").append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

}
