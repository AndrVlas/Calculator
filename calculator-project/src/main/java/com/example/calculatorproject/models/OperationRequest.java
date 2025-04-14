package com.example.calculatorproject.models;

import com.example.calculatorproject.enums.Operation;
import lombok.Data;

@Data
public class OperationRequest {
    private Operation operation;
    private double operand1;
    private double operand2;
}
