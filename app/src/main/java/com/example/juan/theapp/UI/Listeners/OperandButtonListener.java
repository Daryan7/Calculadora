package com.example.juan.theapp.UI.Listeners;

import android.view.View;

import com.example.juan.theapp.Domain.Calculator;
import com.example.juan.theapp.R;

public class OperandButtonListener extends CalculatorListeners {
    public OperandButtonListener(Calculator calculator) {
        super(calculator);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id._div:
                calculator.div();
                break;
            case R.id._mul:
                calculator.mul();
                break;
            case R.id._subs:
                calculator.subs();
                break;
            case R.id._sum:
                calculator.sum();
                break;
            default: throw new Error("Invalid id: " + view.getId());
        }
    }

}
