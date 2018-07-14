package org.lightning.particle.core.template;

import static org.lightning.particle.core.common.Constants.TEMPLATE_NAME_KEY_DEFAULT;

/**
 * Created by cook at 2018/7/9
 */

public class STBeanTemplateContext extends BeanTemplateContext {

    public void setTemplateName(String templateName) {
        super.addScopedVar(TEMPLATE_NAME_KEY_DEFAULT, templateName);
    }

}
