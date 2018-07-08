package org.lightning.particle.core.test;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.jupiter.api.Test;
import org.lightning.particle.core.common.GenerateOptions;
import org.lightning.particle.core.model.BeanInfo;
import org.lightning.particle.core.model.BeanProperty;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

import java.time.LocalDateTime;

/**
 * Created by cook at 2018/7/8
 */
public class JavaBeanTemplateTest {

    @Test
    public void test1() {
        STGroup stg = new STGroupFile("bean.stg");
        ST template = stg.getInstanceOf("beanTemplate");
        BeanInfo bean = new BeanInfo();
        bean.setBeanName("AA");
        bean.setPackageName("org.test");

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

        GenerateOptions options = new GenerateOptions();

        template.add("bean", bean);
        template.add("options",options);

        System.out.println(template.render());
    }

}
