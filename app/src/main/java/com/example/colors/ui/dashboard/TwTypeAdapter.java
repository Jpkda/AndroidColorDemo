package com.example.colors.ui.dashboard;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Outline;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import com.example.colors.DBOpenHelper;
import com.example.colors.R;
import com.example.colors.ColorBean;
import com.example.colors.CollectBean;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class TwTypeAdapter extends BaseAdapter {
    // itemA类的type标志
    // a solo
    // b collect
    private static final int TYPE_A = 0;
    //itemB类的type标志 collect
    private static final int TYPE_B = 1;
    private static final Object TAG = "TwTypeAdapter";
    private Context context;
    //整合数据
    private List<Object> data = new ArrayList<>();

    //sql
    DBOpenHelper dbOpenHelper;
    SQLiteDatabase sdb;

    public TwTypeAdapter(Context context, ArrayList<Object> listDataOne, ArrayList<Object> listDataTwo) {
        this.context = context;
        data.addAll(listDataOne);
        data.addAll(listDataTwo);
        Collections.sort(data, new MyComparator());
    }

    @Override
    public int getItemViewType(int position) {
        int result = 0;
        if (data.get(position) instanceof ColorBean) {
            result = TYPE_A;
        } else if (data.get(position) instanceof CollectBean) {
            result = TYPE_B;
        }
        return result;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        // 创建两种不同种类的viewHolder变量
        ViewHolder1 holder1 = null;
        ViewHolder2 holder2 = null;
        // 根据position获得View的type
        int type = getItemViewType(position);
            holder1 = new ViewHolder1();
            holder2 = new ViewHolder2();

            switch (type) {
                case TYPE_A:
                    view = View.inflate(context, R.layout.tw_item_solo, null);
                    holder1.tw_hex = view.findViewById(R.id.tw_hex);
                    holder1.tw_solo_color = view.findViewById(R.id.tw_solo_color);
                    view.setTag(R.id.tag_first, holder1);
                    break;
                case TYPE_B:
                    view = View.inflate(context, R.layout.tw_item_collect, null);
                    holder2.tw_collect_one = view.findViewById(R.id.tw_collect_one);
                    holder2.tw_collect_two = view.findViewById(R.id.tw_collect_two);
                    holder2.tw_collect_three = view.findViewById(R.id.tw_collect_three);
                    holder2.tw_collect_four = view.findViewById(R.id.tw_collect_four);
                    holder2.tw_collect_main = view.findViewById(R.id.tw_collect_main);
                    view.setTag(R.id.tag_second, holder2);
                    break;
            }

            //根据不同的type来获得tag
            switch (type) {
                case TYPE_A:
                    holder1 = (ViewHolder1) view.getTag(R.id.tag_first);
                    break;
                case TYPE_B:
                    holder2 = (ViewHolder2) view.getTag(R.id.tag_second);
                    break;
            }

        Object o = data.get(position);
        //根据不同的type设置数据
        switch (type) {
            case TYPE_A:
                ColorBean solo = (ColorBean) o;
                if (solo != null){
                    holder1.tw_hex.setText(solo.getHexcolor());
                    setCvBak(holder1.tw_solo_color,solo.getHexcolor());
                    holder1.tw_solo_color.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            final  AlertDialog dialog = builder
                                    .setView(R.layout.tw_dialog)
                                    .create();
                            dialog.show();
                            // 删除solo数据库中的item
                            dialog.getWindow().findViewById(R.id.tw_item_del).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dbOpenHelper = new DBOpenHelper(context, "soloColor.db", null, 1);
                                    sdb = dbOpenHelper.getWritableDatabase();
                                    sdb.delete("colorType","colorHex=?",new String[]{String.valueOf(solo.getHexcolor())});
                                    sdb.delete("colorType","time=?",new String[]{String.valueOf(solo.getTime())});
                                    Toast.makeText(view.getContext(),"成功删除"+ solo.getHexcolor(), Toast.LENGTH_SHORT).show();
                                }
                            });

                            dialog.getWindow().findViewById(R.id.tw_item_out).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialog.dismiss();
                                }
                            });

                            dialog.getWindow().findViewById(R.id.tw_item_main).setBackgroundColor(Color.parseColor(solo.getHexcolor()));


                            // 20221106115441
                            TextView tw_item_time = dialog.getWindow().findViewById(R.id.tw_item_time);
                            String time = ""+ solo.getTime();
                            try {
                                String formatTime = time.substring(0,4)+"-"+ time.substring(4,6)+"-"+ time.substring(6,8)+ " "
                                        + time.substring(8,10)+ ":" + time.substring(10,12) + ":" + time.substring(12,14);
                                tw_item_time.setText(formatTime);
                            } catch (Exception e){
                                Log.e((String) TAG, "时间类型错误");
                            }

                            int[] rgb;
                            rgb = hexToRgb(solo.getHexcolor());
                            int r = rgb[0];
                            int g = rgb[1];
                            int b = rgb[2];
                            TextView tw_item_rgb = dialog.getWindow().findViewById(R.id.tw_item_rgb);
                            tw_item_rgb.setText("RGB: " + r+", "+g+", "+b);

                            TextView tw_item_hex = dialog.getWindow().findViewById(R.id.tw_item_hex);
                            tw_item_hex.setText("HEX: "+solo.getHexcolor());

                            TextView tw_item_cmyk = dialog.getWindow().findViewById(R.id.tw_item_cmyk);
                            tw_item_cmyk.setText(rgbToCmky(r,g,b));

                            CardView tw_item_main_ui = dialog.getWindow().findViewById(R.id.tw_item_main_ui);
                            tw_item_main_ui.setOutlineProvider(new ViewOutlineProvider() {
                                @Override
                                public void getOutline(View view, Outline outline) {
                                    outline.setRoundRect(0,0,view.getWidth(),view.getHeight(),70f);
                                }
                            });
                            tw_item_main_ui.setClipToOutline(true);
                            dialog.getWindow().findViewById(R.id.tw_list_than).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                            tw_item_rgb.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                                    String soloRgb = r + "," + g + "," + b;
                                    cmb.setText(soloRgb.trim());
                                }
                            });

                            tw_item_hex.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                                    cmb.setText(solo.getHexcolor().trim());
                                }
                            });

                            tw_item_cmyk.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                                    String soloCmyk = rgbToCmky(r,g,b);
                                    cmb.setText(soloCmyk.trim());
                                }
                            });
                        }
                    });
                }
                break;

            case TYPE_B:
                CollectBean collect = (CollectBean) o;
                if (collect != null) {
                    setTxBak(holder2.tw_collect_one,collect.getcollectOne());
                    setTxBak(holder2.tw_collect_two,collect.getcollectTwo());
                    setTxBak(holder2.tw_collect_three,collect.getcollectThree());
                    setTxBak(holder2.tw_collect_four,collect.getcollectFour());

                    holder2.tw_collect_main.setOnClickListener(new View.OnClickListener() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onClick(View view) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            final  AlertDialog dialog = builder
                                    .setView(R.layout.tw_dialog_collect)
                                    .create();
                            dialog.show();

                            dialog.getWindow().findViewById(R.id.tw_item_del).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dbOpenHelper = new DBOpenHelper(context, "soloColor.db", null, 1);
                                    sdb = dbOpenHelper.getWritableDatabase();
                                    sdb.delete("colorCollect","time=?",new String[]{String.valueOf(collect.getTime())});
                                    sdb.delete("colorCollect","collectOne=?",new String[]{String.valueOf(collect.getcollectOne())});
                                    sdb.delete("colorCollect","collectTwo=?",new String[]{String.valueOf(collect.getcollectTwo())});
                                    sdb.delete("colorCollect","collectThree=?",new String[]{String.valueOf(collect.getcollectThree())});
                                    sdb.delete("colorCollect","collectFour=?",new String[]{String.valueOf(collect.getcollectFour())});
                                    Toast.makeText(view.getContext(),"成功删除", Toast.LENGTH_SHORT).show();
                                }
                            });
                            dialog.getWindow().findViewById(R.id.tw_item_out).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialog.dismiss();
                                }
                            });

                            TextView tw_item_time_c = dialog.getWindow().findViewById(R.id.tw_item_time_c);
                            String time = ""+ collect.getTime();
                            try {
                                String formatTime = time.substring(0,4)+"-"+ time.substring(4,6)+"-"+ time.substring(6,8)+ " "
                                        + time.substring(8,10)+ ":" + time.substring(10,12) + ":" + time.substring(12,14);
                                tw_item_time_c.setText(formatTime);
                            } catch (Exception e){
                                Log.e((String) TAG, "时间类型错误");
                            }

                            int[] rgbOne;
                            rgbOne = hexToRgb(collect.getcollectOne());
                            int r0 = rgbOne[0];
                            int g0 = rgbOne[1];
                            int b0 = rgbOne[2];

                            int[] rgbTwo;
                            rgbTwo = hexToRgb(collect.getcollectTwo());
                            int r1 = rgbTwo[0];
                            int g1 = rgbTwo[1];
                            int b1 = rgbTwo[2];

                            int[] rgbThree;
                            rgbThree = hexToRgb(collect.getcollectThree());
                            int r2 = rgbThree[0];
                            int g2 = rgbThree[1];
                            int b2 = rgbThree[2];

                            int[] rgbFour;
                            rgbFour = hexToRgb(collect.getcollectFour());
                            int r3 = rgbFour[0];
                            int g3 = rgbFour[1];
                            int b3 = rgbFour[2];

                            TextView tw_item_rgb_one = dialog.getWindow().findViewById(R.id.tw_item_rgb_one);
                            tw_item_rgb_one.setText("RGB: " + r0+", "+g0+", "+b0);

                            TextView tw_item_hex_one = dialog.getWindow().findViewById(R.id.tw_item_hex_one);
                            tw_item_hex_one.setText("HEX: "+collect.getcollectOne());

                            TextView tw_item_cmyk_one = dialog.getWindow().findViewById(R.id.tw_item_cmyk_one);
                            tw_item_cmyk_one.setText(rgbToCmky(r0,g0,b0));

                            CardView tw_item_main_one = dialog.getWindow().findViewById(R.id.tw_item_main_one);

                            setCvBak(tw_item_main_one,collect.getcollectOne());


                            TextView tw_item_rgb_two = dialog.getWindow().findViewById(R.id.tw_item_rgb_two);
                            tw_item_rgb_two.setText("RGB: " + r1+", "+g1+", "+b1);

                            TextView tw_item_hex_two = dialog.getWindow().findViewById(R.id.tw_item_hex_two);
                            tw_item_hex_two.setText("HEX: "+collect.getcollectTwo());

                            TextView tw_item_cmyk_two = dialog.getWindow().findViewById(R.id.tw_item_cmyk_two);
                            tw_item_cmyk_two.setText(rgbToCmky(r1,g1,b1));

                            CardView tw_item_main_two = dialog.getWindow().findViewById(R.id.tw_item_main_two);

                            setCvBak(tw_item_main_two,collect.getcollectTwo());

                            TextView tw_item_rgb_three = dialog.getWindow().findViewById(R.id.tw_item_rgb_three);
                            tw_item_rgb_three.setText("RGB: " + r2+", "+g2+", "+b2);

                            TextView tw_item_hex_three = dialog.getWindow().findViewById(R.id.tw_item_hex_three);
                            tw_item_hex_three.setText("HEX: "+collect.getcollectThree());

                            TextView tw_item_cmyk_three = dialog.getWindow().findViewById(R.id.tw_item_cmyk_three);
                            tw_item_cmyk_three.setText(rgbToCmky(r2,g2,b2));

                            CardView tw_item_main_three = dialog.getWindow().findViewById(R.id.tw_item_main_three);

                            setCvBak(tw_item_main_three,collect.getcollectThree());

                            TextView tw_item_rgb_four = dialog.getWindow().findViewById(R.id.tw_item_rgb_four);
                            tw_item_rgb_four.setText("RGB: " + r3+", "+g3+", "+b3);

                            TextView tw_item_hex_four = dialog.getWindow().findViewById(R.id.tw_item_hex_four);
                            tw_item_hex_four.setText("HEX: "+collect.getcollectFour());

                            TextView tw_item_cmyk_four = dialog.getWindow().findViewById(R.id.tw_item_cmyk_four);
                            tw_item_cmyk_four.setText(rgbToCmky(r3,g3,b3));

                            CardView tw_item_main_one_four = dialog.getWindow().findViewById(R.id.tw_item_main_one_four);

                            setCvBak(tw_item_main_one_four,collect.getcollectFour());
                            tw_item_rgb_one.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                                    String collectRgbO = r0 + "," + g0 + "," + b0;
                                    cmb.setText(collectRgbO.trim());
                                }
                            });

                            tw_item_hex_one.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                                    cmb.setText(collect.getcollectOne().trim());
                                }
                            });

                            tw_item_cmyk_one.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                                    String collectCmykO = rgbToCmky(r0,g0,b0);
                                    cmb.setText(collectCmykO.trim());
                                }
                            });

                            tw_item_rgb_two.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                                    String collectRgbTw = r1 + "," + g1 + "," + b1;
                                    cmb.setText(collectRgbTw.trim());
                                }
                            });

                            tw_item_hex_two.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                                    cmb.setText(collect.getcollectTwo().trim());
                                }
                            });

                            tw_item_cmyk_two.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                                    String collectCmykTw = rgbToCmky(r1,g1,b1);
                                    cmb.setText(collectCmykTw.trim());
                                }
                            });

                            tw_item_rgb_three.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                                    String collectRgbTh = r2 + "," + g2 + "," + b2;
                                    cmb.setText(collectRgbTh.trim());
                                }
                            });

                            tw_item_hex_three.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                                    cmb.setText(collect.getcollectThree().trim());
                                }
                            });

                            tw_item_cmyk_three.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                                    String collectCmykTh = rgbToCmky(r2,g2,b2);
                                    cmb.setText(collectCmykTh.trim());
                                }
                            });

                            //four

                            tw_item_rgb_four.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                                    String collectRgbF = r3 + "," + g3 + "," + b3;
                                    cmb.setText(collectRgbF.trim());
                                }
                            });

                            tw_item_hex_four.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                                    cmb.setText(collect.getcollectFour().trim());
                                }
                            });

                            tw_item_cmyk_four.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                                    String collectCmykT = rgbToCmky(r3,g3,b3);
                                    cmb.setText(collectCmykT.trim());
                                }
                            });
                        }
                    });
                }
                break;
        }


        return view;
    }

    //根据不同的情况通过time排序
    public class MyComparator implements Comparator {
        public int compare(Object arg0, Object arg1) {
            if (arg0 instanceof ColorBean && arg1 instanceof CollectBean) {
                ColorBean a = (ColorBean) arg0;
                CollectBean b = (CollectBean) arg1;
                return Long.valueOf(a.getTime()).compareTo(b.getTime());

            } else if (arg0 instanceof CollectBean && arg1 instanceof ColorBean) {
                CollectBean b = (CollectBean) arg0;
                ColorBean a = (ColorBean) arg1;
                return Long.valueOf(b.getTime()).compareTo(Long.valueOf(a.getTime()));

            } else if (arg0 instanceof ColorBean && arg1 instanceof ColorBean) {

                ColorBean a0 = (ColorBean) arg0;
                ColorBean a1 = (ColorBean) arg1;
                return Long.valueOf(a0.getTime()).compareTo(Long.valueOf(a1.getTime()));

            } else {
                CollectBean b0 = (CollectBean) arg0;
                CollectBean b1 = (CollectBean) arg1;
                return Long.valueOf(b0.getTime()).compareTo(Long.valueOf(b1.getTime()));
            }
        }
    }

     // item A 的Viewholder
    private static class ViewHolder1 {
        TextView time;
        TextView tw_hex;
        CardView soloColor, tw_solo_color, tw_item_solo;
    }

    // item B 的Viewholder
    private static class ViewHolder2 {
        TextView time, tw_collect_one, tw_collect_two, tw_collect_three, tw_collect_four;
        CardView tw_collect_main;
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

    //rgb to CMKY
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

    private void setCvBak(CardView cardView ,String hex ){
        try{
            cardView.setCardBackgroundColor(Color.parseColor(hex));
        }catch (Exception e){
            Log.e((String) TAG, "颜色值设置错误");
        }
    }

    private void setTxBak(TextView cardView ,String hex ){
        try{
            cardView.setBackgroundColor(Color.parseColor(hex));
        }catch (Exception e){
            Log.e((String) TAG, "颜色值设置错误");
        }
    }
}