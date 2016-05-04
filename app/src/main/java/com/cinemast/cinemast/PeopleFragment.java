package com.cinemast.cinemast;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import Utilities.FetchFromServerTask;
import Utilities.FetchFromServerUser;
import Utilities.PeopleParser;

public class PeopleFragment extends Fragment implements FetchFromServerUser{

    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.people_grid, container,false);
        new FetchFromServerTask(this, 0).execute("https://api.themoviedb.org/3/person/popular?api_key=0d9b1f55e11c548f66e11f78a7f38357");
        return view;
    }

    @Override
    public void onFetchCompletion(String string, int id) {
        GridView peopleGrid = (GridView) view.findViewById(R.id.people_grid);
        PeopleParser peopleParser = new PeopleParser(string);
        try {
            PeopleAdapter adapter = new PeopleAdapter(peopleParser.getPeopleList(), getActivity());
            peopleGrid.setAdapter(adapter);
        }catch (Exception ex){
            Toast.makeText(getActivity(), ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onPreFetch() {

    }
}
