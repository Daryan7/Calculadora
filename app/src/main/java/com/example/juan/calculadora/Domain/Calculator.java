package com.example.juan.calculadora.Domain;

import com.example.juan.calculadora.Domain.DataStructures.Stack;
import com.example.juan.calculadora.Domain.Operands.Component;
import com.example.juan.calculadora.Domain.Operands.Operand;
import com.example.juan.calculadora.R;
import com.example.juan.calculadora.UI.CalculatorActivity;

public class Calculator {

    private CalculatorActivity calculatorActivity;
    private Stack<Component> componentStack;

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
        String field = calculatorActivity.getTextField();
        Stack<Double> numStack = new Stack<>();
        Stack<Operand> operandStack = new Stack<>();
        int lastIndex = 0;

        for (int i = 0; i < field.length(); ++i) {
            char actualSymbol = field.charAt(i);
            if (actualSymbol == '+' || actualSymbol == '-' || actualSymbol == '*' || actualSymbol != '÷') {
                String number = field.substring(lastIndex, i);
                if (!number.equals("")) {
                    double res = Double.parseDouble(field.substring(lastIndex, i));
                    numStack.push(res);
                }
            }

        }
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
