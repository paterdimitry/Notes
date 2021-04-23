package com.geekbrain.notes.interfaces;

import com.geekbrain.notes.CardData;

public interface CardSource {
    CardSource init(CardSourceResponse cardSourceResponse);
    CardData getSource(int position);
    int size();
    void add(CardData cardData);
    void changeCard(CardData cardData, int position);
    void deleteCardData(int position);
}
