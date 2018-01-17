package com.jr.chess.UtilityFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jr.chess.R;

public class SettingsFragment extends Fragment {


    public interface ISettingsFragment{
        void closeSettings();
    }

    ISettingsFragment iSettingsFragment;

    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        Button closeButton = view.findViewWithTag("close_button");
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iSettingsFragment.closeSettings();
            }
        });

        return view;
    }


}
