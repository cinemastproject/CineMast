package com.cinemast.cinemast;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MoviesFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.movies_layout, container, false);

        Fragment inTheaterFragment = new InTheaterFragment();
        getChildFragmentManager().beginTransaction().replace(R.id.in_theater, inTheaterFragment).commit();

        Fragment topFragment = new TopRatedFragment();
        getChildFragmentManager().beginTransaction().replace(R.id.top_rated, topFragment).commit();

        Fragment upComingFragment = new UpComingFragment();
        getChildFragmentManager().beginTransaction().replace(R.id.up_coming, upComingFragment).commit();

        Fragment popularMoviesFragment = new PopularMoviesFragment();
        getChildFragmentManager().beginTransaction().replace(R.id.popularMovies, popularMoviesFragment).commit();
        return view;
    }
}