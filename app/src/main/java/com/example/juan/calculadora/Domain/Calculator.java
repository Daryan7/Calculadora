package com.example.juan.calculadora.Domain;

import android.util.Log;

import com.example.juan.calculadora.Domain.DataStructures.Stack;
import com.example.juan.calculadora.Domain.Exceptions.WrongExpression;
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
            double rightNumber = numStack.getPop();
            double leftNumber = numStack.getPop();
            Component currentOperand = operandStack.getPop();
            Log.v("c", "" + currentOperand.getClass());
            double result = ((Operand)currentOperand).operate(leftNumber, rightNumber);
            numStack.push(result);
        }
    }

    public static void executeStackUntilParenthesis(Stack<Double> numStack, Stack<Component> operandStack) throws WrongExpression {
        Component currentOperand = operandStack.getPop();
        while (currentOperand != null && !(currentOperand instanceof OpenParenthesis)) {
            double rightNumber = numStack.getPop();
            double leftNumber = numStack.getPop();
            double result = ((Operand)currentOperand).operate(leftNumber, rightNumber);
            numStack.push(result);
            Log.d("c", "current operand " + currentOperand.getClass());
            currentOperand = operandStack.getPop();
        }
        if (currentOperand == null) throw new WrongExpression();
    }

    public void calculate() {
        Stack<Double> numStack = new Stack<>();
        Stack<Component> operandStack = new Stack<>();
        FieldTextParser parser = new FieldTextParser(calculatorActivity.getTextField());
        try {
            Component lastComponent = parser.nextComponent();
            lastComponent.execute(numStack, operandStack);
            Log.v("Execution", "Executing component " + lastComponent.getClass());
            while (parser.haveComponentsLeft()) {
                Component component = parser.nextComponent();
                if (component == null) {
                    calculatorActivity.onError();
                    return;
                }
                if (lastComponent.isCompatibleWith(component)) {
                    Log.v("Execution", "Executing component " + component.getClass());
                    component.execute(numStack, operandStack);
                }
                lastComponent = component;
            }
            executeStacks(numStack, operandStack);
            calculatorActivity.setResult(Double.toString(numStack.getTop()));
        }
        catch (WrongExpression exception) {
            calculatorActivity.onError();
        }
    }

    public void comma() {
        calculatorActivity.newSymbol(R.string._comma);
    }

    public void closePar() {
        calculatorActivity.newSymbol(")");
    }

    public void openPar() {
        calculatorActivity.newSymbol("(");
    }
}
