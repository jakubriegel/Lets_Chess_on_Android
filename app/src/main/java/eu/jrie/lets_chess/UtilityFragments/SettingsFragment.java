package eu.jrie.chess_time.UtilityFragments;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import eu.jrie.chess_time.R;



public class SettingsFragment extends UtilityFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        assert view != null;
        TextView header = view.findViewById(R.id.utility_header);
        header.setText(R.string.settings_header);

        getChildFragmentManager().beginTransaction().add(R.id.utility_frame, new PreferencesFragment()).commit();

        return view;
    }


    public static class PreferencesFragment extends PreferenceFragment {
        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
        }
    }
}
