package com.notesapp.ghadacoder2015.presenter;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.notesapp.ghadacoder2015.model.CategoriesClass;
import com.notesapp.ghadacoder2015.model.Note;
import com.notesapp.ghadacoder2015.view.CategoryView;
import com.notesapp.ghadacoder2015.view.NotesView;

import java.util.ArrayList;

import static com.notesapp.ghadacoder2015.util.AppControl.sharedpreferences;

/**
 * Created by dani on 4/4/17.
 */

public class CategoriessPresenter {

    private static final String TAG = CategoriessPresenter.class.getSimpleName();

    private final CategoryView categoryView;

    private DatabaseReference mNoteRef;
    private ValueEventListener mNotesEventListener;

    public CategoriessPresenter(CategoryView categoryView) {
        this.categoryView = categoryView;
    }

    public void onCreate() {

        String userId= sharedpreferences.getString("current_user_id","");
        mNoteRef = FirebaseDatabase.getInstance().getReference().child(userId).child("categories");
    }

    public void onStart() {
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<CategoriesClass> categoryList = new ArrayList<CategoriesClass>();
                for (DataSnapshot noteSnapshot: dataSnapshot.getChildren()) {
                    CategoriesClass categoriesClass = noteSnapshot.getValue(CategoriesClass.class);
                    categoryList.add(categoriesClass);
                }

                categoryView.refreshCategoryList(categoryList);
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
