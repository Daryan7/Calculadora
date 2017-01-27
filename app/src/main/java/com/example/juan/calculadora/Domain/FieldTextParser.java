package com.example.juan.calculadora.Domain;

import com.example.juan.calculadora.Domain.DataStructures.Stack;
import com.example.juan.calculadora.Domain.Operands.CloseParenthesis;
import com.example.juan.calculadora.Domain.Operands.Component;
import com.example.juan.calculadora.Domain.Operands.Div;
import com.example.juan.calculadora.Domain.Operands.Mul;
import com.example.juan.calculadora.Domain.Operands.MyNumber;
import com.example.juan.calculadora.Domain.Operands.Subs;
import com.example.juan.calculadora.Domain.Operands.Sum;

public class FieldTextParser {
    private String text;
    private int currentIndex;
    private int openPar = 0;

    private boolean isNumber(char symbol) {
        /*return symbol == '0' || symbol == '1' || symbol == '2' || symbol == '3'
                || symbol == '4' || symbol == '5' || symbol=='6' || symbol == '7'
                || symbol == '8' || symbol == '9';*/
        return symbol >= 48 || symbol <= 57;
    }

    private boolean isOperand(char symbol) {
        return symbol == '+' || symbol == '-' || symbol == 'x' || symbol == '÷';
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

    public Component nextComponent() {
        char currentChar = text.charAt(currentIndex);
        if (currentIndex == 0) {
            if (currentChar == '-' && text.length() > 1) {
                ++currentIndex;
                if (isNumber(text.charAt(currentIndex))) {
                    return readNumber();
                }
                else return null;
            }
            else if (isNumber(currentChar)) {
                return readNumber();
            }
            return null;
        }
        else if (currentChar == ')') {
            if (openPar == 0) return null;
            else {
                --openPar;
                return new CloseParenthesis();
            }
        }
        else if (currentChar == '(') {
            ++openPar;
            return new CloseParenthesis();
        }
        else if (isOperand(currentChar)) {
            char prevSymbol = text.charAt(currentIndex-1);
            if (currentChar == '-') {
                if (prevSymbol == '(') {
                    ++currentChar;
                    if (currentChar < text.length() && isNumber(text.charAt(currentChar))) {
                        return readNumber();
                    }
                    else return null;
                }
            }
            if (isNumber(prevSymbol) || prevSymbol == ')') {
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
                ++currentChar;
            }
            else return null;
        }
        else if (isNumber(currentChar)) return readNumber();
        return null;
    }
}
