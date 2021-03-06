package com.notesapp.ghadacoder2015.model;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by dani on 4/4/17.
 */

@IgnoreExtraProperties
public class Note {

    private String uid;
    private String title;
    private String content;

    public Note() {
    }

    public Note(String uid, String title, String content) {
        this.uid = uid;
        this.title = title;
        this.content = content;
    }

    public String getUid() {
        return uid;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
