package org.lightning.particle.core.model;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
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
    private Set<String> requiredClassNames;

    /**
     * 继承的类名
     */
    private String extendClassName;

    /**
     * 实现的接口(s)
     */
    private List<String> interfaceNames;

    /**
     * beanName-首字母小写
     * @return
     */
    public String getBeanNameLowerCamelCase() {
        return StringUtils.capitalize(getBeanName());
    }

}
