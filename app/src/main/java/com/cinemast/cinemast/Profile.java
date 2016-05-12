package com.cinemast.cinemast;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import Utilities.PopularMovieBean;

public class Profile extends Activity{
    String []posters = new String[]{
            "/8EueDe6rPF0jQU4LSpsH2Rmrqac.jpg",
            "/yDYpYy09nfyXIWHC6mbsaRdLiDV.jpg",
            "/v8qgUe7VUI1tjhvkMBSxbAQUsTC.jpg",
            "/iFw7g1L12c2vh5BVF0soIoYQn1l.jpg",
            "/f3c1rwcOoeU0v6Ak5loUvMyifR0.jpg",
            "/5zljIJOYpOEtWJVVdrnyDR0uiI.jpg",
            "/5AIP3FA0tXGXbWtm4T6pEkNVhhf.jpg",
            "/HRVCIV7uBz9aCxddikcuXgiZZd.jpg",
            "/eYFHUWxTCNg6lPypJCaUQXhoUop.jpg",
            "/f3c1rwcOoeU0v6Ak5loUvMyifR0.jpg"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.people_profile);

        TextView overview = (TextView) findViewById(R.id.overview);
        overview.setSelected(true);
        overview.setEllipsize(TextUtils.TruncateAt.MARQUEE);

        List<PopularMovieBean> popularMoviesList = new ArrayList<>();
        movies(popularMoviesList);
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.person_movies);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        MoviesAdapter adapter = new MoviesAdapter(this, popularMoviesList);
        recyclerView.setAdapter(adapter);
    }

    public void movies(List<PopularMovieBean> popularMoviesList){
        for(int i = 0; i < posters.length; i++) {
            PopularMovieBean bean = new PopularMovieBean();
            bean.setTitle("Scarlett Johanson");
            bean.setPoster_path(posters[i]);
            popularMoviesList.add(bean);
        }
    }
}
