package eu.jrie.chess_time;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class GameLeaveFragment extends Fragment {

    private GameLeaveFragment self;
    private GameManagement gameManagement;

    public GameLeaveFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        self = this;
        gameManagement = (GameManagement) getActivity();

        View view = inflater.inflate(R.layout.fragment_leave_game, container, false);

        Button confirmButton = view.findViewById(R.id.leave_game_confirm_button);
        confirmButton.setOnClickListener(v -> gameManagement.endGame());

        Button declineButton = view.findViewById(R.id.leave_game_decline_button);
        declineButton.setOnClickListener(v -> gameManagement.closeFragment(self));

        return view;
    }

}
