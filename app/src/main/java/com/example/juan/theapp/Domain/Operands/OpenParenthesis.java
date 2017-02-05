package com.example.juan.theapp.Domain.Operands;

import com.example.juan.theapp.Domain.DataStructures.Stack;
import com.example.juan.theapp.Domain.Exceptions.WrongExpression;

public class OpenParenthesis extends Token {

    static int quantity;
    private Mul auxToken;

    private boolean negative;

    public OpenParenthesis() {
        negative = false;
        auxToken = null;
    }

    public void initialToken() {
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
    public void execute(Stack<Double> numStack, Stack<Token> componentStack) throws WrongExpression {
        if (auxToken != null) {
            auxToken.execute(numStack, componentStack);
        }
        componentStack.push(this);
        ++quantity;
    }

    @Override
    public void preExecute(Token leftToken) throws WrongExpression {
        if (leftToken instanceof Subs) {
            if (((Subs)leftToken).isAChangeSignOperator()) negative = true;
        }
        else if (leftToken instanceof MyNumber || leftToken instanceof CloseParenthesis) {
            auxToken = new Mul();
            auxToken.preExecute(leftToken);
        }
        else if (!(leftToken instanceof Operand || leftToken instanceof OpenParenthesis))
            throw new WrongExpression(WrongExpression.ErrorType.SYNTAX);
    }
}
