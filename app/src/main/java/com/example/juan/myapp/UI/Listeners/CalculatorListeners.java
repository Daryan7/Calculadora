package com.example.juan.myapp.UI.Listeners;

import android.view.View;

import com.example.juan.myapp.Domain.Calculator;

abstract class CalculatorListeners implements View.OnClickListener {
    Calculator calculator;

    CalculatorListeners(Calculator calculator) {
        this.calculator = calculator;
    }
}
