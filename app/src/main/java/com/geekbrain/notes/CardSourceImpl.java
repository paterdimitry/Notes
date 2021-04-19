package com.geekbrain.notes;

import android.app.Activity;

import com.geekbrain.notes.interfaces.CardSource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

public class CardSourceImpl implements CardSource {

    List<CardData> dataSource;
    Activity activity;

    public CardSourceImpl(Activity activity) {
        this.activity = activity;
        dataSource = new ArrayList<>();
    }

    @Override
    public CardData getSource(int position) {
        return dataSource.get(position);
    }

    @Override
    public int size() {
        return dataSource.size();
    }

    //Чтение ArrayList с заметками из файла внутреннего хранилища
    public CardSourceImpl init() {
        File storage = new File(activity.getFilesDir(), activity.getString(R.string.storage_file_name));
        try (ObjectInputStream objIn = new ObjectInputStream(new FileInputStream(storage))) {
            dataSource = (ArrayList<CardData>) objIn.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return this;
    }
}
