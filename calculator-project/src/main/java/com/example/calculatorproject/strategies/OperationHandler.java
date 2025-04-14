package com.example.calculatorproject.strategies;

import com.example.calculatorproject.enums.Operation;

public interface OperationHandler {
    Operation getOperation();
    double applyOperation(double operand1, double operand2);
}
