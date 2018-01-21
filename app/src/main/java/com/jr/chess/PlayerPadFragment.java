package com.jr.chess;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.jr.chess.Pieces.Bishop;
import com.jr.chess.Pieces.King;
import com.jr.chess.Pieces.Knight;
import com.jr.chess.Pieces.Pawn;
import com.jr.chess.Pieces.Rook;
import com.jr.chess.Views.CapturedPad;

public class PlayerPadFragment extends Fragment {

    public CapturedPad capturedPad;


    public PlayerPadFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_player_pad, container, false);

        capturedPad = view.findViewById(R.id.capture_pad);

       return view;
    }



}
