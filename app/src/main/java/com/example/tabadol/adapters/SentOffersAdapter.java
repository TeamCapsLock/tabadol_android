package com.example.tabadol.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.tabadol.PostDetails;
import com.example.tabadol.R;
import com.example.tabadol.UserProfile;
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
        ImageView receiverImage = offersList.findViewById(R.id.image_for_receiver_sent_offers_frg);
        TextView receiverUsername = offersList.findViewById(R.id.username_for_reciever_sent_offers_frg);
        TextView receiverCategory = offersList.findViewById(R.id.sent_category_for_receiver_sent_offers);
        TextView receiverPostBody = offersList.findViewById(R.id.body_for_receiver_post_sent_offers_frg);
        TextView receiverDate = offersList.findViewById(R.id.date_for_receiver_post_send_offers_frg);

        ImageView senderImageView = offersList.findViewById(R.id.image_for_sender_sent_offers_frg);
        TextView senderUsername = offersList.findViewById(R.id.username_for_sender_sent_offers_frg);
        TextView senderPostBody = offersList.findViewById(R.id.body_for_sender_post_sent_offers_frg);
        TextView senderCategory = offersList.findViewById(R.id.category_for_sender_post_sent_offers);
        TextView senderDate = offersList.findViewById(R.id.date_for_sender_post_sent_offers_frg);

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
        receiverDate.setText(destinationPost.getCreatedAt().substring(0,10));

        Glide.with(parent)
                .load(sourcePost.getUser().getImage())
                .apply(requestOptions)
                .circleCrop()
                .into(senderImageView);

        senderUsername.setText(sourcePost.getUser().getUsername());
        senderCategory.setText(sourcePost.getCategory());
        senderPostBody.setText(sourcePost.getBody());
        senderDate.setText(sourcePost.getCreatedAt().substring(0,10));

        receiverImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent userProfileIntent = new Intent(parent.getContext(), UserProfile.class);
                userProfileIntent.putExtra("id",destinationPost.getUser().getId());
                parent.getContext().startActivity(userProfileIntent);

            }
        });

        senderImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent userProfileIntent = new Intent(parent.getContext(), UserProfile.class);
                userProfileIntent.putExtra("id",sourcePost.getUser().getId());
                parent.getContext().startActivity(userProfileIntent);
            }
        });



        receiverPostBody.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), PostDetails.class);
                intent.putExtra("post",destinationPost);
                intent.putExtra("user",destinationPost.getUser());
                getContext().startActivity(intent);

            }
        });

        senderPostBody.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), PostDetails.class);
                intent.putExtra("post",sourcePost);
                intent.putExtra("user",sourcePost.getUser());
                getContext().startActivity(intent);

            }
        });


        return offersList;
    }
}
