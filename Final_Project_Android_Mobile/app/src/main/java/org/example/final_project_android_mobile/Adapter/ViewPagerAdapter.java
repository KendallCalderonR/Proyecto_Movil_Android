package org.example.final_project_android_mobile.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> fragments = new ArrayList<>();
    private final List<String> FragmentsTitles = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        return fragments.get(position);
    }

    @Override
    public int getCount() {

        return FragmentsTitles.size();
    }

    @Override
    public CharSequence getPageTitle(int position){
        return  FragmentsTitles.get(position);
    }

    public void AddFragment (Fragment fragment, String Title){
        fragments.add(fragment);
        FragmentsTitles.add(Title);
    }
}
