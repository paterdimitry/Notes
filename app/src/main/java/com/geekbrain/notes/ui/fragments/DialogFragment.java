package com.geekbrain.notes.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.geekbrain.notes.R;
import com.geekbrain.notes.interfaces.OnDialogListener;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class DialogFragment extends BottomSheetDialogFragment {

    OnDialogListener dialogListener;
    int position;

    static final String key = "POSITION";

    public static DialogFragment newInstance(int position) {
        DialogFragment dialog = new DialogFragment();
        Bundle arg = new Bundle();
        arg.putInt(key ,position);
        dialog.setArguments(arg);
        return dialog;
    }

    public void setDialogListener(OnDialogListener dialogListener) {
        this.dialogListener = dialogListener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            position = savedInstanceState.getInt(key);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog_delete, container,
                false);
        setCancelable(false);
        view.findViewById(R.id.btnYes).setOnClickListener(v -> {
            dismiss();
            if (dialogListener != null) dialogListener.onDialogYes(position);
        });
        view.findViewById(R.id.btnNo).setOnClickListener(v -> {
            dismiss();
            if (dialogListener != null) dialogListener.onDialogNo();
        });
        return view;
    }
}
