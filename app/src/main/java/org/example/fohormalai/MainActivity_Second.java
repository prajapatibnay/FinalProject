package org.example.fohormalai;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity_Second extends AppCompatActivity {

   public Button btn1;
   public Button btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__second);
        // get action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("FOHOR MALAI !");

        // Enabling Up / Back navigation
        actionBar.setDisplayHomeAsUpEnabled(true);
        init();
    }

    public void init() {
        btn1 = (Button)findViewById(R.id.btn_register);
        btn2 = (Button)findViewById(R.id.btn_login);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity_Second.this, Registration.class);
                startActivity(i);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(MainActivity_Second.this, Login.class);
                startActivity(j);
            }
        });
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
