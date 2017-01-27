package com.example.juan.calculadora.Domain.Operands;


import com.example.juan.calculadora.Domain.DataStructures.Stack;

public class OpenParenthesis extends Token {

    static int quantity;

    private boolean negative;

    public OpenParenthesis() {
        negative = false;
    }

    public static void resetCounter() {
        quantity = 0;
    }

    public static boolean goodParenthesis() {
        return quantity == 0;
    }

    public void setNegativeResult() {
        negative = true;
    }

    public boolean isResultNegative() {
        return negative;
    }

    @Override
    public void execute(Stack<Double> numStack, Stack<Token> componentStack) {
        componentStack.push(this);
        ++quantity;
    }

    @Override
    public boolean isCompatibleWith(Token rightToken) {
        return (rightToken instanceof MyNumber) || (rightToken instanceof OpenParenthesis) || (rightToken instanceof Subs);
    }
}
