package com.dev.infinity.yellow.common;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dev.infinity.yellow.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import com.dev.infinity.yellow.modals.MovieVideosBean;


public class MovieVideoAdapter extends RecyclerView.Adapter<MovieVideoAdapter.ViewHolder>{

    private Context context;
    private List<MovieVideosBean.Result> list;
    private LayoutInflater inflater;

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private ImageView poster;

        ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            poster = (ImageView) itemView.findViewById(R.id.moviePoster);
        }
    }

    public MovieVideoAdapter(Context context, List<MovieVideosBean.Result> list){
        this.context = context;
        this.list = list;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.movie_video_detail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.title.setText(list.get(position).getName());
        if(list.get(position).getKey() != null)
            Picasso.with(context).load("https://img.youtube.com/vi/" + list.get(position).getKey() + "/0.jpg")
                    .error(R.drawable.notfound)
                    .placeholder(R.drawable.movie)
                    .into(holder.poster);
    }
}