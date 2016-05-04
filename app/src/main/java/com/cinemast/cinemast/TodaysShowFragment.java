package com.cinemast.cinemast;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import Utilities.FetchFromServerTask;
import Utilities.FetchFromServerUser;
import Utilities.PopularMovieBean;
import Utilities.PopularMovieParser;
import Utilities.TvShowsBean;
import Utilities.TvShowsParser;

public class TodaysShowFragment extends Fragment implements FetchFromServerUser {

    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.today_show, container, false);
        new FetchFromServerTask(this, 0).execute("https://api.themoviedb.org/3/tv/airing_today?api_key=0d9b1f55e11c548f66e11f78a7f38357");
        return view;
    }

    @Override
    public void onPreFetch() {

    }

    @Override
    public void onFetchCompletion(String string, int id) {
        Log.d("Today's Show", string);
        TvShowsParser parser = new TvShowsParser(string);
        try {
            List<TvShowsBean> tvShowsList = parser.getShowsList();
            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.today_show);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setHasFixedSize(true);
            TvShowAdapter adapter = new TvShowAdapter(getParentFragment().getActivity(), tvShowsList);
            recyclerView.setAdapter(adapter);
        }catch (Exception ex) {
            Toast.makeText(getActivity(), ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}