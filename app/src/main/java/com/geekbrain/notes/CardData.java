package com.geekbrain.notes;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Date;

public class CardData implements Parcelable, Serializable {

    private String id;
    private String title; //Заголовок заметки
    private String description; //Краткое описание
    private String text; //Текст заметки
    private Date date;//Дата заметки


    public CardData(String title, String description, String text, Date date) {
        this.title = title;
        this.description = description;
        this.text = text;
        this.date = date;
    }

    protected CardData(Parcel in) {
        title = in.readString();
        description = in.readString();
        text = in.readString();
        date = (Date) in.readSerializable();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(text);
        dest.writeSerializable(date);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CardData> CREATOR = new Creator<CardData>() {
        @Override
        public CardData createFromParcel(Parcel in) {
            return new CardData(in);
        }

        @Override
        public CardData[] newArray(int size) {
            return new CardData[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
