package com.example.onlineexchangeratecalcultor;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.onlineexchangeratecalcultor.databinding.ActivityMainBinding;
import com.example.onlineexchangeratecalcultor.helper.SharedPref;
import com.example.onlineexchangeratecalcultor.model.RateKey;
import com.example.onlineexchangeratecalcultor.model.Rates;
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
    private String value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());
        currencyViewModel = new ViewModelProvider(MainActivity.this).get(CurrencyViewModel.class);
        sharedPreferences = new SharedPref(getApplicationContext());
        connectInternet();

    }

    public void connectInternet() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean connected = (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState()
                == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState()
                        == NetworkInfo.State.CONNECTED);
        if (connected) {
            currencyViewModel.observerCurrency();
            ObserverRateCurrency();
        } else {
            currencyViewModel.getLocalRateKey();
            currencyViewModel.getLocalRate();
            observerLiveDataRateKey();
            observerLiveDataValue();
        }
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
            insertRateKey(listOfKeys);
            insertRate(listOfValues);

        });

    }

    public void insertRateKey(ArrayList<String> listOfKeys) {
        for (int i = 0; i < listOfKeys.size(); i++) {
            String name = listOfKeys.get(i);
            RateKey rateKey = new RateKey(name);
            currencyViewModel.insertRateKey(rateKey);
        }
    }

    public void insertRate(ArrayList<Double> listOfValue) {
        for (int i = 0; i < listOfValue.size(); i++) {
            double rateValue = listOfValue.get(i);
            Rates rates = new Rates(rateValue);
            Log.i("insertValue", rateValue + "");
            currencyViewModel.insertRate(rates);
        }
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
                value = String.valueOf(listOfValue.get(i));
                sharedPreferences.putString("newValue", value);
                activityMainBinding.includeMain.edtToCurrency.setText(value);
                String rateName = listOfKeys.get(i);
                sharedPreferences.putString("keyTo", rateName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

        });
    }

    public void eventEdtText() {
        activityMainBinding.includeMain.edtCurrency.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String valueCurrent = charSequence.toString();
                if (value == null) {
                    value = "2.1";
                }
                double valueChangeDouble;
                double valueCurrentDouble;
                try {
                    valueChangeDouble = Double.parseDouble(value);
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

    public void observerLiveDataRateKey() {
        currencyViewModel.getLiveDataRateKey().observe(this, rateKeys -> {
            ArrayList<String> arrayList = new ArrayList<>();
            for (int i = 0; i < rateKeys.size(); i++) {
                RateKey objRate = rateKeys.get(i);
                arrayList.add(objRate.getName());
                updateListRateName(arrayList);
                updateListRateName2(arrayList);
                eventEdtText();
                eventFromSpinner();
            }
        });
    }

    public void observerLiveDataValue() {
        currencyViewModel.getLiveDataRate().observe(this, new Observer<Rates>() {
            ArrayList<Double> arrayList = new ArrayList<>();

            @Override
            public void onChanged(Rates rates) {
                arrayList.add(rates.getUsd());
                arrayList.add(rates.getAud());
                arrayList.add(rates.getAwg());
                arrayList.add(rates.getArs());
                arrayList.add(rates.getAoa());
                arrayList.add(rates.getAed());
                arrayList.add(rates.getAng());
                arrayList.add(rates.getAfn());
                arrayList.add(rates.getAll());
                arrayList.add(rates.getAmd());
                updateListValues(arrayList);
            }
        });
    }
}