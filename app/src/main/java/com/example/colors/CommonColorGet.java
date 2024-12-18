package com.example.colors;

import java.util.ArrayList;
import java.util.List;

public class CommonColorGet {

    public ArrayList<String> HexToString(List<CommonColorApDean> shops){
        ArrayList<String> hex = new ArrayList<>();
        String temp = "x";
        try {
            int i = 0;
            while(i < shops.size())
            {
                i++;
                temp = shops.get(i).getHex();
                hex.add(temp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hex;
    }

    public ArrayList<String> PinyinToString(List<CommonColorApDean> shops){
        ArrayList<String> pinyin = new ArrayList<>();
        String temp = "x";
        try {
            int i = 0;
            while(i < shops.size())
            {
                i++;
                temp = shops.get(i).getName();
                pinyin.add(temp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pinyin;
    }
}
