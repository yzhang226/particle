package org.lightning.particle.core.template;

import org.lightning.particle.core.common.GenerateOptions;
import org.lightning.particle.core.model.BeanInfo;

/**
 * Created by cook at 2018/7/14
 */
public class BeanTemplateContext extends TemplateContext {

    public void setBeanInfo(BeanInfo beanInfo) {
        super.addScopedVar("bean", beanInfo);
    }

    public void setGenerateOptions(GenerateOptions options) {
        super.addScopedVar("options", options);
    }

}
