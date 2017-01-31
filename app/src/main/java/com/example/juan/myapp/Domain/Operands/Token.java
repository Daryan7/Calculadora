package com.example.juan.myapp.Domain.Operands;

import com.example.juan.myapp.Domain.DataStructures.Stack;
import com.example.juan.myapp.Domain.Exceptions.WrongExpression;

public abstract class Token {

    public abstract void execute(Stack<Double> numStack, Stack<Token> componentStack) throws WrongExpression;
    public void initialToken() throws WrongExpression {
        throw new WrongExpression("");
    }
    public void endToken() throws WrongExpression {
        throw new WrongExpression("");
    }
    public abstract void preExecute(Token leftToken) throws WrongExpression;
}
