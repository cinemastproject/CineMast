package com.cinemast.cinemast;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import Utilities.FetchFromServerTask;
import Utilities.FetchFromServerUser;
import Utilities.PopularMovieBean;
import Utilities.PopularMovieParser;
import Utilities.RecyclerItemClickListener;

public class GenericMoviesFragment extends Fragment implements FetchFromServerUser {

    View view;
    String type;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.popular_movies_fragment_layout, container, false);
        String url = getArguments().getString("URL");
        type = getArguments().getString("TYPE");
        new FetchFromServerTask(this, 0).execute(url);
        return view;
    }

    @Override
    public void onPreFetch() {

    }

    @Override
    public void onFetchCompletion(String string, int id) {
        TextView header = (TextView) view.findViewById(R.id.header);
        header.setText(type);
        PopularMovieParser parser = new PopularMovieParser(string);
        final List<PopularMovieBean> popularMoviesList = parser.getMoviesList();
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.popularMovies);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent detailActivity = new Intent(getActivity(), MovieDetail.class);
                detailActivity.putExtra("ID", popularMoviesList.get(position).getId());
                startActivity(detailActivity);
            }
        }));
        recyclerView.setHasFixedSize(true);
        MoviesAdapter adapter = new MoviesAdapter(getActivity(), popularMoviesList);
        recyclerView.setAdapter(adapter);
    }
}