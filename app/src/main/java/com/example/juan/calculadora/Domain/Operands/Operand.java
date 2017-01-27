package com.example.juan.calculadora.Domain.Operands;

import com.example.juan.calculadora.Domain.DataStructures.Stack;

public abstract class Operand extends Component {
    int priority;
    public abstract double operate(double left, double right);

    @Override
    public void execute(Stack<Double> numStack, Stack<Component> componentStack) {
        while (!(componentStack.isEmpty())) {
            Component component = componentStack.getPop();
            if (component instanceof OpenParenthesis) {
                componentStack.push(this);
                return;
            }
            Operand operand = (Operand)component;
            if (operand.priority < priority){
                componentStack.push(this);
                return;
            }
            double leftNumber = numStack.getPop();
            double rightNumber = numStack.getPop();
            ((Operand)component).operate(leftNumber, rightNumber);
        }
        componentStack.push(this);
    }

    @Override
    public boolean isCompatibleWith(Component rightComponent) {
        return (rightComponent instanceof MyNumber) || (rightComponent instanceof OpenParenthesis);
    }

    public int getPriority() {
        return priority;
    }
}