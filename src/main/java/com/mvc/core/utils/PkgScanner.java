package com.mvc.core.utils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 我啊 on 2017-10-25 23:56
 */
public class PkgScanner {


    //项目根目录
    private static String rootPath;

    //存储扫描到的类Class文件
    private static List<Class> classPath;

    //需要扫描的注解
    private static Class<? extends Annotation> annotation;

    private PkgScanner() {
    }

    //初始化，获取项目根路径以及赋值需要扫描的注解

    @SafeVarargs
    private static void init(Class<? extends Annotation>... annotation) {
        PkgScanner.annotation = annotation.length==0 ? null : annotation[0];
        classPath = new ArrayList<>();
        try {
            String var1=PkgScanner.class.getResource("/").getPath();
            rootPath = URLDecoder.decode(var1, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param basePkg    扫描开始包
     * @param annotation 需要扫描的注解
     * @return 返回类的class集合
     */
    public static List<Class> getClassPath(String basePkg, Class<? extends Annotation> annotation) {
        init(annotation);
        String pkgPath = basePkg.replace('.', '/');
        getPath(rootPath + pkgPath);
        matcher();
        return classPath;
    }

    /**
     * @param basePkg 扫描开始包
     * @return 返回类的class集合
     */
    public static List<Class> getClassPath(String basePkg) {
        init();
        String pkgPath = basePkg.replace('.', '/');
        getPath(rootPath + pkgPath);
        matcher();
        return classPath;
    }

    /**
     * 只留下含有相匹配注解的class
     */
    private static void matcher(){
        if (annotation==null)
            return;
        classPath.removeIf(aClass -> aClass.getAnnotation(annotation) == null);
    }
    /**
     * 获取扫描目录以及子目录所有类并将其class文件加入classPath集合
     *
     * @param path 扫描基本包的绝对路径
     */
    private static void getPath(String path) {
        File file = new File(path);
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            assert files != null;
            for (File file1 : files) {
                getPath(file1.getPath());
            }
        } else {
            if (file.getName().endsWith(".class")) {
                String str = file.getPath().substring(rootPath.length() - 1, file.getPath().length() - 6).replace('\\', '.');
                try {
                    Class clazz = Class.forName(str);
                    classPath.add(clazz);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
