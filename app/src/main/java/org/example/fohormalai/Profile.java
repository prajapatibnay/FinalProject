package org.example.fohormalai;

import android.app.VoiceInteractor;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.IOException;

public class Profile extends AppCompatActivity {

    private ImageView profileimage;
    private TextView profilename;
    private TextView profileemail;
    private TextView profilephone;
    private final static int Pick_Image = 101;
    Uri uriProfileImage;
    String profileImageUrl;
/*    FirebaseAuth mAuth;*/
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private FirebaseDatabase database;
    private FirebaseUser currentUser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // get action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Profile!");

        // Enabling Up / Back navigation
        actionBar.setDisplayHomeAsUpEnabled(true);

        //Firebasse Implementation
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance();
        mDatabaseRef = database.getInstance().getReference("User");
        mStorageRef = FirebaseStorage.getInstance().getReference("User");

        profileimage = (ImageView) findViewById(R.id.profile_image);
        profilename = (TextView) findViewById(R.id.profile_name);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        profileemail = (TextView) findViewById(R.id.profile_email);
        mAuth = FirebaseAuth.getInstance();

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    if (currentUser.getUid().equals(dataSnapshot1.getKey())) {
                        for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {
                            if (dataSnapshot2.getKey().equals("image")) {
                                String string = dataSnapshot2.getValue(String.class);
                                mStorageRef.child(string).getDownloadUrl()
                                        .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                Picasso.get().load(uri).into(profileimage);
                                            }
                                        });
                            }
                            if (dataSnapshot2.getKey().equals("username")) {
                                String string = dataSnapshot2.getValue(String.class);
                                profilename.setText(string);
                            }
                            profileemail.setText(currentUser.getEmail());
                        }
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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
