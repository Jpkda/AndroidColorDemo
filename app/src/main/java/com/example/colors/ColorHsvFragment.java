package com.example.colors;

import static com.example.colors.R.*;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.skydoves.colorpickerview.ColorEnvelope;
import com.skydoves.colorpickerview.ColorPickerView;
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener;
import com.skydoves.colorpickerview.sliders.AlphaSlideBar;
import com.skydoves.colorpickerview.sliders.BrightnessSlideBar;

import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ColorHsvFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ColorHsvFragment() {
    }


    // TODO: Rename and change types and number of parameters
    public static ColorHsvFragment newInstance(String param1, String param2) {
        ColorHsvFragment fragment = new ColorHsvFragment();
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
    private CardView hsv_color_one, hsv_color_two, hsv_color_three, hsv_color_four;
    private ColorPickerView colorPickerView;
    private AlphaSlideBar alphaSlideBar;
    private BrightnessSlideBar brightnessSlide;
    private TextView hsv_color_value_r, hsv_color_value_g, hsv_color_value_b, hsv_color_value_hex_one, hsv_color_value_hex_two, hsv_color_value_hex_three, hsv_color_value_hex_four;
    private int Cmax = 255;
    private ImageButton hsv_color_random, hsv_color_save;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(layout.fragment_color_hsv, container, false);
        hsv_color_one = view.findViewById(id.hsv_color_one);
        hsv_color_two = view.findViewById(id.hsv_color_two);
        hsv_color_three = view.findViewById(id.hsv_color_three);
        hsv_color_four = view.findViewById(id.hsv_color_four);

        colorPickerView = view.findViewById(id.colorPickerView);
        brightnessSlide = view.findViewById(id.brightnessSlide);

        hsv_color_value_r = view.findViewById(id.hsv_color_value_r);
        hsv_color_value_g = view.findViewById(id.hsv_color_value_g);
        hsv_color_value_b = view.findViewById(id.hsv_color_value_b);
        hsv_color_value_hex_one = view.findViewById(id.hsv_color_value_hex_one);
        hsv_color_value_hex_two = view.findViewById(id.hsv_color_value_hex_two);
        hsv_color_value_hex_three = view.findViewById(id.hsv_color_value_hex_three);
        hsv_color_value_hex_four = view.findViewById(id.hsv_color_value_hex_four);

        hsv_color_random = view.findViewById(id.hsv_color_random);
        hsv_color_save = view.findViewById(id.hsv_color_save);

        colorPickerView.attachBrightnessSlider(brightnessSlide);

        colorPickerView.setColorListener(new ColorEnvelopeListener() {
            private static final String TAG = "ColorHsvFragment";
            // ffbbccaa
            @Override
            public void onColorSelected(ColorEnvelope envelope, boolean fromUser) {
                hsv_color_one.setCardBackgroundColor(envelope.getColor());
                int[] rgb =  hexToRgb(envelope.getHexCode());
                //set RGB Text Color
                rgbTextColor(hsv_color_value_r, rgb[0], rgb[1],rgb[2]);
                rgbTextColor(hsv_color_value_g, rgb[0], rgb[1],rgb[2]);
                rgbTextColor(hsv_color_value_b, rgb[0], rgb[1],rgb[2]);
                rgbTextColor(hsv_color_value_hex_one, rgb[0], rgb[1],rgb[2]);
                //set RGB v
                hsv_color_value_hex_one.setText(envelope.getHexCode().substring(2,8));
                hsv_color_value_r.setText("R " + rgb [0]);
                hsv_color_value_g.setText("G " + rgb [1]);
                hsv_color_value_b.setText("B " + rgb [2]);

                hsv_color_two.setCardBackgroundColor(Color.rgb(Cmax-rgb[0],Cmax-rgb[1],Cmax-rgb[2]));
                // set Text
                rgbTextColor(hsv_color_value_hex_two, Cmax-rgb[0],Cmax-rgb[1],Cmax-rgb[2]);
                hsv_color_value_hex_two.setText(rgbToHex(Cmax-rgb[0],Cmax-rgb[1],Cmax-rgb[2]));

                // left color
                int [] rgb_l = new int[3];
                rgb_l[0] = overflowRgb(Cmax-rgb[0]+50);
                rgb_l[1] = overflowRgb(Cmax-rgb[1]+50);
                rgb_l[2] = overflowRgb(Cmax-rgb[2]+50);
                hsv_color_three.setCardBackgroundColor(Color.rgb(rgb_l[0],rgb_l[1],rgb_l[2]));
                //set text
                rgbTextColor(hsv_color_value_hex_three, rgb_l[0], rgb_l[1], rgb_l[2]);
                hsv_color_value_hex_three.setText(rgbToHex(rgb_l[0], rgb_l[1], rgb_l[2]));

                // right color
                int [] rgb_r = new int[3];
                rgb_r[0] = overflowRgb(Cmax-rgb[0]-50);
                rgb_r[1] = overflowRgb(Cmax-rgb[1]-50);
                rgb_r[2] = overflowRgb(Cmax-rgb[2]-50);
                hsv_color_four.setCardBackgroundColor(Color.rgb(rgb_r[0],rgb_r[1],rgb_r[2]));
                // set text
                rgbTextColor(hsv_color_value_hex_four, rgb_r[0],rgb_r[1],rgb_r[2]);
                hsv_color_value_hex_four.setText(rgbToHex(rgb_r[0],rgb_r[1],rgb_r[2]));

                hsv_color_save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                        Date date = new Date(System.currentTimeMillis());
                        DBOpenHelper  dbOpenHelper = new DBOpenHelper(getActivity(), "soloColor.db", null, 1);
                        dbOpenHelper.getWritableDatabase();
                        SQLiteDatabase sdb = dbOpenHelper.getReadableDatabase();
                        ContentValues values = new ContentValues();
                        values.put("time",simpleDateFormat.format(date));
                        values.put("collectOne", "#"+envelope.getHexCode().substring(2, 8));
                        values.put("collectTwo", "#"+rgbToHex(Cmax-rgb[0],Cmax-rgb[1],Cmax-rgb[2]));
                        values.put("collectThree", "#"+rgbToHex(rgb_l[0], rgb_l[1], rgb_l[2]));
                        values.put("collectFour", "#"+rgbToHex(rgb_r[0],rgb_r[1],rgb_r[2]));
                        sdb.insert("colorCollect", null, values);
                        Toast.makeText(getActivity(), "保存成功", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        // random color
        hsv_color_random.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SecureRandom random = new SecureRandom();
                int[] rgbvalues = new int[4];
                for (int i = 0; i < 4; i++) {
                    int rgbvalue = random.nextInt(255);
                    rgbvalues[i] = rgbvalue;
                }
                colorPickerView.setInitialColor(Color.argb(rgbvalues[3],rgbvalues[0],rgbvalues[1],rgbvalues[2]));

            }
        });
        return view;
    }

    private static int[] hexToRgb(String string){
        int[] rgb = new int[3];
        rgb[0] = Integer.valueOf(string.substring( 2, 4 ), 16);
        rgb[1] = Integer.valueOf(string.substring( 4, 6 ), 16);
        rgb[2] = Integer.valueOf(string.substring( 6, 8 ), 16);
        return rgb;
    }

    private void rgbTextColor(TextView textView, int R, int G, int B){
        if(R<80 && G<80 && B<80 ){
            textView.setTextColor(Color.parseColor("#ffffff"));
        }
        if(R>130 && G>130 && B>130 ){
            textView.setTextColor(Color.parseColor("#1B471D"));
        }
    }

    // rgb to hex
    private static String rgbToHex(int R, int G, int B){
        String redStr = Integer.toHexString(R);
        String greenStr = Integer.toHexString(G);
        String blueStr = Integer.toHexString(B);
        if (redStr.equals("0")){redStr = "00";}
        if (greenStr.equals("0")){greenStr = "00";}
        if (blueStr.equals("0")){blueStr = "00";}
        if (redStr.length() == 1){redStr = "0"+redStr;}
        if (greenStr.length() == 1){greenStr = "0"+greenStr;}
        if (blueStr.length() == 1){blueStr = "0"+blueStr;}
        return (redStr+greenStr+blueStr).toUpperCase();
    }

    private static int overflowRgb(int value){
        if (value < 0){
            value = 255 + value;
        }else if(value > 255){
            value = value - 255;
        }else if(value < 10){
            String str = new DecimalFormat("0").format(value);
            value = Integer.parseInt(str);
        }
        return value;
    }
}