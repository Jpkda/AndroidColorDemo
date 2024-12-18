package com.example.colors;

public class CommonColorValueR {
    private String hex;
    private String pinyin;
    public CommonColorValueR(String hex, String pinyin){
        this.hex = hex;
        this.pinyin = pinyin;
    }
    public String getHex(){
        return hex;
    }
    public String getPinyin(){
        return pinyin;
    }

}
