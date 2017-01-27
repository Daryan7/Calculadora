package com.example.juan.calculadora.Domain;

import com.example.juan.calculadora.Domain.DataStructures.Stack;
import com.example.juan.calculadora.Domain.Operands.Component;
import com.example.juan.calculadora.Domain.Operands.OpenParenthesis;
import com.example.juan.calculadora.Domain.Operands.Operand;
import com.example.juan.calculadora.R;
import com.example.juan.calculadora.UI.CalculatorActivity;

public class Calculator {

    private CalculatorActivity calculatorActivity;

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

    public static void executeStacks(Stack<Double> numStack, Stack<Component> operandStack) {
        while (!operandStack.isEmpty()) {
            double leftNumber = numStack.getPop();
            double rightNumber = numStack.getPop();
            Component currentOperand = operandStack.getPop();
            double result = ((Operand)currentOperand).operate(leftNumber, rightNumber);
            numStack.push(result);
        }
    }

    public static void executeStackUntilParenthesis(Stack<Double> numStack, Stack<Component> operandStack) {
        Component currentOperand = operandStack.getPop();
        while (!(currentOperand instanceof OpenParenthesis)) {
            double leftNumber = numStack.getPop();
            double rightNumber = numStack.getPop();
            double result = ((Operand)currentOperand).operate(leftNumber, rightNumber);
            numStack.push(result);
            currentOperand = operandStack.getPop();
        }
    }

    public void calculate() {
        Stack<Double> numStack = new Stack<>();
        Stack<Component> operandStack = new Stack<>();
        FieldTextParser parser = new FieldTextParser(calculatorActivity.getTextField());

        Component lastComponent = parser.nextComponent();
        lastComponent.execute(numStack, operandStack);
        while (parser.haveComponentsLeft()) {
            Component component = parser.nextComponent();
            if (lastComponent.isCompatibleWith(component)) {
                component.execute(numStack, operandStack);
            }
        }
        executeStacks(numStack, operandStack);
    }

    public void comma() {
        calculatorActivity.newSymbol(R.string._comma);
    }

    public void closePar() {
        calculatorActivity.newSymbol("(");
    }

    public void openPar() {
        calculatorActivity.newSymbol(")");
    }
}
