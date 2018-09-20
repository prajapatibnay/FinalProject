package org.example.fohormalai.Navigation;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import org.example.fohormalai.Login;
import org.example.fohormalai.R;
// ...

public class EditNameDialogFragment extends DialogFragment {

    private EditText mEditText;

    public EditNameDialogFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static EditNameDialogFragment newInstance(String title) {
        EditNameDialogFragment frag = new EditNameDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("EditNameDialogFragment","Dialog Fragment Entered");
        return inflater.inflate(R.layout.fragment_item, container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i("EditNameDialogFragment","On View Created Entered");
        TextView mNtc = (TextView) view.findViewById(R.id.Ntc);
        TextView mNcell = (TextView) view.findViewById(R.id.Ncell);
        TextView mGmail = (TextView) view.findViewById(R.id.Gmail);


        mNtc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in1 = new Intent(Intent.ACTION_DIAL);
                in1.setData(Uri.parse("tel:9849375019"));
                startActivity(in1);
            }
        });

        mNcell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in2 = new Intent(Intent.ACTION_DIAL);
                in2.setData(Uri.parse("tel:9808270885"));
                startActivity(in2);
            }
        });

        mGmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto:clustered@gmail.com"));
                startActivity(emailIntent);
            }
        });

    }
}