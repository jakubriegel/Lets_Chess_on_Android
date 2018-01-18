package com.jr.chess.UtilityFragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.jr.chess.R;

public class UtilityFragment extends Fragment {

    public interface IUtilityFragment{
        void closeUtility();
    }

    IUtilityFragment iUtilityFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_utility, container, false);

        iUtilityFragment = (IUtilityFragment) getActivity();

        Button closeButton = view.findViewWithTag("close_button");
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iUtilityFragment.closeUtility();
            }
        });

        return view;
    }

}
