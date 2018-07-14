package org.lightning.particle.core.model;

import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by cook at 2018/7/14
 */
@Getter
@Setter
public class BeanMethod {

    /**
     *
     */
    private String methodName;

    /**
     * 方法返回类型, for example - int String Long
     */
    private String returnTypeName;

    /**
     * 方法修饰符(s), for example - public static final
     */
    private List<String> modifiers = Lists.newArrayList();

    /**
     * 方法内容
     */
    private String methodContent;

    /**
     * 方法的注解(s)
     */
    private List<String> annotationNames = Lists.newArrayList();

    /**
     * 方法的参数
     */
    private List<MethodParameter> parameters = Lists.newArrayList();

    /**
     *
     * @param parameter
     */
    public void addParameter(MethodParameter parameter) {
        parameters.add(parameter);
    }

    /**
     *
     * @param anno
     */
    public void addAnnotation(Class<?> anno) {
        annotationNames.add(anno.getSimpleName());
    }

    /**
     *
     * @param modifier
     */
    public void addModifier(String modifier) {
        modifiers.add(modifier);
    }


}
