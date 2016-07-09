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

import java.util.ArrayList;
import java.util.List;

import Utilities.MovieDetailsBean;
import Utilities.MoviesContract;
import Utilities.RecyclerItemClickListener;
import Utilities.TVContract;
import Utilities.TvShowsBean;
import network.API;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GenericFragment extends Fragment {

    View view;
    RecyclerView recyclerView;
    String heading;
    List<MovieDetailsBean> moviesList = new ArrayList<>();
    List<TvShowsBean> tvShowsList = new ArrayList<>();
    MoviesAdapter adapter;

    String type;
    String mClass;

    Retrofit retrofit = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://api.themoviedb.org/3/")
            .build();
    API api = retrofit.create(API.class);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container,savedInstanceState);
        view = inflater.inflate(R.layout.generic_layout_fragment, container, false);
        type = getArguments().getString("TYPE");
        mClass = getArguments().getString("CLASS");

        TextView header = (TextView) view.findViewById(R.id.header);
        heading = getArguments().getString("HEADING");
        header.setText(heading);

        recyclerView = (RecyclerView) view.findViewById(R.id.popularMovies);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        if(type.equals("MOVIES")) {
            api.getMovies("movie", mClass, 1).enqueue(new Callback<MoviesContract>() {
                @Override
                public void onResponse(Call<MoviesContract> call, Response<MoviesContract> response) {
                    moviesList = response.body().getResults();

                    if (getActivity() != null) {

                        if (type != null && type.equals("MOVIES")) {
                            try {
                                recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(View view, int position) {
                                        Intent detailActivity = new Intent(getActivity(), MovieDetail.class);
                                        detailActivity.putExtra("ID", moviesList.get(position).getId());
                                        startActivity(detailActivity);
                                    }
                                }));
                                adapter = new MoviesAdapter(getActivity(), moviesList);
                                recyclerView.setAdapter(adapter);
                            } catch (Exception ex) {
                                showSnack(view, ex.getMessage(), R.color.red);
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<MoviesContract> call, Throwable t) {
                    t = null;
                }
            });
        }else if(type.equals("SERIAL")) {
            api.getTVShows("tv", mClass, 1).enqueue(new Callback<TVContract>() {
                @Override
                public void onResponse(Call<TVContract> call, Response<TVContract> response) {
                    if(getActivity() != null) {
                        try {
                            tvShowsList = response.body().getResults();
                            recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {
                                    Intent detailActivity = new Intent(getActivity(), TVShowDetails.class);
                                    detailActivity.putExtra("ID", tvShowsList.get(position).getId());
                                    startActivity(detailActivity);
                                }
                            }));
                            TvShowAdapter adapter = new TvShowAdapter(getParentFragment().getActivity(), tvShowsList);
                            recyclerView.setAdapter(adapter);

                        } catch (Exception ex) {
                            //showSnack(view, ex.getMessage(), R.color.red);
                            ex.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<TVContract> call, Throwable t) {
                    t = null;
                }
            });
        }
        return view;
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