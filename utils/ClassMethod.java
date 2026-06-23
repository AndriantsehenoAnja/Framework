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
}
