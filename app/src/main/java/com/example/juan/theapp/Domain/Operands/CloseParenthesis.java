package com.example.juan.theapp.Domain.Operands;

import com.example.juan.theapp.Domain.DataStructures.Stack;
import com.example.juan.theapp.Domain.Exceptions.WrongExpression;

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
        else throw new WrongExpression(WrongExpression.ErrorType.PARENTHESIS);
    }

    public void endToken() throws WrongExpression {
    }

    @Override
    public void preExecute(Token leftToken) throws WrongExpression {
        if (!(leftToken instanceof MyNumber || leftToken instanceof CloseParenthesis))
            throw new WrongExpression(WrongExpression.ErrorType.SYNTAX);
    }
}
