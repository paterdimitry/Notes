package com.geekbrain.notes.navigation;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.geekbrain.notes.R;
import com.geekbrain.notes.interfaces.Observer;

public class Navigation {

    private final FragmentManager fragmentManager;

    public Navigation(FragmentManager fragmentManager){
        this.fragmentManager = fragmentManager;
    }

    //в конструкторе фрагмента выполняется подписка в publisher
    public void addFragment(Fragment fragment, boolean useBackStack, Publisher publisher) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        publisher.subscribe((Observer) fragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        if (useBackStack) {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();
    }

    //Для создания боковых фрагментов при горизонтальной ориентации экрана. Можно было бы контейнер для фрагмента вынести в аргументы
    public void addSideFragment(Fragment fragment, boolean useBackStack, Publisher publisher) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.side_container, fragment);
        publisher.subscribe((Observer) fragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        if (useBackStack) {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();}
}

