package com.bene.pictures.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;

import com.bene.pictures.R;
import com.bene.pictures.util.StringFilter;
import com.bene.pictures.util.Util;

public class BaseEditText extends AppCompatEditText {

    private static final int Hangul = 0;
    private static final int Alpha = 1;
    private static final int Numeric = 2;
    private static final int HangulAlpha = 3;
    private static final int HangulNumeric = 4;
    private static final int AlphaNumeric = 5;
    private static final int HangulAlphaNumeric = 6;
    private static final int LowerAlphaNumeric = 7;
    private static final int UpperAlphaOneNumeric = 8;
    private static final int NumericLine = 9;

    private Handler handler = null;

    private boolean isHiddenKeyboard = true;

    public BaseEditText(Context context) {
        super(context);
    }

    public BaseEditText(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context, attrs, 0);
    }

    public BaseEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        init(context, attrs, defStyle);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BaseEditText, defStyle, 0);
        final int filter = a.getInt(R.styleable.BaseEditText_filter, -1);
        a.recycle();

        switch (filter) {
            case Hangul:
                Util.setFilter(context, this, StringFilter.ALLOW_HANGUL);
                break;
            case Alpha:
                Util.setFilter(context, this, StringFilter.ALLOW_ALPHA);
                break;
            case Numeric:
                Util.setFilter(context, this, StringFilter.ALLOW_NUMERIC);
                break;
            case HangulAlpha:
                Util.setFilter(context, this, StringFilter.ALLOW_HANGUL_ALPHA);
                break;
            case HangulNumeric:
                Util.setFilter(context, this, StringFilter.ALLOW_HANGUL_NUMERIC);
                break;
            case AlphaNumeric:
                Util.setFilter(context, this, StringFilter.ALLOW_ALPHA_NUMERIC);
                break;
            case HangulAlphaNumeric:
                Util.setFilter(context, this, StringFilter.ALLOW_HANGUL_ALPHA_NUMERIC);
                break;
            case LowerAlphaNumeric:
                Util.setFilter(context, this, StringFilter.ALLOW_LOWER_ALPHA_NUMERIC);
                break;
            case UpperAlphaOneNumeric:
                Util.setFilter(context, this, StringFilter.ALLOW_UPPER_ALPHA_ONE_NUMERIC);
                break;
            case NumericLine:
                Util.setFilter(context, this, StringFilter.ALLOW_NUMERIC_LINE);
                break;
        }

        Util.setTypeface(attrs, this);
    }

    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (handler != null) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    handler.sendEmptyMessage(InputMethodManager.RESULT_HIDDEN);
                }
            }

            if (isHiddenKeyboard == false) {
                return true;
            }
        }

        return super.dispatchKeyEvent(event);
    }

    public void setHiddenKeyboardOnBackPressed(boolean isHiddenKeyboard) {
        this.isHiddenKeyboard = isHiddenKeyboard;
    }

    public void setOnBackPressedHandler(Handler handler) {
        this.handler = handler;
    }
}
