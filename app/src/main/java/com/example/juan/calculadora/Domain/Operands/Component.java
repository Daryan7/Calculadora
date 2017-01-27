package com.example.juan.calculadora.Domain.Operands;

import com.example.juan.calculadora.Domain.DataStructures.Stack;

public abstract class Component {

    public abstract void execute(Stack<Double> numStack, Stack<Component> componentStack);
    public abstract boolean isCompatibleWith(Component rightComponent);
}
