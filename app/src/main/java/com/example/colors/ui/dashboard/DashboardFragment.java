package com.example.colors.ui.dashboard;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.colors.CollectBean;
import com.example.colors.ColorBean;
import com.example.colors.DBOpenHelper;
import com.example.colors.R;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.colors.databinding.FragmentDashboardBinding;

import java.util.ArrayList;

public class DashboardFragment extends Fragment {

    private TwTypeAdapter adapter;
    private FragmentDashboardBinding binding;
    private ListView tw_tepeView;
    private ArrayList<Object> listDataOne;
    private ArrayList<Object> listDataTwo;
    DBOpenHelper dbOpenHelper;
    SQLiteDatabase sdb;
    ColorBean colorBean;
    CollectBean collectBean;

    @SuppressLint("Range")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        tw_tepeView = root.findViewById(R.id.tw_type_view);

        listDataOne = new ArrayList<Object>();
        listDataTwo = new ArrayList<Object>();

        // 创建MySQLiteHelper实例
        dbOpenHelper = new DBOpenHelper(getActivity(), "soloColor.db", null, 1);
        sdb = dbOpenHelper.getReadableDatabase();
        Cursor cursor= sdb.query("colorCollect",null,null,null,null,null,null);
        while (cursor.moveToNext()){
            collectBean = new CollectBean();

            collectBean.setcollectOne(cursor.getString(cursor.getColumnIndex("collectOne")));
            collectBean.setcollectTwo(cursor.getString(cursor.getColumnIndex("collectTwo")));
            collectBean.setcollectThree(cursor.getString(cursor.getColumnIndex("collectThree")));
            collectBean.setcollectFour(cursor.getString(cursor.getColumnIndex("collectFour")));
            collectBean.setTime(cursor.getLong(cursor.getColumnIndex("time")));

            listDataOne.add(collectBean);
        }

        Cursor cursora= sdb.query("colorType",null,null,null,null,null,null);
        while (cursora.moveToNext()){
            colorBean = new ColorBean();
            colorBean.setHexcolor(cursora.getString(cursora.getColumnIndex("colorHex")));
            colorBean.setTime(cursora.getLong(cursora.getColumnIndex("time")));
            listDataTwo.add(colorBean);
        }

        // 加载头部数据
        int num1 = 0;
        int num = listDataOne.size()+ listDataTwo.size()+ num1;

        final View header = View.inflate(getContext(),R.layout.fragment_collect_list_head,null);
        TextView list_head = header.findViewById(R.id.list_head);
        list_head.setText("颜色数： "+num);

        tw_tepeView.addHeaderView(header,null,false);
        adapter = new TwTypeAdapter(this.getActivity(), listDataOne, listDataTwo);
        tw_tepeView.setAdapter(adapter);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}