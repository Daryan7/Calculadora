package com.example.juan.calculadora.Domain.Operands;

import com.example.juan.calculadora.Domain.DataStructures.Stack;
import com.example.juan.calculadora.Domain.Exceptions.WrongExpression;

public abstract class Token {

    public abstract void execute(Stack<Double> numStack, Stack<Token> componentStack) throws WrongExpression;
    public abstract boolean isCompatibleWith(Token rightToken);
}
