package org.example.fohormalai.Navigation;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.example.fohormalai.Login;
import org.example.fohormalai.R;
import org.example.fohormalai.Registration;

public class ReportIssueActivity extends AppCompatActivity {

    private TextView et_attach, et_capture;
    private EditText message;
    private static int RESULT_LOAD_IMAGE = 1;
    private final static int Pick_Image = 1;
    private final static int Capture_Image = 1;
    private Uri mImageUri;
    private ImageView attachimage;
    private ImageView captureimage;
    Button btn_report;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_issue);

        attachimage = findViewById(R.id.imgView1);
        captureimage = findViewById(R.id.imgView2);


        // get action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Report Waste Issues !");

        // Enabling Up / Back navigation
        actionBar.setDisplayHomeAsUpEnabled(true);

        message = (EditText) findViewById(R.id.type_message);
        et_attach = (TextView)findViewById(R.id.insert);
        et_capture = (TextView)findViewById(R.id.capture);
        btn_report = (Button) findViewById(R.id.btn_report);

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

        btn_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
/*                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto:clustered.concepts018@gmail.com"));
                startActivity(emailIntent);*/
                Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                emailIntent.setType("application/image");
                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"clustered.concepts018@gmail.com"});
                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,"Report Waste Issues");
                emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, message.getText().toString());
                emailIntent.putExtra(Intent.EXTRA_STREAM, mImageUri);
                startActivity(emailIntent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Pick_Image && resultCode == RESULT_OK && data != null && data.getData() != null) {
            mImageUri = data.getData();
            Picasso.get().load(mImageUri).into(attachimage);
            //* or mImageView.setImageUri(mImageUri); if Picasso is not used*//

        }

        else if (requestCode == Capture_Image && resultCode == RESULT_OK && data != null && data.getData() != null) {
            mImageUri = data.getData();
            Picasso.get().load(mImageUri).into(captureimage);
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
