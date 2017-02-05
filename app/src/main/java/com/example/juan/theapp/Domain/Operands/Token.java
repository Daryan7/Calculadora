package com.example.juan.theapp.Domain.Operands;

import com.example.juan.theapp.Domain.DataStructures.Stack;
import com.example.juan.theapp.Domain.Exceptions.WrongExpression;

public abstract class Token {

    public abstract void execute(Stack<Double> numStack, Stack<Token> componentStack) throws WrongExpression;
    public void initialToken() throws WrongExpression {
        throw new WrongExpression(WrongExpression.ErrorType.SYNTAX);
    }
    public void endToken() throws WrongExpression {
        throw new WrongExpression(WrongExpression.ErrorType.SYNTAX);
    }
    public abstract void preExecute(Token leftToken) throws WrongExpression;
}
