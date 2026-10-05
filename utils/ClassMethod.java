package utils;

import java.lang.reflect.Method;
import jakarta.servlet.http.HttpServletRequest;

public class ClassMethod {
    private Method method;
    private Object controllerInstance;

    // Constructeur au démarrage (SANS HttpServletRequest)
    public ClassMethod(Object controllerInstance, Method method) {
        this.controllerInstance = controllerInstance;
        this.method = method;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Object getControllerInstance() {
        return controllerInstance;
    }

    public void setControllerInstance(Object controllerInstance) {
        this.controllerInstance = controllerInstance;
    }

    /**
     * Exécute la méthode du contrôleur en résolvant les paramètres 
     * à partir de la requête HTTP passée en argument.
     */
    public Object execute(HttpServletRequest request) {
        try {
            method.setAccessible(true);
            
            // Résolution des arguments dynamiques issus de la requête HTTP
            Object[] args = ParameterResolver.resolveParameters(method, request);

            // Invocations de la méthode avec ses arguments
            Object result = method.invoke(controllerInstance, args);
            System.out.println("Method executed: " + method.getName() + "()");
            return result;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}