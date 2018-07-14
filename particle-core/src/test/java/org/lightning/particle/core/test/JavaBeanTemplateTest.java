package org.lightning.particle.core.test;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.Test;
import org.lightning.particle.core.common.GenerateOptions;
import org.lightning.particle.core.model.BeanInfo;
import org.lightning.particle.core.model.BeanMethod;
import org.lightning.particle.core.model.BeanProperty;
import org.lightning.particle.core.template.STBeanTemplateContext;
import org.lightning.particle.core.template.STTemplateParser;
import org.lightning.particle.core.template.TemplateContext;

import java.time.LocalDateTime;
import java.util.Map;

import static org.lightning.particle.core.common.Constants.TEMPLATE_NAME_JAVA_BEAN;
import static org.lightning.particle.core.common.Constants.TEMPLATE_PATH_JAVA_BEAN;
import static org.lightning.particle.core.common.Constants.TEMPLATE_NAME_KEY_DEFAULT;

/**
 * Created by cook at 2018/7/8
 */
public class JavaBeanTemplateTest {

    private static BeanInfo createBeanInfo() {
        BeanInfo bean = new BeanInfo();
        bean.setBeanName("AaaBean");
        bean.setPackageName("org.test");
        bean.setComment("测试备注");

        BeanProperty property = new BeanProperty();
        property.setComment("te1");
        property.setPropertyName("te1");
        property.setPropertyTypeName(int.class.getSimpleName());

        BeanProperty property2 = new BeanProperty();
        property2.setComment("te2");
        property2.setPropertyName("te2");
        property2.setPropertyTypeName(String.class.getSimpleName());

        BeanProperty property3 = new BeanProperty();
        property3.setComment("te4");
        property3.setPropertyName("te4");
        property3.setPropertyTypeName(LocalDateTime.class.getSimpleName());

        bean.setProperties(Lists.newArrayList(property, property2, property3));
        bean.setRequiredClassNames(Sets.newHashSet(LocalDateTime.class.getName()));

        bean.setExtendClassName("BaseCrudDao");

        return bean;
    }

    private static BeanInfo createServiceBeanInfo() {
        BeanInfo bean = new BeanInfo();
        bean.setBeanName("AAAService");
        bean.setPackageName("org.test.service");
        bean.setComment("测试备注");

        BeanProperty property = new BeanProperty();
        property.setComment("basic crud");
        property.setPropertyName("aaaDao");
        property.setPropertyTypeName("AaaDao");
        property.addAnnotationName("Autowired");
        property.setDefaultValue("null");

        bean.setProperties(Lists.newArrayList(property));

        bean.setRequiredClassNames(Sets.newHashSet(LocalDateTime.class.getName()));
        bean.addRequiredClassName("org.test.dao.AaaDao");
        bean.addRequiredClassName("org.test.common.BaseCrudDao");

        bean.setExtendClassName("BaseCrudDao");
        bean.setExtendClassGenericTypes(Lists.newArrayList("AaaBean"));

        bean.setAnnotationNames(Lists.newArrayList("Service"));

        return bean;
    }

    private static GenerateOptions createOptions() {
        GenerateOptions options = new GenerateOptions();
        options.setEnableLombok(true);
        options.setEnableGetterSetter(false);
        return options;
    }

    private static BeanMethod serviceMethod1() {
        BeanMethod method =  new BeanMethod();
        method.setMethodName("getCrudDao");
        method.addModifier("protected");
        method.setReturnTypeName("AaaDao");
        method.setMethodContent("return aaDao;");
        return method;
    }


    @Test
    public void testJavaBean() {
        STTemplateParser parser = new STTemplateParser();
        TemplateContext context = new TemplateContext();
        BeanInfo info = createServiceBeanInfo();
        GenerateOptions options = createOptions();
        Map<String, Object> con = ImmutableMap.of(TEMPLATE_NAME_KEY_DEFAULT, "beanTemplate"
                                                    , "bean", info, "options", options);
        context.addScopedVars(con);
        if (options.isEnableGetterSetter() && options.isEnableLombok()) {
            info.addClassAnnotationName(Getter.class);
            info.addClassAnnotationName(Setter.class);
        }
        String text = parser.render(TEMPLATE_PATH_JAVA_BEAN, context);
        System.out.println("text is " + text);
    }

    @Test
    public void testSpringService() {
        STTemplateParser parser = new STTemplateParser();
        BeanInfo beanInfo = createServiceBeanInfo();
        beanInfo.addMethod(serviceMethod1());
        beanInfo.addMethod(serviceMethod1());
        GenerateOptions options = createOptions();

        STBeanTemplateContext context = new STBeanTemplateContext();
        context.setTemplateName(TEMPLATE_NAME_JAVA_BEAN);
        context.setBeanInfo(beanInfo);
        context.setGenerateOptions(options);

        String text = parser.render(TEMPLATE_PATH_JAVA_BEAN, context);
        System.out.println("text is " + text);
    }

}
