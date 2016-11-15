package com.cinemast.cinemast;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import Utilities.CombinedCastDetail;
import Utilities.TVShowDetailBean;

public class GenericAdapter extends RecyclerView.Adapter<GenericAdapter.ViewHolder>{

    Context context;
    List<?> list;
    LayoutInflater inflater;

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private ImageView poster;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            poster = (ImageView) itemView.findViewById(R.id.moviePoster);
        }
    }

    public GenericAdapter(Context context, List<?> list){
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

        if(list.get(position) instanceof CombinedCastDetail.CastDetail) {
            CombinedCastDetail.CastDetail item = (CombinedCastDetail.CastDetail)list.get(position);
            holder.title.setText(item.getOriginal_title());
            if (item.getPoster_path() != null)
                Picasso.with(context).load("https://image.tmdb.org/t/p/w185/" + item.getPoster_path())
                        .error(R.drawable.notfound)
                        .placeholder(R.drawable.movie)
                        .into(holder.poster);
        }else if(list.get(position) instanceof TVShowDetailBean.Season) {
            TVShowDetailBean.Season item = (TVShowDetailBean.Season)list.get(position);
            holder.title.setText("Season " + item.getSeason_number());
            if (item.getPoster_path() != null)
                Picasso.with(context).load("https://image.tmdb.org/t/p/w185/" + item.getPoster_path())
                        .error(R.drawable.notfound)
                        .placeholder(R.drawable.movie)
                        .into(holder.poster);
        }
    }
}