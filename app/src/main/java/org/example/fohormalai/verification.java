package org.example.fohormalai;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.example.fohormalai.Navigation.Navigation;

import de.hdodenhof.circleimageview.CircleImageView;

public class verification extends AppCompatActivity {

    private final static int Pick_Image = 1;
    private Uri mImageUri;
    private ProgressBar mProgressBar;

    private Button verify;
    private EditText username,phoneno;
    private CircleImageView uploadImage;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private FirebaseDatabase database;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        verify = findViewById(R.id.btn_verify);
        username = findViewById(R.id.fullName);
        phoneno = findViewById(R.id.number);
        uploadImage = findViewById(R.id.image_view1);

        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference("User");
        mDatabaseRef = database.getInstance().getReference("User");

        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.getText().toString().trim().isEmpty() ||
                        phoneno.getText().toString().trim().isEmpty()){
                    Toast.makeText(verification.this, "Insert Details", Toast.LENGTH_SHORT).show();
                }else {
                    mAuth.getInstance().getCurrentUser().sendEmailVerification()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()) {
                                        Toast.makeText(verification.this, "Verification Email sent to :" +
                                                FirebaseAuth.getInstance().getCurrentUser().getEmail(), Toast.LENGTH_LONG).show();
                                        uploadFile();
                                        FirebaseAuth.getInstance().signOut();
                                        startActivity(new Intent(verification.this, Login.class));
                                        finish();
                                    }else{
                                        Toast.makeText(verification.this, "Failed to send verification email", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            }
        });

    }

    private void openFileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, Pick_Image);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Pick_Image && resultCode == RESULT_OK && data != null && data.getData() != null) {
            mImageUri = data.getData();
            Picasso.get().load(mImageUri).into(uploadImage);
            //* or mImageView.setImageUri(mImageUri); if Picasso is not used*//

        }
    }

    private String getFileExtension(Uri uri){
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile(){
        if(mImageUri != null){
            StorageReference fileReference = mStorageRef.child(currentUser.getUid() + "." + getFileExtension(mImageUri));

            fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mProgressBar.setProgress(0);
                                }
                            },500 );
                           // Task<Uri> download = taskSnapshot.getMetadata().getReference().getDownloadUrl();
//
                            Toast.makeText(verification.this, "Upload Successful", Toast.LENGTH_LONG).show();
                            Upload upload = new Upload(username.getText().toString().trim().toString()
                                    ,phoneno.getText().toString().trim().toString(),
                                    currentUser.getUid()+"."+getFileExtension(mImageUri));
                            String uploadID = currentUser.getUid();
                            mDatabaseRef.child(uploadID).setValue(upload);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(verification.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0* taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                            mProgressBar.setProgress((int) progress);
                        }
                    });
        }else{
            Toast.makeText(this, "No file selected", Toast.LENGTH_LONG).show();
        }

    }
}
