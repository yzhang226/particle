package org.lightning.particle.plugin.javabean.utils;

/**
 * Created by cook on 2018/12/21
 */
public abstract class PathUtils {

    public static String getTopDir(Class<?> clazz, String currentArtifactId, String resourcePath) {
        String propPath = clazz.getClassLoader().getResource(resourcePath).getPath();
        System.out.println("propPath is " + propPath);

        String topDir = propPath.substring(0, propPath.indexOf("/" + currentArtifactId));
        System.out.println("topDir is " + topDir);

        return topDir;
    }


}
