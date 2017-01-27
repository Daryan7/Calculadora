package com.example.juan.calculadora.Domain.Operands;

import com.example.juan.calculadora.Domain.DataStructures.Stack;

public class Subs extends Operand {

    private boolean _hasNumberAtLeftSide;

    public Subs() {
        priority = 0;
        _hasNumberAtLeftSide = false;
    }

    public void hasNumberAtLeftSide() {
        _hasNumberAtLeftSide = true;
    }

    public boolean getHasNumberAtLeftSide() {
        return _hasNumberAtLeftSide;
    }

    @Override
    public boolean isCompatibleWith(Token rightToken) {
        if (!_hasNumberAtLeftSide) {
            if (rightToken instanceof OpenParenthesis) {
                ((OpenParenthesis) rightToken).setNegativeResult();
            } else if (rightToken instanceof MyNumber) {
                ((MyNumber) rightToken).setNegative();
            }
        }
        return super.isCompatibleWith(rightToken);
    }

    @Override
    public void execute(Stack<Double> numStack, Stack<Token> componentStack) {
        if (_hasNumberAtLeftSide) super.execute(numStack, componentStack);
    }

    @Override
    public double operate(double left, double right) {
        return left-right;
    }
}
