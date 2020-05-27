package echomachine.com.flickrapi_v1.adapter;
import android.com.flickrapi_v1.R;
import echomachine.com.flickrapi_v1.interfaces.OnDoubleClickListener;
import echomachine.com.flickrapi_v1.pojo._PhotoModel.Photos.Photo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

public class RecentImageAdapter extends PagedListAdapter<Photo, RecentImageAdapter.ImageViewHolder> {

    public RecentImageAdapter() {
        super(Photo.CALLBACK);
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ImageViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recycler_recent_photo, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        Photo item = getItem(position);
        Picasso.get()
                .load(item.getUrl_s())
                .placeholder(R.drawable.ic_place_holder_home)
                .fit()
                .centerCrop()
                .into(holder.imageView);

        holder.imageView.setOnClickListener(new OnDoubleClickListener() {
            @Override
            public void onDoubleClick(View v) {
                if (holder.loveImageView.getVisibility() != v.getVisibility()) {
                    holder.loveImageView.setVisibility(View.VISIBLE);
                    Snackbar snackbar = Snackbar
                            .make(v, "Like!, show all liked", Snackbar.LENGTH_LONG)
                            .setAction("Show", view -> {
                                //TODO Access database and moving to other fragment (Create new fragment and viewModel)
                            });
                    snackbar.show();
                } else {
                    holder.loveImageView.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onSingleClick(View v) {
                //TODO Move to PhotoFragment (onGoing) to download and show other similar photo
            }
        });
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView, loveImageView;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_header);
            loveImageView = itemView.findViewById(R.id.love_border_header);
        }
    }
}
