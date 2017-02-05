package com.example.juan.theapp.Domain.Operands;

import com.example.juan.theapp.Domain.Exceptions.WrongExpression;

public class Div extends Operand {

    public Div() {
        priority = 1;
    }

    @Override
    public double operate(double left, double right) throws WrongExpression {
        if (right == 0) throw new WrongExpression(WrongExpression.ErrorType.DIVISION_BY_ZERO);
        return left/right;
    }
}
