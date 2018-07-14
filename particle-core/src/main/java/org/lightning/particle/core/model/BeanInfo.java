package org.lightning.particle.core.model;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.Getter;
import lombok.Setter;
import lombok.Singular;
import org.apache.commons.lang3.StringUtils;

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
     * beanName-首字母小写
     * @return
     */
    public String getBeanNameLowerCamelCase() {
        return StringUtils.capitalize(getBeanName());
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

    /**
     *
     * @param method
     */
    public void addMethod(BeanMethod method) {
        methods.add(method);
    }


}
