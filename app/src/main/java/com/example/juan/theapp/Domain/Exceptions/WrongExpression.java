package com.example.juan.theapp.Domain.Exceptions;

public class WrongExpression extends Exception {

    public enum ErrorType {
        PARENTHESIS,
        DIVISION_BY_ZERO,
        SYNTAX
    }

    private ErrorType type;

    public WrongExpression(String message) {
        super(message);
    }

    public WrongExpression(ErrorType errorType) {
        this.type = errorType;
    }

    public ErrorType getType() {
        return type;
    }
}
