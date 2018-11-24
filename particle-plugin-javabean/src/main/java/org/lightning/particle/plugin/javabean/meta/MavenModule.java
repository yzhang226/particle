package org.lightning.particle.plugin.javabean.meta;

import lombok.Getter;
import lombok.Setter;
import org.lightning.particle.core.common.GenerateOptions;

/**
 * Created by cook on 2018/11/24
 */
@Getter
@Setter
public class MavenModule {

    private static final String JAVA_PATH = "src/main/java";

    private static final String RESOURCE_PATH = "src/main/resources";

    /**
     * 模块名
     */
    private String moduleName;

    /**
     * 基准包名
     */
    private String basePackage;

    public MavenModule(String moduleName, String basePackage) {
        this.moduleName = moduleName;
        this.basePackage = basePackage;
    }

    public String getJavaRelativePath(String packageName) {
        String re = moduleName + "/" + JAVA_PATH + "/" + packageName.replaceAll("\\.", "/");
        return re;
    }

    public String getResourceRelativePath(String subDir) {
        String re = moduleName + "/" + RESOURCE_PATH + "/" + subDir;
        return re;
    }

    private static GenerateOptions createOptions() {
        GenerateOptions options = new GenerateOptions();
        options.setEnableLombok(true);
        options.setEnableGetterSetter(true);
        return options;
    }

}
