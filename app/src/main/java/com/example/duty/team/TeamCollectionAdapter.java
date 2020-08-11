package com.example.duty.team;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class TeamCollectionAdapter extends FragmentStateAdapter {

    public TeamCollectionAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    public TeamCollectionAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    public TeamCollectionAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        Fragment fragment = new Fragment();

        switch (position) {
            default: break;
            case 0:
                fragment = new TeamHomeFragment();
                break;
            case 1:
                fragment = new TeamCalendarFragment();
                break;
            case 2:
                fragment = new TeamPostFragment();
                break;
            case 3:
                fragment = new TeamSettingFragment();
                break;
        }

        return fragment;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}