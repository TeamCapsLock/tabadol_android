package com.example.tabadol.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.tabadol.R;
import com.example.tabadol.api.Offer;
import com.example.tabadol.api.Post;

import java.util.ArrayList;

public class SentOffersAdapter extends ArrayAdapter<Offer> {
    public SentOffersAdapter(Context context, ArrayList<Offer> offers){
        super(context, 0, offers);

    }
    public View getView(int position, View convertView, ViewGroup parent) {
        View offersList = convertView;
        if(offersList == null)
            offersList = LayoutInflater.from(getContext()).inflate(R.layout.sent_offer, parent, false);

        Offer currentOffer = getItem(position);
        ImageView receiverImage = offersList.findViewById(R.id.sent_image);
        TextView receiverUsername = offersList.findViewById(R.id.sent_username);
        TextView receiverCategory = offersList.findViewById(R.id.sent_category);
        TextView receiverPostBody = offersList.findViewById(R.id.sent_body);

        ImageView senderImageView = offersList.findViewById(R.id.sent_image2);
        TextView senderUsername = offersList.findViewById(R.id.sent_username2);
        TextView senderPostBody = offersList.findViewById(R.id.sent_body2);
        TextView senderCategory = offersList.findViewById(R.id.sent_category2);


        RequestOptions requestOptions=new RequestOptions();
        requestOptions.placeholder(R.drawable.male_icon);
        requestOptions.error(R.drawable.male_icon);

        Post sourcePost = currentOffer.getSourcePost();
        Post destinationPost = currentOffer.getDestinationPost();

        Glide.with(parent)
                .load(destinationPost.getUser().getImage())
                .apply(requestOptions)
                .circleCrop()
                .into(receiverImage);

        receiverUsername.setText(destinationPost.getUser().getUsername());
        receiverCategory.setText(destinationPost.getCategory());
        receiverPostBody.setText(destinationPost.getBody());

        Glide.with(parent)
                .load(sourcePost.getUser().getImage())
                .apply(requestOptions)
                .circleCrop()
                .into(senderImageView);

        senderUsername.setText(sourcePost.getUser().getUsername());
        senderCategory.setText(sourcePost.getCategory());
        senderPostBody.setText(sourcePost.getBody());



        return offersList;
    }
}
