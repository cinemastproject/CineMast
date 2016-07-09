package com.cinemast.cinemast;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import Utilities.PersonImagesBean;
import Utilities.RecyclerItemClickListener;
import network.API;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PersonGalleryFragment extends Fragment {

    List<PersonImagesBean.Profile> personImagesList = new ArrayList<>();
    RecyclerView recyclerView;
    PersonGalleryAdapter adapter;

    Retrofit retrofit = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://api.themoviedb.org/3/person/")
            .build();
    API api = retrofit.create(API.class);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        int personId = getArguments().getInt("ID");
        View view = inflater.inflate(R.layout.person_gallery, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.person_images);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        api.getPersonImages(String.valueOf(personId)).enqueue(new Callback<PersonImagesBean>() {
            @Override
            public void onResponse(Call<PersonImagesBean> call, Response<PersonImagesBean> response) {
                personImagesList = response.body().getProfiles();
                adapter = new PersonGalleryAdapter(getActivity(), personImagesList);
                recyclerView.setAdapter(adapter);
                recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                    }
                }));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<PersonImagesBean> call, Throwable t) {
                t.printStackTrace();
            }
        });

        return view;
    }
}
