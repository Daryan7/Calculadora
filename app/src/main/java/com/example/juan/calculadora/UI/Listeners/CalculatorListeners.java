package com.example.juan.calculadora.UI.Listeners;

import android.view.View;

import com.example.juan.calculadora.Domain.Calculator;

abstract class CalculatorListeners implements View.OnClickListener {
    Calculator calculator;

    CalculatorListeners(Calculator calculator) {
        this.calculator = calculator;
    }
}
