package com.example.onlineexchangeratecalcultor;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.example.onlineexchangeratecalcultor.databinding.ActivityMainBinding;
import com.example.onlineexchangeratecalcultor.helper.SharedPref;
import com.example.onlineexchangeratecalcultor.viewmodel.CurrencyViewModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {
    ActivityMainBinding activityMainBinding;
    ArrayList<String> listOfKeys;
    ArrayList<Double> listOfValue;
    String valueFromCurrency;
    private CurrencyViewModel currencyViewModel;
    private SharedPref sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());
        currencyViewModel = new ViewModelProvider(MainActivity.this).get(CurrencyViewModel.class);
        sharedPreferences = new SharedPref(getApplicationContext());
        currencyViewModel.observerCurrency();
        ObserverRateCurrency();
    }

    public void ObserverRateCurrency() {
        currencyViewModel.getRatesMutableLiveData().observe(MainActivity.this, stringDoubleHashMap -> {
            Set<String> keySet = stringDoubleHashMap.keySet();
            ArrayList<String> listOfKeys = new ArrayList<>(keySet);
            Collection<Double> values = stringDoubleHashMap.values();
            ArrayList<Double> listOfValues = new ArrayList<>(values);
            updateListRateName(listOfKeys);
            updateListRateName2(listOfKeys);
            updateListValues(listOfValues);
            eventFromSpinner();
            eventToSpinner();
            eventEdtText();
            Log.i("musa",stringDoubleHashMap+"");
        });

    }

    public void updateListRateName(ArrayList<String> arrayList) {
        this.listOfKeys = arrayList;
        untilizeFromSpinner();
    }

    public void updateListRateName2(ArrayList<String> arrayList) {
        this.listOfKeys = arrayList;
        untilizeToSpinner();
    }

    public void updateListValues(ArrayList<Double> arrayList) {
        this.listOfValue = arrayList;
    }

    public void untilizeFromSpinner() {
        ArrayAdapter<String> adapterCurrency = new ArrayAdapter<>(
                MainActivity.this,
                android.R.layout.simple_spinner_dropdown_item, listOfKeys);
        adapterCurrency.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);
        activityMainBinding.includeMain.spinnerFromCurrency.setAdapter(adapterCurrency);
    }

    public void untilizeToSpinner() {
        Log.i("haji", listOfKeys + "");
        ArrayAdapter<String> adapterCurrency = new ArrayAdapter<>(
                MainActivity.this,
                android.R.layout.simple_spinner_dropdown_item, listOfKeys);
        adapterCurrency.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);
        activityMainBinding.includeMain.spinnerToCurrency.setAdapter(adapterCurrency);
    }


    public void eventFromSpinner() {
        activityMainBinding.includeMain.spinnerFromCurrency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                valueFromCurrency = String.valueOf(listOfValue.get(i));
                activityMainBinding.includeMain.edtCurrency.setText(valueFromCurrency);
                String rateName = listOfKeys.get(i);
                Log.i("j",rateName);
                sharedPreferences.putString("keyFrom",rateName);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    public void eventToSpinner() {
        activityMainBinding.includeMain.spinnerToCurrency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String value = String.valueOf(listOfValue.get(i));
                sharedPreferences.putString("newValue",value);
                activityMainBinding.includeMain.edtToCurrency.setText(value);
                String rateName = listOfKeys.get(i);
                sharedPreferences.putString("keyTo",rateName);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void eventEdtText() {
        String valueTo = sharedPreferences.getString("newValue");
        activityMainBinding.includeMain.edtCurrency.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    Log.i("i", i1 + "");
                    String valueChange = charSequence.toString();
                    String valueCurrent = charSequence.toString();
                    double valueChangeDouble;
                    double valueCurrentDouble;


                        try {
                            valueChangeDouble = Double.parseDouble(valueChange);
                            valueCurrentDouble = Double.parseDouble(valueCurrent);
                        } catch (NumberFormatException e) {
                            valueChangeDouble = 0;
                            valueCurrentDouble = 0;
                        }

                        double result = valueChangeDouble * valueCurrentDouble;
                        String resultStr = String.valueOf(result);
                        activityMainBinding.includeMain.edtToCurrency.setText(resultStr);
                    }


                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }

}