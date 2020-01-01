package com.notesapp.ghadacoder2015.presenter;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.notesapp.ghadacoder2015.model.CategoriesClass;
import com.notesapp.ghadacoder2015.model.Note;

import static com.notesapp.ghadacoder2015.util.AppControl.sharedpreferences;


/**
 * Created by dani on 4/6/17.
 */

public class CategoryPresenter {

    private DatabaseReference mNoteRef;

    public CategoryPresenter() {
        String userId= sharedpreferences.getString("current_user_id","");

        mNoteRef = FirebaseDatabase.getInstance().getReference().child(userId).child("categories");
    }

    public void create(String title) {
        String key = mNoteRef.push().getKey();
        CategoriesClass categoriesClass = new CategoriesClass(key,title);

        mNoteRef.child(key).setValue(categoriesClass);
    }
}
