//package com.example.beender;
//
//import android.os.Bundle;
//
//import androidx.fragment.app.Fragment;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
///**
// * A simple {@link Fragment} subclass.
// * Use the {@link SettingsFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
//public class SettingsFragment extends Fragment {
//
//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
//
//    public SettingsFragment() {
//        // Required empty public constructor
//    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment SettingsFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static SettingsFragment newInstance(String param1, String param2) {
//        SettingsFragment fragment = new SettingsFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_settings, container, false);
//    }
//}


package com.example.beender;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.example.beender.ui.dashboard.DashboardFragment;

public class SettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);

        Preference kind_of_trip = (Preference) findPreference("kind_of_trip");
        kind_of_trip.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                Toast.makeText(getContext(), newValue.toString(), Toast.LENGTH_SHORT).show();
                Log.d(DashboardFragment.class.getSimpleName(), newValue.toString());
                return true;
            }
        });

        Preference numOfPlacesPerDay = (Preference) findPreference("numOfPlacesPerDay");
        numOfPlacesPerDay.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                Toast.makeText(getContext(), newValue.toString(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        Preference kmRadius = (Preference) findPreference("kmRadius");
        kmRadius.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                Toast.makeText(getContext(), newValue.toString(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        Preference numOfDaysForTravel = (Preference) findPreference("numOfDaysForTravel");
        numOfDaysForTravel.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                Toast.makeText(getContext(), newValue.toString(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        Preference adaptedForAWheelchair = (Preference) findPreference("adaptedForAWheelchair");
        adaptedForAWheelchair.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                Toast.makeText(getContext(), newValue.toString(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        Preference ratingStar = (Preference) findPreference("ratingStar");
        ratingStar.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                Toast.makeText(getContext(), newValue.toString(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        Preference priceLevel = (Preference) findPreference("priceLevel");
        priceLevel.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                Toast.makeText(getContext(), newValue.toString(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        Preference darkMode = (Preference) findPreference("darkMode");
        darkMode.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                if ((Boolean)newValue) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }

//                Toast.makeText(getContext(), newValue.toString(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }
}