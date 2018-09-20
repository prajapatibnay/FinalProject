package org.example.fohormalai.Navigation;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.squareup.picasso.Picasso;

import org.example.fohormalai.AboutUsActivity;
import org.example.fohormalai.Login;
import org.example.fohormalai.MainActivity_Second;
import org.example.fohormalai.MainActivity_after_sign_in;
import org.example.fohormalai.MapsActivity;
import org.example.fohormalai.Models.Home;
import org.example.fohormalai.Notification;
import org.example.fohormalai.Profile;
import org.example.fohormalai.R;
import org.example.fohormalai.RecyclerViewAdapter;
import org.example.fohormalai.ScheduleActivity;
import org.example.fohormalai.SettingsActivity;
import org.example.fohormalai.utils.ItemOffsetDecoration;

public class Navigation extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private ImageView userphoto;
    private TextView username;
    private TextView useremail;
    private FirebaseAuth mAuth;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private FirebaseDatabase database;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Home");

        //Firebasse Implementation
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance();
        mDatabaseRef = database.getInstance().getReference("User");
        mStorageRef = FirebaseStorage.getInstance().getReference("User");

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        RecyclerViewAdapter mAdapter = new RecyclerViewAdapter(this, Home.getHomeList(), new RecyclerViewAdapter.ItemListener() {
            @Override
            public void onItemClick(Home item) {
                Log.i("MainActivity"," home "+item.getName());

                if(item.getName().equalsIgnoreCase("My schedule")){
                    Intent in1=new Intent(Navigation.this,ScheduleActivity.class);
                    startActivity(in1);

                }
                if(item.getName().equalsIgnoreCase("Notification")){

                }

                if(item.getName().equalsIgnoreCase("Notices")){

                }

                if(item.getName().equalsIgnoreCase("About us")){
                    Intent in4=new Intent(Navigation.this,AboutUsActivity.class);
                    startActivity(in4);
                }

                if(item.getName().equalsIgnoreCase("Search")){

                }

                if(item.getName().equalsIgnoreCase("Better Recycler")){
                    String url = "https://www.google.com/search?q=be+a+better+recycler&uid=TOSHIBAXDT01ACA100_15DXGKVFSXX15DXGKVFSX&z=5dfcc827cfcd2922cbdbeb1g4z5t9o7z6g8g8m7gee&type=ds";
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                }
            }
        });

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(this, R.dimen.item_offset);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setAdapter(mAdapter);
        Log.i("MainSecondActiviy","inside onCreate");

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //show navigation information
        View navHeaderView = navigationView.inflateHeaderView(R.layout.nav_header_navigation);
        userphoto = navHeaderView.findViewById(R.id.image_view);
        username = navHeaderView.findViewById(R.id.user_name);
        useremail = navHeaderView.findViewById(R.id.user_email);

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    if (currentUser.getUid().equals(dataSnapshot1.getKey())) {
                        for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {
                            if(dataSnapshot2.getKey().equals("image")) {
                                String string = dataSnapshot2.getValue(String.class);
                                mStorageRef.child(string).getDownloadUrl()
                                        .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                Picasso.get().load(uri).into(userphoto);
                                            }
                                        });
                            }
                            if(dataSnapshot2.getKey().equals("username")) {
                                String string = dataSnapshot2.getValue(String.class);
                                username.setText(string);
                            }
                            useremail.setText(currentUser.getEmail());
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
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            Intent intent1 = new Intent(Navigation.this, SettingsActivity.class);
            intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent1);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile)
        {
            Intent in1 = new Intent(Navigation.this, Profile.class);
            in1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            in1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(in1);

        }

        else if (id == R.id.nav_map)
        {
            Intent in2 = new Intent(Navigation.this, MapsActivity.class);
            in2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            in2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(in2);
        }

        else if (id == R.id.nav_report)
        {
            Intent in3 = new Intent(Navigation.this, ReportIssueActivity.class);
            in3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            in3.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(in3);
        }



        else if (id == R.id.nav_logout)
        {
            mAuth.getInstance().signOut();
            Intent in4 = new Intent(Navigation.this, MainActivity_Second.class);
            in4.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            in4.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(in4);
        }

        else if (id == R.id.nav_follow)
        {
            FragmentManager fm1 = getSupportFragmentManager();
            FollowUsDialogFragment followUsDialogFragment = FollowUsDialogFragment.newInstance("Some Title");
            followUsDialogFragment.show(fm1, "fragment_edit_name");
        }

        else if (id == R.id.nav_contact)
        {
            Log.i("Notification", "Contact Clicked!");
            FragmentManager fm2 = getSupportFragmentManager();
            EditNameDialogFragment editNameDialogFragment = EditNameDialogFragment.newInstance("Some Title");
            editNameDialogFragment.show(fm2, "fragment_edit_name");


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
