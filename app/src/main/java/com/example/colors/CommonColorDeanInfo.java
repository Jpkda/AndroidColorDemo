package com.example.colors;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CommonColorDeanInfo {
    public String getJson(){
        // 将json数据转字符串
        StringBuilder stringBuilder = new StringBuilder();
        try {
            InputStream is = CommonColorDeanInfo.this.getClass().getClassLoader().getResourceAsStream("assets/" + "colors.json");
            InputStreamReader streamReader = new InputStreamReader(is);
            BufferedReader bf = new BufferedReader(streamReader);
            String line;
            while ((line = bf.readLine()) != null){
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}
