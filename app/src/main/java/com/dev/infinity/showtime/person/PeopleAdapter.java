package com.dev.infinity.showtime.person;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dev.infinity.showtime.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import com.dev.infinity.showtime.modals.PeopleBean;

class PeopleAdapter extends BaseAdapter {

    private List<PeopleBean> peopleList;
    private Context context;
    private LayoutInflater inflater;

    PeopleAdapter(List<PeopleBean> peopleList, Context context) {
        super();
        this.peopleList = peopleList;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null)
            convertView = inflater.inflate(R.layout.people_grid_item, parent, false);

        TextView name = (TextView) convertView.findViewById(R.id.person_name);
        ImageView personImage = (ImageView) convertView.findViewById(R.id.person_image);

        Picasso.with(context).load("https://image.tmdb.org/t/p/w185/" + peopleList.get(position).getProfile_path()).error(R.drawable.notfound).placeholder(R.drawable.movie).into(personImage);
        name.setText(peopleList.get(position).getName());
        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return peopleList.get(position);
    }

    @Override
    public int getCount() {
        return peopleList.size();
    }
}
