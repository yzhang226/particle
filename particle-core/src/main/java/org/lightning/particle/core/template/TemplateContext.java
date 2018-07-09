package org.lightning.particle.core.template;

import com.google.common.collect.Maps;
import org.apache.commons.collections4.MapUtils;

import java.util.Collections;
import java.util.Map;

/**
 * Created by cook at 2018/7/9
 */

public class TemplateContext {

    private Map<String, Object> context;

    public TemplateContext() {
        this(null);
    }

    public TemplateContext(Map<String, Object> context) {
        this.context = MapUtils.isEmpty(context) ? Maps.newHashMap() : Maps.newHashMap(context);
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
        return MapUtils.isEmpty(context) ? Collections.emptyMap() : Maps.newHashMap(context);
    }

}
