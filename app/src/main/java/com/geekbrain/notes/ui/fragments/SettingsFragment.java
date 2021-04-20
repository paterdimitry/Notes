package com.geekbrain.notes.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.geekbrain.notes.CardData;
import com.geekbrain.notes.R;
import com.geekbrain.notes.interfaces.Observer;

public class SettingsFragment extends Fragment implements Observer {


    public static Fragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }


    @Override
    public void updateCardData(CardData cardData, boolean isChange, int position) {

    }
}