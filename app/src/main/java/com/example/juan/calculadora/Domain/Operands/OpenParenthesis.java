package com.example.juan.calculadora.Domain.Operands;


import com.example.juan.calculadora.Domain.Calculator;
import com.example.juan.calculadora.Domain.DataStructures.Stack;
import com.example.juan.calculadora.Domain.Exceptions.WrongExpression;

public class OpenParenthesis extends Token {

    static int quantity;

    private boolean negative;

    public OpenParenthesis() {
        negative = false;
    }

    public void initialToken() throws WrongExpression {
    }

    public static void resetCounter() {
        quantity = 0;
    }

    public static boolean goodParenthesis() {
        return quantity == 0;
    }

    boolean isResultNegative() {
        return negative;
    }

    @Override
    public void execute(Stack<Double> numStack, Stack<Token> componentStack) {
        componentStack.push(this);
        ++quantity;
    }

    @Override
    public void preExecute(Token leftToken) throws WrongExpression {
        if (leftToken instanceof Subs && ((Subs)leftToken).isAChangeSignOperator()) negative = true;
    }
}
