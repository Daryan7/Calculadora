package com.example.juan.calculadora.Domain.Operands;

import com.example.juan.calculadora.Domain.DataStructures.Stack;
import com.example.juan.calculadora.Domain.Exceptions.WrongExpression;

public abstract class Component {

    public abstract void execute(Stack<Double> numStack, Stack<Component> componentStack) throws WrongExpression;
    public abstract boolean isCompatibleWith(Component rightComponent);
}
