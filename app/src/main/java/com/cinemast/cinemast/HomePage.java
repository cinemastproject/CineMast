package com.cinemast.cinemast;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import Utilities.PagerAnimation;
import Utilities.SlidingTabLayout;

public class HomePage extends FragmentActivity {

    SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;
    SlidingTabLayout tabs;
    Fragment movies, tvShows, discover, people;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        movies = new MoviesFragment();
        tvShows = new TvShowsFragment();
        discover = new MoviesFragment();
        people = new MoviesFragment();

        initFragments();
    }

    private void initFragments(){

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mSectionsPagerAdapter.addFragment(movies);
        mSectionsPagerAdapter.addFragment(tvShows);
        mSectionsPagerAdapter.addFragment(discover);
        mSectionsPagerAdapter.addFragment(people);

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setPageTransformer(true, new PagerAnimation());

        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOffscreenPageLimit(3);

        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        if (tabs != null) {
            tabs.setDistributeEvenly(true);
        }

        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.yellow);
            }
        });

        tabs.setViewPager(mViewPager);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        List<android.support.v4.app.Fragment> fragments;

        private String []title = {"MOVIES", "SHOWS", "DISCOVER", "PEOPLE"};

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
            fragments = new ArrayList<>();
        }

        public void addFragment(android.support.v4.app.Fragment fragment){
            fragments.add(fragment);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public void startUpdate(ViewGroup container) {
            super.startUpdate(container);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return title[position];
        }
    }
}