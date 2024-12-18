package com.example.colors;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class CommonColorApDean{

    @SerializedName("CMYK")
    private List<Integer> cMYK;
    @SerializedName("RGB")
    private List<Integer> rGB;
    private String hex;
    private String name;
    private String pinyin;

    public List<Integer> getCMYK() {
        return cMYK;
    }

    public void setCMYK(List<Integer> cMYK) {
        this.cMYK = cMYK;
    }

    public List<Integer> getRGB() {
        return rGB;
    }

    public void setRGB(List<Integer> rGB) {
        this.rGB = rGB;
    }

    public String getHex() {
        return hex;
    }

    public void setHex(String hex) {
        this.hex = hex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }
}


