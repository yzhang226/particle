package org.lightning.particle.core.common;

/**
 * Created by cook on 2019/3/1
 */
public enum TemplateNameEnum {

    // for JAVA
    JavaBean("st/java/java-bean.stg", "javaBean"),

    SpringDao("st/java/spring-dao.stg", "springDao"),

    SpringService("st/java/spring-service.stg", "springService"),

    SpringBiz("st/java/spring-biz.stg", "springBiz"),

    JavaController("st/java/spring-controller.stg", "springController"),

    BaseMapper("st/java/base-mapper.stg", "baseMapper"),

    EmptyMapper("st/java/empty-mapper.stg", "emptyMapper"),

    // for GO



    ;

    TemplateNameEnum(String templatePath, String templateName) {
        this.templatePath = templatePath;
        this.templateName = templateName;
    }

    /**
     * 模板路径 - 相对 classpath
     */
    private String templatePath;

    /**
     * 模板名称
     */
    private String templateName;

    public String getTemplatePath() {
        return templatePath;
    }

    public String getTemplateName() {
        return templateName;
    }
}
