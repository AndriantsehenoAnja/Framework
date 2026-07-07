package utils;
import java.lang.reflect.Method;
public class ClassMethod {
    Class<?> clazz;
    Method method;

    public ClassMethod(Class<?> clazz, Method method) {
        this.clazz = clazz;
        this.method = method;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public Method getMethod() {
        return method;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    @Override
    public String toString() {
        return "ClassMethod{" +
                "clazz=" + clazz.getName() +
                ", method=" + method.getName() +
                '}';
    }
    public Object execute(){
        try{
            method.setAccessible(true);
            Object controllerInstance = clazz.getDeclaredConstructor().newInstance();
            Object meth = method.invoke(controllerInstance);
            System.out.println("Method executed: " + method.getName() + "()");
            return meth;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
