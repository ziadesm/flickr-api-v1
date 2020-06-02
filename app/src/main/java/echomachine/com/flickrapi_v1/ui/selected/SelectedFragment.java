package echomachine.com.flickrapi_v1.ui.selected;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.com.flickrapi_v1.R;

public class SelectedFragment extends Fragment {

    private SelectedViewModel viewModel;

    public SelectedFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_selected, container, false);

        viewModel = ViewModelProviders
                .of(this).get(SelectedViewModel.class);

        return view;
    }
}