package utils;
import java.util.Objects;
public class UrlMethod {
    private String url;
    private String method;

    public UrlMethod(String url, String method) {
        this.url = url;
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public String getMethod() {
        return method;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    @Override
    public boolean equals(Object other) {
        UrlMethod that = (UrlMethod) other;
        return this.url.equals(that.url) && this.method.equals(that.method);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, method);
    }
}
