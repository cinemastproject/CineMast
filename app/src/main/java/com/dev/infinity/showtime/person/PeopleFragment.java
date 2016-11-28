package com.dev.infinity.showtime.person;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.dev.infinity.showtime.R;
import com.dev.infinity.showtime.modals.PeopleResults;

import network.API;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PeopleFragment extends Fragment {

    private View view;
    private PeopleResults results = new PeopleResults();
    private GridView peopleGrid;
    private PeopleAdapter adapter;
    private boolean loading = true;
    private Snackbar msg;

    private Retrofit retrofit = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.themoviedb.org/3/person/")
            .build();

    private API api = retrofit.create(API.class);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.people_grid, container,false);
        peopleGrid = (GridView) view.findViewById(R.id.people_grid);
        peopleGrid.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(final AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(!loading && firstVisibleItem + visibleItemCount >= totalItemCount){
                    if(results.getPage() < results.getTotal_pages()){
                        loading = true;
                        msg = showSnack(view, "Loading More Stars...", R.color.green);
                        api.getPopularPeople("popular", (results.getPage() + 1)).enqueue(new Callback<PeopleResults>() {
                            @Override
                            public void onResponse(Call<PeopleResults> call, Response<PeopleResults> response) {
                                PeopleResults temp = response.body();
                                results.setPage(temp.getPage());
                                results.setTotal_results(temp.getTotal_results());
                                results.setTotal_pages(temp.getTotal_pages());
                                results.getResults().addAll(temp.getResults());
                                adapter.notifyDataSetChanged();
                                loading = false;
                                peopleGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        Intent peopleDetail = new Intent(getActivity(), Profile.class);
                                        peopleDetail.putExtra("ID", results.getResults().get(position).getId());
                                        startActivity(peopleDetail);
                                    }
                                });
                                if(msg != null)
                                    msg.dismiss();
                            }

                            @Override
                            public void onFailure(Call<PeopleResults> call, Throwable t) {
                                t.printStackTrace();
                                showSnack(view, t.getMessage(), R.color.red);
                            }
                        });
                    }
                }
            }
        });
        if(getActivity() != null)
            adapter = new PeopleAdapter(results.getResults(), getActivity());
        peopleGrid.setAdapter(adapter);
        api.getPopularPeople("popular", 1).enqueue(new Callback<PeopleResults>() {
            @Override
            public void onResponse(Call<PeopleResults> call, Response<PeopleResults> response) {
                PeopleResults temp = response.body();
                results.setPage(temp.getPage());
                results.setTotal_results(temp.getTotal_results());
                results.setTotal_pages(temp.getTotal_pages());
                results.getResults().addAll(temp.getResults());
                adapter.notifyDataSetChanged();
                loading = false;
                peopleGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent peopleDetail = new Intent(getActivity(), Profile.class);
                        peopleDetail.putExtra("ID", results.getResults().get(position).getId());
                        startActivity(peopleDetail);
                    }
                });
                if(msg != null)
                    msg.dismiss();
            }

            @Override
            public void onFailure(Call<PeopleResults> call, Throwable t) {
                t.printStackTrace();
                showSnack(view, t.getMessage(), R.color.red);
            }
        });
        return view;
    }

    public Snackbar showSnack(View view, String msg, int color) {
        Snackbar snack = Snackbar.make(view, msg, Snackbar.LENGTH_INDEFINITE);
        snack.setActionTextColor(ContextCompat.getColor(getActivity(), R.color.white));
        ViewGroup group = (ViewGroup) snack.getView();
        TextView tv = (TextView) group.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(Color.WHITE);
        group.setBackgroundColor(ContextCompat.getColor(getActivity(), color));
        snack.show();
        return snack;
    }
}