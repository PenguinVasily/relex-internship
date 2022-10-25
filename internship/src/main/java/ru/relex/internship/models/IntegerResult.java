package ru.relex.internship.models;

public class IntegerResult extends Result {
    private Integer result;

    public IntegerResult() {}

    public IntegerResult(Integer result) {
        this.result = result;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }
}
