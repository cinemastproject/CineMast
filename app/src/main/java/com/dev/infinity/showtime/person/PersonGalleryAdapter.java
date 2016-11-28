package com.dev.infinity.showtime.person;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dev.infinity.showtime.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import com.dev.infinity.showtime.modals.PersonImagesBean;

public class PersonGalleryAdapter extends RecyclerView.Adapter<PersonGalleryAdapter.ViewHolder>{

    Context context;
    List<PersonImagesBean.Profile> list;
    LayoutInflater inflater;

    static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView poster;

        public ViewHolder(View itemView) {
            super(itemView);
            poster = (ImageView) itemView.findViewById(R.id.moviePoster);
        }
    }

    public PersonGalleryAdapter(Context context, List<PersonImagesBean.Profile> list){
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
        View view = inflater.inflate(R.layout.gallery_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(list.get(position).getFile_path() != null)
            Picasso.with(context).load("https://image.tmdb.org/t/p/w185/" + list.get(position).getFile_path())
                    .error(R.drawable.notfound)
                    .placeholder(R.drawable.movie)
                    .into(holder.poster);
    }
}
