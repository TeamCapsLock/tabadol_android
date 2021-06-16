package com.example.tabadol.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.tabadol.R;
import com.example.tabadol.SelectPostActivity;
import com.example.tabadol.api.Post;

import java.util.List;

public class PostSellectAdapter extends ArrayAdapter<Post> {


    public PostSellectAdapter(Context context, List<Post> posts){
        super(context, 0, posts);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null)
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.user_profile_posts_list, parent, false);

        Post currentPost = getItem(position);
        TextView postBody = listItemView.findViewById(R.id.text_post_body_user_profile);
        TextView postCategory = listItemView.findViewById(R.id.text_post_category_user_profile);
        TextView postType = listItemView.findViewById(R.id.text_post_type_user_profile);
        TextView postCreatedAt = listItemView.findViewById(R.id.text_post_date_user_profile);
        postBody.setText(currentPost.getBody());
        postCategory.setText(currentPost.getCategory());
        postType.setText(currentPost.getType());
        postCreatedAt.setText(currentPost.getCreatedAt());


        listItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                long sourcePostId = currentPost.getId();
                // layout .. accept decline.. visible
                SelectPostActivity.sourcePostId = sourcePostId;
                SelectPostActivity.confirmMessageLayout.setVisibility(View.VISIBLE);
                SelectPostActivity.postsListView.setVisibility(View.INVISIBLE);
            }
        });
        return listItemView;
    }
}
