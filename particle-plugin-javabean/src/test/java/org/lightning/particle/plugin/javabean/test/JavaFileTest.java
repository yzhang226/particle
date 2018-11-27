package org.lightning.particle.plugin.javabean.test;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;
import org.lightning.particle.jdbc.ds.DataSourceParam;
import org.lightning.particle.jdbc.ds.DatasourceLoader;
import org.lightning.particle.plugin.javabean.common.GenerateOption;
import org.lightning.particle.plugin.javabean.meta.JavaFileGenerator;
import org.lightning.particle.plugin.javabean.meta.JdbcJavaFileGenerator;
import org.lightning.particle.plugin.javabean.meta.MavenModule;

import java.io.IOException;
import java.util.List;

/**
 * Created by cook on 2018/11/24
 */
public class JavaFileTest {

    @Test
    public void testJavaFileGenerate() throws IOException {
        GenerateOption option = new GenerateOption();

        option.forceOverrideDtoRequest = true;
        option.forceOverrideDtoResponse = true;
        option.forceOverridePo = true;
        option.forceOverrideCriteria = true;
        option.forceOverrideDao = true;
        option.forceOverrideService = true;
        option.forceOverrideBaseMapper = true;
        option.enableController = true;
        option.enableLocalDateTime = true;
        option.forceOverrideController = true;

        option.removePrefixes = Lists.newArrayList("app_");

        option.rootArtifactId = "dolphin";
        option.basePackageName = "com.fxtech.dolphin";

        JavaFileGenerator fileGenerator = new JavaFileGenerator("/work/fxtech/dolphin");
        DataSourceParam param = DatasourceLoader.buildJdbcParam("local2");
        JdbcJavaFileGenerator generator = new JdbcJavaFileGenerator(param, option, fileGenerator);

        generator.generateJavaFileSuite("app_privilege");
        generator.generateJavaFileSuite("app_user");

    }



}
