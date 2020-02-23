package org.lightning.particle.plugin.javabean.meta;

import lombok.Getter;
import lombok.Setter;
import org.lightning.particle.core.common.GenerateOptions;
import org.lightning.particle.core.common.TemplateNameEnum;
import org.lightning.particle.core.jdbc.meta.Table;
import org.lightning.particle.core.model.BeanInfo;
import org.lightning.particle.core.model.BeanProperty;
import org.lightning.particle.core.template.STTemplateParser;
import org.lightning.particle.core.template.TemplateContext;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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

    public void deleteJavaFile(MavenModule module, BeanInfo info) throws IOException {
        String filePathText = buildJavaFullPath(module, info);

        Path path = Paths.get(filePathText);

        if (Files.exists(path)) {
            Files.delete(path);
            System.out.println("\tDELETE JAVA file#" + filePathText);
        } else {
            System.out.println("\tJAVA file#" + filePathText + " DO NOT exist");
        }

    }

    public void deleteBaseMapperFile(MavenModule module, BeanInfo po, String databaseName) throws IOException {
        String filePathText = buildBaseMapperXmlPath(module, databaseName, po);
        Path path = Paths.get(filePathText);

        if (Files.exists(path)) {
            Files.delete(path);
            System.out.println("\tDELETE BaseMapper XML file#" + filePathText);
        } else {
            System.out.println("\tBaseMapper XML file#" + filePathText + " DO NOT exist");
        }

    }

    public void deleteEmptyMapperFile(MavenModule module, BeanInfo entity, String databaseName) throws IOException {
        String filePathText = buildEmptyMapperXmlPath(module, databaseName, entity);
        Path path = Paths.get(filePathText);

        if (Files.exists(path)) {
            Files.delete(path);
            System.out.println("\tDELETE EmptyMapper XML file#" + filePathText);
        } else {
            System.out.println("\tEmptyMapper XML file#" + filePathText + " DO NOT exist");
        }

    }

    public void deleteDaoFile(MavenModule module, BeanInfo dao) throws IOException {
        String filePathText = buildJavaFullPath(module, dao);
        Path path = Paths.get(filePathText);

        if (Files.exists(path)) {
            Files.delete(path);
            System.out.println("\tDELETE DAO file#" + filePathText);
        } else {
            System.out.println("\tDAO file#" + filePathText + " DO NOT exist");
        }
    }

    public void deleteServiceFile(MavenModule module, BeanInfo service) throws IOException {
        String filePathText = buildJavaFullPath(module, service);
        Path path = Paths.get(filePathText);

        if (Files.exists(path)) {
            Files.delete(path);
            System.out.println("\tDELETE Service file#" + filePathText);
        } else {
            System.out.println("\tService file#" + filePathText + " DO NOT exist");
        }
    }

    public void deleteBizFile(MavenModule module, BeanInfo biz) throws IOException {
        String filePathText = buildJavaFullPath(module, biz);
        Path path = Paths.get(filePathText);

        if (Files.exists(path)) {
            Files.delete(path);
            System.out.println("\tDELETE Biz file#" + filePathText);
        } else {
            System.out.println("\tBiz file#" + filePathText + " DO NOT exist");
        }
    }

    public void deleteControllerFile(MavenModule module, BeanInfo controller) throws IOException {
        String filePathText = buildJavaFullPath(module, controller);
        Path path = Paths.get(filePathText);

        if (Files.exists(path)) {
            Files.delete(path);
            System.out.println("\tDELETE Controller file#" + filePathText);
        } else {
            System.out.println("\tController file#" + filePathText + " DO NOT exist");
        }
    }

    public void generateBaseMapperFile(MavenModule module, BeanInfo daoBean, BeanInfo po, BeanInfo criteria,
                                       String databaseName, Table table, boolean isForceOverride) throws IOException {
        String filePathText = buildBaseMapperXmlPath(module, databaseName, po);
        Path path = Paths.get(filePathText);

        if (!isForceOverride && Files.exists(path)) {
            System.out.println("\tBaseMapper XML file#" + filePathText + " already exist");
            return;
        }

        TemplateContext context = createBaseMapperVarContext(daoBean, po, criteria, table);
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
            System.out.println("\tEmpty Mapper XML file#" + filePathText + " already exist");
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

        System.out.println("\tEmpty Mapper XML file#" + filePathText + " generated");
    }

    public void generateDaoFile(MavenModule module, BeanInfo po,
                                BeanInfo criteria, BeanInfo dao, boolean isForceOverride) throws IOException {
        String filePathText = buildJavaFullPath(module, dao);
        Path path = Paths.get(filePathText);

        if (!isForceOverride && Files.exists(path)) {
            System.out.println("\tDAO file#" + filePathText + " already exist");
            return;
        }

        TemplateContext context = createJavaDaoVarContext(po, criteria, dao);
        String text = parser.render(context);

        if (Files.notExists(path)) {
            if (Files.notExists(path.getParent())) {
                Files.createDirectories(path.getParent());
            }
            Files.createFile(path);
        }

        Files.write(path, text.getBytes());

        System.out.println("\tDAO file#" + filePathText + " generated");
    }

    public void generateServiceFile(MavenModule module, BeanInfo po, BeanInfo request, BeanInfo response,
                                    BeanInfo criteria, BeanInfo dao, BeanInfo service, boolean isForceOverride) throws IOException {
        String filePathText = buildJavaFullPath(module, service);
        Path path = Paths.get(filePathText);

        if (!isForceOverride && Files.exists(path)) {
            System.out.println("\tService file#" + filePathText + " already exist");
            return;
        }

        TemplateContext context = createJavaServiceVarContext(po, request, response, criteria, dao, service);
        String text = parser.render(context);

        if (Files.notExists(path)) {
            if (Files.notExists(path.getParent())) {
                Files.createDirectories(path.getParent());
            }
            Files.createFile(path);
        }

        Files.write(path, text.getBytes());

        System.out.println("\tService file#" + filePathText + " generated");
    }

    public void generateBizFile(MavenModule module, BeanInfo po, BeanInfo request, BeanInfo response,
                                BeanInfo criteria, BeanInfo dao, BeanInfo service, BeanInfo biz,
                                boolean isForceOverride) throws IOException {
        String filePathText = buildJavaFullPath(module, biz);
        Path path = Paths.get(filePathText);

        if (!isForceOverride && Files.exists(path)) {
            System.out.println("\tBiz file#" + filePathText + " already exist");
            return;
        }

        TemplateContext context = createJavaBizVarContext(po, request, response, criteria, dao, service, biz);
        String text = parser.render(context);

        if (Files.notExists(path)) {
            if (Files.notExists(path.getParent())) {
                Files.createDirectories(path.getParent());
            }
            Files.createFile(path);
        }

        Files.write(path, text.getBytes());

        System.out.println("\tBiz file#" + filePathText + " generated");
    }

    public void generateControllerFile(MavenModule module, BeanInfo controller, BeanInfo po,
                                       BeanInfo request, BeanInfo response,
                                       BeanInfo biz, boolean isForceOverride) throws IOException {
        String filePathText = buildJavaFullPath(module, controller);
        Path path = Paths.get(filePathText);

        if (!isForceOverride && Files.exists(path)) {
            System.out.println("\tController file#" + filePathText + " already exist");
            return;
        }

        TemplateContext context = createJavaControllerVarContext(controller, po, request, response, biz);
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
        if (info.isEnableSwagger()) {
//            @ApiModel(description = "")
            info.addClassAnnotationName("io.swagger.annotations.ApiModel", "(description = \"" + info.getComment() + "\")" );

            // @ApiModelProperty("过期的秒数")
            info.addRequiredClassName("io.swagger.annotations.ApiModelProperty");
            for (BeanProperty prop : info.getProperties()) {
                prop.addAnnotationName("ApiModelProperty(\"" + prop.getComment() + "\")");
            }

        }
    }

    private TemplateContext createJavaBeanVarContext(BeanInfo info) {

        TemplateContext context = new TemplateContext(TemplateNameEnum.JavaBean.getTemplatePath(),
                TemplateNameEnum.JavaBean.getTemplateName());
        context.addScopedVar("bean", info);
        context.addScopedVar("options", options);
        return context;
    }

    private TemplateContext createBaseMapperVarContext(BeanInfo daoBean, BeanInfo po,
                                                           BeanInfo criteria, Table table) {
        TemplateContext context = new TemplateContext(TemplateNameEnum.BaseMapper.getTemplatePath(),
                TemplateNameEnum.BaseMapper.getTemplateName());
        context.addScopedVar("daoBean", daoBean);
        context.addScopedVar("entity", po);
        context.addScopedVar("criteria", criteria);
        context.addScopedVar("table", table);
        return context;
    }

    private TemplateContext createEmptyMapperVarContext(BeanInfo daoBean, BeanInfo entity,
                                                       BeanInfo criteria, Table table) {
        TemplateContext context = new TemplateContext(TemplateNameEnum.EmptyMapper.getTemplatePath(),
                TemplateNameEnum.EmptyMapper.getTemplateName());
        context.addScopedVar("daoBean", daoBean);
        context.addScopedVar("entity", entity);
        context.addScopedVar("criteria", criteria);
        context.addScopedVar("table", table);
        return context;
    }

    private TemplateContext createJavaDaoVarContext(BeanInfo po, BeanInfo criteria, BeanInfo dao) {
        // springDao(po, criteria, dao, options)
        TemplateContext context = new TemplateContext(TemplateNameEnum.SpringDao.getTemplatePath(),
                TemplateNameEnum.SpringDao.getTemplateName());
        context.addScopedVar("po", po);
        context.addScopedVar("criteria", criteria);
        context.addScopedVar("dao", dao);
        context.addScopedVar("options", options);
        return context;
    }


    private TemplateContext createJavaServiceVarContext(BeanInfo po, BeanInfo request, BeanInfo response,
                                                        BeanInfo criteria, BeanInfo dao, BeanInfo service) {
        // springService(po, request, response, criteria, dao, service, options)
        TemplateContext context = new TemplateContext(TemplateNameEnum.SpringService.getTemplatePath(),
                TemplateNameEnum.SpringService.getTemplateName());
        context.addScopedVar("po", po);
        context.addScopedVar("request", request);
        context.addScopedVar("response", response);
        context.addScopedVar("criteria", criteria);
        context.addScopedVar("dao", dao);
        context.addScopedVar("service", service);
        context.addScopedVar("options", options);
        return context;
    }

    private TemplateContext createJavaBizVarContext(BeanInfo po, BeanInfo request, BeanInfo response,
                                                    BeanInfo criteria, BeanInfo dao, BeanInfo service,
                                                    BeanInfo biz) {
        // springService(po, request, response, criteria, dao, service, options)
        TemplateContext context = new TemplateContext(TemplateNameEnum.SpringBiz.getTemplatePath(),
                TemplateNameEnum.SpringBiz.getTemplateName());
        context.addScopedVar("po", po);
        context.addScopedVar("request", request);
        context.addScopedVar("response", response);
        context.addScopedVar("criteria", criteria);
        context.addScopedVar("dao", dao);
        context.addScopedVar("service", service);
        context.addScopedVar("biz", biz);
        context.addScopedVar("options", options);
        return context;
    }

    private TemplateContext createJavaControllerVarContext(BeanInfo controller, BeanInfo po,
                                                               BeanInfo request, BeanInfo response,
                                                               BeanInfo biz) {
        // javaController(controller, po, request, response, service, options)
        TemplateContext context = new TemplateContext(TemplateNameEnum.JavaController.getTemplatePath(),
                TemplateNameEnum.JavaController.getTemplateName());
        context.addScopedVar("controller", controller);
        context.addScopedVar("po", po);
        context.addScopedVar("request", request);
        context.addScopedVar("response", response);
        context.addScopedVar("biz", biz);
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
