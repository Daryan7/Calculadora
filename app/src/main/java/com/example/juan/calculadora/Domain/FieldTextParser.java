package com.example.juan.calculadora.Domain;

import com.example.juan.calculadora.Domain.Operands.Component;

public class FieldTextParser {
    private String text;
    private int currentIndex;

    public FieldTextParser(String text) {
        this.text = text;
        currentIndex = 0;
    }

    public boolean haveComponentsLeft() {
        return currentIndex < text.length();
    }

    public Component nextComponent() {
        return null; //TODO: implement method
    }
}
