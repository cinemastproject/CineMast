package com.dev.infinity.yellow.movies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dev.infinity.yellow.R;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;

import com.dev.infinity.yellow.modals.MovieDetailsBean;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder>{

    private Context context;
    private List<MovieDetailsBean> list;
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

    public MoviesAdapter(Context context, List<MovieDetailsBean> list){
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
        View view = inflater.inflate(R.layout.display_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.title.setText(list.get(position).getTitle());
        if(list.get(position).getPoster_path() != null)
            Picasso.with(context).load("https://image.tmdb.org/t/p/w185/" + list.get(position).getPoster_path())
                    .error(R.drawable.notfound)
                    .placeholder(R.drawable.movie)
                    .into(holder.poster);
    }
}
