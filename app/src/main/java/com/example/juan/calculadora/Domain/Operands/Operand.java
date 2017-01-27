package com.example.juan.calculadora.Domain.Operands;

import com.example.juan.calculadora.Domain.DataStructures.Stack;

public abstract class Operand extends Token {
    int priority;
    public abstract double operate(double left, double right);

    @Override
    public void execute(Stack<Double> numStack, Stack<Token> componentStack) {
        while (!(componentStack.isEmpty())) {
            Token token = componentStack.getTop();
            if (token instanceof OpenParenthesis) {
                componentStack.push(this);
                return;
            }
            Operand operand = (Operand) token;
            if (operand.priority < priority){
                componentStack.push(this);
                return;
            }
            double rightNumber = numStack.getPop();
            double leftNumber = numStack.getPop();
            double result = ((Operand) token).operate(leftNumber, rightNumber);
            numStack.push(result);
            componentStack.pop();
        }
        componentStack.push(this);
    }

    @Override
    public boolean isCompatibleWith(Token rightToken) {
        return (rightToken instanceof MyNumber) || (rightToken instanceof OpenParenthesis);
    }
}
