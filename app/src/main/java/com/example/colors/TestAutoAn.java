package com.example.colors;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.widget.TextView;

import java.text.DecimalFormat;

public class TestAutoAn {
        public static void addTextViewAddAnim(TextView tv, int value) {
            TextViewEvaluator evaluator = new TextViewEvaluator(value);
            ValueAnimator animator = ValueAnimator.ofObject(evaluator, tv);
            //动画时间
            animator.setDuration(800);
            animator.start();
        }

        //核心类
        static class TextViewEvaluator implements TypeEvaluator {
            private double value = 0;

            TextViewEvaluator(int value) {
                this.value = value;
            }

            @Override
            public Object evaluate(float fraction, Object startValue, Object endValue) {
                TextView tv = (TextView) endValue;
                DecimalFormat df = new DecimalFormat("0");
                tv.setText(df.format(value * fraction));
                return startValue;
            }

        }
}
