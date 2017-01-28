package com.example.juan.calculadora.Domain.Operands;

import com.example.juan.calculadora.Domain.Exceptions.WrongExpression;

public class Mul extends Operand {

    public Mul() {
        priority = 1;
    }

    @Override
    public double operate(double left, double right) {
        return left*right;
    }
}
