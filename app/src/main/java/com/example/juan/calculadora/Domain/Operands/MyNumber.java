package com.example.juan.calculadora.Domain.Operands;

import android.util.Log;

import com.example.juan.calculadora.Domain.DataStructures.Stack;
import com.example.juan.calculadora.Domain.Exceptions.WrongExpression;

public class MyNumber extends Token {
    double num;

    public MyNumber(String number) {
        num = Double.parseDouble(number);
    }

    MyNumber() {
    }

    public void initialToken() throws WrongExpression {
    }
    public void endToken() throws WrongExpression {
    }

    @Override
    public void execute(Stack<Double> numStack, Stack<Token> componentStack) {
        numStack.push(num);
    }

    @Override
    public void preExecute(Token leftToken) throws WrongExpression {
        if (leftToken instanceof Subs && ((Subs)leftToken).isAChangeSignOperator()) {
            num *= -1;
        }
        if (!(leftToken instanceof Operand || leftToken instanceof OpenParenthesis)) throw new WrongExpression("");
    }
}
