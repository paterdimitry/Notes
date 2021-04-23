package com.geekbrain.notes.ui.fragments;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.geekbrain.notes.CardData;
import com.geekbrain.notes.CardSourceFirebaseImpl;
import com.geekbrain.notes.R;
import com.geekbrain.notes.interfaces.CardSource;
import com.geekbrain.notes.interfaces.CardSourceResponse;
import com.geekbrain.notes.interfaces.Observer;
import com.geekbrain.notes.navigation.Navigation;
import com.geekbrain.notes.navigation.Publisher;
import com.geekbrain.notes.ui.NoteListAdapter;
import com.geekbrain.notes.ui.activities.MainActivity;

public class NoteFragment extends Fragment implements Observer {

    private Navigation navigation;
    private Publisher publisher;
    private CardSource data;
    private NoteListAdapter noteListAdapter;

    public static NoteFragment newInstance() {
        return new NoteFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note, container, false);
        noteListInit(view);
        data = new CardSourceFirebaseImpl().init(new CardSourceResponse() {
            @Override
            public void initialized(CardSource cardSource) {
                noteListAdapter.notifyDataSetChanged();
            }
        });
        noteListAdapter.setDataSource(data);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        MainActivity activity = (MainActivity) context;
        navigation = activity.getNavigation();
        publisher = activity.getPublisher();
    }

    @Override
    public void onDetach() {
        navigation = null;
        publisher = null;
        super.onDetach();
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = requireActivity().getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int position = noteListAdapter.getMenuPosition();
        switch (item.getItemId()) {
            case R.id.add_popup: {
                showChangeNote(null, data.size());
                return true;
            }
            case R.id.detail_popup: {
                showDetails(data.getSource(position));
                return true;
            }
            case R.id.change_popup: {
                showChangeNote(data.getSource(position), position);
                return true;
            }
            case R.id.delete_popup: {
                data.deleteCardData(position);
                noteListAdapter.notifyItemRemoved(position);
                Toast.makeText(getContext(), getResources().getText(R.string.deleted).toString(), Toast.LENGTH_LONG).show();
                return true;
            }
        }
        return super.onContextItemSelected(item);

    }

    private void noteListInit(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.list_note);
        initRecyclerView(recyclerView, data);
    }

    private void initRecyclerView(RecyclerView recyclerView, CardSource data) {
        recyclerView.setHasFixedSize(false);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        noteListAdapter = new NoteListAdapter(this);
        recyclerView.setAdapter(noteListAdapter);

        noteListAdapter.setItemClickListener((view, position) -> NoteFragment.this.showDetails(data.getSource(position)));
    }

    private void showChangeNote(CardData note, int position) {
        boolean isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        if (isLandscape) {
            showChangeNoteLand(note, position);
        } else {
            showChangeNotePort(note, position);
        }
    }

    private void showChangeNotePort(CardData note, int position) {
        navigation.addFragment(ChangeNoteFragment.newInstance(note, position), true, publisher);
    }

    private void showChangeNoteLand(CardData note, int position) {
        navigation.addSideFragment(ChangeNoteFragment.newInstance(note, position), false, publisher);

    }

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
        navigation.addFragment(DetailFragment.newInstance(note), true, publisher);
    }

    //отображение фрагмента с детализацией заметки при экране в горизонтальной ориентации
    private void showDetailLand(CardData note) {
        navigation.addSideFragment(DetailFragment.newInstance(note), false, publisher);
    }


    @Override
    public void updateCardData(CardData cardData, boolean isChange, int position) {
        if (isChange) {
            data.changeCard(cardData, position);
        } else {
            data.add(cardData);
        }
    }
}