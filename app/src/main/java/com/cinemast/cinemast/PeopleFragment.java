package com.cinemast.cinemast;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import Utilities.FetchFromServerTask;
import Utilities.FetchFromServerUser;
import Utilities.PeopleBean;
import Utilities.PeopleParser;
import Utilities.PeopleResults;

public class PeopleFragment extends Fragment implements FetchFromServerUser{

    View view;
    PeopleResults results = new PeopleResults();
    GridView peopleGrid;
    PeopleAdapter adapter;
    boolean loading = true;
    Snackbar msg;

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
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(!loading && firstVisibleItem + visibleItemCount >= totalItemCount){
                    if(results.getPage() < results.getTotalPages()){
                        loading = true;
                        msg = showSnack(view, "Loading More Stars...", R.color.blue);
                        new FetchFromServerTask(PeopleFragment.this, 0).execute("https://api.themoviedb.org/3/person/popular?api_key=0d9b1f55e11c548f66e11f78a7f38357" + "&page=" + (results.getPage() + 1));
                    }
                }
            }
        });
        if(getActivity() != null)
            adapter = new PeopleAdapter(results.getMoviesList(), getActivity());
        peopleGrid.setAdapter(adapter);
        new FetchFromServerTask(this, 0).execute("https://api.themoviedb.org/3/person/popular?api_key=0d9b1f55e11c548f66e11f78a7f38357");
        return view;
    }

    @Override
    public void onFetchCompletion(String string, int id) {
        PeopleParser peopleParser = new PeopleParser(string);
        if(getActivity() != null) {
            try {
                PeopleResults temp = peopleParser.getPeopleList();
                results.setPage(temp.getPage());
                results.setTotalResults(temp.getTotalResults());
                results.setTotalPages(temp.getTotalPages());
                results.getMoviesList().addAll(temp.getMoviesList());
                adapter.notifyDataSetChanged();
                loading = false;
                peopleGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent peopleDetail = new Intent(getActivity(), Profile.class);
                        peopleDetail.putExtra("ID", results.getMoviesList().get(position).getId());
                        startActivity(peopleDetail);
                    }
                });
                if(msg != null)
                    msg.dismiss();
            } catch (Exception ex) {
                showSnack(view, ex.getMessage(), R.color.red);
            }
        }
    }

    @Override
    public void onPreFetch() {

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
