package com.example.colors.ui.notifications;

import android.annotation.SuppressLint;

import android.content.Context;
import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.graphics.Color;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.palette.graphics.Palette;


import com.example.colors.R;
import com.example.colors.databinding.FragmentNotificationsBinding;


import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;


public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    private FragmentNotificationsBinding binding;
    private ImageButton th_get_img, th_save_color;
    private ImageView th_img_pik, testimg;

    private TextView th_color_HEX, th_color_RGB, th_color_CMYK, three_item_value_one, three_item_value_two, three_item_value_three, three_item_value_four, three_item_value_five, three_item_value_six;
    private CardView th_color_one, th_color_two, th_color_three, th_color_four, th_color_five, th_color_six, th_result;
    private MyListener myListener;


    private float scaleX = 3,scaleY = 3;
    private double actx0,acty0;
    private float magnifierLen = 50;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);
        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        th_save_color = root.findViewById(R.id.th_save_color);
        th_get_img = root.findViewById(R.id.th_get_pic);
        th_img_pik = root.findViewById(R.id.th_img_pik);
        th_color_one = root.findViewById(R.id.th_color_one);
        th_color_two = root.findViewById(R.id.th_color_two);
        th_color_three = root.findViewById(R.id.th_color_three);
        th_color_four = root.findViewById(R.id.th_color_four);
        th_color_five = root.findViewById(R.id.th_color_five);
        th_color_six = root.findViewById(R.id.th_color_six);
        th_result = root.findViewById(R.id.th_result);
        th_color_RGB = root.findViewById(R.id.th_color_RGB);
        th_color_HEX = root.findViewById(R.id.th_color_HEX);
        th_color_CMYK = root.findViewById(R.id.th_color_CMYK);
        three_item_value_one = root.findViewById(R.id.three_item_value_one);
        three_item_value_two = root.findViewById(R.id.three_item_value_two);
        three_item_value_three = root.findViewById(R.id.three_item_value_three);
        three_item_value_four = root.findViewById(R.id.three_item_value_four);
        three_item_value_five = root.findViewById(R.id.three_item_value_five);
        three_item_value_six = root.findViewById(R.id.three_item_value_six);
        imgEvent();
        return root;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        myListener = (MyListener) getActivity();
    }

    public interface MyListener{
        public void sendValue(String value);
    }

    //选择图片
    private void imgEvent() {
        th_get_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent("android.intent.action.GET_CONTENT");
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });

        th_img_pik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //打开相册的方法
                Intent intent = new Intent("android.intent.action.GET_CONTENT");
                intent.setType("image/*");
                startActivityForResult(intent, 1);//set requestCode for 1
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (data != null) {
                try {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    BitmapFactory.Options newOpts = new BitmapFactory.Options();
                    Bitmap bitmap = BitmapFactory.decodeStream
                            (getActivity().getContentResolver().openInputStream(data.getData()));
                    getImgColor(bitmap);
                    pic_color(bitmap);
                    th_img_pik.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @SuppressLint("ClickableViewAccessibility")
    private void zoom_pic(Bitmap bitmap){
    }

    private void getImgColor(Bitmap bitmap){
        Palette palette = Palette.generate(bitmap);
        int vibrant = palette.getVibrantColor(0x000000);
        int vibrantLight = palette.getLightVibrantColor(0x000000);
        int vibrantDark = palette.getDarkVibrantColor(0x000000);
        int muted = palette.getMutedColor(0x000000);
        int mutedLight = palette.getLightMutedColor(0x000000);
        int mutedDark = palette.getDarkMutedColor(0x000000);

        th_color_one.setCardBackgroundColor(vibrant);
        th_color_two.setCardBackgroundColor(vibrantLight);
        th_color_three.setCardBackgroundColor(vibrantDark);
        th_color_four.setCardBackgroundColor(mutedLight);
        th_color_five.setCardBackgroundColor(muted);
        th_color_six.setCardBackgroundColor(mutedDark);

        three_item_value_one.setText(intToRgbForHex(vibrant));
        three_item_value_two.setText(intToRgbForHex(vibrantLight));
        three_item_value_three.setText(intToRgbForHex(vibrantDark));
        three_item_value_four.setText(intToRgbForHex(mutedLight));
        three_item_value_five.setText(intToRgbForHex(muted));
        three_item_value_six.setText(intToRgbForHex(mutedDark));
    }

    @SuppressLint("ClickableViewAccessibility")
    private void pic_color(Bitmap bitmap){
        th_img_pik.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                double imgwidth=th_img_pik.getWidth();
                double imgheight=th_img_pik.getHeight();
                double bitwidth=bitmap.getWidth();
                double bitheight=bitmap.getHeight();
                double x=motionEvent.getX();
                double y=motionEvent.getY();
                // 转换为bitmap的坐标
                double stretchx,stretchy;
                // 获取actx0，acty0，为实际的图片原点在imageview的dp坐标
                if (bitheight<400&&bitwidth<400) {
                    actx0=(400-bitwidth)/2;
                    acty0=(400-bitheight)/2;
                    stretchx=bitwidth;stretchy=bitheight;
                } else {
                    if (bitwidth>=bitheight) {
                        actx0= (double) 0.0;
                        acty0= (double) ((400.0-(bitheight*400.0)/bitwidth)/2.0);
                        stretchx=400;stretchy=(bitheight*400.0)/bitwidth;  //在
                    } else { 
                        acty0=(double)0.0;
                        actx0= (double) ((400.0-(bitwidth*400.0)/bitheight)/2.0);
                        stretchx=(bitwidth*400.0)/bitheight;stretchy=400;
                    }
                }
                // 更新为屏幕坐标
                actx0=(actx0/400)*imgwidth;
                acty0=(acty0/400)*imgheight;
                stretchx=(stretchx/400)*imgwidth;
                stretchy=(stretchy/400)*imgheight;

                // 判断触摸点是否在图片上
                if (x<actx0||x>actx0+stretchx||y<acty0||y>acty0+stretchy)
                    return true;
                // 更新在bitmap坐标系中的坐标
                actx0=(x-actx0)/stretchx*bitwidth;
                acty0=(y-acty0)/stretchy*bitheight;

                // 在手抬起来时获取图片颜色
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    int color = bitmap.getPixel((int)actx0, (int)acty0);
                    int r = Color.red(color);
                    int g = Color.green(color);
                    int b = Color.blue(color);
                    th_color_RGB.setText("RGB:" + r+", "+g+", "+b);
                    th_result.setCardBackgroundColor(Color.rgb(r, g, b));
                    th_color_CMYK.setText(rgbToCmky(r, g, b));
                    th_color_HEX.setText("HEX:" + rgbtoHEX(r, g, b));
                    th_save_color.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            myListener.sendValue(rgbtoHEX(r, g, b));
                        }
                    });
                }
                return true;
            }
        });
    }

    private static String intToRgbForHex(int color){
        int cg = color;

        int red = (cg & 0xff0000) >> 16;
        int green = (cg & 0x00ff00) >> 8;
        int blue = (cg & 0x0000ff);

        return  rgbtoHEX(red, green, blue);
    }

    private static String rgbToCmky(int R, int G, int B){
        float cmyk []= new float[4];
        cmyk[3]=(int)(Math.min(Math.min(255-R,255-G),255-B)/2.55);//cmykK
        float MyR = (float)(R/2.55);
        float Div = 100-cmyk[3];
        if (Div == 0)Div = 1;
        cmyk[0] = ((100-MyR-cmyk[3])/Div)*100;//cmykC
        float MyG = (float)(G/2.55);
        cmyk[1] = ((100-MyG-cmyk[3])/Div)*100;//cmykM
        float MyB = (float)(B/2.55);
        cmyk[2] = ((100-MyB-cmyk[3])/Div)*100;//cmykY
        DecimalFormat ints = new DecimalFormat(" ");
        return ("CMYK:"+ints.format(cmyk[0])+", "+ ints.format(cmyk[1]) +", "+ ints.format(cmyk[2]) + ", "+ ints.format(cmyk[3]));
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