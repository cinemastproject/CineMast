package com.cinemast.cinemast;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import Utilities.RecyclerItemClickListener;
import Utilities.SearchResultsBean;
import network.API;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MultiSearch extends Activity {

    Retrofit retrofit = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.themoviedb.org/3/search/")
            .build();

    API api = retrofit.create(API.class);

    AutoCompleteTextView search;
    SearchResultAdapter adapter;
    RecyclerView recyclerView;
    List<SearchResultsBean.Result> results;
    TextView countTxt;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.multi_search);

        results = new ArrayList<>();
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MultiSearch.this.finish();
            }
        });
        countTxt = (TextView) findViewById(R.id.count);
        recyclerView = (RecyclerView) findViewById(R.id.results);
        search = (AutoCompleteTextView) findViewById(R.id.search_box);
        search.setThreshold(2);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                api.getMultiSearchResult(s.toString()).enqueue(new Callback<SearchResultsBean>() {
                    @Override
                    public void onResponse(Call<SearchResultsBean> call, Response<SearchResultsBean> response) {
                        if(response.body() != null) {
                            results = response.body().getResults();
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MultiSearch.this, LinearLayoutManager.VERTICAL, false);
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(MultiSearch.this, new RecyclerItemClickListener.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {
                                    if(results.get(position).getMedia_type().equals("person")) {
                                        Intent peopleDetail = new Intent(MultiSearch.this, Profile.class);
                                        peopleDetail.putExtra("ID", String.valueOf(results.get(position).getId()));
                                        startActivity(peopleDetail);
                                    }
                                    else if(results.get(position).getMedia_type().equals("movie")) {
                                        Intent movieDetail = new Intent(MultiSearch.this, MovieDetail.class);
                                        movieDetail.putExtra("ID", String.valueOf(results.get(position).getId()));
                                        startActivity(movieDetail);
                                    }
                                    else if(results.get(position).getMedia_type().equals("tv")) {
                                        Intent tvShowDetail = new Intent(MultiSearch.this, TVShowDetails.class);
                                        tvShowDetail.putExtra("ID", results.get(position).getId());
                                        startActivity(tvShowDetail);
                                    }
                                }
                            }));
                            recyclerView.setHasFixedSize(true);
                            SearchResultAdapter adapter = new SearchResultAdapter(MultiSearch.this, results);
                            recyclerView.setAdapter(adapter);

                            countTxt.setText(response.body().getTotal_results() + " results found");
                        }
                    }

                    @Override
                    public void onFailure(Call<SearchResultsBean> call, Throwable t) {

                    }
                });
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}