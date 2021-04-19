package com.geekbrain.notes.interfaces;

import com.geekbrain.notes.CardData;

public interface CardSource {
    CardData getSource(int position);
    int size();
}
