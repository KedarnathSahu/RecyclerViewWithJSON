package com.example.user.recyclerviewwithjson;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private ArrayList<ListItem> listItems;
    private Context context;

    public MyAdapter(ArrayList<ListItem> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ListItem listItem=listItems.get(position);

        holder.head.setText(listItem.getHead());
        holder.desc.setText(listItem.getDesc());

        /*Bitmap bitmap= BitmapFactory.decodeResource(context.getResources(),R.mipmap.profile_bg);
        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(),bitmap);
        roundedBitmapDrawable.setCircular(true);
        holder.imageView.setImageDrawable(roundedBitmapDrawable);*/

        Picasso.get().load(listItem.getImage()).into(holder.imageView);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,listItem.getHead()+" is Selected.",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView head;
        public TextView desc;
        public ImageView imageView;
        public LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            head=itemView.findViewById(R.id.textViewHead);
            desc=itemView.findViewById(R.id.textViewDesc);
            imageView=itemView.findViewById(R.id.iv1);
            linearLayout=itemView.findViewById(R.id.linearLayout);
        }
    }

    public void setFilter(ArrayList<ListItem> newList){
        listItems=new ArrayList<>();
        listItems.addAll(newList);
        notifyDataSetChanged();//refresh the adapter
    }

}
