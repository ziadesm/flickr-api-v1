package echomachine.com.flickrapi_v1.adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import echomachine.com.flickrapi_v1.R;
import echomachine.com.flickrapi_v1.pojo.Photo;
import echomachine.com.flickrapi_v1.pojo._PhotoModel;

public class SelectedPhotoAdapter extends RecyclerView.Adapter<SelectedPhotoAdapter.SelectedViewHolder> {
    public List<Photo> mList = new ArrayList<>();

    @NonNull
    @Override
    public SelectedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SelectedViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_recycler_recent_photo, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SelectedViewHolder holder, int position) {
        Photo photo = mList.get(position);
        Picasso.get()
                .load(photo.getUrl_s())
                .placeholder(R.drawable.ic_place_holder_home)
                .fit()
                .centerCrop(1)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setList(List<Photo> _PhotoModels) {
        this.mList = _PhotoModels;
        notifyDataSetChanged();
    }

    public class SelectedViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        public SelectedViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_header);
        }
    }
}
