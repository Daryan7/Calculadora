package com.example.juan.theapp.UI.Activities;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.juan.theapp.Domain.Calculator;
import com.example.juan.theapp.R;
import com.example.juan.theapp.UI.Listeners.FuncButtonListener;
import com.example.juan.theapp.UI.Listeners.NumButtonListener;
import com.example.juan.theapp.UI.Listeners.OperandButtonListener;

public class CalculatorFragment extends MyFragment {
    private TextView inputField;
    private TextView outputField;
    private String actualString;
    private Calculator calculator;
    private boolean toast, state;
    private boolean nextInputResets;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setHasOptionsMenu(true);
        nextInputResets = false;
        calculator = new Calculator(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        SharedPreferences notificationSettings = getActivity().getSharedPreferences("notificationSettings", Context.MODE_PRIVATE);
        toast = notificationSettings.getBoolean("toast", true);
        state = notificationSettings.getBoolean("state", false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_calculator, container, false);
        inputField = (TextView)rootView.findViewById(R.id.input);
        outputField = (TextView)rootView.findViewById(R.id.result);
        if (savedInstanceState != null) {
            actualString = savedInstanceState.getString("inputField");
            inputField.setText(actualString);
            outputField.setText(savedInstanceState.getString("outputField"));
        }
        else actualString = "";

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
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putString("inputField", actualString);
        bundle.putString("outputField", outputField.getText().toString());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.activity_calculator, menu);
        menu.findItem(R.id.toastNotification).setChecked(toast);
        menu.findItem(R.id.stateNotification).setChecked(state);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.toastNotification: {
                toast = !toast;
                item.setChecked(toast);
                SharedPreferences notificationSettings = getActivity().getSharedPreferences("notificationSettings", 0);
                SharedPreferences.Editor editor = notificationSettings.edit();
                editor.putBoolean("toast", toast);
                editor.apply();
                return true;
            }
            case R.id.stateNotification: {
                state = !state;
                SharedPreferences notificationSettings = getActivity().getSharedPreferences("notificationSettings", 0);
                SharedPreferences.Editor editor = notificationSettings.edit();
                editor.putBoolean("state", toast);
                editor.apply();
                item.setChecked(state);
                return true;
            }
            case R.id.call: {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + actualString));
                startActivity(intent);
                return true;
            }
            case R.id.openBrowser: {
                Intent intent = new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_APP_BROWSER);
                startActivity(intent);
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void newSymbol(String symbol) {
        if (nextInputResets) {
            actualString = "";
            nextInputResets = false;
        }
        actualString += symbol;
        inputField.setText(actualString);
    }

    public void removeSymbols(int numberOfSymbols) {
        if (actualString.length() > numberOfSymbols) {
            actualString = actualString.substring(0, actualString.length()-numberOfSymbols);
        }
        else {
            actualString = "";
        }
        inputField.setText(actualString);
        nextInputResets = false;
    }

    public String getTextField() {
        return actualString;
    }

    public void clearField() {
        actualString = "";
        inputField.setText("");
        outputField.setText("");
    }

    public void onError(String message) {
        if (toast) {
            Toast.makeText(getContext(), getActivity().getResources().getString(R.string.toast_notification_text), Toast.LENGTH_SHORT).show();
        }
        if (state) {
            NotificationManager mNotificationManager =
                    (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);

            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(getContext())
                            .setSmallIcon(R.drawable.ic_error)
                            .setContentTitle("Wrong Expression")
                            .setContentText(message);

            mNotificationManager.notify(0, mBuilder.build());
        }
        nextInputResets = false;
    }

    public void setResult(String number) {
        outputField.setText(number);
    }

    public void resetNextInput() {
        nextInputResets = true;
    }

    public boolean doesNextInputResets() {
        return nextInputResets;
    }

    public char lastSymbol() {
        return actualString.charAt(actualString.length()-1);
    }

    public int fieldLength() {
        return actualString.length();
    }
}