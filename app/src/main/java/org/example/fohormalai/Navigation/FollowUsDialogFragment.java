package org.example.fohormalai.Navigation;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import org.example.fohormalai.Login;
import org.example.fohormalai.R;
// ...

public class FollowUsDialogFragment extends DialogFragment {

    private EditText mEditText;

    public FollowUsDialogFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static FollowUsDialogFragment newInstance(String title) {
        FollowUsDialogFragment frag = new FollowUsDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_follow_us, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView mFacebook = (TextView) view.findViewById(R.id.Facebook);
        TextView mInstagram = (TextView) view.findViewById(R.id.Instagram);


        mFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("http://www.facebook.com");
                Intent facebook = new Intent(Intent.ACTION_VIEW, uri);

                facebook.setPackage("com.facebook.android");

                try {
                    startActivity(facebook);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://facebook.com")));
                }
            }
        });

        mInstagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("http://www.instagram.com");
                Intent insta = new Intent(Intent.ACTION_VIEW, uri);

                insta.setPackage("com.instagram.android");

                try {
                    startActivity(insta);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://instagram.com")));
                }
            }
        });

    }
}
