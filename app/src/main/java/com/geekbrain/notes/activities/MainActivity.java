package com.geekbrain.notes.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.geekbrain.notes.Notes;
import com.geekbrain.notes.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        createStorage();
        tempInitStorage();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //временный метод для создания пробных записей во внутренней памяти
    private void tempInitStorage() {
        Notes newNote = new Notes("Напоминание","Созвон", "Необходимо обязательно созвониться с начальником!");
        Notes newNote2 = new Notes("Дело","Сходить в магазин", "Накупить всякого. Молоко, сосиски, хлеб, огурцы, помидоры, кофе, чай, шашлык, творог, печенье, и что-нибудь на выбор");

        //Хранить заметки будем в ArrayList
        ArrayList<Notes> noteList = new ArrayList();
        noteList.add(newNote);
        noteList.add(newNote2);

        File storage = new File(getFilesDir(), getString(R.string.storage_file_name));
        //Записываем ArrayList с заметками в файл
        try(ObjectOutputStream objOut = new ObjectOutputStream(new FileOutputStream(storage))) {
            objOut.writeObject(noteList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //создание файла для хранения записей во внутренней памяти устройства
    private void createStorage() {
        File storage = new File(getFilesDir(),"storage.csv");
        if(!storage.exists()) {
            try {
                storage.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}