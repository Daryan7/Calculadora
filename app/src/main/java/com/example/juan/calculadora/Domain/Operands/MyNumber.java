package com.example.juan.calculadora.Domain.Operands;

import com.example.juan.calculadora.Domain.DataStructures.Stack;

public class MyNumber extends Token {
    private double num;

    public MyNumber(String number) {
        num = Double.parseDouble(number);
    }

    public double getNumber() {
        return num;
    }

    public void setNegative() {
        num = num * -1;
    }

    @Override
    public void execute(Stack<Double> numStack, Stack<Token> componentStack) {
        numStack.push(num);
    }

    @Override
    public boolean isCompatibleWith(Token rightToken) {
        if (rightToken instanceof Subs) {
            ((Subs) rightToken).hasNumberAtLeftSide();
        }
        return (rightToken instanceof MyNumber) || (rightToken instanceof Operand) || (rightToken instanceof CloseParenthesis);
    }
}
