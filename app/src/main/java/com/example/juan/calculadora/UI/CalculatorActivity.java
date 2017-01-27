package com.example.juan.calculadora.UI;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.example.juan.calculadora.Domain.Calculator;
import com.example.juan.calculadora.R;
import com.example.juan.calculadora.UI.Listeners.FuncButtonListener;
import com.example.juan.calculadora.UI.Listeners.NumButtonListener;
import com.example.juan.calculadora.UI.Listeners.OperandButtonListener;


public class CalculatorActivity extends AppCompatActivity {
    private TextView inputField;
    private TextView outputField;
    private String actualString;

    @Override
    public void onCreate(Bundle savedInstaceState) {
        super.onCreate(savedInstaceState);
        setContentView(R.layout.activity_test);
        actualString = "0";
        inputField = (TextView)findViewById(R.id.input);
        outputField = (TextView)findViewById(R.id.result);
        Calculator calculator = new Calculator(this);
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
        findViewById(R.id._openPar).setOnClickListener(funcListener);
        findViewById(R.id._closePar).setOnClickListener(funcListener);
        findViewById(R.id._comma).setOnClickListener(funcListener);
    }

    public void newSymbol(String symbol) {
        if (actualString.equals("0")) {
            actualString = "";
        }
        actualString += symbol;
        inputField.setText(actualString);
    }

    public void newSymbol(int resId) {
        if (actualString.equals("0")) {
            actualString = "";
        }
        actualString += getResources().getString(resId);
        inputField.setText(actualString);
    }

    public void removeSymbol() {
        if (actualString.length() > 1) {
            actualString = actualString.substring(0, actualString.length()-1);
            inputField.setText(actualString);
        }
        else {
            actualString = getResources().getString(R.string._0);
            inputField.setText(actualString);
        }
    }

    public String getTextField() {
        return actualString;
    }

    public void clearField() {
        actualString = "";
        inputField.setText(getResources().getString(R.string._0));
        outputField.setText("");
    }

    public void onError() {
        Toast.makeText(this, "Worng expression", Toast.LENGTH_SHORT);
    }

    public void setResult(String number) {
        outputField.setText(number);
    }
}