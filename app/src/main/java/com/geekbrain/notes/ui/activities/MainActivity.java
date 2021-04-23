package com.geekbrain.notes.ui.activities;

import android.os.Bundle;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.geekbrain.notes.R;
import com.geekbrain.notes.navigation.Navigation;
import com.geekbrain.notes.navigation.Publisher;
import com.geekbrain.notes.ui.fragments.AboutFragment;
import com.geekbrain.notes.ui.fragments.NoteFragment;
import com.geekbrain.notes.ui.fragments.SettingsFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private Navigation navigation;
    private Publisher publisher = new Publisher();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

    public Navigation getNavigation() {
        return navigation;
    }

    public Publisher getPublisher() {
        return publisher;
    }
}