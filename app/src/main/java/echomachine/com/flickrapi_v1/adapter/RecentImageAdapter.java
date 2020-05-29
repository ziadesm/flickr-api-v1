package echomachine.com.flickrapi_v1.adapter;
import android.annotation.SuppressLint;
import android.com.flickrapi_v1.R;

import echomachine.com.flickrapi_v1.data.RepositoryPhoto;
import echomachine.com.flickrapi_v1.interfaces.OnDoubleClickListener;
import echomachine.com.flickrapi_v1.pojo._PhotoModel.Photos.Photo;
import echomachine.com.flickrapi_v1.ui.like.LikedFragment;
import echomachine.com.flickrapi_v1.ui.selected.SelectedFragment;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

public class RecentImageAdapter extends PagedListAdapter<Photo, RecentImageAdapter.ImageViewHolder> {
    private static final String TAG = "Ziad";
    private Context context;
    private RepositoryPhoto repo;
    private Fragment fragment;

    public RecentImageAdapter(Context context, Fragment fragment) {
        super(Photo.CALLBACK);
        this.context = context;
        repo = new RepositoryPhoto(context.getApplicationContext());
        this.fragment = fragment;
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
            @SuppressLint("CheckResult")
            @Override
            public void onDoubleClick(View v) {
                if (holder.loveImageView.getVisibility() != v.getVisibility()) {
                    holder.loveImageView.setVisibility(View.VISIBLE);
                    repo.insertPhoto(new echomachine.com.flickrapi_v1.data.Photo(item.getUrl_s(), item.getOwner(), true))
                            .subscribe(() -> {
                                Toast.makeText(context
                                        , "Photo add to your liked photos"
                                        , Toast.LENGTH_LONG).show();
                                    }
                                    , throwable -> Toast.makeText(context, throwable.getMessage(), Toast.LENGTH_LONG).show());
                    Snackbar snackbar = Snackbar
                            .make(v, "Like!, show all liked", Snackbar.LENGTH_LONG)
                            .setAction("Show", view -> {
                                //TODO Access database and moving to other fragment (Create new fragment and viewModel)
                                NavController navController = Navigation.findNavController(fragment.getActivity(), R.id.nav_host_fragment);
                                navController.navigate(R.id.navigation_liked);
                            });
                    snackbar.show();
                } else {
                    holder.loveImageView.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onSingleClick(View v) {
                //TODO Move to PhotoFragment (onGoing) to download and show other similar photo
                NavController navController = Navigation.findNavController(fragment.getActivity(), R.id.nav_host_fragment);
                navController.navigate(R.id.navigation_selected);
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