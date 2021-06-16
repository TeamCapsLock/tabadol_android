package com.example.tabadol.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.tabadol.OffersActivity;
import com.example.tabadol.R;
import com.example.tabadol.UserProfile;
import com.example.tabadol.api.MyRoutes;
import com.example.tabadol.api.Offer;
import com.example.tabadol.api.Post;

import java.util.ArrayList;

public class ReceivedOffersAdapter extends ArrayAdapter<Offer> {
    public ReceivedOffersAdapter(Context context, ArrayList<Offer> offers){
        super(context, 0, offers);

    }
    public View getView(int position, View convertView, ViewGroup parent) {
        View offersList = convertView;
        if(offersList == null)
            offersList = LayoutInflater.from(getContext()).inflate(R.layout.received_offer, parent, false);

        Offer currentOffer = getItem(position);


        ImageView receiverImage = offersList.findViewById(R.id.image_for_receiver_recieved_offers_frg_2);
        TextView receiverUsername = offersList.findViewById(R.id.username_for_reciever_received_offers_frg);
        TextView receiverCategory = offersList.findViewById(R.id.category_for_receiver_post_received_offers);
        TextView receiverPostBody = offersList.findViewById(R.id.body_for_receiver_post_received_offers_frg);
        TextView receiverDate = offersList.findViewById(R.id.date_for_receiver_post_received_offers_frg);

        ImageView senderImageView = offersList.findViewById(R.id.image_for_sender_received_offers_frg_2);
        TextView senderUsername = offersList.findViewById(R.id.username_for_sender_received_offers_frg);
        TextView senderPostBody = offersList.findViewById(R.id.body_for_sender_post_received_offers_frg);
        TextView senderCategory = offersList.findViewById(R.id.category_for_sender_post_received_offers);
        TextView senderDate = offersList.findViewById(R.id.date_for_sender_post_received_offers_frg);

        Button rejectButton = offersList.findViewById(R.id.received_offers_reject_button);
        Button acceptButton = offersList.findViewById(R.id.received_accept_offer_button);
        Post sourcePost = currentOffer.getSourcePost();
        Post destinationPost = currentOffer.getDestinationPost();

        receiverUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent userProfileIntent = new Intent(parent.getContext(), UserProfile.class);
                userProfileIntent.putExtra("id",destinationPost.getUser().getId());
                parent.getContext().startActivity(userProfileIntent);

            }
        });

        senderUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent userProfileIntent = new Intent(parent.getContext(), UserProfile.class);
                userProfileIntent.putExtra("id",sourcePost.getUser().getId());
                parent.getContext().startActivity(userProfileIntent);
            }
        });


        rejectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rejectButton.setEnabled(false);
                acceptButton.setEnabled(false);
                MyRoutes.getMyRoutesInstanse(parent.getContext()).declinedOffer(sourcePost.getId(),destinationPost.getId());
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        MyRoutes.getMyRoutesInstanse(parent.getContext()).getReceivedOffers();
                    }
                }, 50);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent selfIntent = new Intent(parent.getContext(), OffersActivity.class);
                        parent.getContext().startActivity(selfIntent);
                    }
                }, 2000);
            }
        });

        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rejectButton.setEnabled(false);
                acceptButton.setEnabled(false);
                MyRoutes.getMyRoutesInstanse(parent.getContext()).acceptOffer(sourcePost.getId(),destinationPost.getId());
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        MyRoutes.getMyRoutesInstanse(parent.getContext()).getReceivedOffers();
                        MyRoutes.getMyRoutesInstanse(parent.getContext()).getFinishedOffers();
                        MyRoutes.getMyRoutesInstanse(parent.getContext()).getSentOffers();
                    }
                }, 100);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent selfIntent = new Intent(parent.getContext(), OffersActivity.class);
                        parent.getContext().startActivity(selfIntent);
                    }
                }, 3000);
            }
        });

        RequestOptions requestOptions=new RequestOptions();
        requestOptions.placeholder(R.drawable.male_icon);
        requestOptions.error(R.drawable.male_icon);



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





        return offersList;
    }
}
