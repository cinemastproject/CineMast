package com.dev.infinity.yellow.common;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dev.infinity.yellow.R;
import com.dev.infinity.yellow.movies.MoviesFragment;
import com.dev.infinity.yellow.person.PeopleFragment;
import com.dev.infinity.yellow.search.MultiSearch;
import com.dev.infinity.yellow.tv.TvShowsFragment;

import java.util.ArrayList;
import java.util.List;

import com.dev.infinity.yellow.utils.PagerAnimation;
import com.dev.infinity.yellow.utils.SlidingTabLayout;

public class HomePage extends FragmentActivity {

    SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;
    SlidingTabLayout tabs;
    Fragment movies, tvShows, people;
    ImageView search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        search = (ImageView) findViewById(R.id.search);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, MultiSearch.class);
                startActivity(intent);
            }
        });

        movies = new MoviesFragment();
        tvShows = new TvShowsFragment();
        people = new PeopleFragment();

        initFragments();
    }

    private void initFragments(){

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mSectionsPagerAdapter.addFragment(movies);
        mSectionsPagerAdapter.addFragment(tvShows);
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

        private String []title = {"MOVIES", "TV SHOWS", "PEOPLE"};

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