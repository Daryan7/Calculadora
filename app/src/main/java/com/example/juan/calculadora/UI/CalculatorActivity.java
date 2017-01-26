package com.example.juan.calculadora.UI;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.juan.calculadora.Domain.Calculator;
import com.example.juan.calculadora.R;
import com.example.juan.calculadora.UI.Listeners.NumButtonListener;


public class CalculatorActivity extends AppCompatActivity {
    TextView inputField;
    Calculator calculator;

    @Override
    public void onCreate(Bundle savedInstaceState) {
        super.onCreate(savedInstaceState);
        setContentView(R.layout.activity_test);

        inputField = (TextView)findViewById(R.id.input);
        calculator = new Calculator(this);
        NumButtonListener listener = new NumButtonListener(calculator);

        findViewById(R.id._0).setOnClickListener(listener);
        findViewById(R.id._1).setOnClickListener(listener);
        findViewById(R.id._2).setOnClickListener(listener);
        findViewById(R.id._3).setOnClickListener(listener);
        findViewById(R.id._4).setOnClickListener(listener);
        findViewById(R.id._5).setOnClickListener(listener);
        findViewById(R.id._6).setOnClickListener(listener);
        findViewById(R.id._7).setOnClickListener(listener);
        findViewById(R.id._8).setOnClickListener(listener);
        findViewById(R.id._9).setOnClickListener(listener);

    }

    public void newSymbol(String symbol) {
        String fieldText = inputField.getText().toString();
        inputField.setText(fieldText + symbol);
    }
}
