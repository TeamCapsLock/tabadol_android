package com.example.tabadol;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.tabadol.api.Post;

import java.util.ArrayList;

public class PostAdapter extends ArrayAdapter<Post> {
    public PostAdapter(Context context, ArrayList<Post> posts){
        super(context, 0, posts);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null)
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.post_list, parent, false);

        Post currentPost = getItem(position);
        TextView postBody = listItemView.findViewById(R.id.rating_user_list);
        TextView postCategory = listItemView.findViewById(R.id.text_post_category);
        TextView postOwnerName = listItemView.findViewById(R.id.user_name_user_list);
        ImageView postOwnerImage = listItemView.findViewById(R.id.user_image_posts_page);
        postBody.setText(currentPost.getBody());
        postCategory.setText(currentPost.getCategory());
        postOwnerName.setText(currentPost.getUser().getFirstname() + " " + currentPost.getUser().getLastname());

        RequestOptions requestOptions=new RequestOptions();
        requestOptions.placeholder(R.drawable.male_icon);
        requestOptions.error(R.drawable.male_icon);

        Glide.with(parent)
                .load(currentPost.getUser().getImage())
                .apply(requestOptions)
                .circleCrop()
                .into(postOwnerImage);


        return listItemView;
    }
}
