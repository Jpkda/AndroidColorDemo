package com.example.colors;

public class CollectBean {
    private String collectOne;
    private String collectTwo;
    private String collectThree;
    private String collectFour;
    private String colorHex;
    private int TYPE;
    private Long time;

    public Long getTime(){
        return time;
    }
    public void setTime(Long time){
        this.time = time;
    }

    public CollectBean(){
        super();
    }
    public String getcollectOne(){
        return collectOne;
    }
    public void setcollectOne(String collectOne){
        this.collectOne = collectOne;
    }

    public String getcollectTwo(){
        return collectTwo;
    }
    public void setcollectTwo(String collectTwo){
        this.collectTwo = collectTwo;
    }

    public String getcollectThree(){
        return collectThree;
    }
    public void setcollectThree(String collectThree){
        this.collectThree = collectThree;
    }

    public String getcollectFour(){
        return collectFour;
    }
    public void setcollectFour(String collectFour){
        this.collectFour = collectFour;
    }

}
