package com.example.juan.calculadora.Domain;

import com.example.juan.calculadora.R;
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
    public void sum() {
        calculatorActivity.newSymbol(R.string._sum);
    }

    public void subs() {
        calculatorActivity.newSymbol(R.string._subs);
    }

    public void div() {
        calculatorActivity.newSymbol(R.string._div);
    }

    public void mul() {
        calculatorActivity.newSymbol(R.string._mul);
    }

    public void del() {
        calculatorActivity.removeSymbol();
    }

    public void clear() {
        calculatorActivity.clearField();

    }

    public void calculate() {

    }

    public void comma() {
        calculatorActivity.newSymbol(R.string._comma);
    }

    public void changeSign() {
        calculatorActivity.newSymbol("-");
    }

    public void parenthesis() {

    }
}
