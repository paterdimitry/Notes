package com.geekbrain.notes.fragments;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.geekbrain.notes.activities.DetailActivity;
import com.geekbrain.notes.Notes;
import com.geekbrain.notes.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class NoteFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_note, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        noteListInit(view);
    }


    private void noteListInit(View view) {
        LinearLayout linearLayout = (LinearLayout) view;
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        ArrayList<Notes> noteList = getNoteList();
        for (Notes notes : noteList) {
            initTextView(linearLayout, notes.getTitle(), 22).setOnClickListener(v -> showDetails(notes));
            initTextView(linearLayout, notes.getDescription(), 12);
        }
    }

    //метод инициализации TextView и вывода полей заметки
    private TextView initTextView(LinearLayout linearLayout, String text, int textSize) {
        TextView tvTitle = new TextView(getContext());
        tvTitle.setText(text);
        tvTitle.setTextSize(textSize);
        linearLayout.addView(tvTitle);
        return tvTitle;
    }

    //отображенеи детализации заметки. Выбор метода отображения в зависимости от ориентации экрана
    private void showDetails(Notes notes) {
        boolean isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        if (isLandscape) {
            showDetailLand(notes);
        } else {
            showDetailPort(notes);
        }
    }

    //создание активити дял отображения фрагмента с детализацией заметки при вертикальной ориентации экрана
    private void showDetailPort(Parcelable notes) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), DetailActivity.class);
        intent.putExtra(DetailFragment.ARG_PARAM1, notes);
        startActivity(intent);
    }

    //отображение фрагмента с детализацией заметки при экране в горизонтальной ориентации
    private void showDetailLand(Notes notes) {
        DetailFragment detail = DetailFragment.newInstance(notes);
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.detail, detail);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
    }

    //Чтение ArrayList с заметками из файла внутреннего хранилища
    private ArrayList<Notes> getNoteList() {
        File storage = new File(getActivity().getFilesDir(), getString(R.string.storage_file_name));
        ArrayList<Notes> noteList = null;
        try (ObjectInputStream objIn = new ObjectInputStream(new FileInputStream(storage))) {
            noteList = (ArrayList<Notes>) objIn.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return noteList;
    }
}