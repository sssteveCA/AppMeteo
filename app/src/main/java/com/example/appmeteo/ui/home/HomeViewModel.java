package com.example.appmeteo.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Benvenuto. Con questa app puoi vedere le previsioni meteo di una località a tua scelta!");
    }

    public LiveData<String> getText() {
        return mText;
    }
}