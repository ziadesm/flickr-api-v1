package echomachine.com.flickrapi_v1.adapter;

import echomachine.com.flickrapi_v1.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import echomachine.com.flickrapi_v1.data.RepositoryPhoto;
import echomachine.com.flickrapi_v1.pojo.LikedPhoto;
import echomachine.com.flickrapi_v1.interfaces.ReachTheEndFinally;

public class LikedPhotoAdapter extends RecyclerView.Adapter<LikedPhotoAdapter.LikedViewModel> {

    private ReachTheEndFinally listener;
    private List<LikedPhoto> mList = new ArrayList<>();
    private List<LikedPhoto> deletedList = new ArrayList<>();
    private Fragment fragment;
    private RepositoryPhoto repo;
    private Context context;

    public LikedPhotoAdapter(Context context, Fragment fragment) {
        this.fragment = fragment;
        this.context = context;
        repo = new RepositoryPhoto(context);
    }

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
        holder.checkBox.setVisibility(View.GONE);

        holder.imageView.setOnLongClickListener(v -> {
            deletedList.add(photo);
            holder.checkBox.setVisibility(View.VISIBLE);
            holder.checkBox.setChecked(true);
            return true;
        });

        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                deletedList.add(mList.get(position));
            } else {
                deletedList.remove(mList.get(position));
            }
        });

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

    public void onSwipeAction(int pos) {
        LikedPhoto photo = mList.get(pos);
        repo.deletePhoto(photo);
        mList.remove(pos);
        notifyItemRemoved(pos);
    }

    public class LikedViewModel extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private CheckBox checkBox;
        public LikedViewModel(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_header);
            checkBox = itemView.findViewById(R.id.item_check_box_recycler);

            itemView.setOnClickListener(v -> {
                NavController navController = Navigation.findNavController(fragment.getActivity(), R.id.nav_host_fragment);
                navController.navigate(R.id.navigation_selected);
            });
        }
    }
}