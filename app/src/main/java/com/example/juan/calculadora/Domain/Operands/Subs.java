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
    public boolean isCompatibleWith(Component rightComponent) {
        if (!_hasNumberAtLeftSide) {
            if (rightComponent instanceof OpenParenthesis) {
                ((OpenParenthesis) rightComponent).setNegativeResult();
            }
            else if (rightComponent instanceof MyNumber) {
                ((MyNumber) rightComponent).setNegative();
            }
        }
        return super.isCompatibleWith(rightComponent);
    }

    @Override
    public void execute(Stack<Double> numStack, Stack<Component> componentStack) {
        if (_hasNumberAtLeftSide) super.execute(numStack, componentStack);
    }

    @Override
    public double operate(double left, double right) {
        return left-right;
    }
}
