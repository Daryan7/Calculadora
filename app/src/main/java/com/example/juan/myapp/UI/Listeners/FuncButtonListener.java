package com.example.juan.myapp.UI.Listeners;

import android.view.View;

import com.example.juan.myapp.Domain.Calculator;
import com.example.juan.myapp.R;

public class FuncButtonListener extends CalculatorListeners {

    public FuncButtonListener(Calculator calculator) {
        super(calculator);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id._ans:
                calculator.ans();
                break;
            case R.id._del:
                calculator.del();
                break;
            case R.id._comma:
                calculator.comma();
                break;
            case R.id._closePar:
                calculator.closePar();
                break;
            case R.id._openPar:
                calculator.openPar();
                break;
            case R.id._equal:
                calculator.calculate();
                break;
            default: throw new Error("Invalid id: " + view.getId());
        }
    }
}
