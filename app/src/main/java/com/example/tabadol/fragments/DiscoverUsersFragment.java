package com.example.tabadol.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.tabadol.PostAdapter;
import com.example.tabadol.R;
import com.example.tabadol.api.Post;
import com.example.tabadol.api.User;

import java.util.ArrayList;

public class DiscoverUsersFragment extends Fragment {

    public DiscoverUsersFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.activity_list_view, container, false);
        ArrayList<Post> posts = new ArrayList<>();
        for(int i=0; i<15; i++)
            posts.add(new Post(1,"body", i+"", "type", 6, true, "createdAt", "offer type", new User()));
        PostAdapter postAdapter = new PostAdapter(getActivity(), posts);
        ListView listView = rootView.findViewById(R.id.listView);
        listView.setAdapter(postAdapter);
        return rootView;
    }
}