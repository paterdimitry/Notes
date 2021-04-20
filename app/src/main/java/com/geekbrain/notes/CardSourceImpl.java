package com.geekbrain.notes;

import android.app.Activity;

import com.geekbrain.notes.interfaces.CardSource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CardSourceImpl implements CardSource, Serializable {

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
    public List<CardData> getDataSource() {
        return dataSource;
    }

    @Override
    public int size() {
        return dataSource.size();
    }

    @Override
    public void add(CardData cardData) {
        dataSource.add(cardData);
        writeFile();
    }

    @Override
    public void changeCard(CardData cardData, int position) {
        dataSource.get(position).setTitle(cardData.getTitle());
        dataSource.get(position).setDescription(cardData.getDescription());
        dataSource.get(position).setText(cardData.getText());
        dataSource.get(position).setDate(cardData.getDate());
        writeFile();
    }

    @Override
    public void deleteCardData(int position) {
        dataSource.remove(position);
        writeFile();
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

    private void writeFile() {
        File storage = new File(activity.getFilesDir(), activity.getString(R.string.storage_file_name));
        try (ObjectOutputStream objOut = new ObjectOutputStream(new FileOutputStream(storage))) {
            objOut.writeObject(dataSource);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
