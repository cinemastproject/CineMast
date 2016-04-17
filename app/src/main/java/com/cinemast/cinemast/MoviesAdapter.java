package com.cinemast.cinemast;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MoviesAdapter extends BaseAdapter{

    Context context;
    List<PopularMovieBean> list;
    LayoutInflater inflater;

    public MoviesAdapter(Context context, List<PopularMovieBean> list){
        this.context = context;
        this.list = list;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = inflater.inflate(R.layout.new_released_movies_item, parent, false);
        ImageView poster = (ImageView) convertView.findViewById(R.id.moviePoster);
        //TextView title = (TextView) convertView.findViewById(R.id.title);

        Picasso.with(context).load("http://image.tmdb.org/t/p/w342/" + list.get(position).getPoster_path()).into(poster);
        //title.setText(list.get(position).getTitle());
        return convertView;
    }
}
