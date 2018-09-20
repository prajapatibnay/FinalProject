package org.example.fohormalai;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.example.fohormalai.Navigation.Navigation;

public class Login extends AppCompatActivity {

    private EditText et_email, et_password;
    private String email, password;
    private TextView et_register;
    private FirebaseAuth mAuth;
    private boolean emailchecker;
    private FirebaseAuth.AuthStateListener mAuthListener;
    Button btn_log;
    ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_v2);
        // get action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Login !");

        // Enabling Up / Back navigation
        actionBar.setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();

        et_register = (TextView) findViewById(R.id.toRegister);

        et_email = (EditText) findViewById(R.id.emailID);
        et_password = (EditText) findViewById(R.id.passID);
        btn_log = (Button) findViewById(R.id.btn_signIn);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null ) {
                    Intent intent = new Intent(Login.this, Navigation.class);
                    startActivity(intent);
                }
            }
        };
        btn_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   //Progress Bar
                   mDialog = new ProgressDialog(Login.this);
                   mDialog.setMessage("Please Wait");
                   validate();
            }
        });

        et_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reg = new Intent(Login.this, Registration.class);
                startActivity(reg);
            }
        });

    }


    public void onLoginSuccess() {

        mDialog.show();
        mDialog.setCanceledOnTouchOutside(false);

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            mDialog.dismiss();
                            VerifyEmail();

                        } else {
                            mDialog.dismiss();
                            Toast.makeText(Login.this, "Login Failed", Toast.LENGTH_LONG).show();
                        }

                    }
                });
    }

    public void validate() {
        initialize();
        boolean valid = true;

        if (email.isEmpty()) {
            et_email.setError("Please enter your username");
            valid = false;
        }
        if (password.isEmpty()) {
            et_password.setError("Please enter your password");
            valid = false;
        }
        if(valid){
            onLoginSuccess();
        }
    }

    public void initialize() {
        email = et_email.getText().toString().trim();
        password = et_password.getText().toString().trim();
    }

    @Override
    protected void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);
    }

    private void VerifyEmail(){
        FirebaseUser user = mAuth.getCurrentUser();
        emailchecker = user.isEmailVerified();

        if (emailchecker){
            Toast.makeText(Login.this,"Login Successful",Toast.LENGTH_SHORT).show();
            mAuth.addAuthStateListener(mAuthListener);

        }
        else{
            Toast.makeText(Login.this, "Please Verify Your Email First", Toast.LENGTH_SHORT).show();
            mAuth.signOut();
            startActivity(new Intent(Login.this, Login.class));
        }
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
