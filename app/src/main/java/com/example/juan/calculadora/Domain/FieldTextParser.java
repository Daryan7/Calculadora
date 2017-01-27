package com.example.juan.calculadora.Domain;


import android.util.Log;

import com.example.juan.calculadora.Domain.Exceptions.WrongExpression;
import com.example.juan.calculadora.Domain.Operands.CloseParenthesis;
import com.example.juan.calculadora.Domain.Operands.Component;
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

    public boolean haveComponentsLeft() {
        return currentIndex < text.length();
    }

    private MyNumber readNumber() {
        int initialIndex = currentIndex;
        while (currentIndex < text.length() && isNumber(text.charAt(currentIndex))) {
            ++currentIndex;
        }
        return new MyNumber(text.substring(initialIndex, currentIndex));
    }

    private MyNumber readNegativeNumber() {
        int initialIndex = currentIndex;
        ++currentIndex;
        while (currentIndex < text.length() && isNumber(text.charAt(currentIndex))) {
            ++currentIndex;
        }
        return new MyNumber(text.substring(initialIndex, currentIndex));
    }

    public Component nextComponent() throws WrongExpression {
        /*char currentChar = text.charAt(currentIndex);
        Log.v("chars", "Current char " + currentChar + " at index " + currentIndex);
        if (currentIndex == 0) {
            if (currentChar == '-') {
                if (currentIndex + 1 < text.length()) {
                    if (isNumber(text.charAt(currentIndex + 1))) {
                        return readNegativeNumber();
                    } else if (text.charAt(currentIndex + 1) == '(') {
                        currentIndex += 2;
                        ++openPar;
                        return new OpenParenthesis(true);
                    } else return null;
                } else return null;
            }
            if (isOperand(currentChar)) {
                return null;
            }
        }
        if (currentChar == ')') {
            if (openPar == 0) return null;
            else {
                --openPar;
                ++currentIndex;
                return new CloseParenthesis();
            }
        } else if (currentChar == '(') {
            Log.d("Chars", "is open partenthesis");
            ++openPar;
            ++currentIndex;
            return new OpenParenthesis(false);
        } else if (isOperand(currentChar)) {
            char prevSymbol = text.charAt(currentIndex - 1);
            if (currentIndex + 1 < text.length()) return null;
            char nextSymbol = text.charAt(currentIndex + 1);
            if (currentChar == '-') {
                if (prevSymbol == '(') {
                    if (isNumber(nextSymbol)) {
                        return readNegativeNumber();
                    }
                    else if (nextSymbol == '(') {
                        ++openPar;
                        currentIndex += 2;
                        return new OpenParenthesis(true);
                    }
                    else return null;
                }
            }
            if (prevSymbol == ')' || isNumber(prevSymbol) && nextSymbol == '(' || isNumber(')'))
            ++currentIndex;
            switch (currentChar) {
                case '+':
                    return new Sum();
                case '-':
                    return new Subs();
                case 'x':
                    return new Mul();
                case '÷':
                    return new Div();
            }
        }*/
        if (currentIndex >= text.length()) throw new WrongExpression();
        char currentSymbol = text.charAt(currentIndex);
        Log.v("Parser", "Parsing " + currentSymbol + " at index " + currentIndex);
        if (isNumber(currentSymbol)) {
            return readNumber();
        }
        Component component;
        switch (currentSymbol) {
            case ')':
                component = new CloseParenthesis();
                break;
            case '(':
                component = new OpenParenthesis();
                break;
            case '+':
                component = new Sum();
                break;
            case '-':
                component = new Subs();
                break;
            case 'x':
                component = new Mul();
                break;
            case '÷':
                component = new Div();
                break;
            default: throw new WrongExpression();
        }
        ++currentIndex;
        return component;
    }
}
