package com.geekbrain.notes.ui.fragments;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.geekbrain.notes.CardData;
import com.geekbrain.notes.R;

public class DetailFragment extends Fragment {

    public static final String ARG_PARAM1 = "note";

    private CardData note;

    public static DetailFragment newInstance(CardData note) {
        DetailFragment fragment = new DetailFragment();
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
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showNote(view);
    }

    private void showNote(View view) {
        LinearLayout linearLayout = (LinearLayout)view;
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        initTextView(linearLayout,
                note.getTitle(),
                getResources().getDimension(R.dimen.large),
                Gravity.CENTER);
        initTextView(linearLayout,
                note.getDescription(),
                getResources().getDimension(R.dimen.small),
                Gravity.START);
        initTextView(linearLayout,
                note.getDate(),
                getResources().getDimension(R.dimen.small),
                Gravity.START);
        initTextView(linearLayout,
                note.getText(),
                getResources().getDimension(R.dimen.middle),
                Gravity.FILL_HORIZONTAL);
    }

    private void initTextView(LinearLayout linearLayout, String text, float textSize, int gravity) {
        TextView textView = new TextView(getContext());
        textView.setText(text);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        textView.setGravity(gravity);
        linearLayout.addView(textView);
    }
}