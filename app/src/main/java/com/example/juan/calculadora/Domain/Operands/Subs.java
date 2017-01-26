package com.example.juan.calculadora.Domain.Operands;

public class Subs extends Operand {

    public Subs() {
        priority = 0;
    }

    @Override
    public double operate(double left, double right) {
        return left-right;
    }
}
