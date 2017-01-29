package com.example.juan.calculadora.Domain;

import com.example.juan.calculadora.Domain.DataStructures.Stack;
import com.example.juan.calculadora.Domain.Exceptions.WrongExpression;
import com.example.juan.calculadora.Domain.Operands.Token;
import com.example.juan.calculadora.Domain.Operands.OpenParenthesis;
import com.example.juan.calculadora.Domain.Operands.Operand;
import com.example.juan.calculadora.R;
import com.example.juan.calculadora.UI.CalculatorActivity;

import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Calculator {

    private CalculatorActivity calculatorActivity;
    private DecimalFormat decimalFormat;
    private static double lastResult;

    public Calculator(CalculatorActivity calculatorActivity) {
        this.calculatorActivity = calculatorActivity;
        decimalFormat = new DecimalFormat("#.######");
    }

    public void newDigit(int digit) {
        calculatorActivity.newSymbol(Integer.toString(digit));
    }

    public void sum() {
        if (calculatorActivity.doesNextInputResets()) calculatorActivity.newSymbol(FieldTextParser.ans);
        calculatorActivity.newSymbol(FieldTextParser.sum);
    }

    public void subs() {
        if (calculatorActivity.doesNextInputResets()) calculatorActivity.newSymbol(FieldTextParser.ans);
        calculatorActivity.newSymbol(FieldTextParser.subs);
    }

    public void div() {
        if (calculatorActivity.doesNextInputResets()) calculatorActivity.newSymbol(FieldTextParser.ans);
        calculatorActivity.newSymbol(FieldTextParser.div);
    }

    public void mul() {
        if (calculatorActivity.doesNextInputResets()) calculatorActivity.newSymbol(FieldTextParser.ans);
        calculatorActivity.newSymbol(FieldTextParser.mul);
    }

    public void del() {
        if (calculatorActivity.fieldLenght() > 0 && calculatorActivity.lastSymbol() == 's') {
            calculatorActivity.removeSymbols(FieldTextParser.ans.length());
        }
        else calculatorActivity.removeSymbols(1);
    }

    public void clear() {
        calculatorActivity.clearField();
    }

    private void executeStacks(Stack<Double> numStack, Stack<Token> operandStack) {
        while (!operandStack.isEmpty()) {
            double rightNumber = numStack.getPop();
            double leftNumber = numStack.getPop();
            Operand currentOperand = (Operand)operandStack.getPop();
            double result = currentOperand.operate(leftNumber, rightNumber);
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
            calculatorActivity.setResult(decimalFormat.format(lastResult));
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
        calculatorActivity.newSymbol(FieldTextParser.commma);
    }

    public void closePar() {
        calculatorActivity.newSymbol(FieldTextParser.closePar);
    }

    public void openPar() {
        calculatorActivity.newSymbol(FieldTextParser.openPar);
    }

    public void ans() {
        calculatorActivity.newSymbol(FieldTextParser.ans);
    }
}
