package org.lightning.particle.plugin.javabean.utils;

import org.apache.commons.lang3.SystemUtils;

/**
 * Created by cook on 2018/12/21
 */
public abstract class PathUtils {

    public static String getTopDir(Class<?> clazz, String currentArtifactId, String resourcePath) {
        String propPath = clazz.getClassLoader().getResource(resourcePath).getPath();

        propPath = trimPath(propPath);

        System.out.println("propPath is " + propPath);

        String topDir = propPath.substring(0, propPath.indexOf("/" + currentArtifactId));
        System.out.println("topDir is " + topDir);

        return topDir;
    }

    private static String trimPath(String propPath) {
        if (SystemUtils.IS_OS_WINDOWS) {
            int idx2 = propPath.indexOf('/', 1);
            String diskChar = propPath.substring(1, idx2);
            if (diskChar.contains(":")) {
                propPath = propPath.substring(1);
            }
        }
        return propPath;
    }


}
