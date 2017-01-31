package com.example.juan.theapp.Domain.Operands;

public class Sum extends Operand {

    public Sum() {
        priority = 0;
    }

    @Override
    public double operate(double left, double right) {
        return left + right;
    }
}
