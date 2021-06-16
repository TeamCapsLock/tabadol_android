package com.example.tabadol.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.tabadol.PostAdapter;
import com.example.tabadol.R;
import com.example.tabadol.adapters.SentOffersAdapter;
import com.example.tabadol.api.MyRoutes;
import com.example.tabadol.api.Offer;
import com.example.tabadol.api.Post;
import com.example.tabadol.api.User;

import java.util.ArrayList;


public class SentOffersFragment extends Fragment {

    private ArrayList<Offer> offers;
    private MyRoutes myRoutes;
    public SentOffersFragment() {
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
        offers = new ArrayList<>();
        myRoutes = MyRoutes.getMyRoutesInstanse(getActivity());
        myRoutes.getSentOffers();
        offers = myRoutes.getSentOffers2();

        SentOffersAdapter sentOffersAdapter = new SentOffersAdapter(getActivity(), offers);
        ListView listView = rootView.findViewById(R.id.listView);
        listView.setAdapter(sentOffersAdapter);
        return rootView;
    }
}