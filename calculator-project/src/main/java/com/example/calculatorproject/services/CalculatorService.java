package com.example.calculatorproject.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.calculatorproject.enums.Operation;
import com.example.calculatorproject.strategies.OperationHandler;
import org.springframework.stereotype.Service;

@Service
public class CalculatorService {

    private final Map<Operation, OperationHandler> operationHandlerMap = new HashMap<>();

    public CalculatorService(List<OperationHandler> handlers) {
        for (OperationHandler handler : handlers) {
            operationHandlerMap.put(handler.getOperation(), handler);
        }
    }

    public double calculate(Operation operation, double operand1, double operand2) {
        OperationHandler handler = operationHandlerMap.get(operation);
        if (handler == null) {
            throw new UnsupportedOperationException("Operation " + operation + "is not supported");
        }
        return handler.applyOperation(operand1, operand2);
    }

    public CalculatorChainService chain(double startValue) {
        return new CalculatorChainService(startValue, operationHandlerMap);
    }
}
