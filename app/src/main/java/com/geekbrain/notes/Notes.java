package com.geekbrain.notes;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Notes implements Parcelable, Serializable {

    private String title; //Заголовок заметки
    private String description; //Краткое описание
    private String text; //Текст заметки
    private String date;//Дата создания
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd:MM:yyyy HH:mm");

    public Notes(String title, String description, String text) {
        this.title = title;
        this.description = description;
        this.text = text;
        this.date = dateFormat.format(new Date());
    }

    protected Notes(Parcel in) {
        title = in.readString();
        description = in.readString();
        text = in.readString();
        date = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(text);
        dest.writeString(date);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Notes> CREATOR = new Creator<Notes>() {
        @Override
        public Notes createFromParcel(Parcel in) {
            return new Notes(in);
        }

        @Override
        public Notes[] newArray(int size) {
            return new Notes[size];
        }
    };

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

    public String getDate() {
        return date;
    }

}
