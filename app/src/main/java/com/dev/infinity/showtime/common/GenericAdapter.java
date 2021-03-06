package com.dev.infinity.showtime.common;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dev.infinity.showtime.R;
import com.dev.infinity.showtime.modals.CrewDetails;
import com.dev.infinity.showtime.modals.GuestStarsDetails;
import com.squareup.picasso.Picasso;

import java.util.List;

import com.dev.infinity.showtime.modals.CombinedCastDetail;
import com.dev.infinity.showtime.modals.SeasonDetailsBean;
import com.dev.infinity.showtime.modals.TVShowDetailBean;

public class GenericAdapter extends RecyclerView.Adapter<GenericAdapter.ViewHolder>{

    private Context context;
    private List<?> list;
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
        }else if(list.get(position) instanceof SeasonDetailsBean.Episode) {
            SeasonDetailsBean.Episode item = (SeasonDetailsBean.Episode) list.get(position);
            holder.title.setText(item.getName());
            if (item.getStill_path() != null)
                Picasso.with(context).load("https://image.tmdb.org/t/p/w185/" + item.getStill_path())
                        .error(R.drawable.notfound)
                        .placeholder(R.drawable.movie)
                        .into(holder.poster);
        }else if(list.get(position) instanceof TVShowDetailBean) {
            TVShowDetailBean item = (TVShowDetailBean)list.get(position);
            holder.title.setText(item.getName());
            if (item.getPoster_path() != null)
                Picasso.with(context).load("https://image.tmdb.org/t/p/w185/" + item.getPoster_path())
                        .error(R.drawable.notfound)
                        .placeholder(R.drawable.movie)
                        .into(holder.poster);
        }else if(list.get(position) instanceof GuestStarsDetails) {
            GuestStarsDetails item = (GuestStarsDetails)list.get(position);
            holder.title.setText(item.getName());
            if (item.getProfile_path() != null)
                Picasso.with(context).load("https://image.tmdb.org/t/p/w185/" + item.getProfile_path())
                        .error(R.drawable.notfound)
                        .placeholder(R.drawable.movie)
                        .into(holder.poster);
        }else if(list.get(position) instanceof CrewDetails) {
            CrewDetails item = (CrewDetails)list.get(position);
            holder.title.setText(item.getName());
            if (item.getProfile_path() != null)
                Picasso.with(context).load("https://image.tmdb.org/t/p/w185/" + item.getProfile_path())
                        .error(R.drawable.notfound)
                        .placeholder(R.drawable.movie)
                        .into(holder.poster);
        }
    }
}