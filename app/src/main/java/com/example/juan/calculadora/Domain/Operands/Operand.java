package com.example.juan.calculadora.Domain.Operands;

public abstract class Operand extends Component{
    int priority;
    public abstract double operate(double left, double right);

    public int getPriority() {
        return priority;
    }
}
