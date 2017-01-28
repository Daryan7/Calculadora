package com.example.juan.calculadora.Domain;

import android.util.Log;

import com.example.juan.calculadora.Domain.DataStructures.Stack;
import com.example.juan.calculadora.Domain.Exceptions.WrongExpression;
import com.example.juan.calculadora.Domain.Operands.CloseParenthesis;
import com.example.juan.calculadora.Domain.Operands.MyNumber;
import com.example.juan.calculadora.Domain.Operands.Token;
import com.example.juan.calculadora.Domain.Operands.OpenParenthesis;
import com.example.juan.calculadora.Domain.Operands.Operand;
import com.example.juan.calculadora.Domain.Operands.Subs;
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
            if ((lastToken instanceof Operand && !(lastToken instanceof Subs)) || lastToken instanceof CloseParenthesis) {
                throw new WrongExpression("Syntax error at beginning of expression: found " + lastToken.getClass() + ", which is invalid");
            }
            lastToken.execute(numStack, operandStack);
            Log.d("Execution", "Executing component " + lastToken.getClass());
            while (parser.haveTokensLeft()) {
                Token token = parser.nextToken();
                if (lastToken.isCompatibleWith(token)) {
                    Log.d("Execution", "Executing token " + token.getClass());
                    token.execute(numStack, operandStack);
                }
                else throw new WrongExpression("Syntax error while treating expression: found " + lastToken.getClass() + " with " + token.getClass() + ", which is invalid");
                lastToken = token;
            }
            if (!(lastToken instanceof MyNumber || lastToken instanceof CloseParenthesis)) {
                throw new WrongExpression("Syntax error at ending of expression: found " + lastToken.getClass() + ", which is invalid");
            }
            if (!OpenParenthesis.goodParenthesis()) throw new WrongExpression("Parenthesis not well written");
            executeStacks(numStack, operandStack);
            calculatorActivity.setResult(Double.toString(numStack.getTop()));
        }
        catch (WrongExpression exception) {
            Log.d("Exception", exception.getMessage());
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
