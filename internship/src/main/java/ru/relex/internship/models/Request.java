package ru.relex.internship.models;

import java.io.Serializable;

public class Request implements Serializable {
    private String path;
    private String operation;

    public Request() {};

    public Request(String path, String operation) {
        this.path = path;
        this.operation = operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getOperation() {
        return operation;
    }

    public String getPath() {
        return path;
    }
}
