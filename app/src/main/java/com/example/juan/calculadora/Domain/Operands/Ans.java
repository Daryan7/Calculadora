package com.example.juan.calculadora.Domain.Operands;


import com.example.juan.calculadora.Domain.Calculator;
import com.example.juan.calculadora.Domain.Exceptions.WrongExpression;

public class Ans extends MyNumber {

    @Override
    public void preExecute(Token leftToken) throws WrongExpression {
        num = Calculator.getLastResult();
        super.preExecute(leftToken);
    }
}
