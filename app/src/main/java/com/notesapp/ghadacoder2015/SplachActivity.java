package com.notesapp.ghadacoder2015;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.notesapp.ghadacoder2015.util.CheckConnectivity;

import static com.notesapp.ghadacoder2015.util.AppControl.sharedpreferences;

public class SplachActivity extends AppCompatActivity {
    Boolean conn;
    CheckConnectivity check;
    private int dosply_splash = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splach);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        new Handler().postDelayed(new Runnable() {

            @SuppressLint("ResourceAsColor")
            @Override

            public void run() {

                check = new CheckConnectivity();

                if (check.checkNow(SplachActivity.this)){

                    String sh= sharedpreferences.getString("Login","");

                    if (sh.equals("")) {

                        Intent intent = new Intent(SplachActivity.this, SigininActivity.class);
                        SplachActivity.this.startActivity(intent);
                        SplachActivity.this.finish();
                        finish();
                    } else {
                        Intent intent = new Intent(SplachActivity.this, MainActivity.class);
                        SplachActivity.this.startActivity(intent);
                        SplachActivity.this.finish();
                        finish();

                    }

                }
                else {

                    Snackbar snack = Snackbar.make(findViewById(android.R.id.content),"لا يوجد اتصال بالانترنت", Snackbar.LENGTH_LONG);
                    View vieww = snack.getView();
                    vieww.setBackgroundColor(R.color.colorPrimary);
                    TextView tv = (TextView) vieww.findViewById(com.google.android.material.R.id.snackbar_text);
                    tv.setTextColor(ContextCompat.getColor(SplachActivity.this, R.color.white));
                    tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    snack.show();

                }

            }

        }, dosply_splash);


    }

}
