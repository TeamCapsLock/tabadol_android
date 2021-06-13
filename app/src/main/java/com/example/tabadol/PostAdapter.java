package com.example.tabadol;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


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
        postBody.setText(currentPost.getBody());
        postCategory.setText(currentPost.getCategory());
        return listItemView;
    }
}
