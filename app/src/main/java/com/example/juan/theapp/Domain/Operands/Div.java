package com.example.juan.theapp.Domain.Operands;

public class Div extends Operand {

    public Div() {
        priority = 1;
    }

    @Override
    public double operate(double left, double right) {
        return left/right;
    }
}
