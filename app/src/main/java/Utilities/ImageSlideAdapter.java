package Utilities;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cinemast.cinemast.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageSlideAdapter extends PagerAdapter {

    FragmentActivity activity;
    List<String> images;

    public ImageSlideAdapter(FragmentActivity activity, List<String> images) {
        this.activity = activity;
        this.images = images;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public View instantiateItem(ViewGroup container, final int position) {
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.slider_image_item, container, false);

        ImageView mImageView = (ImageView) view.findViewById(R.id.image_display);
        Picasso.with(activity).load("https://image.tmdb.org/t/p/w320/" + images.get(position))
                .error(R.drawable.notfound)
                .placeholder(R.drawable.movie)
                .into(mImageView);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}