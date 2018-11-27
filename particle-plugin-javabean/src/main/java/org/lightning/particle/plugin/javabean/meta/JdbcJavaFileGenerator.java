package org.lightning.particle.plugin.javabean.meta;

import org.lightning.particle.core.model.BeanInfo;
import org.lightning.particle.core.jdbc.meta.Table;
import org.lightning.particle.jdbc.ds.DataSourceParam;
import org.lightning.particle.jdbc.meta.MetadataFetcher;
import org.lightning.particle.jdbc.meta.MetadataFetcherFacotry;
import org.lightning.particle.plugin.javabean.common.GenerateOption;

import java.io.IOException;

/**
 * Created by cook on 2018/11/24
 */
public class JdbcJavaFileGenerator {

    private DataSourceParam param;

    private MetadataFetcher fetcher;

    private GenerateOption option;

    private JavaFileGenerator fileGenerator;

    public JdbcJavaFileGenerator(DataSourceParam param, GenerateOption option, JavaFileGenerator fileGenerator) {
        this.param = param;
        this.fetcher = MetadataFetcherFacotry.createFetcher(param);
        this.option = option;
        this.fileGenerator = fileGenerator;
    }

    public void generateJavaFileSuite(String tableName) throws IOException {
        Table table = fetcher.fetchTable(tableName);

        TableJavaConverter converter = new TableJavaConverter(option, table);

        // support
        MavenModule supportModule = new MavenModule(option.getRootArtifactId() + "-support", option.getBasePackageName() + ".support.model");
        String poPackage = supportModule.getBasePackage() + ".po." + param.getDatabaseName();
        BeanInfo po = converter.createPoInfo(poPackage);
        BeanInfo criteria = converter.createCriteriaInfo(poPackage);
        fileGenerator.generateJavaFile(supportModule, po, option.isForceOverridePo());
        fileGenerator.generateJavaFile(supportModule, criteria, option.isForceOverrideCriteria());

        if (po.getPk().isMultiplePk()) {
            // generate pk
            fileGenerator.generateJavaFile(supportModule, po.getPk(), option.isForceOverridePo());
        }

        // dto
        MavenModule dtoModule = new MavenModule(option.getRootArtifactId() + "-model", option.getBasePackageName() + ".model");
        String dtoPackage = dtoModule.getBasePackage() + ".dto." + param.getDatabaseName();
        BeanInfo request = converter.createRequestInfo(dtoPackage);
        BeanInfo response = converter.createResponseInfo(dtoPackage);

        fileGenerator.generateJavaFile(dtoModule, request, option.isForceOverrideDtoRequest());
        fileGenerator.generateJavaFile(dtoModule, response, option.isForceOverrideDtoResponse());

        // dao
        MavenModule daoModule = new MavenModule(option.getRootArtifactId() + "-dao", option.getBasePackageName() + ".dao");
        String daoPackage = daoModule.getBasePackage() + ".daos." + param.getDatabaseName();
        BeanInfo dao = converter.createDaoInfo(daoPackage, po, criteria);

        fileGenerator.generateDaoFile(daoModule, po, criteria, dao, option.isForceOverrideDao());

        // service
        MavenModule serviceModule = new MavenModule(option.getRootArtifactId() + "-service", option.getBasePackageName() + ".service");
        String servicePackage = serviceModule.getBasePackage() + ".services." + param.getDatabaseName();
        BeanInfo service = converter.createServiceInfo(servicePackage, po, criteria, request, response, dao);

        fileGenerator.generateServiceFile(serviceModule, po, request, response, criteria, dao, service, option.isForceOverrideService());



        // mapper xml
        fileGenerator.generateBaseMapperFile(daoModule, dao, po, criteria, param.getDatabaseName(),
                table, option.isForceOverrideBaseMapper());

        fileGenerator.generateEmptyMapperFile(daoModule, dao, po, criteria, param.getDatabaseName(),
                table, option.isForceOverrideBaseMapper());

        MavenModule controllerModule = new MavenModule(option.getRootArtifactId() + "-api", option.getBasePackageName() + ".api");
        String controllerPackage = controllerModule.getBasePackage() + ".controller." + param.getDatabaseName();
        BeanInfo controller = converter.createControllerInfo(controllerPackage);

        if (option.isEnableController()) {
            fileGenerator.generateControllerFile(controllerModule, controller, po, request, response, service, option.isForceOverrideController());
        }







    }


}
