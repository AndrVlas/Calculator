package com.example.calculatorproject.strategies.impl;

import com.example.calculatorproject.enums.Operation;
import com.example.calculatorproject.strategies.OperationHandler;
import org.springframework.stereotype.Component;

@Component
public class MultiplyOperation implements OperationHandler {
    @Override
    public Operation getOperation() {
        return Operation.MULTIPLY;
    }

    @Override
    public double applyOperation(double operand1, double operand2) {
        return operand1 * operand2;
    }
}
