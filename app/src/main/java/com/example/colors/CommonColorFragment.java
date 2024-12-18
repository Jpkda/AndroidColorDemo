package com.example.colors;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CommonColorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CommonColorFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "CommonColor";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CommonColorFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CommonColorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CommonColorFragment newInstance(String param1, String param2) {
        CommonColorFragment fragment = new CommonColorFragment();
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


    private List<CommonColorValueR> ColorValue = new ArrayList<>();
    private List<CommonColorValueR> ColorValueOne = new ArrayList<>();
    private List<CommonColorValueR> ColorValueTwo = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_common_color, container, false);

        CommonColorDeanInfo commonColorDeanInfo = new CommonColorDeanInfo();
        String str = commonColorDeanInfo.getJson();
        Gson gson = new Gson();

        List<CommonColorApDean> shops = gson.fromJson(str, new TypeToken<List<CommonColorApDean>>() {}.getType());

        CommonColorGet commonColorGet = new CommonColorGet();
        //hex
        ArrayList<String> hex = commonColorGet.HexToString(shops);
        String[] hex_cl = hex.toArray(new String[hex.size()]) ;
        //word
        ArrayList<String> pinyin = commonColorGet.PinyinToString(shops);
        String[] pinyin_cl = pinyin.toArray(new String[pinyin.size()]) ;

        for (int j = 0; j < hex.size(); j++){
            CommonColorValueR colorValue =new CommonColorValueR(hex_cl[j], pinyin_cl[j]);
            ColorValue.add(colorValue);
        }
        //ColorValue 分割
        ColorValueOne = ColorValue.subList(0, ColorValue.size()/2);
        ColorValueTwo = ColorValue.subList(ColorValue.size()/2, ColorValue.size());

        RecyclerView fragment_common_RecycleView = view.findViewById(R.id.fragment_common_RecycleView);
        RecyclerView fragment_common_RecycleView_one = view.findViewById(R.id.fragment_common_RecycleView_one);

        fragment_common_RecycleView.setLayoutManager(new GridLayoutManager(getActivity(),1));
        CommonColorRecyclerAdapters adapters = new CommonColorRecyclerAdapters(this.getActivity(),ColorValueOne);
        fragment_common_RecycleView.setAdapter(adapters);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),1);
        gridLayoutManager.setReverseLayout(true);
        fragment_common_RecycleView_one.setLayoutManager(gridLayoutManager);
        CommonColorRecyclerAdapters adapterss = new CommonColorRecyclerAdapters(this.getActivity(),ColorValueTwo);
        fragment_common_RecycleView_one.setAdapter(adapterss);

        fragment_common_RecycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                fragment_common_RecycleView_one.scrollBy(dx,-dy);
            }
        });
        return view;
    }

}