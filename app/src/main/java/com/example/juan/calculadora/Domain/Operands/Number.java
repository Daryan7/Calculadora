package com.example.juan.calculadora.Domain.Operands;

public class Number extends Component {
    private double num;
    private int digits;

    public Number() {
        digits = 0;
        num = 0;
    }

    public void addDigit(int digit) {
        num *= 10;
        num += digit;
        ++digits;
    }

    public void removeDigit() {
        num = (int)num/10;
        --digits;
    }

    public boolean isEmpty() {
        return digits == 0;
    }
}
