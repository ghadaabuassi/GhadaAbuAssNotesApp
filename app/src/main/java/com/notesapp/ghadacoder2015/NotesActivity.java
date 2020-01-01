package com.notesapp.ghadacoder2015;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.notesapp.ghadacoder2015.adapter.NotesAdapter;
import com.notesapp.ghadacoder2015.model.Note;
import com.notesapp.ghadacoder2015.presenter.NotesPresenter;
import com.notesapp.ghadacoder2015.util.MarginItemDecoration;
import com.notesapp.ghadacoder2015.view.NotesView;

import java.util.ArrayList;
import java.util.List;

public class NotesActivity extends AppCompatActivity implements NotesView {


    private RecyclerView mRvNotes;
    private NotesAdapter mNotesAdapter;
    private ArrayList<Note> mNotes;
    private NotesPresenter mNotesPresenter;
    private String categoriesId;

    private String TAG = NotesActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        getCategoryId();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_notes_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "klik floating action button");

                Intent intent = new Intent(view.getContext(), NewNoteActivity.class);
                intent.putExtra("categoriesID",categoriesId);
                startActivity(intent);
            }
        });


        mRvNotes = (RecyclerView) findViewById(R.id.rv_note);
        mNotes = new ArrayList<Note>();
        mNotesAdapter = new NotesAdapter(this, mNotes);
        mNotesPresenter = new NotesPresenter(this);

        int itemMargin = getResources().getDimensionPixelSize(R.dimen.item_margin);
        mRvNotes.addItemDecoration(new MarginItemDecoration(itemMargin));
        mRvNotes.setAdapter(mNotesAdapter);
        mRvNotes.setLayoutManager(new LinearLayoutManager(this));

        mNotesPresenter.onCreate(categoriesId);
    }

    private void getCategoryId() {

        if (getIntent()!=null){
            if (getIntent().hasExtra("categoriesID")){
                categoriesId=getIntent().getStringExtra("categoriesID");
            }
        }

    }

    @Override
    protected void onStart() {
        super.onStart();

        mNotesPresenter.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();

        mNotesPresenter.onStop();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void refreshNoteList(List<Note> notes) {
        mNotes.clear();
        mNotes.addAll(notes);
        mNotesAdapter.notifyDataSetChanged();
    }

}