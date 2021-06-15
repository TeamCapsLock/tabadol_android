package com.example.tabadol;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tabadol.api.User;

import java.util.ArrayList;

public class FollowersAdapter extends ArrayAdapter<User> {
    public FollowersAdapter(Context context, ArrayList<User> FollowersList){
        super(context, 0, FollowersList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null)
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.followers_list, parent, false);

        User FollowersList = getItem(position);
        ImageView userImage = listItemView.findViewById(R.id.followers_list_imageView);
        TextView username = listItemView.findViewById(R.id.text_username_followers_list);

//        userImage.setImageDrawable();

        username.setText(FollowersList.getUsername());
        return listItemView;
    }
}
