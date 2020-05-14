package android.com.flickrapi_v1.adapter;
import android.com.flickrapi_v1.R;
import android.com.flickrapi_v1.pojo._PhotoModel.Photos.Photo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.RecyclerView;

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
        if (item.getTitle() == null) {
            holder.titleTv.setText(R.string.no_image_found);
        } else {
            holder.titleTv.setText(item.getTitle());
        }
        Picasso.get()
                .load(item.getUrl_s())
                .into(holder.imageView);
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView titleTv;
        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_header);
            titleTv = itemView.findViewById(R.id.title_image_tv);
        }
    }
}
