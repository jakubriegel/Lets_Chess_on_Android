package com.jr.chess;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class LeaveGameFragment extends Fragment {

    LeaveGameFragment self;

    public interface ILeaveGameFragment{
        void closeLeaveGameFragment(LeaveGameFragment leaveGameFragment);
    }

    ILeaveGameFragment iLeaveGameFragment;

    public LeaveGameFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        self = this;

        View view = inflater.inflate(R.layout.fragment_leave_game, container, false);

        iLeaveGameFragment = (ILeaveGameFragment) getActivity();

        Button confirmButton = view.findViewById(R.id.leave_game_confirm_button);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

        Button declineButton = view.findViewById(R.id.leave_game_decline_button);
        declineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iLeaveGameFragment.closeLeaveGameFragment(self);
            }
        });

        return view;
    }

}
