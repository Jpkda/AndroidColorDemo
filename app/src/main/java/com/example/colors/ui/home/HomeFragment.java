package com.example.colors.ui.home;


import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.colors.DBOpenHelper;
import com.example.colors.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.colors.databinding.FragmentHomeBinding;

import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HomeFragment extends Fragment {

    private SeekBar one_red_seek, one_greed_seek, one_blue_seek;
    private CardView one_main_color, one_item_color_one, one_item_color_two, one_item_color_three, one_item_color_four;
    private TextView one_color_rgb_value, one_text_red_value, one_text_greed_value, one_text_blue_value, one_item_value_one, one_item_value_two, one_item_value_three, one_item_value_four;
    private ImageButton one_rgb_copy, seva_collect, one_color_random;
    private static int r = 0, g =0, b = 0, max = 255;
    private FragmentHomeBinding binding;
    private String hexcolor ;
    private int int_cm_r, int_adjacent_l_r, int_adjacent_r_r, int_cm_g, int_adjacent_l_g, int_adjacent_r_g, int_cm_b, int_adjacent_l_b, int_adjacent_r_b;
    private TextView one_fragment_switch_one, one_fragment_switch_two;


    private ConstraintLayout fragment_home;

    private ClipData mClipData;   //剪切板Data对象
    private ClipboardManager mClipboardManager;   //剪切板管理工具类
    DBOpenHelper dbOpenHelper;
    SQLiteDatabase sdb;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        // seekbar r g b
        one_red_seek = root.findViewById(R.id.one_red_seek);
        one_greed_seek = root.findViewById(R.id.one_greed_seek);
        one_blue_seek = root.findViewById(R.id.one_blue_seek);
        // main color
        one_main_color = root.findViewById(R.id.one_main_color);
        // item color
        one_item_color_one = root.findViewById(R.id.one_item_color_one);
        one_item_color_two = root.findViewById(R.id.one_item_color_two);
        one_item_color_three = root.findViewById(R.id.one_item_color_three);
        one_item_color_four = root.findViewById(R.id.one_item_color_four);
        // item hex
        one_item_value_one = root.findViewById(R.id.one_item_value_one);
        one_item_value_two = root.findViewById(R.id.one_item_value_two);
        one_item_value_three = root.findViewById(R.id.one_item_value_three);
        one_item_value_four = root.findViewById(R.id.one_item_value_four);
        // img button
        one_color_rgb_value = root.findViewById(R.id.one_color_rgb_value);
        // text r g b
        one_text_red_value = root.findViewById(R.id.one_text_red_value);
        one_text_greed_value = root.findViewById(R.id.one_text_greed_value);
        one_text_blue_value = root.findViewById(R.id.one_text_blue_value);
        // img button
        one_rgb_copy = root.findViewById(R.id.one_rgb_copy);
        seva_collect = root.findViewById(R.id.seva_collect);
        one_color_random = root.findViewById(R.id.one_color_random);
        // container
        fragment_home = root.findViewById(R.id.fragment_home);

        one_red_seek.setMax(255);
        one_greed_seek.setMax(255);
        one_blue_seek.setMax(255);

        one_red_seek.setProgress(0);
        one_greed_seek.setProgress(0);
        one_blue_seek.setProgress(0);

        one_red_seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean bo) {
                r = i;
                int size = 4;
                String[] colorlist = new String[size];
                colorlist = color_differentiation(r,g,b);
                String int_color_r = String.valueOf(r);
                one_text_red_value.setText(int_color_r);
                one_color_rgb_value.setText(int_color_r + ", "+ g+", "+b);
                one_main_color.setCardBackgroundColor(Color.parseColor(colorlist[0]));
                one_item_color_one.setCardBackgroundColor(Color.parseColor(colorlist[0]));
                one_item_color_two.setCardBackgroundColor(Color.parseColor(colorlist[1]));
                one_item_color_three.setCardBackgroundColor(Color.parseColor(colorlist[2]));
                one_item_color_four.setCardBackgroundColor(Color.parseColor(colorlist[3]));
                one_item_value_one.setText(colorlist[0]);
                one_item_value_two.setText(colorlist[1]);
                one_item_value_three.setText(colorlist[2]);
                one_item_value_four.setText(colorlist[3]);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        one_greed_seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean bo) {
                g = i;
                String[] colorlist;
                colorlist = color_differentiation(r,g,b);
                String int_color_g = String.valueOf(g);
                one_text_greed_value.setText(int_color_g);
                one_color_rgb_value.setText(r + ", "+ int_color_g+", "+b);
                one_main_color.setCardBackgroundColor(Color.parseColor(colorlist[0]));
                one_item_color_one.setCardBackgroundColor(Color.parseColor(colorlist[0]));
                one_item_color_two.setCardBackgroundColor(Color.parseColor(colorlist[1]));
                one_item_color_three.setCardBackgroundColor(Color.parseColor(colorlist[2]));
                one_item_color_four.setCardBackgroundColor(Color.parseColor(colorlist[3]));
                one_item_value_one.setText(colorlist[0]);
                one_item_value_two.setText(colorlist[1]);
                one_item_value_three.setText(colorlist[2]);
                one_item_value_four.setText(colorlist[3]);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        one_blue_seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean bo) {
                b = i;
                String[] colorlist;
                colorlist = color_differentiation(r,g,b);
                String int_color_b = String.valueOf(b);
                one_text_blue_value.setText(int_color_b);
                one_color_rgb_value.setText(r + ", "+ g+", "+int_color_b);
                one_main_color.setCardBackgroundColor(Color.parseColor(colorlist[0]));
                one_item_color_one.setCardBackgroundColor(Color.parseColor(colorlist[0]));
                one_item_color_two.setCardBackgroundColor(Color.parseColor(colorlist[1]));
                one_item_color_three.setCardBackgroundColor(Color.parseColor(colorlist[2]));
                one_item_color_four.setCardBackgroundColor(Color.parseColor(colorlist[3]));
                one_item_value_one.setText(colorlist[0]);
                one_item_value_two.setText(colorlist[1]);
                one_item_value_three.setText(colorlist[2]);
                one_item_value_four.setText(colorlist[3]);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        one_color_random.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SecureRandom random = new SecureRandom();
                int[] rgbvalues = new int[3];
                for (int i = 0; i < 3; i++) {
                    int rgbvalue = random.nextInt(255);
                    rgbvalues[i] = rgbvalue;
                }
                one_red_seek.setProgress(rgbvalues[0]);
                one_greed_seek.setProgress(rgbvalues[1]);
                one_blue_seek.setProgress(rgbvalues[2]);
            }
        });


        one_rgb_copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String copy_value = r+", "+ g+ "," + b;
                copy(copy_value, getContext());
                Toast.makeText(getActivity(),"成功复制"+copy_value, Toast.LENGTH_SHORT).show();
            }
        });

        seva_collect.setOnClickListener(new View.OnClickListener() {
            private static final String TAG = "333333";

            @Override
            public void onClick(View view) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                Date date = new Date(System.currentTimeMillis());
                // sql insert
                hexcolor = rgbtoHEX(r,g,b);
                dbOpenHelper = new DBOpenHelper(getActivity(), "soloColor.db", null, 1);
                dbOpenHelper.getWritableDatabase();
                sdb = dbOpenHelper.getReadableDatabase();
                ContentValues values = new ContentValues();
                values.put("time",simpleDateFormat.format(date));
                String[] colorlist;
                colorlist = color_differentiation(r,g,b);
                values.put("collectOne", colorlist[0]);
                values.put("collectTwo", colorlist[1]);
                values.put("collectThree", colorlist[2]);
                values.put("collectFour", colorlist[3]);
                sdb.insert("colorCollect", null, values);
                Toast.makeText(getActivity(), "保存成功", Toast.LENGTH_SHORT).show();
            }
        });
        return root;
    }

    public static String[] color_differentiation(int r, int g, int b){
        int int_cm_r = max - r;
        int int_cm_g = max - g;
        int int_cm_b = max - b;
        int int_adjacent_l_r = int_cm_r - 20;
        int int_adjacent_r_r = int_cm_r + 20;
        int int_adjacent_l_g = int_cm_g - 20;
        int int_adjacent_r_g = int_cm_g + 20;
        int int_adjacent_l_b = int_cm_b - 20;
        int int_adjacent_r_b = int_cm_b + 20;
        int size = 4;
        String[] colorList = new String[size];
        colorList[0] = rgbtoHEX(r,g,b);
        colorList[1] = rgbtoHEX(int_cm_r,int_cm_g,int_cm_b);
        colorList[2] = rgbtoHEX(overflow(int_adjacent_l_r),overflow(int_adjacent_l_g),overflow(int_adjacent_l_b));
        colorList[3] = rgbtoHEX(overflow(int_adjacent_r_r),overflow(int_adjacent_r_g),overflow(int_adjacent_r_b));
        return colorList;
    }

    // color int to rgb
    private static String intToRgbForHex(int color){
        int cg = color;
        int red = (cg & 0xff0000) >> 16;
        int green = (cg & 0x00ff00) >> 8;
        int blue = (cg & 0x0000ff);
        return  rgbtoHEX(red, green, blue);
    }


    // 相邻色溢出处理
    public static int overflow(int value){
        if (value < 0){
            value = max + value;
        }else if(value > max){
            value = value - max;
        }else if(value < 10){
            //小于10补零
            String str = new DecimalFormat("0").format(value);
            value = Integer.parseInt(str);
        }
        return value;
    }

    // 复制功能函数
    public static void copy(String content, Context context) {
        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setText(content.trim());
    }

    private static String rgbtoHEX(int R, int G, int B) {
        String redStr = Integer.toHexString(R);
        String greenStr = Integer.toHexString(G);
        String blueStr = Integer.toHexString(B);
        if (redStr.equals("0")){redStr = "00";}
        if (greenStr.equals("0")){greenStr = "00";}
        if (blueStr.equals("0")){blueStr = "00";}
        if (redStr.length() == 1){redStr = "0"+redStr;}
        if (greenStr.length() == 1){greenStr = "0"+greenStr;}
        if (blueStr.length() == 1){blueStr = "0"+blueStr;}
        return ("#" + redStr + greenStr + blueStr).toUpperCase();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }
}