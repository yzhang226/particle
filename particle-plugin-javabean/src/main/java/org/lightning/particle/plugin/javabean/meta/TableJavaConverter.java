package org.lightning.particle.plugin.javabean.meta;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.lightning.particle.core.model.BeanInfo;
import org.lightning.particle.core.model.BeanMethod;
import org.lightning.particle.core.model.BeanProperty;
import org.lightning.particle.core.model.MethodParameter;
import org.lightning.particle.core.utils.CamelCaseUtils;
import org.lightning.particle.core.jdbc.meta.Column;
import org.lightning.particle.core.jdbc.meta.Table;
import org.lightning.particle.plugin.javabean.common.GenerateOption;
import org.lightning.particle.plugin.javabean.common.JavaJdbcTypeMappings;

import java.util.List;

/**
 * Created by cook on 2018/11/24
 */
public class TableJavaConverter {

    private GenerateOption option;

    private Table table;

    public TableJavaConverter(GenerateOption option, Table table) {
        this.option = option;
        this.table = table;
    }

    public BeanInfo createPoInfo(String packageName) {
        BeanInfo bean = new BeanInfo();
        bean.setBeanName(poName());
        bean.setPackageName(packageName);
        bean.setComment(table.getRemarks());

        List<BeanProperty> properties = buildBeanProperties(table, bean);
        bean.setProperties(properties);

        bean.addRequiredClassName("com.fxtech.panda.core.base.BaseBean");
        bean.setExtendClassName("BaseBean");
        bean.setEnableGetterSetter(true);
        bean.setEnableLombok(true);

        postProcess(bean);

        return bean;
    }

    public BeanInfo createCriteriaInfo(String packageName) {
        BeanInfo bean = new BeanInfo();
        bean.setBeanName(criteriaName());
        bean.setPackageName(packageName);
        bean.setComment(table.getRemarks());

        List<BeanProperty> properties = buildBeanProperties(table, bean);
        bean.setProperties(properties);

        bean.addRequiredClassName("com.fxtech.panda.core.base.BaseCriteria");
        bean.setExtendClassName("BaseCriteria");
        bean.setEnableGetterSetter(true);
        bean.setEnableLombok(true);

        postProcess(bean);

        return bean;
    }


    public BeanInfo createRequestInfo(String packageName) {
        BeanInfo bean = new BeanInfo();
        bean.setBeanName(requestName());
        bean.setPackageName(packageName);
        bean.setComment(table.getRemarks());

        List<BeanProperty> properties = buildBeanProperties(table, bean);
        bean.setProperties(properties);

        bean.addRequiredClassName("com.fxtech.panda.core.base.BaseRequest");
        bean.setExtendClassName("BaseRequest");
        bean.setEnableGetterSetter(true);
        bean.setEnableLombok(true);

        postProcess(bean);

        return bean;
    }

    public BeanInfo createResponseInfo(String packageName) {
        BeanInfo bean = new BeanInfo();
        bean.setBeanName(responseName());
        bean.setPackageName(packageName);
        bean.setComment(table.getRemarks());

        List<BeanProperty> properties = buildBeanProperties(table, bean);
        bean.setProperties(properties);

        bean.addRequiredClassName("com.fxtech.panda.core.base.BaseResponse");
        bean.setExtendClassName("BaseResponse");
        bean.setEnableGetterSetter(true);
        bean.setEnableLombok(true);

        postProcess(bean);

        return bean;
    }

    public BeanInfo createDaoInfo(String packageName, BeanInfo po, BeanInfo criteria) {
        BeanInfo bean = new BeanInfo();
        bean.setBeanName(daoName());
        bean.setPackageName(packageName);
        bean.setComment(table.getRemarks());

        bean.addRequiredClassName("com.fxtech.panda.dao.capacity.CrudDao");
        bean.setExtendClassName("CrudDao");

        bean.addRequiredClassName(po.getFullBeanName());
        bean.addRequiredClassName(criteria.getFullBeanName());
        bean.addRequiredClassName("com.fxtech.panda.dao.common.Dao");
        bean.setAnnotationNames(Lists.newArrayList("Dao"));

        bean.setExtendClassGenericTypes(Lists.newArrayList(pkClassType().getSimpleName(), poName(), criteriaName()));

        bean.addModifier("public");
        bean.addModifier("interface");

        postProcess(bean);

        return bean;
    }

    public BeanInfo createServiceInfo(String packageName, BeanInfo po, BeanInfo criteria,
                                      BeanInfo request, BeanInfo response, BeanInfo dao) {
        BeanInfo bean = new BeanInfo();
        bean.setBeanName(serviceName());
        bean.setPackageName(packageName);
        bean.setComment(table.getRemarks());

        String daoPropertyName = StringUtils.uncapitalize(daoName());
        BeanProperty property = new BeanProperty();
        property.setComment(getEntityName() + "'s dao");
        property.setPropertyName(daoPropertyName);
        property.setPropertyTypeName(daoName());
        property.setPropertyTypeClass("");
        property.addAnnotationName("Autowired");

        bean.addProperty(property);

        BeanMethod crudMethod = new BeanMethod();
        crudMethod.setMethodName("getCrudDao");
        crudMethod.setModifiers(Lists.newArrayList("public"));
        crudMethod.setReturnTypeName(dao.getBeanName());
        crudMethod.setMethodContent("return " + daoPropertyName + ";");
        bean.addMethod(crudMethod);

        BeanMethod saveMethod = new BeanMethod();

        MethodParameter parameter = new MethodParameter();
        parameter.setParameterName("request");
        parameter.setParameterType(request.getBeanName());
        saveMethod.setParameters(Lists.newArrayList(parameter));

        saveMethod.setMethodName("save");
        saveMethod.setModifiers(Lists.newArrayList("public"));
        saveMethod.setReturnTypeName(po.getPkProperty().getPropertyTypeName());

        String content = "{poName} bean = BeanUtils.copy(request, {poName}.class);\n" +
                "saveBean(bean);\n" +
                "request.setId(bean.get{pkNameUp}());\n" +
                "return bean.get{pkNameUp}();";
        content = content.replaceAll("\\{poName}", poName())
                .replaceAll("\\{pkNameUp}", StringUtils.capitalize(po.getPkProperty().getPropertyName()))
                ;
        saveMethod.setMethodContent(content);
        String comment = "/**\n" +
                "* 保存<code>request</code>\n" +
                "* @param request\n" +
                "* @return 新增的数据的行主键(如果主键是自增)\n" +
                "*/";
        saveMethod.setComment(comment);
        bean.addMethod(saveMethod);

        bean.addRequiredClassName(po.getFullBeanName());
        bean.addRequiredClassName(criteria.getFullBeanName());
        bean.addRequiredClassName(request.getFullBeanName());
        bean.addRequiredClassName(response.getFullBeanName());
        bean.addRequiredClassName(dao.getFullBeanName());
        bean.addRequiredClassName("org.springframework.beans.factory.annotation.Autowired");
        bean.addRequiredClassName("com.fxtech.panda.dao.capacity.BaseBizService");
        bean.addRequiredClassName("com.fxtech.panda.core.reflect.BeanUtils");

        bean.setExtendClassName("BaseBizService");

        bean.addRequiredClassName("org.springframework.stereotype.Service");
        bean.setAnnotationNames(Lists.newArrayList("Service"));

        bean.setExtendClassGenericTypes(Lists.newArrayList(pkClassType().getSimpleName(),
                poName(), criteriaName(), requestName(), responseName()));

        postProcess(bean);

        return bean;
    }

    public BeanInfo createControllerInfo(String packageName) {
        BeanInfo bean = new BeanInfo();
        bean.setBeanName(controllerName());
        bean.setPackageName(packageName);
        bean.setComment(table.getRemarks());

        List<BeanProperty> properties = buildBeanProperties(table, bean);
        bean.setProperties(properties);

        postProcess(bean);

        return bean;
    }

    private void postProcess(BeanInfo beanInfo) {
        beanInfo.setTableName(table.getTableName());
        beanInfo.setEntityName(getEntityName());
    }


    private String getEntityName() {
        String tName = table.getTableName();
        for (String removePrefix : option.getRemovePrefixes()) {
            if (tName.startsWith(removePrefix)) {
                tName = tName.substring(removePrefix.length());
                break;
            }
        }
        return CamelCaseUtils.toUpCamelCase(tName);
    }


    private List<BeanProperty> buildBeanProperties(Table table, BeanInfo bean) {
        List<BeanProperty> properties = Lists.newArrayList();
        for (Column column : table.getColumns()) {
            BeanProperty property = new BeanProperty();
            property.setComment(column.getRemarks());
            property.setPropertyName(CamelCaseUtils.toCamelCase(column.getColumnName()));
            property.setDefaultValue(column.getColumnDefault());

            Class<?> colType = JavaJdbcTypeMappings.guessJavaType(column, false);

            property.setPropertyTypeName(colType.getSimpleName());
            property.setPropertyTypeClass(colType.getName());

            property.setJdbcTypeName(JavaJdbcTypeMappings.guessJdbcTypeName(column));
            property.setColumn(column);

            if (column.isPrimaryKey() && bean.getPkProperty() == null) {
                bean.setPkProperty(property);
            }

            properties.add(property);

            if (!colType.isPrimitive() && !colType.getName().startsWith("java.lang.")) {
                bean.addRequiredClassName(colType.getName());
            }
        }
        return properties;
    }

    private String poName() {
        return getEntityName() + "Bean";
    }

    private String criteriaName() {
        return getEntityName() + "Criteria";
    }

    private String requestName() {
        return getEntityName() + "Request";
    }

    private String responseName() {
        return getEntityName() + "Response";
    }

    private String daoName() {
        return getEntityName() + "Dao";
    }

    private String serviceName() {
        return getEntityName() + "Service";
    }

    private String controllerName() {
        return getEntityName() + "Controller";
    }

    private Class<?> pkClassType() {
        Column pkColumn = table.getPkColumn();
        return JavaJdbcTypeMappings.guessJavaType(pkColumn, false);
    }




}
