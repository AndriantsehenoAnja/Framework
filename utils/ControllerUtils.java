package utils;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import mg.etu4370.annotation.UrlMapping;
import utils.Dto.InfoMethodeAndController;
public class ControllerUtils {

    public static boolean isAnnotationMethod(Method m) {
        if (m.isAnnotationPresent(UrlMapping.class)) {
            return true;
        } else {
            return false;
        }
    }

    public static List<Method> findMethod(Class<?> clazz) {
        List<Method> methods = new ArrayList<>();
        for (Method method : clazz.getDeclaredMethods()) {
            if (isAnnotationMethod(method)) {
                methods.add(method);
            }
        }
        return methods;
    }
    public static List<InfoMethodeAndController> findAllMethodes(String packageName){
        
        List<InfoMethodeAndController> infoMethodeAndControllers = new ArrayList<>();
        List<Class<?>> controllerClasses = getControllers(packageName);
        for (Class<?> controllerClass : controllerClasses) {
            List<Method> methods = findMethod(controllerClass);
            for (Method method : methods) {
                UrlMapping urlMapping = method.getAnnotation(UrlMapping.class);
                String url = urlMapping.value();
                String methodName = method.getName();
                String controllerName = controllerClass.getSimpleName();
                InfoMethodeAndController infoMethodeAndController = new InfoMethodeAndController(url, methodName, controllerName);
                infoMethodeAndControllers.add(infoMethodeAndController);
            }
        }
        return infoMethodeAndControllers;
    }
    public static InfoMethodeAndController findClassByUrl(List<InfoMethodeAndController> infoMethodeAndControllers, String url) {
        for (InfoMethodeAndController infoMethodeAndController : infoMethodeAndControllers) {
            if(infoMethodeAndController.getUrl().equals(url)){
                return infoMethodeAndController;
            }
        }
        return null;
    }

    // getContextPath
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
                            String className = packageName + '.' + file.getName().substring(0, file.getName().length() - 6);
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
