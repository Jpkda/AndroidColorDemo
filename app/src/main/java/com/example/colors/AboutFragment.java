package com.example.colors;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.colors.ico.ParallaxLayerLayout;
import com.example.colors.ico.SensorTranslationUpdater;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AboutFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AboutFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AboutFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AboutFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AboutFragment newInstance(String param1, String param2) {
        AboutFragment fragment = new AboutFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private ParallaxLayerLayout ico_parallax;
    private SensorTranslationUpdater translationUpdater;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_about, container, false);
        TextView about_cpv = view.findViewById(R.id.about_cpv);
        TextView about_cpp = view.findViewById(R.id.about_cpp);
        TextView about_op = view.findViewById(R.id.about_op);
        TextView about_mong = view.findViewById(R.id.about_mong);
        TextView about_mc = view.findViewById(R.id.about_mc);
        TextView about_pll = view.findViewById(R.id.about_pll);


        ico_parallax = view.findViewById(R.id.ico_parallax);
        about_cpv.setOnClickListener(this);
        about_cpp.setOnClickListener(this);
        about_op.setOnClickListener(this);
        about_mong.setOnClickListener(this);
        about_mc.setOnClickListener(this);
        about_pll.setOnClickListener(this);


        // ico parallax
        translationUpdater = new SensorTranslationUpdater(getActivity());
        ico_parallax.setTranslationUpdater(translationUpdater);
        ico_parallax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                translationUpdater.reset();
            }
        });
        return view;
    }

    private void toWeb(String web){
           Uri uri = Uri.parse(web);
           Intent intent = new Intent(Intent.ACTION_VIEW, uri);
           startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.about_cpv:
                toWeb("https://github.com/jaredrummler/ColorPicker");
                break;
            case R.id.about_cpp:
                toWeb("https://github.com/skydoves/ColorPickerPreference");
                break;
            case R.id.about_op:
                toWeb("https://github.com/SpringLoveSummer/Omelette");
                break;
            case R.id.about_mong:
                toWeb("http://www.manongjc.com/");
                break;
            case R.id.about_mc:
                toWeb("https://github.com/PhilJay/MPAndroidChart");
                break;
            case R.id.about_pll:
                toWeb("https://github.com/AdevintaSpain/Parallax-Layer-Layout");
                break;
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        translationUpdater.registerSensorManager();
    }

    @Override
    public void onPause() {
        super.onPause();
        translationUpdater.unregisterSensorManager();
    }
}