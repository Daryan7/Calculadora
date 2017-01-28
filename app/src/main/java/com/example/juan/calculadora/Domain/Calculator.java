package com.example.juan.calculadora.Domain;

import com.example.juan.calculadora.Domain.DataStructures.Stack;
import com.example.juan.calculadora.Domain.Exceptions.WrongExpression;
import com.example.juan.calculadora.Domain.Operands.Token;
import com.example.juan.calculadora.Domain.Operands.OpenParenthesis;
import com.example.juan.calculadora.Domain.Operands.Operand;
import com.example.juan.calculadora.R;
import com.example.juan.calculadora.UI.CalculatorActivity;

public class Calculator {

    private CalculatorActivity calculatorActivity;
    private static double lastResult;

    public Calculator(CalculatorActivity calculatorActivity) {
        this.calculatorActivity = calculatorActivity;
        lastResult = 0;
    }

    public void newDigit(int digit) {
        calculatorActivity.newSymbol(Integer.toString(digit));
    }

    public void sum() {
        if (calculatorActivity.doesNextInputResets()) calculatorActivity.newSymbol(R.string._ans);
        calculatorActivity.newSymbol(R.string._sum);
    }

    public void subs() {
        if (calculatorActivity.doesNextInputResets()) calculatorActivity.newSymbol(R.string._ans);
        calculatorActivity.newSymbol(R.string._subs);
    }

    public void div() {
        if (calculatorActivity.doesNextInputResets()) calculatorActivity.newSymbol(R.string._ans);
        calculatorActivity.newSymbol(R.string._div);
    }

    public void mul() {
        if (calculatorActivity.doesNextInputResets()) calculatorActivity.newSymbol(R.string._ans);
        calculatorActivity.newSymbol(R.string._mul);
    }

    public void del() {
        String text = calculatorActivity.getTextField();
        if (text.charAt(text.length()-1) == 's') {
            calculatorActivity.removeSymbols(3);
        }
        else calculatorActivity.removeSymbols(1);
    }

    public void clear() {
        calculatorActivity.clearField();
    }

    private static void executeStacks(Stack<Double> numStack, Stack<Token> operandStack) {
        while (!operandStack.isEmpty()) {
            double rightNumber = numStack.getPop();
            double leftNumber = numStack.getPop();
            Token currentOperand = operandStack.getPop();
            double result = ((Operand)currentOperand).operate(leftNumber, rightNumber);
            numStack.push(result);
        }
    }

    public void calculate() {
        OpenParenthesis.resetCounter();
        Stack<Double> numStack = new Stack<>();
        Stack<Token> operandStack = new Stack<>();
        FieldTextParser parser = new FieldTextParser(calculatorActivity.getTextField());
        try {
            Token lastToken = parser.nextToken();
            lastToken.initialToken();
            lastToken.execute(numStack, operandStack);
            while (parser.haveTokensLeft()) {
                Token token = parser.nextToken();
                token.preExecute(lastToken);
                token.execute(numStack, operandStack);
                lastToken = token;
            }
            lastToken.endToken();
            if (!OpenParenthesis.goodParenthesis()) throw new WrongExpression("Parenthesis not well placed");
            executeStacks(numStack, operandStack);
            lastResult = numStack.getPop();
            calculatorActivity.setResult(Double.toString(lastResult));
            calculatorActivity.resetNextInput();
        }
        catch (WrongExpression exception) {
            calculatorActivity.onError();
        }
    }

    public static double getLastResult() {
        return lastResult;
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

    public void ans() {
        calculatorActivity.newSymbol(R.string._ans);
    }
}
