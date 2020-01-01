package com.notesapp.ghadacoder2015;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.notesapp.ghadacoder2015.presenter.CategoriessPresenter;
import com.notesapp.ghadacoder2015.presenter.CategoryPresenter;

public class NewCategoriesActivity extends AppCompatActivity {


     private String TAG=NewCategoriesActivity.class.getSimpleName();

      CategoryPresenter categoriessPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_categories);

         categoriessPresenter= new CategoryPresenter();

        final EditText etCategoryTitle = (EditText) findViewById(R.id.et_category_title);
        Button btnNoteSave = (Button) findViewById(R.id.btn_category_submit);

        btnNoteSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = etCategoryTitle.getText().toString();

                categoriessPresenter.create(title);

                onBackPressed();
            }
        });
    }

}
