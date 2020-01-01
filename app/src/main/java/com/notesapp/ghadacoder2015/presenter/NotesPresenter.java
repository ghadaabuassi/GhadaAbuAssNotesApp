package com.notesapp.ghadacoder2015.presenter;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.notesapp.ghadacoder2015.model.Note;
import com.notesapp.ghadacoder2015.view.NotesView;

import java.util.ArrayList;

import static com.notesapp.ghadacoder2015.util.AppControl.sharedpreferences;

/**
 * Created by dani on 4/4/17.
 */

public class NotesPresenter {

    private static final String TAG = NotesPresenter.class.getSimpleName();
    
    private final NotesView notesView;

    private DatabaseReference mNoteRef;
    private ValueEventListener mNotesEventListener;

    public NotesPresenter(NotesView notesView) {
        this.notesView = notesView;
    }

    public void onCreate(String categoriesId) {

        String userId= sharedpreferences.getString("current_user_id","");
        mNoteRef = FirebaseDatabase.getInstance().getReference().child(userId).child("categories").child(categoriesId).child("notes");
    }

    public void onStart() {
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Note> notes = new ArrayList<Note>();

                for (DataSnapshot noteSnapshot: dataSnapshot.getChildren()) {
                    Note note = noteSnapshot.getValue(Note.class);
                    notes.add(note);
                }

                notesView.refreshNoteList(notes);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadNote:onCancelled", databaseError.toException());
            }
        };

        mNoteRef.orderByKey().addValueEventListener(valueEventListener);

        mNotesEventListener = valueEventListener;
    }

    public void onStop() {
        if (mNotesEventListener != null) {
            mNoteRef.removeEventListener(mNotesEventListener);
        }
    }
}
