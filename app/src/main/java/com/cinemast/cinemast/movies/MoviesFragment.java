package com.cinemast.cinemast.movies;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cinemast.cinemast.common.GenericFragment;
import com.cinemast.cinemast.R;

public class MoviesFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.movies_layout, container, false);

        Fragment inTheaterFragment = new GenericFragment();
        Bundle inTheaterData = new Bundle();
        inTheaterData.putString("HEADING", "Now In Theater");
        inTheaterData.putString("TYPE", "MOVIES");
        inTheaterData.putString("CLASS", "now_playing");
        inTheaterFragment.setArguments(inTheaterData);
        getChildFragmentManager().beginTransaction().replace(R.id.in_theater, inTheaterFragment).commit();

        Fragment topFragment = new GenericFragment();
        Bundle topRatedData = new Bundle();
        topRatedData.putString("HEADING", "Top Rated Movies");
        topRatedData.putString("TYPE", "MOVIES");
        topFragment.setArguments(topRatedData);
        topRatedData.putString("CLASS", "top_rated");
        getChildFragmentManager().beginTransaction().replace(R.id.top_rated, topFragment).commit();

        Fragment upComingFragment = new GenericFragment();
        Bundle upComingData = new Bundle();
        upComingData.putString("HEADING", "Upcoming Movies");
        upComingData.putString("TYPE", "MOVIES");
        upComingData.putString("CLASS", "upcoming");
        upComingFragment.setArguments(upComingData);
        getChildFragmentManager().beginTransaction().replace(R.id.up_coming, upComingFragment).commit();

        Fragment popularMoviesFragment = new GenericFragment();
        Bundle popularData = new Bundle();
        popularData.putString("HEADING", "Popular Movies");
        popularData.putString("TYPE", "MOVIES");
        popularData.putString("CLASS", "popular");
        popularMoviesFragment.setArguments(popularData);
        getChildFragmentManager().beginTransaction().replace(R.id.popular_movies, popularMoviesFragment).commit();

        return view;
    }
}