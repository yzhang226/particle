package org.lightning.particle.core.template;

import org.apache.commons.lang3.StringUtils;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

import java.util.Map;

import static org.lightning.particle.core.common.Constants.TEMPLATE_NAME_KEY_DEFAULT;

/**
 * Created by cook at 2018/7/9
 */
public class STTemplateParser implements TemplateParser {

    @Override
    public String render(String templateFilePath, TemplateContext context) {
        String instanceName = context.popVar(TEMPLATE_NAME_KEY_DEFAULT);
        if (StringUtils.isEmpty(instanceName)) {
            throw new RuntimeException("必须填入默认st模板组的模板名称");
        }
        STGroup stg = new STGroupFile(templateFilePath);
        ST template = stg.getInstanceOf(instanceName);
        Map<String, Object> vars = context.getContext();
        vars.forEach(template::add);
        return template.render();
    }

}
