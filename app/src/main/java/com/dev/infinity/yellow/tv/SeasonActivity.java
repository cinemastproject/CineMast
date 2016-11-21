package com.dev.infinity.yellow.tv;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.dev.infinity.yellow.R;
import com.dev.infinity.yellow.common.GenericAdapter;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.etiennelawlor.imagegallery.library.activities.FullScreenImageGalleryActivity;
import com.etiennelawlor.imagegallery.library.adapters.FullScreenImageGalleryAdapter;
import com.etiennelawlor.imagegallery.library.enums.PaletteColorType;
import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.dev.infinity.yellow.modals.ImagesBean;
import com.dev.infinity.yellow.utils.RecyclerItemClickListener;
import com.dev.infinity.yellow.modals.SeasonDetailsBean;
import com.dev.infinity.yellow.utils.Utils;
import network.API;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SeasonActivity extends AppCompatActivity implements FullScreenImageGalleryAdapter.FullScreenImageLoader  {

    Retrofit retrofit = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://api.themoviedb.org/3/tv/")
            .build();

    API api = retrofit.create(API.class);

    private PaletteColorType paletteColorType;

    private ImageView poster;
    private ExpandableTextView overview;
    private SliderLayout cover;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.season_activity);

        paletteColorType = PaletteColorType.VIBRANT;

        overview = (ExpandableTextView) findViewById(R.id.overview);
        cover = (SliderLayout) findViewById(R.id.cover);
        poster = (ImageView) findViewById(R.id.poster);

        String id = getIntent().getStringExtra("ID");
        int seasonNumber = getIntent().getIntExtra("SEASON_NUMBER", -1);

        api.getSeason(id, String.valueOf(seasonNumber)).enqueue(new Callback<SeasonDetailsBean>() {
            @Override
            public void onResponse(Call<SeasonDetailsBean> call, Response<SeasonDetailsBean> response) {
                SeasonDetailsBean bean = response.body();
                overview.setText(bean.getOverview());
                Picasso.with(SeasonActivity.this).load("https://image.tmdb.org/t/p/w320/" + bean.getPoster_path())
                        .error(R.drawable.notfound)
                        .placeholder(R.drawable.movie)
                        .into(poster);

                final List<SeasonDetailsBean.Episode> moviesList = bean.getEpisodes();
                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.seasons);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(SeasonActivity.this, LinearLayoutManager.HORIZONTAL, false);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(SeasonActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        //Intent detailActivity = new Intent(TVShowDetails.this, SeasonActivity.class);
                        //detailActivity.putExtra("ID", String.valueOf(tvId));
                        //detailActivity.putExtra("SEASON_NUMBER", moviesList.get(position).getSeason_number());
                        //(detailActivity);
                    }
                }));
                recyclerView.setHasFixedSize(true);
                GenericAdapter adapter = new GenericAdapter(SeasonActivity.this, moviesList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<SeasonDetailsBean> call, Throwable t) {

            }
        });

        api.getImages(id + "/season/" + String.valueOf(seasonNumber)).enqueue(new Callback<ImagesBean>() {
            @Override
            public void onResponse(Call<ImagesBean> call, Response<ImagesBean> response) {
                List<ImagesBean.Profile> posters = response.body().getPosters();
                List<String> images = new ArrayList<>();

                for(int j = 0; j < posters.size(); j++) {
                    if(j >= 10)
                        break;
                    String path = posters.get(j).getFile_path();
                    images.add(path);
                }

                final ArrayList<String> imagesURL = new ArrayList<>();
                for(int i = 0; i < posters.size(); i++) {
                    imagesURL.add(i, "https://image.tmdb.org/t/p/w500/" + posters.get(i).getFile_path());
                }

                Collections.shuffle(imagesURL);

                for(int i = 0; i < images.size(); i++){
                    TextSliderView textSliderView = new TextSliderView(SeasonActivity.this);
                    textSliderView
                            .image("https://image.tmdb.org/t/p/w500/" + images.get(i))
                            .setScaleType(BaseSliderView.ScaleType.Fit);

                    textSliderView.bundle(new Bundle());
                    textSliderView.getBundle().putString("extra","" + i);

                    cover.addSlider(textSliderView);
                }

                ImageView gallery = (ImageView) findViewById(R.id.slideshow);
                gallery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(SeasonActivity.this, FullScreenImageGalleryActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putStringArrayList(FullScreenImageGalleryActivity.KEY_IMAGES, imagesURL);
                        bundle.putInt(FullScreenImageGalleryActivity.KEY_POSITION, 0);
                        intent.putExtras(bundle);
                        startActivity(intent);

                        FullScreenImageGalleryActivity.setFullScreenImageLoader(SeasonActivity.this);
                    }
                });
            }

            @Override
            public void onFailure(Call<ImagesBean> call, Throwable t) {

            }
        });
    }

    @Override
    public void loadFullScreenImage(final ImageView iv, String imageUrl, int width, final LinearLayout bgLinearLayout) {
        if (!TextUtils.isEmpty(imageUrl)) {
            Picasso.with(iv.getContext())
                    .load(imageUrl)
                    .resize(width, 0)
                    .into(iv, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {
                            Bitmap bitmap = ((BitmapDrawable) iv.getDrawable()).getBitmap();
                            Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                                public void onGenerated(Palette palette) {
                                    Utils.applyPalette(palette, paletteColorType, bgLinearLayout);
                                }
                            });
                        }

                        @Override
                        public void onError() {

                        }
                    });
        } else {
            iv.setImageDrawable(null);
        }
    }
}