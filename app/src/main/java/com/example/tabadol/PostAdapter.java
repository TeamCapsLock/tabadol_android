package com.example.tabadol;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
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
        TextView postBody = listItemView.findViewById(R.id.text_post_body);
        TextView postCategory = listItemView.findViewById(R.id.text_post_category);
        TextView postOwnerName = listItemView.findViewById(R.id.post_owner_name);
        ImageView postOwnerImage = listItemView.findViewById(R.id.user_image_posts_page);
        postBody.setText(currentPost.getBody());
        postCategory.setText(currentPost.getCategory());
        postOwnerName.setText("AbdalQader Mhemed");
        if(position % 2 == 0)
            postOwnerImage.setImageResource(R.drawable.male_icon);
        else
            postOwnerImage.setImageResource(R.drawable.female_icon);

        return listItemView;
    }
}
