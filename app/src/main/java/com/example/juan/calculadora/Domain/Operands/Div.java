package com.example.juan.calculadora.Domain.Operands;

import android.util.Log;

import com.example.juan.calculadora.Domain.Calculator;

public class Div extends Operand {

    public Div() {
        priority = 1;
    }

    @Override
    public double operate(double left, double right) {
        return left/right;
    }
}
