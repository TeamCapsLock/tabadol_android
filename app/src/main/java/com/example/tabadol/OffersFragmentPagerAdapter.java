package com.example.tabadol;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.tabadol.fragments.DiscoverUsersFragment;
import com.example.tabadol.fragments.FinishedOffersFragment;
import com.example.tabadol.fragments.PostFragment;
import com.example.tabadol.fragments.ReceivedOffersFragment;
import com.example.tabadol.fragments.SentOffersFragment;

public class OffersFragmentPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 3;
    private String tabTitles[] = new String[] {"Received", "Sent", "Finished"};
    private Context context;

    public OffersFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0)
            return new ReceivedOffersFragment();
        else if (position == 1)
            return new SentOffersFragment();
        else
            return new FinishedOffersFragment();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}