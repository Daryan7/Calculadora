package com.example.juan.theapp.Domain.Operands;

import com.example.juan.theapp.Domain.Calculator;

public class Ans extends MyNumber {
    public Ans() {
        num = Calculator.getLastResult();
    }
}
