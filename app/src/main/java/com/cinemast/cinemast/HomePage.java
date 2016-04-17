package com.cinemast.cinemast;

import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class HomePage extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        Fragment popularMoviesFragment = new PopularMoviesFragment();
        getFragmentManager().beginTransaction().replace(R.id.popularMoviesFragment, popularMoviesFragment).commit();

        Fragment upcomingMoviesFragment = new UpcomingFragment();
        getFragmentManager().beginTransaction().replace(R.id.upcomingMoviesFragment, upcomingMoviesFragment).commit();
    }
}