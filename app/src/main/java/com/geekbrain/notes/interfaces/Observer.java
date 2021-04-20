package com.geekbrain.notes.interfaces;

import com.geekbrain.notes.CardData;

public interface Observer {
    void updateCardData(CardData cardData, boolean isChange, int position);
}
