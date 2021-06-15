package com.example.tabadol.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.tabadol.PostAdapter;
import com.example.tabadol.PostDetails;
import com.example.tabadol.R;
import com.example.tabadol.api.MyRoutes;
import com.example.tabadol.api.Post;
import com.example.tabadol.api.User;

import java.io.Serializable;
import java.util.ArrayList;

public class PostFragment extends Fragment  {
    private Intent intent;

    public PostFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    ArrayList<Post> posts = null;
    MyRoutes myRoutes ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.activity_list_view, container, false);

        posts = new ArrayList<>();
        myRoutes = MyRoutes.getMyRoutesInstanse(getActivity());
        posts = myRoutes.getPost2();
//        for(int i=0; i<15; i++)
//            posts.add(new Post(1,"body", "category", "type", 6, true, "createdAt", "offer type", new User()));
        PostAdapter postAdapter = new PostAdapter(getActivity(), posts);
        ListView listView = rootView.findViewById(R.id.listView);
        listView.setAdapter(postAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getActivity(), posts.get(position), Toast.LENGTH_SHORT).show();
                  intent = new Intent(getActivity(), PostDetails.class);
                  intent.putExtra("post", posts.get(position));
                  intent.putExtra("user", posts.get(position).getUser());
                  startActivity(intent);
            }

        });
        return rootView;
    }
}