package org.lightning.particle.plugin.javabean.meta;

import com.google.common.collect.Lists;
import org.lightning.particle.core.model.BeanInfo;
import org.lightning.particle.core.model.BeanProperty;
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

        postProcess(bean);

        return bean;
    }

    public BeanInfo createServiceInfo(String packageName, BeanInfo po, BeanInfo criteria,
                                      BeanInfo request, BeanInfo response, BeanInfo dao) {
        BeanInfo bean = new BeanInfo();
        bean.setBeanName(serviceName());
        bean.setPackageName(packageName);
        bean.setComment(table.getRemarks());

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

        bean.getPk().getProperties().forEach(pkProp -> {
            bean.addRequiredClassName(pkProp.getPropertyTypeClass());
        });

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
        List<BeanProperty> pkProps = Lists.newArrayList();
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

            if (column.isPrimaryKey()) {
//                bean.setPkProperty(property);
                pkProps.add(property);
            }

            properties.add(property);

            if (!colType.isPrimitive() && !colType.getName().startsWith("java.lang.")) {
                bean.addRequiredClassName(colType.getName());
            }
        }

        BeanInfo pkBean = new BeanInfo();
        pkBean.setProperties(pkProps);

        if (pkProps.size() == 1) {
            BeanProperty prop = pkProps.get(0);
            String pack = prop.getPropertyTypeClass();
            pack = pack.substring(0, pack.lastIndexOf("."));
            pkBean.setBeanName(prop.getPropertyTypeName());
            pkBean.setPackageName(pack);
        } else {
            // generate pk-bean
            pkBean.setBeanName(bean.getBeanName() + "Pk");
            pkBean.setPackageName(bean.getPackageName());
        }

        pkProps.forEach(pkProp -> {
            pkBean.addRequiredClassName(pkProp.getPropertyTypeClass());
        });

        pkBean.addRequiredClassName("com.fxtech.panda.core.base.BasePk");
        pkBean.setExtendClassName("BasePk");
        pkBean.setEnableGetterSetter(true);
        pkBean.setEnableLombok(true);

        bean.setPk(pkBean);

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

//    private Class<?> pkClassType() {
//        Column pkColumn = table.getPkColumn();
//        return JavaJdbcTypeMappings.guessJavaType(pkColumn, false);
//    }




}
