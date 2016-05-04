package com.cinemast.cinemast;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import Utilities.FetchFromServerTask;
import Utilities.FetchFromServerUser;
import Utilities.PopularMovieBean;
import Utilities.PopularMovieParser;

public class InTheaterFragment extends Fragment implements FetchFromServerUser {

    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.in_theater, container, false);
        new FetchFromServerTask(this, 0).execute("https://api.themoviedb.org/3/movie/now_playing?api_key=0d9b1f55e11c548f66e11f78a7f38357");
        return view;
    }

    @Override
    public void onPreFetch() {

    }

    @Override
    public void onFetchCompletion(String string, int id) {
        PopularMovieParser parser = new PopularMovieParser(string);
        List<PopularMovieBean> popularMoviesList = parser.getMoviesList();
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.in_theater);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        MoviesAdapter adapter = new MoviesAdapter(getParentFragment().getActivity(), popularMoviesList);
        recyclerView.setAdapter(adapter);
    }
}