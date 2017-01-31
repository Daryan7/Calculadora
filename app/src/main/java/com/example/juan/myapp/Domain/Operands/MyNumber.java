package com.example.juan.myapp.Domain.Operands;

import com.example.juan.myapp.Domain.DataStructures.Stack;
import com.example.juan.myapp.Domain.Exceptions.WrongExpression;

public class MyNumber extends Token {
    double num;

    public MyNumber(String number) {
        num = Double.parseDouble(number);
    }

    MyNumber() {
    }

    public void initialToken() {
    }
    public void endToken() {
    }

    @Override
    public void execute(Stack<Double> numStack, Stack<Token> componentStack) {
        numStack.push(num);
    }

    @Override
    public void preExecute(Token leftToken) throws WrongExpression {
        if (leftToken instanceof Subs && ((Subs)leftToken).isAChangeSignOperator()) {
            num *= -1;
            return;
        }
        if (!(leftToken instanceof Operand || leftToken instanceof OpenParenthesis)) throw new WrongExpression("");
    }
}
