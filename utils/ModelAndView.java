package utils;
import java.lang.reflect.Method;
import java.util.Map;

public class ModelAndView {

    String view;
    Map<String, Object> attributs;

    public ModelAndView(String view) {
        this.view = view;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public Map<String, Object> getAttributs() {
        return attributs;
    }

    public void setAttributs(Map<String, Object> attributs) {
        this.attributs = attributs;
    }
}
