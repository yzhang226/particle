package org.lightning.particle.plugin.javabean.meta;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.lightning.particle.core.model.BeanInfo;
import org.lightning.particle.core.jdbc.meta.Table;
import org.lightning.particle.jdbc.ds.DataSourceParam;
import org.lightning.particle.jdbc.meta.MetadataFetcher;
import org.lightning.particle.jdbc.meta.MetadataFetcherFacotry;
import org.lightning.particle.plugin.javabean.common.GenerateOption;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by cook on 2018/11/24
 */
public class JdbcJavaFileGenerator {

    private DataSourceParam param;

    private MetadataFetcher fetcher;

    private GenerateOption option;

    private JavaFileGenerator fileGenerator;

    private Map<String, String> packMappings;

    public JdbcJavaFileGenerator(DataSourceParam param, GenerateOption option, JavaFileGenerator fileGenerator) {
        this.param = param;
        this.fetcher = MetadataFetcherFacotry.createFetcher(param);
        this.option = option;
        this.fileGenerator = fileGenerator;

        if (CollectionUtils.isNotEmpty(option.databaseMappings)) {
            packMappings = option.databaseMappings.stream()
                    .map(StringUtils::trimToEmpty)
                    .filter(StringUtils::isNotEmpty)
                    .collect(Collectors.toMap(x -> x.split(":")[0], x -> x.split(":")[1]));
        } else {
            packMappings = Arrays.stream(param.getDatabaseMappings().split(","))
                    .map(StringUtils::trimToEmpty)
                    .filter(StringUtils::isNotEmpty)
                    .collect(Collectors.toMap(x -> x.split(":")[0], x -> x.split(":")[1]));
        }

        System.out.println("packMappings is " + packMappings);

        if (CollectionUtils.isEmpty(option.generateTableNames)
                && StringUtils.isNotEmpty(param.getTableNames())) {
            option.generateTableNames = Arrays.stream(param.getTableNames().split(","))
                    .map(StringUtils::trimToEmpty)
                    .filter(StringUtils::isNotEmpty)
                    .collect(Collectors.toList());
        }

        System.out.println("generateTableNames is " + option.generateTableNames);

    }

    private String getDbPackName() {
        if (MapUtils.isNotEmpty(packMappings) && packMappings.containsKey(param.getDatabaseName())) {
            return packMappings.get(param.getDatabaseName());
        }

        return param.getDatabaseName();
    }

    public void generateJavaFileSuites() throws IOException {
        for (String tableName : option.generateTableNames) {
            generateJavaFileSuite(tableName);
        }
    }

    private void cleanJavaFileSuiteCarefully(String tableName) throws IOException {
        Table table = fetcher.fetchTable(tableName);

        TableJavaConverter converter = new TableJavaConverter(option, table);

        String dbPackName = getDbPackName();

        // support
        MavenModule supportModule = new MavenModule(option.getRootArtifactId() + "-support", option.getBasePackageName() + ".support.model");
        String poPackage = supportModule.getBasePackage() + ".po." + dbPackName;
        BeanInfo po = converter.createPoInfo(poPackage);
        BeanInfo criteria = converter.createCriteriaInfo(poPackage);
        fileGenerator.deleteJavaFile(supportModule, po);
        fileGenerator.deleteJavaFile(supportModule, criteria);

        if (po.getPk().isMultiplePk()) {
            // generate pk
            fileGenerator.deleteJavaFile(supportModule, po.getPk());
        }

        // dto
        MavenModule dtoModule = new MavenModule(option.getRootArtifactId() + "-model", option.getBasePackageName() + ".model");
        String dtoPackage = dtoModule.getBasePackage() + ".dto." + dbPackName;
        BeanInfo request = converter.createRequestInfo(dtoPackage);
        BeanInfo response = converter.createResponseInfo(dtoPackage);

        fileGenerator.deleteJavaFile(dtoModule, request);
        fileGenerator.deleteJavaFile(dtoModule, response);

        // dao
        MavenModule daoModule = new MavenModule(option.getRootArtifactId() + "-dao", option.getBasePackageName() + ".dao");
        String daoPackage = daoModule.getBasePackage() + ".daos." + dbPackName;
        BeanInfo dao = converter.createDaoInfo(daoPackage, po, criteria);

        fileGenerator.deleteDaoFile(daoModule, dao);

        // service
        MavenModule serviceModule = new MavenModule(option.getRootArtifactId() + "-service", option.getBasePackageName() + ".service");
        String servicePackage = serviceModule.getBasePackage() + ".services." + dbPackName;
        BeanInfo service = converter.createServiceInfo(servicePackage, po, criteria, request, response, dao);

        fileGenerator.deleteServiceFile(serviceModule, service);

        // biz
        MavenModule bizModule = new MavenModule(option.getRootArtifactId() + "-service", option.getBasePackageName() + ".service");
        String bizPackage = serviceModule.getBasePackage() + ".bizs." + dbPackName;
        BeanInfo biz = converter.createBizInfo(bizPackage, po, criteria, request, response, dao);

        fileGenerator.deleteBizFile(bizModule, biz);

        // mapper xml
        fileGenerator.deleteBaseMapperFile(daoModule, po, dbPackName);

        fileGenerator.deleteEmptyMapperFile(daoModule, po, dbPackName);

        MavenModule controllerModule = new MavenModule(option.getRootArtifactId() + "-api", option.getBasePackageName() + ".api");
        String controllerPackage = controllerModule.getBasePackage() + ".controller." + dbPackName;
        BeanInfo controller = converter.createControllerInfo(controllerPackage);

        if (option.isEnableController()) {
            fileGenerator.deleteControllerFile(controllerModule, controller);
        }

    }

    private void generateJavaFileSuite(String tableName) throws IOException {
        //
        Table table = fetcher.fetchTable(tableName);

        TableJavaConverter converter = new TableJavaConverter(option, table);

        String dbPackName = getDbPackName();

        // support
        MavenModule supportModule = new MavenModule(option.getRootArtifactId() + "-support", option.getBasePackageName() + ".support.model");
        String poPackage = supportModule.getBasePackage() + ".po." + dbPackName;
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
        String dtoPackage = dtoModule.getBasePackage() + ".dto." + dbPackName;
        BeanInfo request = converter.createRequestInfo(dtoPackage);
        request.setEnableSwagger(true);
        BeanInfo response = converter.createResponseInfo(dtoPackage);
        response.setEnableSwagger(true);

        fileGenerator.generateJavaFile(dtoModule, request, option.isForceOverrideDtoRequest());
        fileGenerator.generateJavaFile(dtoModule, response, option.isForceOverrideDtoResponse());

        // dao
        MavenModule daoModule = new MavenModule(option.getRootArtifactId() + "-dao", option.getBasePackageName() + ".dao");
        String daoPackage = daoModule.getBasePackage() + ".daos." + dbPackName;
        BeanInfo dao = converter.createDaoInfo(daoPackage, po, criteria);

        fileGenerator.generateDaoFile(daoModule, po, criteria, dao, option.isForceOverrideDao());

        // service
        MavenModule serviceModule = new MavenModule(option.getRootArtifactId() + "-service", option.getBasePackageName() + ".service");
        String servicePackage = serviceModule.getBasePackage() + ".services." + dbPackName;
        BeanInfo service = converter.createServiceInfo(servicePackage, po, criteria, request, response, dao);

        fileGenerator.generateServiceFile(serviceModule, po, request, response, criteria, dao, service, option.isForceOverrideService());

        // biz
        MavenModule bizModule = new MavenModule(option.getRootArtifactId() + "-service", option.getBasePackageName() + ".service");
        String bizPackage = bizModule.getBasePackage() + ".bizs." + dbPackName;
        BeanInfo biz = converter.createBizInfo(bizPackage, po, criteria, request, response, dao);

        fileGenerator.generateBizFile(serviceModule, po, request, response, criteria, dao, service, biz, option.isForceOverrideBiz());

        // mapper xml
        fileGenerator.generateBaseMapperFile(daoModule, dao, po, criteria, dbPackName,
                table, option.isForceOverrideBaseMapper());

        fileGenerator.generateEmptyMapperFile(daoModule, dao, po, criteria, dbPackName,
                table, option.isForceOverrideMapper());

        MavenModule controllerModule = new MavenModule(option.getRootArtifactId() + "-api", option.getBasePackageName() + ".api");
        String controllerPackage = controllerModule.getBasePackage() + ".controller." + dbPackName;
        BeanInfo controller = converter.createControllerInfo(controllerPackage);

        if (option.isEnableController()) {
            fileGenerator.generateControllerFile(controllerModule, controller, po, request, response, biz, option.isForceOverrideController());
        }

    }

    public void cleanJavaFileSuitesCarefully() throws IOException {
        for (String tableName : option.generateTableNames) {
            cleanJavaFileSuiteCarefully(tableName);
        }
    }


}
