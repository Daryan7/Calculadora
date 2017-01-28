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

    @Override
    public boolean isCompatibleWith(Token rightToken) {
        if (rightToken instanceof Subs) ((Subs)rightToken).hasNumberAtLeftSide();
        return (rightToken instanceof Operand) || (rightToken instanceof CloseParenthesis);
    }
}
