package com.geekbrain.notes.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.geekbrain.notes.CardData;
import com.geekbrain.notes.R;
import com.geekbrain.notes.interfaces.Observer;
import com.geekbrain.notes.navigation.Publisher;
import com.geekbrain.notes.ui.activities.MainActivity;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;
import java.util.Date;


public class ChangeNoteFragment extends Fragment implements Observer {

    private static final String ARG_PARAM1 = "Card_data";
    private static final String ARG_PARAM2 = "position";
    CardData cardData;
    int position;
    private Publisher publisher;

    private TextInputEditText title;
    private TextInputEditText description;
    private TextInputEditText text;
    private DatePicker datePicker;
    private boolean isChange = false;

    private Button button;

    public static ChangeNoteFragment newInstance(CardData cardData, int position) {
        ChangeNoteFragment fragment = new ChangeNoteFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, cardData);
        args.putInt(ARG_PARAM2, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            cardData = getArguments().getParcelable(ARG_PARAM1);
            position = getArguments().getInt(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_change_note, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        if (cardData != null) {
            isChange = true;
            populateView();
        } else {
            position = -1;
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        MainActivity activity = (MainActivity) context;
        publisher = activity.getPublisher();
    }

    @Override
    public void onDetach() {
        publisher = null;
        super.onDetach();
    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private CardData collectCardData() {
        String title = this.title.getText().toString();
        String description = this.description.getText().toString();
        String text = this.text.getText().toString();
        Date date = getDateFromDatePicker();
        return new CardData(title, description, text, date);
    }

    private Date getDateFromDatePicker() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, this.datePicker.getYear());
        cal.set(Calendar.MONTH, this.datePicker.getMonth());
        cal.set(Calendar.DAY_OF_MONTH, this.datePicker.getDayOfMonth());
        return cal.getTime();
    }

    private void initView(View view) {
        title = view.findViewById(R.id.change_title);
        description = view.findViewById(R.id.change_description);
        text = view.findViewById(R.id.change_text);
        datePicker = view.findViewById(R.id.date_picker);
        button = view.findViewById(R.id.change_confirm);
        button.setOnClickListener(v -> {
            cardData = collectCardData();
            publisher.notifySingle(cardData, isChange, position);
            getFragmentManager().popBackStack();
        });
    }

    private void populateView() {
        title.setText(cardData.getTitle());
        description.setText(cardData.getDescription());
        text.setText(cardData.getText());
        initDatePicker(cardData.getDate());
    }

    private void initDatePicker(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        this.datePicker.init(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                null);
    }

    @Override
    public void updateCardData(CardData cardData, boolean isChange, int position) {

    }
}
