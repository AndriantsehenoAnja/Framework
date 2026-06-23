package utils;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mg.etu4370.annotation.UrlMapping;

public class ControllerUtils {
    public static boolean isAnnotationMethod(Method m) {
        if (m.isAnnotationPresent(UrlMapping.class)) {
            return true;
        } else {
            return false;
        }
    }

    public static Map<UrlMethod, ClassMethod> findAllMethodesWithUrlMethod(String packageName) {
        Map<UrlMethod, ClassMethod> map = new HashMap<>();
        List<Class<?>> controllerClasses = getControllers(packageName);
        for (Class<?> controllerClass : controllerClasses) {
            for (Method method : controllerClass.getDeclaredMethods()) {
                if (isAnnotationMethod(method)) {
                    UrlMapping urlMapping = method.getAnnotation(UrlMapping.class);
                    String url = urlMapping.value();
                    String httpMethod = urlMapping.method();
                    UrlMethod urlMethod = new UrlMethod(url, httpMethod);
                    ClassMethod classMethod = new ClassMethod(controllerClass, method);
                    map.put(urlMethod, classMethod);
                }
            }
        }
        return map;
    }

    public static ClassMethod findClassByUrlMethod(Map<UrlMethod, ClassMethod> map, String url, String httpMethod) {
        return map.get(new UrlMethod(url, httpMethod));
    }
    // public static Map<String, ClassMethod> findAllMethodes(String packageName) {
    //     Map<String, ClassMethod> map = new HashMap<>();
    //     List<Class<?>> controllerClasses = getControllers(packageName);
    //     for (Class<?> controllerClass : controllerClasses) {
    //         for (Method method : controllerClass.getDeclaredMethods()) {
    //             if (isAnnotationMethod(method)) {
    //                 UrlMapping urlMapping = method.getAnnotation(UrlMapping.class);
    //                 String url = urlMapping.value();
    //                 ClassMethod classMethod = new ClassMethod(controllerClass, method);
    //                 map.put(url, classMethod);
    //             }
    //         }
    //     }
    //     return map;
    // }

    // public static ClassMethod findClassByUrl(Map<String, ClassMethod> map, String url) {
    //     return map.get(url);
    // }

    public static List<Class<?>> getControllers(String packageName) {
        List<Class<?>> classe = new ArrayList<>();

        List<Class<?>> classes = findClass(packageName, classe);
        List<Class<?>> controllerNames = new ArrayList<>();
        for (Class<?> clazz : classes) {
            if (clazz.isAnnotationPresent(mg.etu4370.annotation.Controller.class)) {
                controllerNames.add(clazz);
            }
        }
        return controllerNames;
    }

    public static List<Class<?>> findClass(String packageName, List<Class<?>> classes) {
        String packagePath = packageName.replace('.', '/');
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            java.net.URL packageURL = classLoader.getResource(packagePath);
            if (packageURL == null) {
                throw new RuntimeException("Package not found: " + packageName);
            }
            java.io.File directory = new java.io.File(packageURL.toURI());
            if (directory.exists() && directory.isDirectory()) {
                File[] files = directory.listFiles();
                if (files != null) {
                    for (File file : files) {
                        if (file.getName().endsWith(".class")) {
                            String className = packageName + '.'
                                    + file.getName().substring(0, file.getName().length() - 6);
                            classes.add(Class.forName(className));
                        } else if (file.isDirectory()) {
                            // classes.add(Class.forName(file.getName()));
                            findClass(packageName + "." + file.getName(), classes);
                        }
                    }
                } else {
                    throw new RuntimeException("No classes found in package: " + packageName);
                }
            } else {
                throw new RuntimeException("Directory not found for package: " + packageName);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error loading classes from package: " + packageName, e);
        }
        return classes;
    }
}
