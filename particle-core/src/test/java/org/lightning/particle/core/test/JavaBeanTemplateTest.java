package org.lightning.particle.core.test;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.jupiter.api.Test;
import org.lightning.particle.core.common.GenerateOptions;
import org.lightning.particle.core.model.BeanInfo;
import org.lightning.particle.core.model.BeanProperty;
import org.lightning.particle.core.template.STTemplateParser;
import org.lightning.particle.core.template.TemplateContext;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

import java.time.LocalDateTime;
import java.util.Map;

import static org.lightning.particle.core.common.Constants.TEMPLATE_NAME_KEY_DEFAULT;

/**
 * Created by cook at 2018/7/8
 */
public class JavaBeanTemplateTest {

    private static BeanInfo createBeanInfo() {
        BeanInfo bean = new BeanInfo();
        bean.setBeanName("AA");
        bean.setPackageName("org.test");
        bean.setComment("测试备注");

        BeanProperty property = new BeanProperty();
        property.setComment("te1");
        property.setPropertyName("te1");
        property.setPropertyType(int.class);

        BeanProperty property2 = new BeanProperty();
        property2.setComment("te2");
        property2.setPropertyName("te2");
        property2.setPropertyType(String.class);

        BeanProperty property3 = new BeanProperty();
        property3.setComment("te4");
        property3.setPropertyName("te4");
        property3.setPropertyType(LocalDateTime.class);

        bean.setProperties(Lists.newArrayList(property, property2, property3));
        bean.setRequiredClasses(Sets.newHashSet(LocalDateTime.class));

        return bean;
    }

    private static GenerateOptions createOptions() {
        GenerateOptions options = new GenerateOptions();
        options.setEnableLombok(true);
        return options;
    }

    @Test
    public void test1() {
        STGroup stg = new STGroupFile("bean.stg");
        ST template = stg.getInstanceOf("beanTemplate");

        template.add("bean", createBeanInfo());
        template.add("options",createOptions());

        System.out.println(template.render());
    }

    @Test
    public void test2() {
        STTemplateParser parser = new STTemplateParser();
        TemplateContext context = new TemplateContext();
        Map<String, Object> con = ImmutableMap.of(TEMPLATE_NAME_KEY_DEFAULT, "beanTemplate"
                                                    , "bean", createBeanInfo(), "options",createOptions());
        context.addScopedVars(con);
        String text = parser.render("st/java-bean.stg", context);
        System.out.println("text is " + text);
    }

}
