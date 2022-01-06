package edu.neu.myapplication.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import edu.neu.myapplication.fragments.LearningMainFragment;
import edu.neu.myapplication.fragments.LevelTestFragment;
import edu.neu.myapplication.fragments.Add;
import edu.neu.myapplication.fragments.Home;
//import edu.neu.myapplication.fragments.PostFood;
import edu.neu.myapplication.fragments.PostFood;
import edu.neu.myapplication.fragments.Profile;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    int noOfTabs;

    LearningMainFragment learningMainFragment = new LearningMainFragment();
    Home home = new Home();
    Add add = new Add();
    PostFood postFood = new PostFood();
    Profile profile = new Profile();


    public ViewPagerAdapter(@NonNull FragmentManager fm, int noOfTabs) {
        super(fm);
        this.noOfTabs = noOfTabs;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
//                return new LearningMainFragment();
                return learningMainFragment;
            case 1:
                return home;
            case 2:
                return add;
            case 3:
                return postFood;
            case 4:
                return profile;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return noOfTabs;
    }
}
