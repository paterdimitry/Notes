package com.geekbrain.notes.activities;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;

import com.geekbrain.notes.fragments.AboutFragment;
import com.geekbrain.notes.Notes;
import com.geekbrain.notes.R;
import com.geekbrain.notes.fragments.NoteFragment;
import com.geekbrain.notes.fragments.SettingsFragment;
import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        createStorage();
        tempInitStorage();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        openNoteFragment();
        initView();
    }

    private void initView() {
        Toolbar toolbar = initToolbar();
        initDrawer(toolbar);
    }

    private void initDrawer(Toolbar toolbar) {
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (navigateFragment(id)) {
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
            return false;
        });

    }

    private boolean navigateFragment(int id) {
        switch (id) {
            case R.id.main_page: {
                openNoteFragment();
                return true;
            }
            case R.id.about_page: {
                openAboutFragment();
                return true;
            }
            case R.id.settings_page: {
                openSettingsFragment();
                return true;
            }
        }
        return false;
    }

    private void openNoteFragment() {
        NoteFragment noteFragment = new NoteFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, noteFragment).commit();
    }

    private void openAboutFragment() {
        AboutFragment aboutFragment = new AboutFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, aboutFragment).commit();
    }

    private void openSettingsFragment() {
        SettingsFragment settingsFragment = new SettingsFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, settingsFragment).commit();
    }

    private Toolbar initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        return toolbar;
    }

    //временный метод для создания пробных записей во внутренней памяти
    private void tempInitStorage() {
        Notes newNote = new Notes("Напоминание", "Созвон", "Необходимо обязательно созвониться с начальником!");
        Notes newNote2 = new Notes("Дело", "Сходить в магазин", "Накупить всякого. Молоко, сосиски, хлеб, огурцы, помидоры, кофе, чай, шашлык, творог, печенье, и что-нибудь на выбор");

        //Хранить заметки будем в ArrayList
        ArrayList<Notes> noteList = new ArrayList();
        noteList.add(newNote);
        noteList.add(newNote2);

        File storage = new File(getFilesDir(), getString(R.string.storage_file_name));
        //Записываем ArrayList с заметками в файл
        try (ObjectOutputStream objOut = new ObjectOutputStream(new FileOutputStream(storage))) {
            objOut.writeObject(noteList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //создание файла для хранения записей во внутренней памяти устройства
    private void createStorage() {
        File storage = new File(getFilesDir(), "storage.csv");
        if (!storage.exists()) {
            try {
                storage.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}