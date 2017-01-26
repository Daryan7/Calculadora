package com.example.juan.calculadora.UI.Listeners;

import android.view.View;

import com.example.juan.calculadora.Domain.Calculator;
import com.example.juan.calculadora.R;

public class FuncButtonListener extends CalculatorListeners {

    public FuncButtonListener(Calculator calculator) {
        super(calculator);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id._CLR:
                calculator.clear();
                break;
            case R.id._del:
                calculator.del();
                break;
            case R.id._comma:
                calculator.comma();
                break;
            case R.id._sign:
                calculator.changeSign();
                break;
            case R.id._par:
                calculator.parenthesis();
                break;
            case R.id._equal:
                calculator.calculate();
                break;
            default: throw new Error("Invalid id: " + view.getId());
        }
    }
}
