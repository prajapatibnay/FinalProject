package org.example.fohormalai;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Registration extends AppCompatActivity {

    private EditText et_name, et_phone, et_email, et_pass, et_confpass;
    private String name, phone, email, password, Cpassword;
    private TextView et_login;
    private FirebaseAuth mAuth;
    Button btn_reg;
    ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_v2);

        // get action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Register");

        // Enabling Up / Back navigation
        actionBar.setDisplayHomeAsUpEnabled(true);
        mAuth = FirebaseAuth.getInstance();

        et_login = (TextView)findViewById(R.id.toLogin);
        et_name = (EditText)findViewById(R.id.fullName);
        et_email = (EditText)findViewById(R.id.emailID);
        et_pass = (EditText)findViewById(R.id.password);
        et_phone = (EditText)findViewById(R.id.number);
        et_confpass = (EditText) findViewById(R.id.confPassword);btn_reg = (Button)findViewById(R.id.btn_register2);


        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog = new ProgressDialog(Registration.this);
                mDialog.setMessage("Please Wait...");
                validate();
                //call when the button is clicked to validate the input
            }
        });

        et_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent k = new Intent(Registration.this, Login.class);
                startActivity(k);
            }
        });

    }


    public void onRegisterSuccess(){
        mDialog.show();
        mDialog.setCanceledOnTouchOutside(false);

        //TODO what will go after the valid input
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            mDialog.dismiss();
                            initialize();
                            Toast.makeText(Registration.this, "createUserWithEmail: Success", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(Registration.this, verification.class);
                            finish();
                            startActivity(intent);
                        }
                        else {
                            if(task.getException() instanceof FirebaseAuthUserCollisionException){
                                    mDialog.dismiss();
                                    Toast.makeText(Registration.this, "Email already in use", Toast.LENGTH_LONG).show();
                            }else{
                                mDialog.dismiss();
                                Toast.makeText(Registration.this,task.getException().getLocalizedMessage(),Toast.LENGTH_LONG).show();
                            }
                        }


                    }
                });

    }

    public void validate(){
        initialize();
        boolean valid = true;

        if (name.isEmpty()||name.length()>32){
            et_name.setError("Please Enter valid name");
            valid = false;
        }
        if (phone.isEmpty()){
            et_phone.setError("Please enter phone number");
            valid = false;
        }
        if (email.isEmpty()||!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            et_email.setError("Please enter valid email address");
            valid = false;
        }
        if (password.isEmpty()){
            et_pass.setError("Please enter password");
            valid = false;
        }
        if (!password.equals(Cpassword)){
            et_confpass.setError("The password did not match");
            valid = false;
        }

        if(valid){
            onRegisterSuccess();
        }

    }
    public void initialize(){
        name = et_name.getText().toString().trim();
        email = et_email.getText().toString().trim();
        password = et_pass.getText().toString().trim();
        phone = et_phone.getText().toString().trim();
        Cpassword = et_confpass.getText().toString().trim();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
