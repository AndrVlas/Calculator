package com.example.calculatorproject.services;

import java.util.Map;

import com.example.calculatorproject.enums.Operation;
import com.example.calculatorproject.strategies.OperationHandler;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CalculatorChainService {

    private double currentValue;
    private final Map<Operation, OperationHandler> operationHandlerMap;

    public CalculatorChainService calculate(Operation operation, double operand) {
        OperationHandler handler = operationHandlerMap.get(operation);
        if (handler == null) {
            throw new UnsupportedOperationException("Operation " + operation + "is not supported");
        }

        currentValue = handler.applyOperation(currentValue, operand);
        return this;
    }

    public double getCurrentValue() {
        return currentValue;
    }
}
