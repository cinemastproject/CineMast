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

import Utilities.FetchFromServerTask;
import Utilities.FetchFromServerUser;
import Utilities.PersonImagesBean;
import Utilities.PersonImagesParser;
import Utilities.RecyclerItemClickListener;

/**
 * Created by suny on 13/5/16.
 */
public class PersonGalleryFragment extends Fragment implements FetchFromServerUser{

    List<PersonImagesBean> personImagesList = new ArrayList<>();
    RecyclerView recyclerView;
    PersonGalleryAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        int personId = getArguments().getInt("ID");
        View view = inflater.inflate(R.layout.person_gallery, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.person_images);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        new FetchFromServerTask(this, 0).execute("http://api.themoviedb.org/3/person/" + personId + "/images?api_key=0d9b1f55e11c548f66e11f78a7f38357");
        return view;
    }

    @Override
    public void onPreFetch() {

    }

    @Override
    public void onFetchCompletion(String string, int id) {
        PersonImagesParser parser = new PersonImagesParser(string);
        try {
            personImagesList = parser.getImagesList();
            adapter = new PersonGalleryAdapter(getActivity(), personImagesList);
            recyclerView.setAdapter(adapter);
            recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {

                }
            }));
            adapter.notifyDataSetChanged();
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
