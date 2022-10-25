package ru.relex.internship.models;

public class FloatResult extends Result {
    private Float result;

    public FloatResult() {}

    public FloatResult(Float result) {
        this.result = result;
    }

    public Float getResult() {
        return result;
    }

    public void setResult(Float result) {
        this.result = result;
    }
}
