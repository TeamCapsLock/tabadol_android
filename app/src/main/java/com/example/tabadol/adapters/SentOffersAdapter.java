package com.example.tabadol.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.tabadol.R;
import com.example.tabadol.api.Offer;
import com.example.tabadol.api.Post;

import java.util.ArrayList;

public class SentOffersAdapter extends ArrayAdapter<Offer> {
    private ArrayList<Offer> offers;
    private Context context;
    public SentOffersAdapter(Context context, ArrayList<Offer> offers){
        super(context, 0, offers);
        this.offers = offers;
        this.context = context;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        View offersList =LayoutInflater.from(context).inflate(R.layout.)
    }
}
