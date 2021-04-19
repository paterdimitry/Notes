package com.geekbrain.notes.ui.fragments;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.geekbrain.notes.CardData;
import com.geekbrain.notes.CardSourceImpl;
import com.geekbrain.notes.R;
import com.geekbrain.notes.interfaces.CardSource;
import com.geekbrain.notes.ui.NoteListAdapter;

public class NoteFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note, container, false);
        noteListInit(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void noteListInit(View view) {

        CardSource noteList = new CardSourceImpl(getActivity()).init();
        RecyclerView recyclerView = view.findViewById(R.id.list_note);
        initRecyclerView(recyclerView, noteList);
    }

    private void initRecyclerView(RecyclerView recyclerView, CardSource noteList) {
        recyclerView.setHasFixedSize(false);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        NoteListAdapter noteListAdapter = new NoteListAdapter(noteList);
        recyclerView.setAdapter(noteListAdapter);

        //Подключение слушателей к адаптеру
        noteListAdapter.setItemClickListener((view, position) -> showDetails(noteList.getSource(position)));
        noteListAdapter.setItemLongClickListener((view, position) -> showPopupMenu(noteList.getSource(position), view));
    }

    //Всплывающее меню по долгому нажатию на элемент
    private boolean showPopupMenu(CardData note, View v) {
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

    private void showChangeNote(CardData note) {
        boolean isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        if (isLandscape) {
            showChangeNoteLand(note);
        } else {
            showChangeNotePort(note);
        }
    }

    private void showChangeNotePort(CardData note) {
        ChangeNoteFragment changeNote = ChangeNoteFragment.newInstance(note);
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, changeNote);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void showChangeNoteLand(CardData note) {
        ChangeNoteFragment changeNote = ChangeNoteFragment.newInstance(note);
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, changeNote);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
    }
    //метод инициализации TextView и вывода полей заметки

    //отображенеи детализации заметки. Выбор метода отображения в зависимости от ориентации экрана
    private void showDetails(CardData note) {
        boolean isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        if (isLandscape) {
            showDetailLand(note);
        } else {
            showDetailPort(note);
        }
    }

    //создание активити дял отображения фрагмента с детализацией заметки при вертикальной ориентации экрана
    private void showDetailPort(CardData note) {
        DetailFragment detail = DetailFragment.newInstance(note);
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, detail);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    //отображение фрагмента с детализацией заметки при экране в горизонтальной ориентации
    private void showDetailLand(CardData note) {
        DetailFragment detail = DetailFragment.newInstance(note);
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.side_container, detail);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
    }


}