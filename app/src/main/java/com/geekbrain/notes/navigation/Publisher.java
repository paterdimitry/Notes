package com.geekbrain.notes.navigation;

import com.geekbrain.notes.CardData;
import com.geekbrain.notes.interfaces.Observer;

import java.util.ArrayList;
import java.util.List;

public class Publisher {

    List<Observer> observers;

    public Publisher() {
        observers = new ArrayList<>();
    }

    public void subscribe(Observer observer) {
        observers.add(observer);
    }

    public void unsubscribe() {
        observers.clear();
    }

    public void notifySingle(CardData cardData, boolean isChange, int position) {
        for (Observer observer : observers) {
            observer.updateCardData(cardData, isChange, position);
        }
        unsubscribe();
    }
}
