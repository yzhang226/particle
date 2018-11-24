package org.lightning.particle.core.template;

/**
 * Created by cook at 2018/7/9
 */
public interface TemplateParser {

    /**
     * 渲染模板
     * @param context
     * @return
     */
    String render(TemplateContext context);

}
