package msgcopy.com.androiddemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by liang on 2017/4/13.
 */

public class MyTabFragment extends Fragment{

    private MyTabPageIndicator myTabPageIndicator;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mytab,null);
        myTabPageIndicator = (MyTabPageIndicator) view.findViewById(R.id.myTabPageIndicator);
        return view;
    }
}
