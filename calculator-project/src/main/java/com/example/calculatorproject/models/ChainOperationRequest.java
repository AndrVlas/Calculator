package com.example.calculatorproject.models;

import java.util.List;

import com.example.calculatorproject.enums.Operation;
import lombok.Data;

@Data
public class ChainOperationRequest {
    private double initialValue;
    private List<Chain> chains;

    @Data
    public static class Chain {
        private Operation operation;
        private double operand;
    }

}
