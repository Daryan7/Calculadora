package com.example.juan.calculadora.Domain.Operands;


import com.example.juan.calculadora.Domain.DataStructures.Stack;

public class OpenParenthesis extends Component {

    @Override
    public void execute(Stack<Double> numStack, Stack<Component> componentStack) {

    }

    @Override
    public boolean isCompatibleWith(Component rightComponent) {
        return (rightComponent instanceof MyNumber) || (rightComponent instanceof OpenParenthesis);
    }
}
