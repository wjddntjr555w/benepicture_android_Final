package com.bene.pictures.ui.widget;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.bene.pictures.util.Util;


public class BaseTextView extends AppCompatTextView {

    public BaseTextView(Context context) {
        super(context);
    }

    public BaseTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Util.setTypeface(attrs, this);
    }

    public BaseTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Util.setTypeface(attrs, this);
    }
}
