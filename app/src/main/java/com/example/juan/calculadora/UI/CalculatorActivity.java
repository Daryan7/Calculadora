package com.example.juan.calculadora.UI;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.juan.calculadora.Domain.Calculator;
import com.example.juan.calculadora.R;
import com.example.juan.calculadora.UI.Comunication.OnFragmentInteractionListener;
import com.example.juan.calculadora.UI.Listeners.FuncButtonListener;
import com.example.juan.calculadora.UI.Listeners.NumButtonListener;
import com.example.juan.calculadora.UI.Listeners.OperandButtonListener;


public class CalculatorActivity extends Fragment {
    private TextView inputField;
    private TextView outputField;
    private String actualString;
    private OnFragmentInteractionListener mListener;
    private boolean toast, state;
    private boolean nextInputResets;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setHasOptionsMenu(true);
        nextInputResets = false;
    }

    @Override
    public void onStart() {
        super.onStart();
        SharedPreferences notificationSettings = getActivity().getSharedPreferences("notificationSettings", Context.MODE_PRIVATE);
        toast = notificationSettings.getBoolean("toast", false);
        state = notificationSettings.getBoolean("state", false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_calculator, container, false);
        actualString = "0";
        inputField = (TextView)rootView.findViewById(R.id.input);
        outputField = (TextView)rootView.findViewById(R.id.result);
        final Calculator calculator = new Calculator(this);
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

        rootView.findViewById(R.id._ans).setOnClickListener(funcListener);
        rootView.findViewById(R.id._del).setOnClickListener(funcListener);
        rootView.findViewById(R.id._equal).setOnClickListener(funcListener);
        rootView.findViewById(R.id._openPar).setOnClickListener(funcListener);
        rootView.findViewById(R.id._closePar).setOnClickListener(funcListener);
        rootView.findViewById(R.id._comma).setOnClickListener(funcListener);
        rootView.findViewById(R.id._del).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                calculator.clear();
                return true;
            }
        });

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.activity_calculator, menu);
        menu.findItem(R.id.toastNotification).setChecked(toast);
        menu.findItem(R.id.stateNotification).setChecked(state);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }
        else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.toastNotification:
                toast = !toast;
                item.setChecked(toast);
                return true;
            case R.id.stateNotification:
                state = !state;
                item.setChecked(state);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void newSymbol(String symbol) {
        if (actualString.equals("0") || nextInputResets) {
            actualString = "";
            nextInputResets = false;
        }
        actualString += symbol;
        inputField.setText(actualString);
    }

    public void newSymbol(int resId) {
        if (actualString.equals("0") || nextInputResets) {
            actualString = "";
            nextInputResets = false;
        }
        actualString += getResources().getString(resId);
        inputField.setText(actualString);
    }

    public void removeSymbols(int numberOfSymbols) {
        if (actualString.length() > numberOfSymbols) {
            actualString = actualString.substring(0, actualString.length()-numberOfSymbols);
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
        if (toast) {
            Toast.makeText(getContext(), getActivity().getResources().getString(R.string.toast_notification_text), Toast.LENGTH_SHORT).show();
        }
    }

    public void setResult(String number) {
        outputField.setText(number);
    }

    @Override
    public void onStop() {
        super.onStop();

        SharedPreferences notificationSettings = getActivity().getSharedPreferences("notificationSettings", 0);
        SharedPreferences.Editor editor = notificationSettings.edit();

        editor.putBoolean("toast", toast);
        editor.putBoolean("state", state);

        editor.apply();
    }

    public void resetNextInput() {
        nextInputResets = true;
    }

    public boolean doesNextInputResets() {
        return nextInputResets;
    }
}