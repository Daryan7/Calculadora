package com.example.juan.calculadora.Domain;

import com.example.juan.calculadora.UI.CalculatorActivity;

public class Calculator {
    private CalculatorActivity calculatorActivity;
    private int currentNumber;

    public Calculator(CalculatorActivity calculatorActivity) {
        this.calculatorActivity = calculatorActivity;
    }

    public void newDigit(int digit) {
        calculatorActivity.newSymbol(Integer.toString(digit));
    }
}
