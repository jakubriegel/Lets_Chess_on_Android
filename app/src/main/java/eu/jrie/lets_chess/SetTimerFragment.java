package eu.jrie.lets_chess;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class SetTimerFragment extends Fragment {

    private GameManagement gameManagement;

    private Fragment self;

    public SetTimerFragment() {
        // Required empty public constructor
    }

    private int beginningTime;
    private int addedTime;

    private TextView beginningTimePreview;
    private TextView addedTimePreview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        gameManagement = (GameManagement) getActivity();
        self = this;

        View view = inflater.inflate(R.layout.fragment_set_timer, container, false);

        beginningTimePreview = view.findViewById(R.id.beginning_time_preview);
        addedTimePreview = view.findViewById(R.id.added_time_preview);

        SeekBar beginningTimeBar = view.findViewById(R.id.beginning_time_bar);
        beginningTimeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                beginningTime = (i+1) * 60000;
                beginningTimePreview.setText(String.format("%smin", String.valueOf(i + 1)));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        beginningTimeBar.setProgress(3); // 4min

        SeekBar addedTimeBar = view.findViewById(R.id.add_time_bar);
        addedTimeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                addedTime = i * 30000;
                String time = "";
                int sec = addedTime/1000;
                if(sec == 0) time = "0s";
                else{
                    if(sec / 60 > 0) time += String.format("%smin",String.valueOf((sec) / 60));
                    if(sec % 60 > 0) time += String.format(" %ss",String.valueOf((sec) % 60));
                }

                addedTimePreview.setText(time);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        addedTimeBar.setProgress(1); // 30s

        Button confirmButton = view.findViewById(R.id.confirm_time_button);
        confirmButton.setOnClickListener(v -> {
            gameManagement.closeFragment(self);
            gameManagement.initGame(beginningTime, addedTime);
        });


        return view;
    }


}
