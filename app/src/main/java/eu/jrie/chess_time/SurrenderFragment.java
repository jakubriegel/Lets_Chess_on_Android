package eu.jrie.chess_time;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class SurrenderFragment extends Fragment {

    private PlayerPadFragment pad;

    public SurrenderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        pad = (PlayerPadFragment) getParentFragment();

        View view = inflater.inflate(R.layout.fragment_surrender, container, false);

        Button confirmButton = view.findViewById(R.id.surrender_confirm_button);
        confirmButton.setOnClickListener(v -> pad.surrender(true));

        Button declineButton = view.findViewById(R.id.surrender_decline_button);
        declineButton.setOnClickListener(v -> pad.surrender(false));

        return view;
    }

}
