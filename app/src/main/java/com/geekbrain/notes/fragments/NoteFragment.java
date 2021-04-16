package com.geekbrain.notes.fragments;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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
            initTextView(linearLayout, notes,
                    notes.getTitle(),
                    getResources().getDimension(R.dimen.large));
            initTextView(linearLayout,
                    notes, notes.getDescription(),
                    getResources().getDimension(R.dimen.small));
        }
    }

    private TextView initTextView(LinearLayout linearLayout, Notes notes, String text, float textSize) {
        TextView tv = new TextView(getContext());
        tv.setText(text);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        linearLayout.addView(tv);
        tv.setOnLongClickListener(v -> showPopupMenu(notes, v));
        tv.setOnClickListener(v -> showDetails(notes));
        return tv;
    }

    //Всплывающее меню по долгому нажатию на элемент
    private boolean showPopupMenu(Notes note, View v) {
        Activity activity = requireActivity();
        PopupMenu popupMenu = new PopupMenu(activity, v);
        activity.getMenuInflater().inflate(R.menu.popup, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();
            switch (id) {
                case R.id.detail_popup:
                    showDetails(note);
                    break;
                case R.id.change_popup:
                    showChangeNote(note);
                    break;
                case R.id.delete_popup:
                    Toast.makeText(getContext(), "Удалить", Toast.LENGTH_LONG).show();
                    break;
            }

            return false;
        });
        popupMenu.show();
        return true;
    }

    private void showChangeNote(Notes note) {
        boolean isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        if (isLandscape) {
            showChangeNoteLand(note);
        } else {
            showChangeNotePort(note);
        }
    }

    private void showChangeNotePort(Notes note) {
        ChangeNoteFragment changeNote = ChangeNoteFragment.newInstance(note);
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, changeNote);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void showChangeNoteLand(Notes note) {
        ChangeNoteFragment changeNote = ChangeNoteFragment.newInstance(note);
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, changeNote);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
    }
    //метод инициализации TextView и вывода полей заметки

    //отображенеи детализации заметки. Выбор метода отображения в зависимости от ориентации экрана
    private void showDetails(Notes note) {
        boolean isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        if (isLandscape) {
            showDetailLand(note);
        } else {
            showDetailPort(note);
        }
    }

    //создание активити дял отображения фрагмента с детализацией заметки при вертикальной ориентации экрана
    private void showDetailPort(Notes note) {
        DetailFragment detail = DetailFragment.newInstance(note);
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, detail);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    //отображение фрагмента с детализацией заметки при экране в горизонтальной ориентации
    private void showDetailLand(Notes note) {
        DetailFragment detail = DetailFragment.newInstance(note);
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.side_container, detail);
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