package com.example.springhomework2.entity;

import java.util.List;

public class MathObject {

    private List<Double> numbers;
    private String operationType;

    public MathObject(List<Double> numbers, String operationType) {
        this.numbers = numbers;
        this.operationType = operationType;
    }

    public List<Double> getNumbers() {
        return numbers;
    }

    public void setNumbers(List<Double> numbers) {
        this.numbers = numbers;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    @Override
    public String toString() {
        return numbers.get(0) + " " + operationType + " " + numbers.get(1);
    }
}

