package com.example.juan.myapp.Domain.Operands;

import com.example.juan.myapp.Domain.DataStructures.Stack;
import com.example.juan.myapp.Domain.Exceptions.WrongExpression;

public class Subs extends Operand {

    private boolean _hasNumberAtLeftSide;

    public Subs() {
        priority = 0;
        _hasNumberAtLeftSide = false;
    }

    public void initialToken() throws WrongExpression {
    }

    public boolean isAChangeSignOperator() {
        return !_hasNumberAtLeftSide;
    }

    @Override
    public void preExecute(Token leftToken) throws WrongExpression {
        if (leftToken instanceof MyNumber || leftToken instanceof CloseParenthesis) {
            _hasNumberAtLeftSide = true;
            return;
        }
        if (leftToken instanceof Mul || leftToken instanceof Div || leftToken instanceof OpenParenthesis) return;
        super.preExecute(leftToken);
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