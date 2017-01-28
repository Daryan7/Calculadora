package com.example.juan.calculadora.Domain.Operands;

import com.example.juan.calculadora.Domain.Calculator;
import com.example.juan.calculadora.Domain.DataStructures.Stack;
import com.example.juan.calculadora.Domain.Exceptions.WrongExpression;

public class Subs extends Operand {

    private boolean _hasNumberAtLeftSide;

    public Subs() {
        priority = 0;
        _hasNumberAtLeftSide = false;
    }

    public void initialToken() throws WrongExpression {
    }

    public void hasNumberAtLeftSide() {
        _hasNumberAtLeftSide = true;
    }

    public boolean isAChangeSignOperator() {
        return !_hasNumberAtLeftSide;
    }

    @Override
    public void preExecute(Token leftToken) throws WrongExpression {
        if (leftToken instanceof MyNumber) _hasNumberAtLeftSide = true;
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
