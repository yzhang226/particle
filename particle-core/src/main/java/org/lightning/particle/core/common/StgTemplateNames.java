package org.lightning.particle.core.common;

/**
 * Created by cook at 2018/7/9
 */
public interface StgTemplateNames {

    /**
     * st-模板组的默认执行的模板名
     */
    String KEY_TEMPLATE_NAME = "__default_template_name__";

//    /**
//     *
//     */
//    String TEMPLATE_NAME_JAVA_BEAN = "beanTemplate";

//    /**
//     * java-bean之st模板路径
//     */
//    String TEMPLATE_PATH_JAVA_BEAN = "st/java-bean.stg";

    interface JavaBean {
        String TEMPLATE_PATH = "st/java-bean.stg";
        String TEMPLATE_NAME = "javaBean";
    }

    interface JavaController {
        String TEMPLATE_PATH = "st/java-controller.stg";
        String TEMPLATE_NAME = "javaController";
    }

    interface BaseMapper {
        String TEMPLATE_PATH = "st/base-mapper.stg";
        String TEMPLATE_NAME = "baseMapper";
    }

    interface EmptyMapper {
        String TEMPLATE_PATH = "st/empty-mapper.stg";
        String TEMPLATE_NAME = "emptyMapper";
    }


}
