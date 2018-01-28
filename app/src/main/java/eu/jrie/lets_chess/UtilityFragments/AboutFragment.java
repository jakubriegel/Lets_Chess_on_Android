package eu.jrie.lets_chess.UtilityFragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import eu.jrie.lets_chess.R;

public class AboutFragment extends UtilityFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        assert view != null;
        TextView header = view.findViewById(R.id.utility_header);
        header.setText(R.string.about_header);

        LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert layoutInflater != null;
        @SuppressLint("InflateParams")
        View content = layoutInflater.inflate(R.layout.content_about, null);
        FrameLayout contentFrame = view.findViewById(R.id.utility_frame);
        contentFrame.addView(content);

        return view;
    }
}
