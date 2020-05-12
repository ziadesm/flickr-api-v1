package android.com.flickrapi_v1.adapter;

import android.com.flickrapi_v1.R;
import android.com.flickrapi_v1.pojo._PhotoModel;
import android.com.flickrapi_v1.pojo._PhotoModel.Photos.Photo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecentImageAdapter extends RecyclerView.Adapter<RecentImageAdapter.ImageViewHolder> {
    private List<_PhotoModel.Photos.Photo> mList = new ArrayList<>();

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ImageViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recycler_recent_photo, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        if (mList.get(position).getTitle() == null) {
            holder.titleTv.setText("There\'s no title here");
        } else {
            holder.titleTv.setText(mList.get(position).getTitle());
        }
        Picasso.get()
                .load(mList.get(position).getUrl_s())
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setList(_PhotoModel photos) {
        this.mList = photos.getPhotos().photo;
        notifyDataSetChanged();
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
