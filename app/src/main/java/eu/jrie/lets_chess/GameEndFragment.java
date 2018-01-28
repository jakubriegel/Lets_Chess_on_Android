package eu.jrie.chess_time;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class GameEndFragment extends Fragment {

    private GameManagement gameManagement;

    public GameEndFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        gameManagement = (GameManagement) getActivity();

        View view = inflater.inflate(R.layout.fragment_game_end, container, false);

        Bundle winnerCode = getArguments();
        int winner = winnerCode.getInt("winner");

        TextView resultText = view.findViewById(R.id.game_result_text);
        switch (winner){
            case Const.WHITE:
                resultText.setText(R.string.white_won);
                break;
            case Const.BLACK:
                resultText.setText(R.string.black_won);
                break;
            default:
                resultText.setText(R.string.draw);
        }

        Button menuButton = view.findViewById(R.id.end_game_main_menu_button);
        menuButton.setOnClickListener(v -> gameManagement.endGame());

        Button againButton = view.findViewById(R.id.end_game_again_button);
        againButton.setOnClickListener(v -> gameManagement.resetGame());

        Button showBoardButton = view.findViewById(R.id.end_game_show_board_button);
        showBoardButton.setOnClickListener(v -> gameManagement.showBoard());

        Button shareButton = view.findViewById(R.id.end_game_share_button);
        shareButton.setOnClickListener(v -> gameManagement.shareBoard());

        return view;
    }

}
