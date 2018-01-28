package eu.jrie.lets_chess;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import eu.jrie.lets_chess.Views.CapturedPad;

public class PlayerPadFragment extends Fragment {

    private GameManagement gameManagement;

    public CapturedPad capturedPad; // necessary to be visible for activity

    private int color;
    private Button drawButton;

    private FrameLayout fragmentFrame;
    private SurrenderFragment surrenderFragment;

    public PlayerPadFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        gameManagement = (GameManagement) getActivity();

        View view = inflater.inflate(R.layout.fragment_player_pad, container, false);

        fragmentFrame = view.findViewById(R.id.player_pad_fragment_frame);
        fragmentFrame.setVisibility(View.GONE);
        surrenderFragment = new SurrenderFragment();

        capturedPad = view.findViewById(R.id.capture_pad);

        drawButton = view.findViewById(R.id.player_pad_draw_button);
        drawButton.setOnClickListener(v -> gameManagement.manageDraw(color));

        Button surrenderButton = view.findViewById(R.id.player_pad_surrender_button);
        surrenderButton.setOnClickListener(v -> confirmSurrender());

        return view;
    }

    public void setColor(int c){
        color = c;
    }

    public void setBackground(int id){
        drawButton.setBackgroundResource(id);
    }

    private void confirmSurrender(){
        getChildFragmentManager().beginTransaction().add(fragmentFrame.getId(), surrenderFragment).commit();
        fragmentFrame.bringToFront();
        fragmentFrame.setVisibility(View.VISIBLE);
    }

    public void surrender(boolean sur){
        getChildFragmentManager().beginTransaction().remove(surrenderFragment).commit();
        fragmentFrame.setVisibility(View.GONE);
        if(sur) gameManagement.proceedSurrendering(color);
    }

    public void reset(){
        setBackground(R.drawable.draw_icon);
        capturedPad.reset();
    }




}
