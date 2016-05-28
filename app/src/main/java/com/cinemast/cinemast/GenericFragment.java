package com.cinemast.cinemast;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Utilities.FetchFromServerTask;
import Utilities.FetchFromServerUser;
import Utilities.PopularMovieBean;
import Utilities.PopularMovieParser;
import Utilities.RecyclerItemClickListener;
import Utilities.TvShowsBean;
import Utilities.TvShowsParser;

public class GenericFragment extends Fragment implements FetchFromServerUser {

    View view;
    RecyclerView recyclerView;
    String heading;
    List<PopularMovieBean> moviesList = new ArrayList<>();
    List<TvShowsBean> tvShowsList = new ArrayList<>();
    MoviesAdapter adapter;

    String type;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container,savedInstanceState);
        view = inflater.inflate(R.layout.generic_layout_fragment, container, false);
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
        heading = getArguments().getString("HEADING");
        header.setText(heading);

        recyclerView = (RecyclerView) view.findViewById(R.id.popularMovies);
        if(getActivity() != null) {
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setHasFixedSize(true);

            if (type != null && type.equals("MOVIES")) {
                try {
                    PopularMovieParser parser = new PopularMovieParser(string);
                    recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Intent detailActivity = new Intent(getActivity(), MovieDetail.class);
                            detailActivity.putExtra("ID", moviesList.get(position).getId());
                            startActivity(detailActivity);
                        }
                    }));
                    moviesList = parser.getMoviesList();
                    adapter = new MoviesAdapter(getActivity(), moviesList);
                    recyclerView.setAdapter(adapter);
                } catch (Exception ex) {
                    showSnack(view, ex.getMessage(), R.color.red);
                }
            } else if (type != null && type.equals("SERIAL")) {
                TvShowsParser parser = new TvShowsParser(string);
                try {
                    tvShowsList = parser.getShowsList();
                    recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Intent detailActivity = new Intent(getActivity(), MovieDetails.class);
                            detailActivity.putExtra("ID", tvShowsList.get(position).getId());
                            startActivity(detailActivity);
                        }
                    }));
                    TvShowAdapter adapter = new TvShowAdapter(getParentFragment().getActivity(), tvShowsList);
                    recyclerView.setAdapter(adapter);
                } catch (Exception ex) {
                    showSnack(view, ex.getMessage(), R.color.red);
                }
            }
        }
    }

    public void showSnack(View view, String msg, int color) {
        Snackbar snack = Snackbar.make(view, msg, Snackbar.LENGTH_LONG);
        snack.setActionTextColor(ContextCompat.getColor(getActivity(), R.color.white));
        ViewGroup group = (ViewGroup) snack.getView();
        TextView tv = (TextView) group.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(Color.WHITE);
        group.setBackgroundColor(ContextCompat.getColor(getActivity(), color));
        snack.show();
    }
}