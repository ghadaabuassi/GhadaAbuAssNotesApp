package com.notesapp.ghadacoder2015.presenter;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.notesapp.ghadacoder2015.model.Note;

import static com.notesapp.ghadacoder2015.util.AppControl.sharedpreferences;


/**
 * Created by dani on 4/6/17.
 */

public class NotePresenter {

    private DatabaseReference mNoteRef;

    public NotePresenter(String categroiesId) {
        String userId= sharedpreferences.getString("current_user_id","");

        mNoteRef = FirebaseDatabase.getInstance().getReference().child(userId).child("categories").child(categroiesId).child("notes");
    }

    public void create(String title, String content) {
        String key = mNoteRef.push().getKey();
        Note note = new Note(key, title, content);

        mNoteRef.child(key).setValue(note);
    }
}
