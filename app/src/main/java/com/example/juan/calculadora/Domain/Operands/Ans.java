package com.example.juan.calculadora.Domain.Operands;

import com.example.juan.calculadora.Domain.Calculator;

public class Ans extends MyNumber {
    public Ans() {
        num = Calculator.getLastResult();
    }
}
