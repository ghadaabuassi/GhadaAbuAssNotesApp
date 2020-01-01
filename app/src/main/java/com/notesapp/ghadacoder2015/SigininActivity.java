package com.notesapp.ghadacoder2015;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.notesapp.ghadacoder2015.util.AppControl.editor;

public class SigininActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG ="signin" ;
    @BindView(R.id.edt_emial_user_singin)
    EditText edt_emial_user_singin;
    @BindView(R.id.edt_password_user_siginin)
    EditText  edt_password_user_siginin;
    @BindView(R.id.tv_forgeting_Pasword)
    TextView tv_forgeting_Pasword;
    private FirebaseAuth mAuth;
    private Dialog activationDialog;
    private DatabaseReference mUserDatabase;

    private Button butt_send_forgeting_dailog ,   butt_cancle_forgetting_dailog;   ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_siginin);
        FirebaseApp.initializeApp(SigininActivity.this);
        ButterKnife.bind(this);
        mAuth = FirebaseAuth.getInstance();
        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("AdminNotes");

        tv_forgeting_Pasword.setOnClickListener(this);
    }
    public void tv_have_account(View view) {
        startActivity(new Intent(SigininActivity.this,SiginupActivity.class));
        finish();


    }

    public void linear_siginin(View view) {
        if (!checkAllEdittextNotEmpaty()){
            siginInNow();
        }
    }

    private void siginInNow() {
        final SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#78B844"));
        pDialog.setTitleText("Loading to Login ...");
        pDialog.setCancelable(false);
        pDialog.show();

        mAuth.signInWithEmailAndPassword(edt_emial_user_singin.getText().toString(), edt_password_user_siginin.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            editor.putString("Login","Login").apply();
                            final String current_user_id = mAuth.getCurrentUser().getUid();
                            editor.putString("current_user_id",current_user_id).commit();
                            pDialog.dismiss();
                            startActivity(new Intent(SigininActivity.this,MainActivity.class));
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(SigininActivity.this, "Faild Login ",
                                    Toast.LENGTH_SHORT).show();
                            pDialog.dismiss();

                        }


                    }
                });


    }
    private static boolean isValidatePassword(String pass){

        if (pass.length()<6){
            return  false ;

        }else {
            return  true;
        }

    }

    private boolean checkAllEdittextNotEmpaty() {
        boolean isEmpty = false ;
        if(edt_emial_user_singin.getText().toString().matches("")){
            edt_emial_user_singin.setText("");
            edt_emial_user_singin.setError("Please enter email ");
            isEmpty = true;
        }
        if (!isValidEmail(edt_emial_user_singin.getText().toString())){
            edt_emial_user_singin.setText("");
            edt_emial_user_singin.setError("Please enter  correct email");

            isEmpty = true;
        }
        if(edt_password_user_siginin.getText().toString().matches("")){
            edt_password_user_siginin.setText("");
            edt_password_user_siginin.setError("Please enter  password");
            isEmpty = true;
        }

        if (!isValidatePassword(edt_password_user_siginin.getText().toString())){
            edt_password_user_siginin.setText("");
            edt_password_user_siginin.setError("Please enter correct password");
            isEmpty = true;
        }

        return  isEmpty;
    }
    public final static boolean isValidEmail(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        switch (id){
            case R.id.tv_forgeting_Pasword :

                activationDialog = new Dialog(SigininActivity.this, R.style.Theme_Dialog);
                activationDialog.setContentView(R.layout.item_forgeting_password_dailog);
                activationDialog.show();
                final EditText edt_email_forgeting_dailog = (EditText) activationDialog.findViewById(R.id.edt_email_forgeting_dailog);
                butt_send_forgeting_dailog = (Button) activationDialog.findViewById(R.id.butt_send_forgeting_dailog);
                butt_cancle_forgetting_dailog = (Button) activationDialog.findViewById(R.id.butt_cancle_forgetting_dailog);


                butt_send_forgeting_dailog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (!edt_email_forgeting_dailog.getText().toString().matches("")){
                            FirebaseAuth.getInstance().sendPasswordResetEmail(""+edt_email_forgeting_dailog.getText().toString())
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                activationDialog.dismiss();
                                                Toast.makeText(SigininActivity.this, "Email sent .....", Toast.LENGTH_SHORT).show();

                                            }
                                        }
                                    });



                        }
                        else {
                            edt_email_forgeting_dailog.setError("Enter email please ..");
                        }

                    }
                });

                butt_cancle_forgetting_dailog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        activationDialog.dismiss();
                    }
                });

                break;
        }
    }
}