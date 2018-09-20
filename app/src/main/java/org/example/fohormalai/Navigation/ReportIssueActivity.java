package org.example.fohormalai.Navigation;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.example.fohormalai.Login;
import org.example.fohormalai.R;
import org.example.fohormalai.Registration;

public class ReportIssueActivity extends AppCompatActivity {

    private TextView et_attach, et_capture;
    private static int RESULT_LOAD_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_issue);

        // get action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Report Waste Issues !");

        // Enabling Up / Back navigation
        actionBar.setDisplayHomeAsUpEnabled(true);

        et_attach = (TextView)findViewById(R.id.insert);
        et_capture = (TextView)findViewById(R.id.capture);

        et_attach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(
                        Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });

        et_capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent capt = new Intent("android.media.action.IMAGE_CAPTURE");
                startActivity(capt);
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