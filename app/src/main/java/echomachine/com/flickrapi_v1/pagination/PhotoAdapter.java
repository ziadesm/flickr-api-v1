package android.com.flickrapi_v1.pagination;
import android.com.flickrapi_v1.R;
import android.com.flickrapi_v1.pojo._PhotoModel;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PhotoAdapter extends PagedListAdapter<_PhotoModel.Photos.Photo, PhotoAdapter.PhotoViewHolder> {
    private List<_PhotoModel.Photos.Photo> mList = new ArrayList<>();

    public PhotoAdapter() {
        super(_PhotoModel.Photos.Photo.CALLBACK);
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_recent_photo, parent, false);
        return new PhotoViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
        holder.titleTv.setText(getItem(position).getTitle());
        Picasso.get()
                .load(getItem(position).getUrl_s())
                .into(holder.imageView);
    }

    public class PhotoViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView titleTv;
        public PhotoViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_header);
            titleTv = itemView.findViewById(R.id.title_image_tv);
        }
    }
}
