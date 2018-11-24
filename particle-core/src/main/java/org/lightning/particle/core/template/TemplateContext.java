package org.lightning.particle.core.template;

import com.google.common.collect.Maps;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * Created by cook at 2018/7/9
 */

@Getter
@Setter
public class TemplateContext {

    private String templatePath;

    private String templateName;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private Map<String, Object> context;

    public TemplateContext(String templatePath, String templateName) {
        this.templatePath = templatePath;
        this.templateName = templateName;
        this.context = Maps.newHashMap();
    }

    /**
     *
     * @param key
     * @param obj
     */
    public void addScopedVar(String key, Object obj) {
        context.put(key, obj);
    }

    /**
     *
     * @param vars
     */
    public void addScopedVars(Map<String, Object> vars) {
        context.putAll(vars);
    }

    /**
     *
     * @param key
     * @param <T>
     * @return
     */
    public <T> T getVar(String key) {
        return (T) context.get(key);
    }

    /**
     *
     * @param key
     * @param <T>
     * @return
     */
    public <T> T popVar(String key) {
        return (T) context.remove(key);
    }

    /**
     *
     * @return
     */
    public Map<String, Object> getContext() {
        return Maps.newHashMap(context);
    }

}
