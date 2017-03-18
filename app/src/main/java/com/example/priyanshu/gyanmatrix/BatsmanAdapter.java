package com.example.priyanshu.gyanmatrix;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Priyanshu on 12-Mar-17.
 */


public class BatsmanAdapter extends RecyclerView.Adapter<BatsmanAdapter.MyViewHolder> {
    private List<Batsman> Batsmanlist;
    private TextView titleview;
    private ImageView imageView;
    public Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        // Check if there is an existing list item view (called convertView) that we can reuse,
        // otherwise, if convertView is null, then inflate a new list item layout.
        public MyViewHolder(View view)
        {
            super(view);
            titleview = (TextView) view.findViewById(R.id.batsman_name);
imageView=(ImageView)view.findViewById(R.id.batsman_image);

        }



    }
    public BatsmanAdapter(List<Batsman> batsmanlist,Context context)
    {
        Batsmanlist=batsmanlist;
        this.context=context;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);

        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        Batsman currentBatsman = Batsmanlist.get(position);
        String name=currentBatsman.getBatsmanname();
        Picasso.with(context).load(currentBatsman.getBatsmanimageurl()).fit().placeholder(R.drawable.placeholder).into(imageView);
       titleview.setText(name);




    }

    @Override
    public int getItemCount() {
        return Batsmanlist.size();
    }

}

