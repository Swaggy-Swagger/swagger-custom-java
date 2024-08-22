package io.swaggy.swagger.customlib.utils;

public class MethodPath {
    private String path;
    private String httpMethod;

    public MethodPath(String path, String httpMethod) {
        this.path = path;
        this.httpMethod = httpMethod;
    }

    public String getPath() {
        return path;
    }

    public String getHttpMethod() {
        return httpMethod;
    }
}
