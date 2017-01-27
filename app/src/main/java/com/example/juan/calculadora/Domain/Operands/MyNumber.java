package com.example.juan.calculadora.Domain.Operands;

import android.util.Log;

import com.example.juan.calculadora.Domain.DataStructures.Stack;

public class MyNumber extends Component {
    private double num;

    public MyNumber(String number) {
        num = Double.parseDouble(number);
    }

    public double getNumber() {
        return num;
    }

    public void setNegative() {
        num = num * -1;
    }

    @Override
    public void execute(Stack<Double> numStack, Stack<Component> componentStack) {
        numStack.push(num);
    }

    @Override
    public boolean isCompatibleWith(Component rightComponent) {
        if (rightComponent instanceof Subs) {
            ((Subs)rightComponent).hasNumberAtLeftSide();
        }
        return (rightComponent instanceof MyNumber) || (rightComponent instanceof Operand) || (rightComponent instanceof CloseParenthesis);
    }
}
