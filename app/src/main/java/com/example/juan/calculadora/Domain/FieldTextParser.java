package com.example.juan.calculadora.Domain;


import android.util.Log;

import com.example.juan.calculadora.Domain.Exceptions.WrongExpression;
import com.example.juan.calculadora.Domain.Operands.CloseParenthesis;
import com.example.juan.calculadora.Domain.Operands.Token;
import com.example.juan.calculadora.Domain.Operands.Div;
import com.example.juan.calculadora.Domain.Operands.Mul;
import com.example.juan.calculadora.Domain.Operands.MyNumber;
import com.example.juan.calculadora.Domain.Operands.OpenParenthesis;
import com.example.juan.calculadora.Domain.Operands.Subs;
import com.example.juan.calculadora.Domain.Operands.Sum;

public class FieldTextParser {
    private String text;
    private int currentIndex;

    private boolean isNumber(char symbol) {
        return symbol == '0' || symbol == '1' || symbol == '2' || symbol == '3'
                || symbol == '4' || symbol == '5' || symbol == '6' || symbol == '7'
                || symbol == '8' || symbol == '9';
    }


    public FieldTextParser(String text) {
        this.text = text;
        currentIndex = 0;
    }

    public boolean haveTokensLeft() {
        return currentIndex < text.length();
    }

    private MyNumber readNumber() {
        int initialIndex = currentIndex;
        while (currentIndex < text.length() && (isNumber(text.charAt(currentIndex)) || text.charAt(currentIndex) == '.')) {
            ++currentIndex;
        }
        return new MyNumber(text.substring(initialIndex, currentIndex));
    }

    public Token nextToken() throws WrongExpression {
        if (currentIndex >= text.length()) throw new WrongExpression("Tried to parse after end of expression, index found " + currentIndex + " but text has length " + text.length());
        char currentSymbol = text.charAt(currentIndex);
        Log.d("Parser", "Parsing " + currentSymbol + " at index " + currentIndex);
        if (isNumber(currentSymbol)) {
            return readNumber();
        }
        Token token;
        switch (currentSymbol) {
            case ')':
                token = new CloseParenthesis();
                break;
            case '(':
                token = new OpenParenthesis();
                break;
            case '+':
                token = new Sum();
                break;
            case '-':
                token = new Subs();
                break;
            case 'x':
                token = new Mul();
                break;
            case '÷':
                token = new Div();
                break;
            default: throw new WrongExpression("Found invalid symbol while parsing: " + currentSymbol);
        }
        ++currentIndex;
        return token;
    }
}
