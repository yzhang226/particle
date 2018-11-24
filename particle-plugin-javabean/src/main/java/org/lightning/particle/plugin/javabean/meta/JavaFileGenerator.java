package org.lightning.particle.plugin.javabean.meta;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.Setter;
import org.lightning.particle.core.common.StgTemplateNames;
import org.lightning.particle.core.common.GenerateOptions;
import org.lightning.particle.core.jdbc.meta.Table;
import org.lightning.particle.core.model.BeanInfo;
import org.lightning.particle.core.template.STTemplateParser;
import org.lightning.particle.core.template.TemplateContext;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import static org.lightning.particle.core.common.StgTemplateNames.KEY_TEMPLATE_NAME;

/**
 * Created by cook on 2018/11/24
 */
public class JavaFileGenerator {

    private String topDir;

    private STTemplateParser parser;

//    private TemplateContext context;

    private GenerateOptions options;

    public JavaFileGenerator(String topDir) {
        this.topDir = topDir;
        parser = new STTemplateParser();
        options = createOptions();
    }

    public String buildJavaFullPath(MavenModule module, BeanInfo beanInfo) {
        String path = topDir
                + "/" + module.getJavaRelativePath(beanInfo.getPackageName())
                + "/" + beanInfo.getBeanName() + ".java";
        return path;
    }

    public String buildBaseMapperXmlPath(MavenModule module, String databaseName, BeanInfo beanInfo) {
        String path = topDir
                + "/" + module.getResourceRelativePath("mapper/" + databaseName + "/base")
                + "/Base" + beanInfo.getBeanName() + "Mapper.xml";
        return path;
    }

    public String buildEmptyMapperXmlPath(MavenModule module, String databaseName, BeanInfo beanInfo) {
        String path = topDir
                + "/" + module.getResourceRelativePath("mapper/" + databaseName )
                + "/" + beanInfo.getBeanName() + "Mapper.xml";
        return path;
    }

    public void generateJavaFile(MavenModule module, BeanInfo info, boolean isForceOverride) throws IOException {
        String filePathText = buildJavaFullPath(module, info);

        Path path = Paths.get(filePathText);

        if (!isForceOverride && Files.exists(path)) {
            System.out.println("\tJAVA file#" + filePathText + " already exist");
            return;
        }

        preProcessBean(info);
        TemplateContext context = createJavaBeanVarContext(info);
        String text = parser.render(context);

        if (Files.notExists(path)) {
            if (Files.notExists(path.getParent())) {
                Files.createDirectories(path.getParent());
            }
            Files.createFile(path);
        }

        Files.write(path, text.getBytes());

        System.out.println("\tJAVA file#" + filePathText + " generated");

        return;
    }

    public void generateBaseMapperFile(MavenModule module, BeanInfo daoBean, BeanInfo entity, BeanInfo criteria,
                                       String databaseName, Table table, boolean isForceOverride) throws IOException {
        String filePathText = buildBaseMapperXmlPath(module, databaseName, entity);
        Path path = Paths.get(filePathText);

        if (!isForceOverride && Files.exists(path)) {
            System.out.println("\tBaseMapper XML file#" + filePathText + " already exist");
            return;
        }

        TemplateContext context = createBaseMapperVarContext(daoBean, entity, criteria, table);
        String text = parser.render(context);

        if (Files.notExists(path)) {
            if (Files.notExists(path.getParent())) {
                Files.createDirectories(path.getParent());
            }
            Files.createFile(path);
        }

        Files.write(path, text.getBytes());

        System.out.println("\tBaseMapper XML file#" + filePathText + " generated");
    }

    public void generateEmptyMapperFile(MavenModule module, BeanInfo daoBean, BeanInfo entity, BeanInfo criteria,
                                       String databaseName, Table table, boolean isForceOverride) throws IOException {
        String filePathText = buildEmptyMapperXmlPath(module, databaseName, entity);
        Path path = Paths.get(filePathText);

        if (!isForceOverride && Files.exists(path)) {
            System.out.println("\tBaseMapper XML file#" + filePathText + " already exist");
            return;
        }

        TemplateContext context = createEmptyMapperVarContext(daoBean, entity, criteria, table);
        String text = parser.render(context);

        if (Files.notExists(path)) {
            if (Files.notExists(path.getParent())) {
                Files.createDirectories(path.getParent());
            }
            Files.createFile(path);
        }

        Files.write(path, text.getBytes());

        System.out.println("\tBaseMapper XML file#" + filePathText + " generated");
    }

    public void generateControllerFile(MavenModule module, BeanInfo controller, BeanInfo po,
                                       BeanInfo request, BeanInfo response,
                                       BeanInfo service, boolean isForceOverride) throws IOException {
        String filePathText = buildJavaFullPath(module, controller);
        Path path = Paths.get(filePathText);

        if (!isForceOverride && Files.exists(path)) {
            System.out.println("\tController file#" + filePathText + " already exist");
            return;
        }

        TemplateContext context = createJavaControllerVarContext(controller, po, request, response, service);
        String text = parser.render(context);

        if (Files.notExists(path)) {
            if (Files.notExists(path.getParent())) {
                Files.createDirectories(path.getParent());
            }
            Files.createFile(path);
        }

        Files.write(path, text.getBytes());

        System.out.println("\tController file#" + filePathText + " generated");
    }

    private void preProcessBean(BeanInfo info) {
        if (info.isEnableGetterSetter() && info.isEnableLombok()) {
            info.addClassAnnotationName(Getter.class);
            info.addClassAnnotationName(Setter.class);
        }
    }

    private TemplateContext createJavaBeanVarContext(BeanInfo info) {
        TemplateContext context = new TemplateContext(StgTemplateNames.JavaBean.TEMPLATE_PATH,
                StgTemplateNames.JavaBean.TEMPLATE_NAME);
        context.addScopedVar("bean", info);
        context.addScopedVar("options", options);
        return context;
    }

    private TemplateContext createBaseMapperVarContext(BeanInfo daoBean, BeanInfo entity,
                                                           BeanInfo criteria, Table table) {
        TemplateContext context = new TemplateContext(StgTemplateNames.BaseMapper.TEMPLATE_PATH,
                StgTemplateNames.BaseMapper.TEMPLATE_NAME);
        context.addScopedVar("daoBean", daoBean);
        context.addScopedVar("entity", entity);
        context.addScopedVar("criteria", criteria);
        context.addScopedVar("table", table);
        return context;
    }

    private TemplateContext createEmptyMapperVarContext(BeanInfo daoBean, BeanInfo entity,
                                                       BeanInfo criteria, Table table) {
        TemplateContext context = new TemplateContext(StgTemplateNames.EmptyMapper.TEMPLATE_PATH,
                StgTemplateNames.EmptyMapper.TEMPLATE_NAME);
        context.addScopedVar("daoBean", daoBean);
        context.addScopedVar("entity", entity);
        context.addScopedVar("criteria", criteria);
        context.addScopedVar("table", table);
        return context;
    }

    private TemplateContext createJavaControllerVarContext(BeanInfo controller, BeanInfo po,
                                                               BeanInfo request, BeanInfo response,
                                                               BeanInfo service) {
        // javaController(controller, po, request, response, service, options)
        TemplateContext context = new TemplateContext(StgTemplateNames.JavaController.TEMPLATE_PATH,
                StgTemplateNames.JavaController.TEMPLATE_NAME);
        context.addScopedVar("controller", controller);
        context.addScopedVar("po", po);
        context.addScopedVar("request", request);
        context.addScopedVar("response", response);
        context.addScopedVar("service", service);
        context.addScopedVar("options", options);
        return context;
    }

    private static GenerateOptions createOptions() {
        GenerateOptions options = new GenerateOptions();
        options.setEnableLombok(true);
        options.setEnableGetterSetter(true);
        return options;
    }

}
