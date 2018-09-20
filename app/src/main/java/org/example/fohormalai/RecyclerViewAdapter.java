package org.example.fohormalai;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.example.fohormalai.Models.Home;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    List<Home> mValues;
    Context mContext;
    protected ItemListener mListener;

    public RecyclerViewAdapter(Context context, List<Home> values, ItemListener itemListener) {

        mValues = values;
        mContext = context;
        mListener=itemListener;
    }



    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.recycler_view_item, parent, false);


        return new ViewHolder(view,mListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(mValues.get(position));
    }


    @Override
    public int getItemCount() {

        return mValues.size();
    }

    public interface ItemListener {
        void onItemClick(Home item);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textView;
        public ImageView imageView;
        public LinearLayout linearLayout;
        ItemListener mListener;


        public ViewHolder(View v , ItemListener l) {

            super(v);
            this.mListener=l;

            textView = (TextView) v.findViewById(R.id.textView);
            imageView = (ImageView) v.findViewById(R.id.imageView);
            linearLayout = (LinearLayout) v.findViewById(R.id.relativeLayout);


        }

        public void setData(final Home item) {


            textView.setText(item.getName());
            imageView.setImageResource(item.getImage());
//            relativeLayout.setBackgroundColor(Color.parseColor(item.color));
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mListener!=null){
                        mListener.onItemClick(item);
                    }
                }
            });



        }



    }
}