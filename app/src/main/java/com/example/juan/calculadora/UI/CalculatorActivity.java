package com.example.juan.calculadora.UI;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.juan.calculadora.Domain.Calculator;
import com.example.juan.calculadora.R;
import com.example.juan.calculadora.UI.Listeners.FuncButtonListener;
import com.example.juan.calculadora.UI.Listeners.NumButtonListener;
import com.example.juan.calculadora.UI.Listeners.OperandButtonListener;


public class CalculatorActivity extends Fragment implements NavigationView.OnNavigationItemSelectedListener {
    private TextView inputField;
    private TextView outputField;
    private String actualString;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_test, container, false);
        actualString = "0";
        inputField = (TextView)rootView.findViewById(R.id.input);
        outputField = (TextView)rootView.findViewById(R.id.result);
        Calculator calculator = new Calculator(this);
        NumButtonListener numListener = new NumButtonListener(calculator);
        OperandButtonListener operandListener = new OperandButtonListener(calculator);
        FuncButtonListener funcListener = new FuncButtonListener(calculator);

        rootView.findViewById(R.id._0).setOnClickListener(numListener);
        rootView.findViewById(R.id._1).setOnClickListener(numListener);
        rootView.findViewById(R.id._2).setOnClickListener(numListener);
        rootView.findViewById(R.id._3).setOnClickListener(numListener);
        rootView.findViewById(R.id._4).setOnClickListener(numListener);
        rootView.findViewById(R.id._5).setOnClickListener(numListener);
        rootView.findViewById(R.id._6).setOnClickListener(numListener);
        rootView.findViewById(R.id._7).setOnClickListener(numListener);
        rootView.findViewById(R.id._8).setOnClickListener(numListener);
        rootView.findViewById(R.id._9).setOnClickListener(numListener);

        rootView.findViewById(R.id._subs).setOnClickListener(operandListener);
        rootView.findViewById(R.id._sum).setOnClickListener(operandListener);
        rootView.findViewById(R.id._mul).setOnClickListener(operandListener);
        rootView.findViewById(R.id._div).setOnClickListener(operandListener);

        rootView.findViewById(R.id._CLR).setOnClickListener(funcListener);
        rootView.findViewById(R.id._del).setOnClickListener(funcListener);
        rootView.findViewById(R.id._equal).setOnClickListener(funcListener);
        rootView.findViewById(R.id._openPar).setOnClickListener(funcListener);
        rootView.findViewById(R.id._closePar).setOnClickListener(funcListener);
        rootView.findViewById(R.id._comma).setOnClickListener(funcListener);

        return rootView;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
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
        actualString = "0";
        inputField.setText(getResources().getString(R.string._0));
        outputField.setText("");
    }

    public void onError() {
        Toast.makeText(getContext(), "Wrong expression", Toast.LENGTH_SHORT).show();
    }

    public void setResult(String number) {
        outputField.setText(number);
    }
}