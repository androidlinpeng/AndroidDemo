package msgcopy.com.androiddemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by liang on 2017/4/11.
 */

public class PubsListFragment extends Fragment {

    public static PubsListFragment newInstance(String leaf) {
        PubsListFragment fragment = new PubsListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("leaf_id", "---");
        fragment.setArguments(bundle);
        return fragment;
    }

    public PubsListFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_pubs,null);

        return rootView;
    }
}
