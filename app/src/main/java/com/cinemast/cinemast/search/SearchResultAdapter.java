package com.cinemast.cinemast.search;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cinemast.cinemast.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import com.cinemast.cinemast.modals.SearchResultsBean;

/**
 * Created by sumitkumar on 11/11/16.
 */

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.ViewHolder>{

        private Context context;
        private List<SearchResultsBean.Result> list;
        private LayoutInflater inflater;

        static class ViewHolder extends RecyclerView.ViewHolder {

            private TextView name;
            private ImageView image;
            private TextView type;

            public ViewHolder(View itemView) {
                super(itemView);
                name = (TextView) itemView.findViewById(R.id.name);
                image = (ImageView) itemView.findViewById(R.id.image);
                type = (TextView) itemView.findViewById(R.id.type);
            }
        }

        public SearchResultAdapter(Context context, List<SearchResultsBean.Result> list){
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
        public SearchResultAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.search_result_item, parent, false);
            return new SearchResultAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(SearchResultAdapter.ViewHolder holder, int position) {

            if(list.get(position).getMedia_type().equals("movie")) {
                holder.name.setText(list.get(position).getTitle());
            }else if(list.get(position).getMedia_type().equals("tv")) {
                holder.name.setText(list.get(position).getName());
            }else if(list.get(position).getMedia_type().equals("person")) {
                holder.name.setText(list.get(position).getName());
                if(list.get(position).getProfile_path() != null)
                    Picasso.with(context).load("https://image.tmdb.org/t/p/w185/" + list.get(position).getProfile_path())
                            .error(R.drawable.notfound)
                            .placeholder(R.drawable.movie)
                            .error(R.drawable.notfound)
                            .into(holder.image);
            }
            holder.type.setText(list.get(position).getMedia_type().toUpperCase());
            if(list.get(position).getPoster_path() != null)
                Picasso.with(context).load("https://image.tmdb.org/t/p/w185/" + list.get(position).getPoster_path())
                        .error(R.drawable.notfound)
                        .placeholder(R.drawable.movie)
                        .error(R.drawable.notfound)
                        .into(holder.image);
        }
    }