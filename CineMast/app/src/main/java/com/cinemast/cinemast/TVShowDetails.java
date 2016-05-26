package com.cinemast.cinemast;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import Utilities.FetchFromServerTask;
import Utilities.FetchFromServerUser;
import Utilities.TVShowDetailBean;
import Utilities.TVShowDetailParser;

public class TVShowDetails extends Activity implements FetchFromServerUser {
    View view;
    TextView genre;
    TextView tvShowName;
    TextView year;
    TextView overview;
    ImageView poster;
    TextView createdBy;
    TextView duration;
    TextView firstAirDate;
    TextView noOfSeason;
    TextView noOfEpisode;
    ImageView tvShowImage;
    int tvShowID;
    private SliderLayout mDemoSlider;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tv_show_layout);
        genre = (TextView) findViewById(R.id.genre);
        tvShowName = (TextView) findViewById(R.id.tv_show_name);
        year = (TextView) findViewById(R.id.tv_show_year);
        overview = (TextView) findViewById(R.id.overview);
        poster = (ImageView) findViewById(R.id.poster);
        createdBy = (TextView) findViewById(R.id.created_by);
        duration = (TextView) findViewById(R.id.duration);
        firstAirDate = (TextView) findViewById(R.id.first_air_date);
        noOfSeason = (TextView) findViewById(R.id.no_Of_seasons);
        noOfEpisode = (TextView) findViewById(R.id.no_Of_episodes);
        //tvShowImage = (ImageView) findViewById(R.id.tv_show_images);

        mDemoSlider = (SliderLayout)findViewById(R.id.slider);

        HashMap<String,String> url_maps = new HashMap<String, String>();
        url_maps.put("Hannibal", "http://static2.hypable.com/wp-content/uploads/2013/12/hannibal-season-2-release-date.jpg");
        url_maps.put("Big Bang Theory", "http://tvfiles.alphacoders.com/100/hdclearart-10.png");
        url_maps.put("House of Cards", "http://cdn3.nflximg.net/images/3093/2043093.jpg");
        url_maps.put("Game of Thrones", "http://images.boomsbeat.com/data/images/full/19640/game-of-thrones-season-4-jpg.jpg");


        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);
        //mDemoSlider.addOnPageChangeListener(this);

        Intent intent = getIntent();
        tvShowID = intent.getIntExtra("ID", -1);

        if(tvShowID != -1) {

            new FetchFromServerTask(this, 0).execute("http://api.themoviedb.org/3/tv/" + tvShowID + "?api_key=0d9b1f55e11c548f66e11f78a7f38357");
        }

    }

    @Override
    public void onPreFetch() {

    }

    @Override
    public void onFetchCompletion(String string, int id) {

        TVShowDetailParser parser = new TVShowDetailParser(string);
        try {
            TVShowDetailBean detailsBean = parser.getTvShowDetail();
            tvShowName.setText(detailsBean.getName());
            int i = 0;
            int sizeGenre = detailsBean.getGenreDetails().size();
            String[] genreString = new String[sizeGenre];
            while(i < sizeGenre) {
                genreString[i] = detailsBean.getGenreDetails().get(i).getName();
                i++;
            }
            StringBuilder genreStr = new StringBuilder();
            for (i = 0; i < genreString.length; i++) {
                genreStr.append(genreString[i]);
                if (i < genreString.length - 1)
                    genreStr.append(" | ");
            }
            genre.setText(genreStr.toString());
            year.setText("(" + String.valueOf(detailsBean.getFirst_air_date().split("-")[0]) + ")");
            overview.setText(detailsBean.getOverview());
            Picasso.with(this).load("https://image.tmdb.org/t/p/w320/" + detailsBean.getPoster_path())
                    .error(R.drawable.notfound)
                    .placeholder(R.drawable.movie)

                    .into(poster);
            Picasso.with(this).load("https://image.tmdb.org/t/p/w320/" + detailsBean.getPoster_path())
                    .error(R.drawable.notfound)
                    .placeholder(R.drawable.movie)
                    .into(tvShowImage);
            int sizeCreatedBy = detailsBean.getCreatedBy().size();
            String[] createdByString = new String[sizeCreatedBy];
            i = 0;
            while(i < sizeCreatedBy) {
                createdByString[i] = detailsBean.getCreatedBy().get(i).getName();
                i++;
            }
            StringBuilder createdByStr = new StringBuilder();
            for (i = 0; i < createdByString.length; i++) {
                createdByStr.append(createdByString[i]);
                if (i < createdByString.length - 1)
                    createdByStr.append("\n");
            }
            createdBy.setText(createdByStr.toString());
            int[] episodeRunTime = detailsBean.getEpisode_run_time();
            StringBuilder episodeRunTimeStr = new StringBuilder();
            for (i = 0; i < episodeRunTime.length; i++) {
                episodeRunTimeStr.append(episodeRunTime[i]);
                if (i <= episodeRunTime.length - 1)
                    episodeRunTimeStr.append(" mins\n");
            }
            duration.setText(episodeRunTimeStr.toString());
            firstAirDate.setText(detailsBean.getFirst_air_date());
            noOfSeason.setText(String.valueOf(detailsBean.getNumber_of_seasons()));
            noOfEpisode.setText(String.valueOf(detailsBean.getNumber_of_episodes()));
            /*Fragment movieteVideoFragment = new MovieVideoFragment();
            Bundle data = new Bundle();
            data.putInt("ID", movieId);
            movieVideoFragment.setArguments(data);
            getSupportFragmentManager().beginTransaction().replace(R.id.video_frames, movieVideoFragment).commit();
            */
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }



}