package com.geekbrain.notes.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.geekbrain.notes.CardData;
import com.geekbrain.notes.R;
import com.geekbrain.notes.interfaces.Observer;

public class AboutFragment extends Fragment implements Observer {

    public static Fragment newInstance() {
        return new AboutFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about, container, false);
    }

    @Override
    public void updateCardData(CardData cardData, boolean isChange, int position) {

    }
}