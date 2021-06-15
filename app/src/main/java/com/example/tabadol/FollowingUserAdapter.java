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

public class FollowingUserAdapter extends ArrayAdapter<User> {
    public FollowingUserAdapter(Context context, ArrayList<User> followingUser){
        super(context, 0, followingUser);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null)
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.following_user_list, parent, false);

        User followingUser = getItem(position);
        ImageView userImage = listItemView.findViewById(R.id.following_imageView);
        TextView username = listItemView.findViewById(R.id.text_username_followingUser);

//        userImage.setImageDrawable();

        username.setText(followingUser.getUsername());
        return listItemView;
    }
}
