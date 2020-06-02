package echomachine.com.flickrapi_v1.adapter;

import android.com.flickrapi_v1.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import echomachine.com.flickrapi_v1.pojo.LikedPhoto;
import echomachine.com.flickrapi_v1.interfaces.ReachTheEndFinally;

public class LikedPhotoAdapter extends RecyclerView.Adapter<LikedPhotoAdapter.LikedViewModel> {

    private ReachTheEndFinally listener;
    private List<LikedPhoto> mList = new ArrayList<>();

    @NonNull
    @Override
    public LikedViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LikedViewModel(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_recycler_recent_photo
                , parent, false));
    }

    public void setReachFinallyListener(ReachTheEndFinally listener) {
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull LikedViewModel holder, int position) {
        LikedPhoto photo = mList.get(position);
        Picasso.get()
                .load(photo.getUrl_s())
                .placeholder(R.drawable.ic_place_holder_home)
                .fit()
                .centerCrop()
                .into(holder.imageView);
        if (position == mList.size()) {
            listener.OnBottomReach(position);
        } else if (mList.size() >= 16 && (position == 0 || position == 1)) {
            listener.OnTopReach(position);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setList(List<LikedPhoto> list) {
        this.mList = list;
        notifyDataSetChanged();
    }

    public class LikedViewModel extends RecyclerView.ViewHolder {

        private ImageView imageView;
        public LikedViewModel(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_header);
        }
    }
}
