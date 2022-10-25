package ru.relex.internship.models;

import java.util.List;

public class ListResult extends Result {
    private List result;

    public ListResult() {}

    public ListResult(List result) {
        this.result = result;
    }

    public List getResult() {
        return result;
    }

    public void setResult(List result) {
        this.result = result;
    }
}
