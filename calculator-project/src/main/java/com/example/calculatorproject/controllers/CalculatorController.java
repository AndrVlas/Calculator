package com.example.calculatorproject.controllers;

import com.example.calculatorproject.enums.Operation;
import com.example.calculatorproject.models.ChainOperationRequest;
import com.example.calculatorproject.models.OperationRequest;
import com.example.calculatorproject.services.CalculatorChainService;
import com.example.calculatorproject.services.CalculatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/calculator")
@RequiredArgsConstructor
public class CalculatorController {

    private final CalculatorService calculatorService;

    @PostMapping("/calculate")
    public ResponseEntity<Double> calculate(@RequestBody OperationRequest request) {
        if (request.getOperation() == null) {
            throw new UnsupportedOperationException("Operation is not supported");
        }
        if (request.getOperation().equals(Operation.DIVIDE) && request.getOperand2() == 0) {
            throw new ArithmeticException("Division by zero is not allowed");
        }
        double result = calculatorService.calculate(request.getOperation(), request.getOperand1(), request.getOperand2());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/chain")
    public ResponseEntity<Double> chain(@RequestBody ChainOperationRequest request) {
        CalculatorChainService chain = calculatorService.chain(request.getInitialValue());
        for (ChainOperationRequest.Chain step : request.getChains()) {
            if (step.getOperation() == null) {
                throw new UnsupportedOperationException("Operation is not supported");
            }
            if (step.getOperation().equals(Operation.DIVIDE) && step.getOperand() == 0) {
                throw new ArithmeticException("Division by zero is not allowed");
            }
            chain.calculate(step.getOperation(), step.getOperand());
        }
        return ResponseEntity.ok(chain.getCurrentValue());
    }
}
