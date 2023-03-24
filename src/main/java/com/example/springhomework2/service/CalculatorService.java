package com.example.springhomework2.service;

import com.example.springhomework2.entity.MathObject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CalculatorService {

    public List<String> performOperations(List<MathObject> mathObjects) throws InterruptedException {
        List<String> results = new ArrayList<>();
        for (MathObject mathObject : mathObjects) {     //trecem prin lista de perechi
            List<Double> numbers = mathObject.getNumbers();      //luam numelere
            String operationType = mathObject.getOperationType();   //luam operatia

            Double result = makeOperation(operationType, numbers);
            results.add(mathObject.toString() + " = " + result);
        }
        return results;    //returneaza lista de operatii formatate si efectuate
    }

    private Double makeOperation(String operationType, List<Double> numbers) throws InterruptedException {
        Thread.sleep(4000);
        switch (operationType) {
            case "+":
                return numbers.get(0) + numbers.get(1);
            case "-":
                return numbers.get(0) - numbers.get(1);
            case "*":
                return numbers.get(0) * numbers.get(1);
            case "/":
                return numbers.get(0) / numbers.get(1);
            default:
                System.err.println("Operator not supported");
                return null;
        }
    }
}
