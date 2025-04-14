package com.example.calculatorproject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.calculatorproject.controllers.CalculatorController;
import com.example.calculatorproject.enums.Operation;
import com.example.calculatorproject.models.ChainOperationRequest;
import com.example.calculatorproject.models.OperationRequest;
import com.example.calculatorproject.services.CalculatorService;
import com.example.calculatorproject.strategies.OperationHandler;
import com.example.calculatorproject.strategies.impl.AddOperation;
import com.example.calculatorproject.strategies.impl.MultiplyOperation;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CalculatorController.class)
class CalculatorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CalculatorService calculatorService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testAddCalculation() throws Exception {
        OperationRequest request = new OperationRequest();
        request.setOperation(Operation.ADD);
        request.setOperand1(4);
        request.setOperand2(5);

        Mockito.when(calculatorService.calculate(eq(Operation.ADD), eq(4.0), eq(5.0))).thenReturn(9.0);

        mockMvc.perform(post("/api/v1/calculator/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("9.0"));
    }

    @Test
    public void testMultiplyCalculation() throws Exception {
        OperationRequest request = new OperationRequest();
        request.setOperation(Operation.MULTIPLY);
        request.setOperand1(4);
        request.setOperand2(5);

        Mockito.when(calculatorService.calculate(eq(Operation.MULTIPLY), eq(4.0), eq(5.0))).thenReturn(20.0);

        mockMvc.perform(post("/api/v1/calculator/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("20.0"));
    }

    @Test
    public void testChainCalculator() throws Exception {
        ChainOperationRequest.Chain operation1 = new ChainOperationRequest.Chain();
        operation1.setOperation(Operation.ADD);
        operation1.setOperand(6);

        ChainOperationRequest.Chain operation2 = new ChainOperationRequest.Chain();
        operation2.setOperation(Operation.MULTIPLY);
        operation2.setOperand(4);

        ChainOperationRequest request = new ChainOperationRequest();
        request.setInitialValue(10);
        request.setChains(List.of(operation1, operation2));

        List<OperationHandler> list = new ArrayList<>();
        list.add(new AddOperation());
        list.add(new MultiplyOperation());
        calculatorService = new CalculatorService(list);

//        Mockito.when(mockMap.get(Operation.ADD)).thenReturn(mockMap.);
//        Mockito.when(mockMap.get(Operation.MULTIPLY)).thenReturn(new MultiplyOperation());


//        Mockito.when(calculatorService.chain(10)).thenCallRealMethod();

        Mockito.when(calculatorService.chain(10)
                .calculate(Operation.ADD, 6)
                .calculate(Operation.MULTIPLY, 4)
                .getCurrentValue()).thenReturn(64.0);

        mockMvc.perform(post("/api/v1/calculator/chain")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("64.0"));
    }

    @Test
    public void testDivisionByZero() throws Exception {
        OperationRequest request = new OperationRequest();
        request.setOperation(Operation.DIVIDE);
        request.setOperand1(2);
        request.setOperand2(0);

        Mockito.when(calculatorService.calculate(Operation.DIVIDE, 2, 0)).thenThrow(new ArithmeticException("Division by zero is not allowed"));

        mockMvc.perform(post("/api/v1/calculator/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Division by zero is not allowed"));
    }

    @Test
    public void testUnsupportedOperation() throws Exception {
        OperationRequest request = new OperationRequest();
        request.setOperation(null);
        request.setOperand1(2);
        request.setOperand2(4);

        Mockito.when(calculatorService.calculate(null, 2, 4)).thenThrow(new UnsupportedOperationException("Operation is not supported"));

        mockMvc.perform(post("/api/v1/calculator/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotImplemented())
                .andExpect(content().string("Operation is not supported"));
    }

}
