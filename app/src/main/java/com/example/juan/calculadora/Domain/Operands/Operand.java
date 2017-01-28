package com.example.juan.calculadora.Domain.Operands;

import com.example.juan.calculadora.Domain.Calculator;
import com.example.juan.calculadora.Domain.DataStructures.Stack;
import com.example.juan.calculadora.Domain.Exceptions.WrongExpression;

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
    public void preExecute(Token leftToken) throws WrongExpression {
        if (!(leftToken instanceof MyNumber || leftToken instanceof CloseParenthesis)) throw new WrongExpression("");
    }
}
