package org.lightning.particle.core.template;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

import java.util.Map;

/**
 * Created by cook at 2018/7/9
 */
public class STTemplateParser implements TemplateParser {

    @Override
    public String render(TemplateContext context) {
        STGroup stg = new STGroupFile(context.getTemplatePath());
        ST template = stg.getInstanceOf(context.getTemplateName());

        Map<String, Object> vars = context.getContext();
        vars.forEach(template::add);
        return template.render();
    }

}
