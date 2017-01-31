package com.example.juan.myapp.UI.Listeners;

import android.view.View;

import com.example.juan.myapp.Domain.Calculator;
import com.example.juan.myapp.R;

public class NumButtonListener extends CalculatorListeners {

    public NumButtonListener(Calculator calculator) {
        super(calculator);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id._0:
                calculator.newDigit(0);
                break;
            case R.id._1:
                calculator.newDigit(1);
                break;
            case R.id._2:
                calculator.newDigit(2);
                break;
            case R.id._3:
                calculator.newDigit(3);
                break;
            case R.id._4:
                calculator.newDigit(4);
                break;
            case R.id._5:
                calculator.newDigit(5);
                break;
            case R.id._6:
                calculator.newDigit(6);
                break;
            case R.id._7:
                calculator.newDigit(7);
                break;
            case R.id._8:
                calculator.newDigit(8);
                break;
            case R.id._9:
                calculator.newDigit(9);
                break;
            default:
                throw new Error("Invalid id: " + view.getId());
        }
    }
}
