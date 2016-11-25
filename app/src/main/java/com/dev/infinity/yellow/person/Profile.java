package com.dev.infinity.yellow.person;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dev.infinity.yellow.R;
import com.dev.infinity.yellow.tv.Episode;
import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.dev.infinity.yellow.modals.ProfileDetailBean;
import de.hdodenhof.circleimageview.CircleImageView;
import network.API;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Profile extends FragmentActivity {

    private TextView name, alsoKnownAs, gender, birthday, birthplace;
    private ExpandableTextView biography;
    private CircleImageView profileImage;

    private Retrofit retrofit = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://api.themoviedb.org/3/person/")
            .build();

    private API api = retrofit.create(API.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.people_profile);

        int profileId = Integer.parseInt(getIntent().getStringExtra("ID"));

        name = (TextView) findViewById(R.id.name);
        alsoKnownAs = (TextView) findViewById(R.id.also_known_as);
        biography = (ExpandableTextView) findViewById(R.id.biography);
        gender = (TextView) findViewById(R.id.gender);
        birthday = (TextView) findViewById(R.id.birthday);
        birthplace = (TextView) findViewById(R.id.birth_country);
        profileImage = (CircleImageView) findViewById(R.id.person_image);

        ImageView back = (ImageView) findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Profile.this.finish();
            }
        });

        if(profileId != -1) {
            api.getProfile(profileId).enqueue(new Callback<ProfileDetailBean>() {
                @Override
                public void onResponse(Call<ProfileDetailBean> call, Response<ProfileDetailBean> response) {
                    ProfileDetailBean profile = response.body();
                    name.setText(profile.getName());
                    ArrayList<String> list = profile.getAlso_known_as();
                    StringBuilder builder = new StringBuilder();
                    for (int i = 0; i < list.size(); i++) {
                        builder.append(list.get(i));
                        if(i <= list.size() - 2)
                            builder.append(" | ");
                    }
                    alsoKnownAs.setText(builder.toString());
                    if(profile.getGender() == 1)
                        gender.setText("ACTRESS");
                    else
                        gender.setText("ACTOR");
                    try {
                        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
                        Date t = ft.parse(profile.getBirthday());
                        birthday.setText(String.format("%tB %<te, %<tY", t));
                    }catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    birthplace.setText(profile.getPlace_of_birth());
                    biography.setText(profile.getBiography());

                    ImageView cover = (ImageView) findViewById(R.id.cover);
                    Picasso.with(getApplicationContext()).load("https://image.tmdb.org/t/p/w185/" + profile.getProfile_path()).error(R.drawable.notfound).placeholder(R.drawable.movie).into(profileImage);
                    Picasso.with(getApplicationContext()).load("https://image.tmdb.org/t/p/w500/" + profile.getProfile_path()).error(R.drawable.notfound).placeholder(R.drawable.movie).into(cover);
                }

                @Override
                public void onFailure(Call<ProfileDetailBean> call, Throwable t) {
                    t.printStackTrace();
                }
            });

            Fragment personGallery = new PersonGalleryFragment();
            Fragment castedIn = new CastedInFragment();
            Bundle data = new Bundle();
            data.putInt("ID", profileId);
            personGallery.setArguments(data);
            castedIn.setArguments(data);
            getSupportFragmentManager().beginTransaction().replace(R.id.person_gallery, personGallery).commit();
            getSupportFragmentManager().beginTransaction().replace(R.id.casted_in, castedIn).commit();
        }
    }
}
