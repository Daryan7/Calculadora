package com.example.juan.calculadora.UI;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.juan.calculadora.Domain.Calculator;
import com.example.juan.calculadora.R;
import com.example.juan.calculadora.UI.Listeners.FuncButtonListener;
import com.example.juan.calculadora.UI.Listeners.NumButtonListener;
import com.example.juan.calculadora.UI.Listeners.OperandButtonListener;


public class CalculatorActivity extends AppCompatActivity {
    TextView inputField;
    Calculator calculator;

    @Override
    public void onCreate(Bundle savedInstaceState) {
        super.onCreate(savedInstaceState);
        setContentView(R.layout.activity_test);

        inputField = (TextView)findViewById(R.id.input);
        calculator = new Calculator(this);
        NumButtonListener numListener = new NumButtonListener(calculator);
        OperandButtonListener operandListener = new OperandButtonListener(calculator);
        FuncButtonListener funcListener = new FuncButtonListener(calculator);

        findViewById(R.id._0).setOnClickListener(numListener);
        findViewById(R.id._1).setOnClickListener(numListener);
        findViewById(R.id._2).setOnClickListener(numListener);
        findViewById(R.id._3).setOnClickListener(numListener);
        findViewById(R.id._4).setOnClickListener(numListener);
        findViewById(R.id._5).setOnClickListener(numListener);
        findViewById(R.id._6).setOnClickListener(numListener);
        findViewById(R.id._7).setOnClickListener(numListener);
        findViewById(R.id._8).setOnClickListener(numListener);
        findViewById(R.id._9).setOnClickListener(numListener);

        findViewById(R.id._subs).setOnClickListener(operandListener);
        findViewById(R.id._sum).setOnClickListener(operandListener);
        findViewById(R.id._mul).setOnClickListener(operandListener);
        findViewById(R.id._div).setOnClickListener(operandListener);

        findViewById(R.id._CLR).setOnClickListener(funcListener);
        findViewById(R.id._del).setOnClickListener(funcListener);
        findViewById(R.id._equal).setOnClickListener(funcListener);
        findViewById(R.id._par).setOnClickListener(funcListener);
        findViewById(R.id._sign).setOnClickListener(funcListener);
        findViewById(R.id._comma).setOnClickListener(funcListener);
    }

    public void newSymbol(String symbol) {
        String fieldText = inputField.getText().toString();
        inputField.setText(fieldText + symbol);
    }

    public void newSymbol(int resId) {
        String fieldText = inputField.getText().toString();
        inputField.setText(fieldText + getResources().getString(resId));
    }

    public void removeSymbol() {
        String fieldText = inputField.getText().toString();
        if (fieldText.length() > 1) inputField.setText(fieldText.substring(0, fieldText.length()-1));
        else inputField.setText(getResources().getString(R.string._0));
    }

    public void clearField() {
        inputField.setText(getResources().getString(R.string._0));
    }
}