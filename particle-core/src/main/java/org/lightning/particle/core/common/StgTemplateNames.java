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

    interface SpringDao {
        String TEMPLATE_PATH = "st/spring-dao.stg";
        String TEMPLATE_NAME = "springDao";
    }

    interface SpringService {
        String TEMPLATE_PATH = "st/spring-service.stg";
        String TEMPLATE_NAME = "springService";
    }

    interface SpringBiz {
        String TEMPLATE_PATH = "st/spring-biz.stg";
        String TEMPLATE_NAME = "springBiz";
    }

    interface JavaController {
        String TEMPLATE_PATH = "st/spring-controller.stg";
        String TEMPLATE_NAME = "springController";
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
