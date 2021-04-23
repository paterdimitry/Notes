package com.geekbrain.notes;

import com.geekbrain.notes.interfaces.CardSource;
import com.geekbrain.notes.interfaces.CardSourceResponse;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CardSourceFirebaseImpl implements CardSource {

    private static final String CARDS_COLLECTION = "cards";
    private static final String TAG = "[CardsSourceFirebaseImpl]";

    private FirebaseFirestore store = FirebaseFirestore.getInstance();
    private CollectionReference collection = store.collection(CARDS_COLLECTION);

    private List<CardData> data = new ArrayList<>();

    @Override
    public CardSource init(final CardSourceResponse cardSourceResponse) {
        collection.orderBy(CardDataMapping.Fields.DATE, Query.Direction.DESCENDING).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        data = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Map<String, Object> doc = document.getData();
                            String id = document.getId();
                            CardData cardData = CardDataMapping.toCardData(id, doc);
                            data.add(cardData);
                        }

                        cardSourceResponse.initialized(CardSourceFirebaseImpl.this);
                    } else {

                    }
                });

        return this;
    }

    @Override
    public CardData getSource(int position) {
        return data.get(position);
    }

    @Override
    public int size() {
        if (data == null) {
            return 0;
        }
        return data.size();
    }

    @Override
    public void add(CardData cardData) {
        collection.add(CardDataMapping.toDocument(cardData))
                .addOnSuccessListener(documentReference -> cardData.setId(documentReference.getId()));

    }

    @Override
    public void changeCard(CardData cardData, int position) {
        String id = cardData.getId();
        // Изменить документ по идентификатору
        collection.document(id).set(CardDataMapping.toDocument(cardData));

    }

    @Override
    public void deleteCardData(int position) {
        collection.document(data.get(position).getId()).delete();
        data.remove(position);

    }
}
