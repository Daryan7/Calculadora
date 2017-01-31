package com.example.juan.myapp.Domain.Operands;

import com.example.juan.myapp.Domain.Calculator;

public class Ans extends MyNumber {
    public Ans() {
        num = Calculator.getLastResult();
    }
}
