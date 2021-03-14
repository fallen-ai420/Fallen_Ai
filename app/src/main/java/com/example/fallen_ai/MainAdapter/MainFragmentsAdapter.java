package com.example.fallen_ai.MainAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.fallen_ai.MainFragments.Chats;
import com.example.fallen_ai.MainFragments.Course;
import com.example.fallen_ai.MainFragments.Profile;

public class MainFragmentsAdapter extends FragmentPagerAdapter {
    public MainFragmentsAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0 : return new Course();
            case 1 : return new Chats();
            case 2: return new Profile();
            default: return new Course();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if(position==0){
        title="Course";
        }
        if(position==1){
            title="Chats";
        }

        if (position==2) {
            title = "Profile";
        }
        return title;
    }
}
