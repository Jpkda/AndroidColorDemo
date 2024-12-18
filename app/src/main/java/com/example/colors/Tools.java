package com.example.colors;

import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import java.text.DecimalFormat;

public class Tools {
    // rgb to CMKY
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
        return ("CMYK: "+ints.format(cmyk[0])+", "+ ints.format(cmyk[1]) +", "+ ints.format(cmyk[2]) + ", "+ ints.format(cmyk[3]));
    }
    // HEX转 int
    private static int[] hexToRgb(String string){
        if(string != null && !"".equals(string) && string.length() == 7){
            int[] rgb = new int[3];
            rgb[0] = Integer.valueOf(string.substring( 1, 3 ), 16);
            rgb[1] = Integer.valueOf(string.substring( 3, 5 ), 16);
            rgb[2] = Integer.valueOf(string.substring( 5, 7 ), 16);
            return rgb;
        }
        return null;
    }

    // rgb值转HEX值
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

    public static void copy(String content, Context context) {
        // 得到剪贴板管理器
        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setText(content.trim());
    }



    private void setCvBak(CardView cardView , String hex ){
        try{
            cardView.setCardBackgroundColor(Color.parseColor(hex));
        }catch (Exception e){
        }
    }

    private void setTxBak(TextView cardView , String hex ){
        try{
            cardView.setBackgroundColor(Color.parseColor(hex));
        }catch (Exception e){
        }
    }
}


