package com.notesapp.ghadacoder2015;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.notesapp.ghadacoder2015.model.InfroAdmin;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.notesapp.ghadacoder2015.util.AppControl.editor;

public class SiginupActivity extends AppCompatActivity {


    @BindView(R.id.edt_emial_user)
    EditText  edt_emial_user;
    @BindView(R.id.edt_password_user)
    EditText  edt_password_user;



    private FirebaseAuth mAuth;
    private DatabaseReference mDaDatabaseReference;
    String current_user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_siginup);
        ButterKnife.bind(this);
        FirebaseApp.initializeApp(SiginupActivity.this);
        mAuth = FirebaseAuth.getInstance();
        mDaDatabaseReference= FirebaseDatabase.getInstance().getReference().child("AdminNotes");

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

    }


    public final static boolean isValidEmail(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    private boolean isValidateMobile(String mobile){
        boolean result = false ;
        if (mobile.length() == 10 ) {
            if (mobile.substring(0, 3).matches("059")) {
                result = true ;
            }
            else{
                result = false;
            }
        }else{
            result = false ;
        }

        return result;
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


        if(edt_password_user.getText().toString().matches("")) {
            edt_password_user.setText("");
            edt_password_user.setHint("Pls .. enter password ");
            edt_password_user.setHintTextColor(Color.RED);
            isEmpty = true;
        }
        if (!isValidEmail(edt_emial_user.getText().toString())){
            edt_emial_user.setText("");
            edt_emial_user.setHint("Pls .. enter  correct email ");
            edt_emial_user.setHintTextColor(Color.RED);
            isEmpty = true;
        }
        if (!isValidatePassword(edt_password_user.getText().toString())){
            edt_password_user.setText("");
            edt_password_user.setHint("password should contains 6 letters or higher ");
            edt_password_user.setHintTextColor(Color.RED);
            isEmpty = true;
        }



        return isEmpty;

    }

    public void but_siginup_user(View view) {

        if (!checkAllEdittextNotEmpaty()){
            RigesterUser();
        }

    }



    private void RigesterUser(){
        final SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#78B844"));
        pDialog.setTitleText("Loading to create account");
        pDialog.setCancelable(false);
        pDialog.show();

        mAuth.createUserWithEmailAndPassword(edt_emial_user.getText().toString(),edt_password_user.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            current_user_id = mAuth.getCurrentUser().getUid();
                            InfroAdmin infroAdmin= new InfroAdmin();
                            infroAdmin.setId(current_user_id);
                            infroAdmin.setEmail(edt_emial_user.getText().toString());
                            infroAdmin.setPasword(edt_password_user.getText().toString());

                            mDaDatabaseReference.child(current_user_id).setValue(infroAdmin).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    editor.putString("Login","done").commit();
                                    editor.putString("current_user_id",current_user_id).commit();
                                    pDialog.dismiss();
                                    startActivity(new Intent(SiginupActivity.this,MainActivity.class));
                                    finish();

                                }
                            });

                            // Sign in success, update UI with the signed-in user's information

                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(SiginupActivity.this, "Failed create account ",
                                    Toast.LENGTH_SHORT).show();
                            pDialog.dismiss();
                            // updateUI(null);
                        }

                        // ...
                    }
                });
    }




    public void tv_dont_have_account(View view) {
        startActivity(new Intent(SiginupActivity.this,SigininActivity.class));
        finish();

    }
}