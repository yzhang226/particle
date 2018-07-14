package org.lightning.particle.core.model;

import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by cook at 2018/7/14
 */
@Getter
@Setter
public class MethodParameter {

    /**
     *
     */
    private String parameterName;

    /**
     * 参数类型
     */
    private String parameterType;

    /**
     * 参数泛型(s), example - < Integer, Map< String, Integer > ==> ("Integer", "Map< String, Integer>");
     */
    private List<String> genericTypes = Lists.newArrayList();;

}
