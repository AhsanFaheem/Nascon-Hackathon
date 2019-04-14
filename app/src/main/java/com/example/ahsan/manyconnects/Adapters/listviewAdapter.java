package com.example.ahsan.manyconnects.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ahsan.manyconnects.Activities.MessageDetails;
import com.example.ahsan.manyconnects.Models.item;
import com.example.ahsan.manyconnects.R;

import java.util.ArrayList;

class listviewHolder extends RecyclerView.ViewHolder  {
    public TextView receiver;
    public TextView message;
    public TextView timestamp;
    public ImageView fbimage;
    public ImageView instaimage;
    public ImageView whatsappimage;
    public ImageView twitterimage;
    public ImageView linkedinimage;

    public listviewHolder (View view, final View.OnClickListener listner){
        super(view);
        receiver = view.findViewById(R.id.receiverTextView);
        message = view.findViewById(R.id.messageTextView);
        timestamp = view.findViewById(R.id.timestampTextView);
        fbimage = view.findViewById(R.id.fbimage);
        instaimage = view.findViewById(R.id.instaimage);
        linkedinimage = view.findViewById(R.id.linkedinimage);
        twitterimage = view.findViewById(R.id.twitterimage);
        whatsappimage = view.findViewById(R.id.whatsappimage);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(v.getContext(), MessageDetails.class);
                i.putExtra("receiver",receiver.getText().toString());
                i.putExtra("timestamp",timestamp.getText().toString());
                i.putExtra("message",message.getText().toString());

                itemView.getContext().startActivity(i);
            }
        });
    }
}

public class listviewAdapter extends RecyclerView.Adapter<listviewHolder>{

    private ArrayList<item> itemsList;
    private Context context2;
    private int LayoutId = 0;
    private View.OnClickListener clickListener = null;

    public listviewAdapter(ArrayList<item> itemsList2, Context context2, int layoutId, View.OnClickListener clickListener) {
        this.itemsList = itemsList2;
        this.context2 = context2;
        LayoutId = layoutId;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public listviewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View LayoutView = LayoutInflater.from(viewGroup.getContext()).inflate(LayoutId,null);
        listviewHolder rcv = new listviewHolder(LayoutView,clickListener);
        return rcv;
    }

    @Override
    public void onBindViewHolder(@NonNull listviewHolder holder, int i) {
        item myItem = itemsList.get(i);
        holder.receiver.setText(myItem.getReceiver());
        String temp;
        if(!myItem.getMessage().getHeader().contains("") && myItem.getMessage().getHeader() != null)
            temp = myItem.getMessage().getHeader() + "\n\n" + myItem.getMessage().getBody();
        else
            temp = myItem.getMessage().getBody();
        if (!myItem.getMessage().getFooter().contains("") && myItem.getMessage().getFooter() != null)
            temp += "\n\n" + myItem.getMessage().getFooter();
        holder.message.setText(temp);
        holder.timestamp.setText(myItem.getTimeStamp());

//        Bitmap tempBitmap = itemsList2.get(position);
//        Drawable tempDrawable = new BitmapDrawable(tempBitmap);
//        listviewHolder.img.setBackground(tempDrawable);
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }
}
