package com.example.juan.theapp.UI.Listeners;

import android.view.View;

import com.example.juan.theapp.Domain.Calculator;

abstract class CalculatorListeners implements View.OnClickListener {
    Calculator calculator;

    CalculatorListeners(Calculator calculator) {
        this.calculator = calculator;
    }
}
