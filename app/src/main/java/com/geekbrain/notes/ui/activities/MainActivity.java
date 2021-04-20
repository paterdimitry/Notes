package com.geekbrain.notes.ui.activities;

import android.os.Bundle;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.geekbrain.notes.CardData;
import com.geekbrain.notes.R;
import com.geekbrain.notes.navigation.Navigation;
import com.geekbrain.notes.navigation.Publisher;
import com.geekbrain.notes.ui.fragments.AboutFragment;
import com.geekbrain.notes.ui.fragments.NoteFragment;
import com.geekbrain.notes.ui.fragments.SettingsFragment;
import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private Navigation navigation;
    private Publisher publisher = new Publisher();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        createStorage();
        tempInitStorage();
        super.onCreate(savedInstanceState);
        navigation = new Navigation(getSupportFragmentManager());
        setContentView(R.layout.activity_main);
        getNavigation().addFragment(NoteFragment.newInstance(), false, publisher);
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
                navigation.addFragment(NoteFragment.newInstance(), false, publisher);
                return true;
            }
            case R.id.about_page: {
                navigation.addFragment(AboutFragment.newInstance(), true, publisher);
                return true;
            }
            case R.id.settings_page: {
                navigation.addFragment(SettingsFragment.newInstance(), true, publisher);
                return true;
            }
        }
        return false;
    }

    private Toolbar initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        return toolbar;
    }

    //временный метод для создания пробных записей во внутренней памяти
    private void tempInitStorage() {
        CardData newNote = new CardData("Напоминание", "Созвон", "Необходимо обязательно созвониться с начальником!", new Date());
        CardData newNote2 = new CardData("Дело", "Сходить в магазин", "Накупить всякого. Молоко, сосиски, хлеб, огурцы, помидоры, кофе, чай, шашлык, творог, печенье, и что-нибудь на выбор", new Date());

        //Хранить заметки будем в ArrayList
        ArrayList<CardData> noteList = new ArrayList();
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

    public Navigation getNavigation() {
        return navigation;
    }

    public Publisher getPublisher() {
        return publisher;
    }
}