package com.example.juan.calculadora.Domain;

import com.example.juan.calculadora.Domain.Exceptions.WrongExpression;
import com.example.juan.calculadora.Domain.Operands.Ans;
import com.example.juan.calculadora.Domain.Operands.CloseParenthesis;
import com.example.juan.calculadora.Domain.Operands.Token;
import com.example.juan.calculadora.Domain.Operands.Div;
import com.example.juan.calculadora.Domain.Operands.Mul;
import com.example.juan.calculadora.Domain.Operands.MyNumber;
import com.example.juan.calculadora.Domain.Operands.OpenParenthesis;
import com.example.juan.calculadora.Domain.Operands.Subs;
import com.example.juan.calculadora.Domain.Operands.Sum;

public class FieldTextParser {
    public final static String sum = "+";
    public final static char sumChar = '+';
    public final static String subs = "-";
    public final static char subsChar = '-';
    public final static String mul = "x";
    public final static char mulChar = 'x';
    public final static String div = "÷";
    public final static char divChar = '÷';
    public final static String commma = ".";
    public final static char commaChar = '.';
    public final static String openPar = "(";
    public final static char openParChar = '(';
    public final static String closePar = ")";
    public final static char closeParChar = ')';
    public final static String ans = "Ans";

    private String text;
    private int currentIndex;

    private boolean isNumber(char symbol) {
        return symbol >= '0' && symbol <= '9';
    }

    private boolean isLetter(char symbol) {
        return ((symbol >= 'A' && symbol <= 'Z') || (symbol >= 'a' && symbol <= 'z')) && symbol != mulChar;
    }

    public FieldTextParser(String text) {
        this.text = text;
        currentIndex = 0;
    }

    public boolean haveTokensLeft() {
        return currentIndex < text.length();
    }

    private MyNumber readNumber() throws WrongExpression {
        int initialIndex = currentIndex;
        boolean hasComma = false;
        while (currentIndex < text.length()) {
            char currentChar = text.charAt(currentIndex);
            if (currentChar == commaChar) {
                if (hasComma) throw new WrongExpression("Found invalid number, it has more than one comma");
                hasComma = true;
            }
            else if (!isNumber(currentChar)) break;
            ++currentIndex;
        }
        return new MyNumber(text.substring(initialIndex, currentIndex));
    }

    private Ans readAnsWord() throws WrongExpression {
        if (currentIndex+2 < text.length() && text.substring(currentIndex, currentIndex+3).equals(ans)) {
            currentIndex += 3;
            return new Ans();
        }
        throw new WrongExpression("Found invalid word while parsing");
    }

    public Token nextToken() throws WrongExpression {
        if (currentIndex >= text.length()) throw new WrongExpression("Tried to parse after end of expression, index found " + currentIndex + " but text has length " + text.length());
        char currentSymbol = text.charAt(currentIndex);
        if (isNumber(currentSymbol)) {
            return readNumber();
        }
        if (isLetter(currentSymbol)) {
            return readAnsWord();
        }
        Token token;
        switch (currentSymbol) {
            case closeParChar:
                token = new CloseParenthesis();
                break;
            case openParChar:
                token = new OpenParenthesis();
                break;
            case sumChar:
                token = new Sum();
                break;
            case subsChar:
                token = new Subs();
                break;
            case mulChar:
                token = new Mul();
                break;
            case divChar:
                token = new Div();
                break;
            default: throw new WrongExpression("Found invalid symbol while parsing: " + currentSymbol);
        }
        ++currentIndex;
        return token;
    }
}
