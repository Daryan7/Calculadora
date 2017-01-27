package com.example.juan.calculadora.Domain.Operands;

import android.util.Log;

public class Div extends Operand {

    public Div() {
        priority = 1;
    }

    @Override
    public double operate(double left, double right) {
        Log.d("div", "" + left + "/" + right);
        return left/right;
    }
}
