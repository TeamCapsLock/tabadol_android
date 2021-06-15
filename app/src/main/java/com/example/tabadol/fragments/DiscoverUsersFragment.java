package com.example.tabadol.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.tabadol.PostAdapter;
import com.example.tabadol.R;
import com.example.tabadol.UserProfile;
import com.example.tabadol.adapters.AllUsersAdapter;
import com.example.tabadol.api.MyRoutes;
import com.example.tabadol.api.Post;
import com.example.tabadol.api.User;

import java.util.ArrayList;

public class DiscoverUsersFragment extends Fragment {
    private Intent intent;

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
        MyRoutes myRoutes = MyRoutes.getMyRoutesInstanse(getActivity());
        ArrayList<User> users  =myRoutes.getAllUsers2();
        View rootView = inflater.inflate(R.layout.activity_list_view, container, false);
//        ArrayList<Post> posts = new ArrayList<>();
//        for(int i=0; i<15; i++)
//            posts.add(new Post(1,"body", i+"", "type", 6, true, "createdAt", "offer type", new User()));
        AllUsersAdapter postAdapter = new AllUsersAdapter(getActivity(), users);
        ListView listView = rootView.findViewById(R.id.listView);
        listView.setAdapter(postAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                intent = new Intent(getActivity(), UserProfile.class);
                intent.putExtra("id", users.get(i).getId());
                startActivity(intent);
            }
        });

        return rootView;
    }
}