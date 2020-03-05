package com.bene.pictures.util;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public final class PrefMgr {

    public static final String BENEPICTURE_PREFS = "benepicture_prefs";

    public static final String MAIN_GUIDE_SHOW = "main_guide_show";

    public static final String USER_LOGIN_TYPE = "login_type";
    public static final String USER_ID = "user_id";
    public static final String USER_PWD = "pwd";
    public static final String FCM_TOKEN = "fcm_token";
    public static final String PUSH_ID = "push_id";
    public static final String PUSH_TYPE = "push_type";
    public static final String DATA_SET = "data_set";
    public static final String KAKAO_ID = "kakao_id";
    public static final String KAKAO_NAME = "kakao_name";

    public static final String IS_CALL_KAKAO_FRIEND = "is_call_kakao_friend";
    public static final String KAKAO_FRIEND = "kakao_friend";

    private SharedPreferences prefs;

    public PrefMgr(SharedPreferences prefs) {
        super();
        this.prefs = prefs;
    }

    public void put(String key, Object value) {
        Editor editor = prefs.edit();
        if (value.getClass().equals(String.class)) {
            editor.putString(key, (String) value);
        } else if (value.getClass().equals(Boolean.class)) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value.getClass().equals(Integer.class)) {
            editor.putInt(key, (Integer) value);
        } else if (value.getClass().equals(Float.class) || value.getClass().equals(Double.class)) {
            editor.putFloat(key, (Float) value);
        }

        editor.commit();
    }

    public String getString(String key, String defaultValue) {
        return prefs.getString(key, defaultValue);
    }

    public float getFloat(String key, float defaultValue) {
        return prefs.getFloat(key, defaultValue);
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return prefs.getBoolean(key, defaultValue);
    }

    public int getInt(String key, int defaultValue) {
        return prefs.getInt(key, defaultValue);
    }

    public SharedPreferences getPrefs() {
        return prefs;
    }
}
