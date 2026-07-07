package utils;

import java.io.File;
import java.lang.reflect.Method;
import jakarta.servlet.http.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import mg.etu4370.annotation.UrlMapping;
import mg.etu4370.utils.ModelAndView;

public class ControllerUtils {

    public static void execute(Object method,String pathSource,String extension,HttpServletRequest request, HttpServletResponse response)
        {
        if(method instanceof ModelAndView){
            String view = pathSource + ((ModelAndView) method).getView() + extension;
            ModelAndView modelAndView = (ModelAndView) method;
            try {
                for(Map.Entry<String, Object> entry : modelAndView.getAttributs().entrySet()) {
                    request.setAttribute(entry.getKey(), entry.getValue());
                    System.out.println("Attribute added: " + entry.getKey() + " = " + entry.getValue());
                }
                request.getRequestDispatcher(view).forward(request, response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public static boolean isAnnotationMethod(Method m) {
        if (m.isAnnotationPresent(UrlMapping.class)) {
            return true;
        } else {
            return false;
        }
    }

    public static void findAllMethodesWithUrlMethod(String packageName,Map<UrlMethod, ClassMethod> map)throws RuntimeException {
        List<Class<?>> controllerClasses = getControllers(packageName);
        for (Class<?> controllerClass : controllerClasses) {
            for (Method method : controllerClass.getDeclaredMethods()) {
                if (isAnnotationMethod(method)) {
                    UrlMapping urlMapping = method.getAnnotation(UrlMapping.class);
                    String url = urlMapping.value();
                    String httpMethod = urlMapping.method();
                    UrlMethod urlMethod = new UrlMethod(url, httpMethod);
                    if (map.containsKey(urlMethod)) {
                        throw new RuntimeException("Duplicate mapping for URL: " + url + " and HTTP method: " + httpMethod);
                    }
                    ClassMethod classMethod = new ClassMethod(controllerClass, method);
                    map.put(urlMethod, classMethod);
                }
            }
        }
    }

    public static ClassMethod findClassByUrlMethod(Map<UrlMethod, ClassMethod> map, String url, String httpMethod) {
        return map.get(new UrlMethod(url, httpMethod));
    }

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
