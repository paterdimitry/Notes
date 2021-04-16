package com.geekbrain.notes.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.geekbrain.notes.Notes;
import com.geekbrain.notes.R;

public class ChangeNoteFragment extends Fragment {

    private static final String ARG_PARAM1 = "note";

    Notes note;
    public static ChangeNoteFragment newInstance(Notes note) {
        ChangeNoteFragment fragment = new ChangeNoteFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, note);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            note = getArguments().getParcelable(ARG_PARAM1);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_change_note, container, false);
    }
}