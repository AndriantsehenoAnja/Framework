package utils;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
public class ControllerUtils {
    public static List<String> getControllers(String packageName) {
        List<Class<?>> classe = new ArrayList<>();

        List<Class<?>> classes = findClass(packageName,classe);
        List<String> controllerNames = new ArrayList<>();
        for (Class<?> clazz : classes) {
            if (clazz.isAnnotationPresent(mg.etu4370.annotation.Controller.class)) {
                controllerNames.add(clazz.getName());
            }
        }
        return controllerNames;
    }
    public static List<Class<?>> findClass(String packageName,List<Class<?>> classes){
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
                        }
                        else if(file.isDirectory()){
                            // classes.add(Class.forName(file.getName()));
                            findClass(packageName + "." + file.getName(),classes);
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
