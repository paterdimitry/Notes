package com.geekbrain.notes.interfaces;

import com.geekbrain.notes.CardData;

import java.util.List;

public interface CardSource {
    CardData getSource(int position);
    int size();
    List<CardData> getDataSource();
    void add(CardData cardData);
    void changeCard(CardData cardData, int position);
    void deleteCardData(int position);
}
