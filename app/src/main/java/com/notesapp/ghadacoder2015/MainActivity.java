package com.notesapp.ghadacoder2015;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.notesapp.ghadacoder2015.adapter.CategoriesPageAdabter;
import com.notesapp.ghadacoder2015.model.CategoriesClass;
import com.notesapp.ghadacoder2015.presenter.CategoriessPresenter;
import com.notesapp.ghadacoder2015.util.MarginItemDecoration;
import com.notesapp.ghadacoder2015.view.CategoryView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements CategoryView {

    private static final String TAG = MainActivity.class.getSimpleName();

    private RecyclerView recyclerView;
    private CategoriesPageAdabter categoriesAdapter;
    private ArrayList<CategoriesClass> categoriesAdapterArrayList;
    private CategoriessPresenter categoriessPresenter;
    private  String categoriesId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "klik floating action button");

                Intent intent = new Intent(view.getContext(), NewCategoriesActivity.class);
                startActivity(intent);
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.rv_category);
        categoriesAdapterArrayList = new ArrayList<CategoriesClass>();
        categoriesAdapter = new CategoriesPageAdabter(this,categoriesAdapterArrayList, new CategoriesPageAdabter.OnItemClickListener() {
            @Override
            public void onItemClick(CategoriesClass item) {

                Intent intent = new Intent(MainActivity.this, NotesActivity.class);
                intent.putExtra("categoriesID",item.getUiu());
                startActivity(intent);
            }
        });

        categoriessPresenter = new CategoriessPresenter(this);

        int itemMargin = getResources().getDimensionPixelSize(R.dimen.item_margin);
        recyclerView.addItemDecoration(new MarginItemDecoration(itemMargin));
        recyclerView.setAdapter(categoriesAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        categoriessPresenter.onCreate();
    }

    @Override
    protected void onStart() {
        super.onStart();

        categoriessPresenter.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();

        categoriessPresenter.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void refreshCategoryList(List<CategoriesClass> categoriesClasses) {

        categoriesAdapterArrayList.clear();
        categoriesAdapterArrayList.addAll(categoriesClasses);
        categoriesAdapter.notifyDataSetChanged();

    }
}
