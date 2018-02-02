package eu.jrie.lets_chess;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import eu.jrie.lets_chess.Views.CapturedPad;

public class PlayerPadFragment extends Fragment {

    private GameManagement gameManagement;

    public CapturedPad capturedPad; // necessary to be visible for activity

    private byte color;
    private Button drawButton;

    private FrameLayout fragmentFrame;
    private SurrenderFragment surrenderFragment;

    int timeToEnd;
    int timeToAdd;
    int timeToEndConst;
    int timeToAddConst;
    private TextView timerView;
    private CountDownTimer timer;
    private boolean measureTime;

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

        timerView = view.findViewById(R.id.player_pad_timer_view);
        timer = null;
        timeToEnd = 0;
        updateTimerView();

        return view;
    }

    public void setUp(byte c, int tB, int tA){
        color = c;
        timeToEnd = tB;
        timeToEndConst= tB;
        if(timeToEnd > 0){
            measureTime = true;
            timeToAdd = tA;
            timeToAddConst = tA;
            if(color == Const.BLACK) timeToEnd -= timeToAdd;
            updateTimerView();
        }
        else{
            measureTime = false;
            timerView.setVisibility(View.GONE);
        }

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
        if(sur) gameManagement.proceedFailure(color);
    }

    public void reset(){
        setBackground(R.drawable.draw_icon);
        capturedPad.reset();
        timeToEnd = timeToEndConst;
        if(timeToEnd > 0){
            timeToAdd = timeToAddConst;
            updateTimerView();
        }
    }

    public void end(){
        if(measureTime) timer.cancel();
    }

    public void changeTurn(int activeColor){
        if(activeColor == color){
            if(measureTime) startTimer();
        }
        else {
            if (measureTime){
                stopTimer();
                timeToEnd += timeToAdd;
                updateTimerView();
            }
        }
    }

    private void updateTimerView(){
        int min = timeToEnd / 60000;
        int sec = (timeToEnd % 60000) / 1000;
        String time = min + ":";
        if(min < 10) time = "0" + time;
        if(sec < 10) time += "0";
        time += sec;
        timerView.setText(time);
    }

    void startTimer(){
        timer = new CountDownTimer(timeToEnd, 1000){
            @Override
            public void onTick(long l) {
                timeToEnd -= 1000;
                updateTimerView();
            }

            @Override
            public void onFinish() {
                timeToEnd = 0;
                updateTimerView();
                gameManagement.proceedFailure(color);
            }
        }.start();
    }

    void stopTimer(){
        if(timer != null) timer.cancel();
    }





}
