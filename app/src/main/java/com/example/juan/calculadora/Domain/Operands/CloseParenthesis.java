package com.example.juan.calculadora.Domain.Operands;

import com.example.juan.calculadora.Domain.Calculator;
import com.example.juan.calculadora.Domain.DataStructures.Stack;
import com.example.juan.calculadora.Domain.Exceptions.WrongExpression;

public class CloseParenthesis extends Token {

    @Override
    public void execute(Stack<Double> numStack, Stack<Token> operandStack) throws WrongExpression {
        if (OpenParenthesis.quantity > 0) {
            --OpenParenthesis.quantity;
            Token currentOperand = operandStack.getPop();
            while (!(currentOperand instanceof OpenParenthesis)) {
                double rightNumber = numStack.getPop();
                double leftNumber = numStack.getPop();
                double result = ((Operand)currentOperand).operate(leftNumber, rightNumber);
                numStack.push(result);
                currentOperand = operandStack.getPop();
            }
            if (((OpenParenthesis)currentOperand).isResultNegative()) {
                double top = numStack.getPop();
                numStack.push(top*-1);
            }
        }
        else throw new WrongExpression("Parenthesis are not well written");
    }

    public void endToken() throws WrongExpression {
    }

    @Override
    public void preExecute(Token leftToken) throws WrongExpression {
        if (!(leftToken instanceof MyNumber || leftToken instanceof CloseParenthesis)) throw new WrongExpression("");
        /*if (leftToken instanceof Subs) ((Subs) leftToken).hasNumberAtLeftSide();
        return (leftToken instanceof Operand) || (leftToken instanceof CloseParenthesis);*/
    }
}
