package com.example.colors;

    public class ColorBean {
    private String hexcolor;
    private Long time;

    public ColorBean(){
        super();
    }

    public Long getTime(){
        return time;
    }
    public void setTime(Long time){
        this.time = time;
    }

    public String getHexcolor(){
        return hexcolor;
    }
    public void setHexcolor(String hexcolor){
        this.hexcolor = hexcolor;
    }

}
