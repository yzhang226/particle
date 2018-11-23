package org.lightning.particle.jdbc.test;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.Test;
import org.lightning.particle.core.common.GenerateOptions;
import org.lightning.particle.core.model.BeanInfo;
import org.lightning.particle.core.model.BeanProperty;
import org.lightning.particle.core.template.STTemplateParser;
import org.lightning.particle.core.template.TemplateContext;
import org.lightning.particle.jdbc.common.meta.Column;
import org.lightning.particle.jdbc.common.meta.Table;
import org.lightning.particle.jdbc.ds.DataSourceParam;
import org.lightning.particle.jdbc.meta.MetadataFetcher;
import org.lightning.particle.jdbc.meta.MetadataFetcherFacotry;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.lightning.particle.core.common.Constants.TEMPLATE_NAME_KEY_DEFAULT;
import static org.lightning.particle.core.common.Constants.TEMPLATE_PATH_JAVA_BEAN;

/**
 * Created by cook at 2018/7/8
 */
public class MetaDataFetcherTest {

    @Test
    public void testMySQLFetch() {
        DataSourceParam param = DbTestUtils.createMySQLDemoParam();
        MetadataFetcher fetcher = MetadataFetcherFacotry.createFetcher(param);
        Table table = fetcher.fetchTable("mkt_tasks");
        System.out.println(table);

        STTemplateParser parser = new STTemplateParser();
        TemplateContext context = new TemplateContext();
        BeanInfo info = createBeanInfo("com.fxtech.dolphin", table);
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


    private static BeanInfo createBeanInfo(String packageName, Table table) {
        BeanInfo bean = new BeanInfo();
        bean.setBeanName(table.getTableName() + "Bean");
        bean.setPackageName(packageName);
        bean.setComment(table.getRemarks());

        List<BeanProperty> properties = Lists.newArrayList();
        for (Column column : table.getColumns()) {
            BeanProperty property = new BeanProperty();
            property.setComment(column.getRemarks());
            property.setPropertyName(column.getColumnName());
            property.setPropertyTypeName(column.getTypeName());
            property.setDefaultValue(column.getColumnDefault());

            properties.add(property);
        }

        bean.setProperties(properties);

        bean.setRequiredClassNames(Sets.newHashSet(LocalDateTime.class.getName(),
                Getter.class.getName(), Setter.class.getName(),
                "com.fxtech.panda.spring.core.common.BaseBean"));

        bean.setExtendClassName("BaseBean");

//        bean.setAnnotationNames(Lists.newArrayList("Getter", "Setter"));

        return bean;
    }

    private static GenerateOptions createOptions() {
        GenerateOptions options = new GenerateOptions();
        options.setEnableLombok(true);
        options.setEnableGetterSetter(true);
        return options;
    }

    @Test
    public void testSQLServerFetch() {
        DataSourceParam param = DbTestUtils.createSQLServerDemoParam();
        MetadataFetcher fetcher = MetadataFetcherFacotry.createFetcher(param);
        Table table = fetcher.fetchTable("cmn_BizLog");
        System.out.println(table);
    }

}
