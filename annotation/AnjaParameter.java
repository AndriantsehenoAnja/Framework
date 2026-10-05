package mg.etu4370.annotation;
import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface AnjaParameter {
    String name();
}
// java 8
// tomcat 9
// oracle 11g
// ant
