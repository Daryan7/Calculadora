package com.example.juan.calculadora.Domain.Operands;

import com.example.juan.calculadora.Domain.Calculator;
import com.example.juan.calculadora.Domain.DataStructures.Stack;
import com.example.juan.calculadora.Domain.Exceptions.WrongExpression;

public class MyNumber extends Token {
    double num;

    public MyNumber(String number) {
        num = Double.parseDouble(number);
    }

    public MyNumber() {
    }

    public void initialToken() throws WrongExpression {
    }
    public void endToken() throws WrongExpression {
    }
    /*public void setNegative() {
        num = num * -1;
    }*/

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
        /*if (leftToken instanceof Subs) {
            ((Subs) leftToken).hasNumberAtLeftSide();
        }
        return (leftToken instanceof MyNumber) || (leftToken instanceof Operand) || (leftToken instanceof CloseParenthesis);*/
    }
}
