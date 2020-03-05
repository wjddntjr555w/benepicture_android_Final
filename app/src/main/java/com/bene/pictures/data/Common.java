package com.bene.pictures.data;

import android.content.Context;
import android.graphics.Typeface;
import android.support.design.widget.TabLayout;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Common {

    public static bank_info[] g_bankinfoArray = {
            new bank_info(1, "국민은행", "004"),
            new bank_info(2, "우리은행", "020"),
            new bank_info(3, "신한은행", "088"),
            new bank_info(4, "농협은행", "011"),
            new bank_info(5, "하나은행", "081"),
            new bank_info(6, "SC은행", "023"),
            new bank_info(7, "기업은행", "003"),
            new bank_info(8, "산업은행", "002"),
            new bank_info(9, "씨티은행", "027"),
            new bank_info(10, "HSBC은행", "054"),
            new bank_info(11, "도이치은행", "055"),
            new bank_info(12, "경남은행", "039"),
            new bank_info(13, "전북은행", "037"),
            new bank_info(14, "광주은행", "034"),
            new bank_info(15, "대구은행", "031"),
            new bank_info(16, "부산은행", "032"),
            new bank_info(17, "제주은행", "035"),
            new bank_info(18, "우체국은행", "071"),
            new bank_info(19, "새마을금고", "045"),
            new bank_info(20, "수협은행", "007"),
            new bank_info(21, "신협은행", "048"),
            new bank_info(22, "카카오은행", "090"),
            new bank_info(23, "케이뱅크", "089")
    };

    public static void setTabItemFontAndSize(Context context, TabLayout tl, int idx, String fontType, float dp, int style) {
        ViewGroup vg = (ViewGroup) tl.getChildAt(0);
        int tabsCount = vg.getChildCount();

        Typeface typeface = null;
        if (fontType != null) {
            try {
                typeface = Typeface.createFromAsset(context.getAssets(), fontType);
            } catch (Exception e) {
                typeface = null;
            }
        }

        if (idx < tabsCount) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(idx);
            int tabChildsCount = vgTab.getChildCount();
            for (int i = 0; i < tabChildsCount; i++) {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView) {
                    ((TextView) tabViewChild).setTypeface(typeface != null ? typeface : ((TextView) tabViewChild).getTypeface(), style != 0 ? style : Typeface.NORMAL);
                    ((TextView) tabViewChild).setTextSize(TypedValue.COMPLEX_UNIT_DIP, dp);
                    ((TextView) tabViewChild).setSingleLine(false);
                }
            }
        }
    }

}
