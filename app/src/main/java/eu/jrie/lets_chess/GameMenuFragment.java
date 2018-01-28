package eu.jrie.lets_chess;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class GameMenuFragment extends Fragment {
    private Fragment self;
    private GameManagement gameManagement;

    public GameMenuFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        self = this;
        gameManagement = (GameManagement) getActivity();

        View view = inflater.inflate(R.layout.fragment_game_menu, container, false);

        Button endButton = view.findViewById(R.id.game_menu_end_button);
        endButton.setOnClickListener(v -> {
            gameManagement.getGame().state = Const.STATE_END;
            gameManagement.leaveGame();
        });

        Button shareButton = view.findViewById(R.id.game_share_button);
        shareButton.setOnClickListener(v -> gameManagement.shareBoard());

        Button continueButton = view.findViewById(R.id.game_menu_continue_button);
        continueButton.setOnClickListener(v -> gameManagement.closeFragment(self));

        return view;
    }

}
