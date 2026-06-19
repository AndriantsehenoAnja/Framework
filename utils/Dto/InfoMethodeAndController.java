package utils.Dto;

public class InfoMethodeAndController {
    private String url;
    private String methodName;
    private String controllerName;

    public InfoMethodeAndController(String url, String methodName, String controllerName) {
        this.url = url;
        this.methodName = methodName;
        this.controllerName = controllerName;
    }

    public String getUrl() {
        return url;
    }

    public String getMethodName() {
        return methodName;
    }

    public String getControllerName() {
        return controllerName;
    }

    @Override
    public String toString(){
        return "URL: " + url + ", Controller: " + controllerName + ", Method: " + methodName;
    }
    // public void
}
