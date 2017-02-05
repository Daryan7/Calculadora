package com.example.juan.theapp.Domain;

import com.example.juan.theapp.Domain.DataStructures.Stack;
import com.example.juan.theapp.Domain.Exceptions.WrongExpression;
import com.example.juan.theapp.Domain.Operands.Token;
import com.example.juan.theapp.Domain.Operands.OpenParenthesis;
import com.example.juan.theapp.Domain.Operands.Operand;
import com.example.juan.theapp.UI.Activities.CalculatorFragment;

public class Calculator {

    private CalculatorFragment calculatorFragment;
    private static double lastResult;

    public Calculator(CalculatorFragment calculatorFragment) {
        this.calculatorFragment = calculatorFragment;
    }

    public void newDigit(int digit) {
        calculatorFragment.newSymbol(Integer.toString(digit));
    }

    public void sum() {
        if (calculatorFragment.doesNextInputResets()) calculatorFragment.newSymbol(FieldTextParser.ans);
        calculatorFragment.newSymbol(FieldTextParser.sum);
    }

    public void subs() {
        if (calculatorFragment.doesNextInputResets()) calculatorFragment.newSymbol(FieldTextParser.ans);
        calculatorFragment.newSymbol(FieldTextParser.subs);
    }

    public void div() {
        if (calculatorFragment.doesNextInputResets()) calculatorFragment.newSymbol(FieldTextParser.ans);
        calculatorFragment.newSymbol(FieldTextParser.div);
    }

    public void mul() {
        if (calculatorFragment.doesNextInputResets()) calculatorFragment.newSymbol(FieldTextParser.ans);
        calculatorFragment.newSymbol(FieldTextParser.mul);
    }

    public void del() {
        if (calculatorFragment.fieldLength() > 0 && calculatorFragment.lastSymbol() == 's') {
            calculatorFragment.removeSymbols(FieldTextParser.ans.length());
        }
        else calculatorFragment.removeSymbols(1);
    }

    public void clear() {
        calculatorFragment.clearField();
    }

    private void executeStacks(Stack<Double> numStack, Stack<Token> operandStack) throws WrongExpression {
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
        FieldTextParser parser = new FieldTextParser(calculatorFragment.getTextField());
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
            if (!OpenParenthesis.goodParenthesis()) throw new WrongExpression(WrongExpression.ErrorType.PARENTHESIS);
            executeStacks(numStack, operandStack);
            lastResult = numStack.getPop();
            calculatorFragment.setResult(Double.toString(lastResult));
            calculatorFragment.resetNextInput();
        }
        catch (WrongExpression exception) {
            String message = "";
            switch (exception.getType()) {
                case DIVISION_BY_ZERO:
                    message = "Can't divide by zero";
                    calculatorFragment.setResult("This is not the operation you are looking for");
                    break;
                case SYNTAX:
                    message = "The expression has syntax errors";
                    break;
                case PARENTHESIS:
                    message = "Parenthesis are not correct";
                    break;
            }
            calculatorFragment.onError(message);
        }
    }

    public static double getLastResult() {
        return lastResult;
    }

    public void comma() {
        calculatorFragment.newSymbol(FieldTextParser.commma);
    }

    public void closePar() {
        calculatorFragment.newSymbol(FieldTextParser.closePar);
    }

    public void openPar() {
        calculatorFragment.newSymbol(FieldTextParser.openPar);
    }

    public void ans() {
        calculatorFragment.newSymbol(FieldTextParser.ans);
    }
}
