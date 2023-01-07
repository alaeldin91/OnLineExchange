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
import java.util.List;
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
        eventFromSpinner();
        eventToSpinner();
        eventEdtText();
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
            insertRateKey(listOfKeys);
            insertRate(listOfValues);
        });

    }

    public void insertRateKey(ArrayList<String> listOfKeys) {
        RateKey rateKey = new RateKey(listOfKeys.get(0), listOfKeys.get(1), listOfKeys.get(2),
                listOfKeys.get(3), listOfKeys.get(4),
                listOfKeys.get(5), listOfKeys.get(6), listOfKeys.get(7),
                listOfKeys.get(8), listOfKeys.get(9));
        currencyViewModel.insertRateKey(rateKey);

    }

    public void insertRate(ArrayList<Double> listOfValue) {
        Rates rates = new Rates(listOfValue.get(0), listOfValue.get(1), listOfValue.get(2),
                listOfValue.get(3), listOfValue.get(4),
                listOfValue.get(5), listOfValue.get(6), listOfValue.get(7),
                listOfValue.get(8), listOfValue.get(9));
        currencyViewModel.insertRate(rates);
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
            ArrayList<RateKey> arrayListRate = (ArrayList) rateKeys;
            for (int i = 0; i < rateKeys.size(); i++) {
                arrayList.add(arrayListRate.get(i).getUsd());
                arrayList.add(arrayListRate.get(i).getAed());
                arrayList.add(arrayListRate.get(i).getAfn());
                arrayList.add(arrayListRate.get(i).getAll());
                arrayList.add(arrayListRate.get(i).getArs());
                arrayList.add(arrayListRate.get(i).getAmd());
                arrayList.add(arrayListRate.get(i).getAng());
                arrayList.add(arrayListRate.get(i).getAoa());
                arrayList.add(arrayListRate.get(i).getAwg());
                arrayList.add(arrayListRate.get(i).getAud());

            }
            updateListRateName(arrayList);
            updateListRateName2(arrayList);
            Log.i("rateName", arrayList + "");
        });
    }

    public void observerLiveDataValue() {
        currencyViewModel.getLiveDataRate().observe(this, new Observer<List<Rates>>() {
            ArrayList<Double> arrayListDouble = new ArrayList<>();

            @Override
            public void onChanged(List<Rates> rates) {
                ArrayList<Rates> arrayListRate = (ArrayList) rates;
                for (int i = 0; i < arrayListRate.size(); i++) {
                    arrayListDouble.add(arrayListRate.get(i).getUsd());
                    arrayListDouble.add(arrayListRate.get(i).getAed());
                    arrayListDouble.add(arrayListRate.get(i).getAfn());
                    arrayListDouble.add(arrayListRate.get(i).getAll());
                    arrayListDouble.add(arrayListRate.get(i).getArs());
                    arrayListDouble.add(arrayListRate.get(i).getAmd());
                    arrayListDouble.add(arrayListRate.get(i).getAng());
                    arrayListDouble.add(arrayListRate.get(i).getAoa());
                    arrayListDouble.add(arrayListRate.get(i).getAwg());
                    arrayListDouble.add(arrayListRate.get(i).getAud());
                }
                updateListValues(arrayListDouble);
                Log.i("arr", arrayListDouble + "");
            }
        });
    }
}