package com.example.colors;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CommonColorRecyclerAdapters extends RecyclerView.Adapter<CommonColorRecyclerAdapters.ViewHolder>{
    private final List<CommonColorValueR> colorValue;
    private final Context context;

    public CommonColorRecyclerAdapters(Context context, List<CommonColorValueR> colorValue) {
        this.colorValue = colorValue;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.fragment_common_color_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CommonColorValueR model = colorValue.get(position);
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/QingEngravedSongYue.TTF");

        //设置文本颜色
        int[] rgb =hexToRgb(model.getHex());
        int r = rgb[0];
        int g = rgb[1];
        int b = rgb[2];
        if(r<120 && g<120 && b<120 ){
            holder.getTextName().setTextColor(Color.parseColor("#ffffff"));
            holder.getTextValue().setTextColor(Color.parseColor("#ffffff"));
        }
        if(r>120 && g>120 && b>120 ){
            holder.getTextName().setTextColor(Color.parseColor("#000000"));
            holder.getTextValue().setTextColor(Color.parseColor("#000000"));
        }
        holder.getTextName().setTypeface(typeface);
        holder.getTextName().setText(model.getPinyin());

        holder.getTextValue().setText(model.getHex().substring(1, 7).toUpperCase());
        holder.getCardViewColor().setCardBackgroundColor(Color.parseColor(model.getHex()));

        // set dialog fragment
        holder.getCardViewColor().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                final  AlertDialog dialog = builder
                        .setView(R.layout.fragment_common_color_list_dialog_item)
                        .create();
                dialog.show();

                PieChart fm_dialog_color_chart = dialog.getWindow().findViewById(R.id.fm_dialog_color_chart);
                // pie chart class
                List<PieEntry> cmyk = new ArrayList<>();
                float cmkyV[] = rgbToCmky(r,g,b);

                cmyk.add(new PieEntry(cmkyV[0],"Cyan"+cmkyV[0]));
                cmyk.add(new PieEntry(cmkyV[1],"Magenta"+cmkyV[1]));
                cmyk.add(new PieEntry(cmkyV[2],"Yellow"+cmkyV[2]));
                cmyk.add(new PieEntry(cmkyV[3],"Black"+cmkyV[3]));

                List<Integer> colors = new ArrayList<>();
                colors.add(Color.rgb(0,128,128));
                colors.add(Color.rgb(255,0,255));
                colors.add(Color.rgb(255,255,0));
                colors.add(Color.rgb(0,0,0));

                fm_dialog_color_chart.setDragDecelerationFrictionCoef(0.95f);
                fm_dialog_color_chart.setDrawEntryLabels(false);
                fm_dialog_color_chart.getDescription().setEnabled(false);
                // 设置pieChart图表展示动画效果
                fm_dialog_color_chart.animateY(800, Easing.EasingOption.EaseInOutQuad);
                // 设置 pieChart 内部圆环属性
                fm_dialog_color_chart.setDrawHoleEnabled(true);
                fm_dialog_color_chart.setHoleColor(Color.rgb(r,g,b));
                fm_dialog_color_chart.setHoleRadius(58f);
                fm_dialog_color_chart.setTransparentCircleRadius(62f);
                fm_dialog_color_chart.setTransparentCircleAlpha(255);

                fm_dialog_color_chart.setDrawCenterText(true);
                fm_dialog_color_chart.setCenterTextTypeface(typeface);
                fm_dialog_color_chart.setCenterText(model.getPinyin());
                fm_dialog_color_chart.setCenterTextSize(24f);
                fm_dialog_color_chart.setCenterTextColor(Color.WHITE);

                Legend l = fm_dialog_color_chart.getLegend();
                l.setEnabled(true);
                l.setFormSize(8f);
                l.setFormToTextSpace(8f);
                l.setTextSize(8f);

                PieDataSet pieDataSet = new PieDataSet(cmyk, "");
                pieDataSet.setColors(colors);
                pieDataSet.setSliceSpace(2f);

                PieData pieData = new PieData(pieDataSet);
                pieData.setDrawValues(true);
                pieData.setValueTextColor(Color.rgb(98,73,65));
                pieData.setValueTextSize(12f);
                fm_dialog_color_chart.setData(pieData);

                TextView fm_dialog_color_title = dialog.getWindow().findViewById(R.id.fm_dialog_color_title);
                TextView fm_dialog_color_value_r = dialog.getWindow().findViewById(R.id.fm_dialog_color_value_r);
                TextView fm_dialog_color_value_g = dialog.getWindow().findViewById(R.id.fm_dialog_color_value_g);
                TextView fm_dialog_color_value_b = dialog.getWindow().findViewById(R.id.fm_dialog_color_value_b);

                fm_dialog_color_title.setTypeface(typeface);
                fm_dialog_color_title.setTextColor(Color.rgb(r,g,b));
                fm_dialog_color_title.setText(model.getPinyin());

                TestAutoAn.addTextViewAddAnim(fm_dialog_color_value_r, r);
                TestAutoAn.addTextViewAddAnim(fm_dialog_color_value_g, g);
                TestAutoAn.addTextViewAddAnim(fm_dialog_color_value_b, b);
            }
        });
    }

    @Override
    public int getItemCount() {
        return colorValue.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView common_item_color_name, common_item_color_value;
        private CardView common_item_color_main;

        public ViewHolder(View view) {
            super(view);
            common_item_color_name = (TextView) view.findViewById(R.id.common_item_color_name);
            common_item_color_value = (TextView) view.findViewById(R.id.common_item_color_value);
            common_item_color_main = (CardView) view.findViewById(R.id.common_item_color_main);
        }

        public TextView getTextName() {
            return common_item_color_name;
        }
        public TextView getTextValue() {
            return common_item_color_value;
        }
        public CardView getCardViewColor() {
            return common_item_color_main;
        }
    }

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

    private static float[] rgbToCmky(int R, int G, int B){
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
        DecimalFormat df = new DecimalFormat("0.00");
        cmyk[0] = Float.parseFloat(df.format(cmyk[0]));
        cmyk[1] = Float.parseFloat(df.format(cmyk[1]));
        cmyk[2] = Float.parseFloat(df.format(cmyk[2]));
        cmyk[3] = Float.parseFloat(df.format(cmyk[3]));
        return cmyk;
    }
}