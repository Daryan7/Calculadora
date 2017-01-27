package com.example.juan.calculadora.Domain.Operands;

import com.example.juan.calculadora.Domain.Calculator;
import com.example.juan.calculadora.Domain.DataStructures.Stack;
import com.example.juan.calculadora.Domain.Exceptions.WrongExpression;

public class CloseParenthesis extends Component {

    @Override
    public void execute(Stack<Double> numStack, Stack<Component> componentStack) throws WrongExpression {
        Calculator.executeStackUntilParenthesis(numStack, componentStack);
    }

    @Override
    public boolean isCompatibleWith(Component rightComponent) {
        return (rightComponent instanceof Operand) || (rightComponent instanceof CloseParenthesis);
    }
}
