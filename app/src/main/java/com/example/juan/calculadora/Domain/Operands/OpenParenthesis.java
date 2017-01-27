package com.example.juan.calculadora.Domain.Operands;


import com.example.juan.calculadora.Domain.DataStructures.Stack;

public class OpenParenthesis extends Component {

    private boolean negative;

    public OpenParenthesis() {
        negative = false;
    }

    public void setNegativeResult() {
        negative = true;
    }

    public boolean isResultNegative() {
        return negative;
    }

    @Override
    public void execute(Stack<Double> numStack, Stack<Component> componentStack) {
        componentStack.push(this);
    }

    @Override
    public boolean isCompatibleWith(Component rightComponent) {
        return (rightComponent instanceof MyNumber) || (rightComponent instanceof OpenParenthesis) || (rightComponent instanceof Subs);
    }
}
