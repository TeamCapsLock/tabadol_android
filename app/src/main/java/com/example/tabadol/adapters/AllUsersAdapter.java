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
import com.example.tabadol.R;
import com.example.tabadol.UserProfile;
import com.example.tabadol.api.User;

import java.util.ArrayList;

public class AllUsersAdapter extends ArrayAdapter<User> {
    public AllUsersAdapter(Context context, ArrayList<User> FollowersList){
        super(context, 0, FollowersList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null)
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.users_list, parent, false);

        User user = getItem(position);
        ImageView userImage = listItemView.findViewById(R.id.user_image_users_list);
        TextView username = listItemView.findViewById(R.id.user_name_user_list);
        TextView fullname = listItemView.findViewById(R.id.full_name_user_list);
        TextView rating = listItemView.findViewById(R.id.text_rating_users_list);


        username.setText(user.getUsername());
        rating.setText(user.getRating().toString());
        fullname.setText(user.getFirstname() + " "  + user.getLastname());
        RequestOptions requestOptions=new RequestOptions();
        requestOptions.placeholder(R.drawable.male_icon);
        requestOptions.error(R.drawable.male_icon);

        Glide.with(parent)
                .load(user.getImage())
                .apply(requestOptions)
                .circleCrop()
                .into(userImage);

        listItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent userProfileIntent = new Intent(parent.getContext(),UserProfile.class);
                userProfileIntent.putExtra("id", user.getId());
                parent.getContext().startActivity(userProfileIntent);
            }
        });

        return listItemView;
    }
}
