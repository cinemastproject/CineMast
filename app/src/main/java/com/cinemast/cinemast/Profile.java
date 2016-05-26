package com.cinemast.cinemast;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import Utilities.FetchFromServerTask;
import Utilities.FetchFromServerUser;
import Utilities.ProfileDetailBean;
import Utilities.ProfileDetailParser;
import de.hdodenhof.circleimageview.CircleImageView;

public class Profile extends FragmentActivity implements FetchFromServerUser{

    TextView name, alsoKnownAs, gender, birthday, birthplace;
    ExpandableTextView biography;
    CircleImageView profileImage;
    int profileId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.people_profile);

        profileId = Integer.parseInt(getIntent().getStringExtra("ID"));

        name = (TextView) findViewById(R.id.name);
        alsoKnownAs = (TextView) findViewById(R.id.also_known_as);
        biography = (ExpandableTextView) findViewById(R.id.biography);
        gender = (TextView) findViewById(R.id.gender);
        birthday = (TextView) findViewById(R.id.birthday);
        birthplace = (TextView) findViewById(R.id.birth_country);
        profileImage = (CircleImageView) findViewById(R.id.person_image);

        if(profileId != -1) {
            Log.d("TAG", "http://api.themoviedb.org/3/person/"+ profileId + "?api_key=0d9b1f55e11c548f66e11f78a7f38357");
            new FetchFromServerTask(this, 0).execute("http://api.themoviedb.org/3/person/"+ profileId + "?api_key=0d9b1f55e11c548f66e11f78a7f38357");
        }

        Fragment personGallery = new PersonGalleryFragment();
        Fragment castedIn = new CastedInFragment();
        Bundle data = new Bundle();
        data.putInt("ID", profileId);
        personGallery.setArguments(data);
        castedIn.setArguments(data);
        getSupportFragmentManager().beginTransaction().replace(R.id.person_gallery, personGallery).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.casted_in, castedIn).commit();
    }

    @Override
    public void onPreFetch() {

    }

    @Override
    public void onFetchCompletion(String string, int id) {
        ProfileDetailParser parser = new ProfileDetailParser(string);
        try {
            ProfileDetailBean profile = parser.getProfileDetail();
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
            SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
            Date t = ft.parse(profile.getBirthday());
            birthday.setText(String.format("%tB %<te, %<tY", t));
            birthplace.setText(profile.getPlace_of_birth());
            biography.setText(profile.getBiography());
            Picasso.with(this).load("https://image.tmdb.org/t/p/w185/" + profile.getProfile_path()).error(R.drawable.notfound).placeholder(R.drawable.movie).into(profileImage);
        }catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
