package echomachine.com.flickrapi_v1.ui.like;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import echomachine.com.flickrapi_v1.R;

import echomachine.com.flickrapi_v1.adapter.LikedPhotoAdapter;
import echomachine.com.flickrapi_v1.adapter.SimpleRecyclerItemTouchHelper;
import echomachine.com.flickrapi_v1.data.RepositoryPhoto;
import echomachine.com.flickrapi_v1.pojo.LikedPhoto;

public class LikedFragment extends Fragment {

    private static final String TAG = "ZiadLiked";
    private RepositoryPhoto repo;
    private RecyclerView recyclerView;
    private LikedPhotoAdapter adapter;
    private List<LikedPhoto> mList;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_liked, container, false);
        recyclerView = view.findViewById(R.id.recycler_liked_photo);
        repo = new RepositoryPhoto(getContext());
        adapter = new LikedPhotoAdapter(getContext(), this);

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        repo.getAllLikedPhoto().observe(getActivity(), c -> adapter.setList(c));
        recyclerView.setAdapter(adapter);
        ItemTouchHelper.Callback callback = new SimpleRecyclerItemTouchHelper(adapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        return view;
    }
}