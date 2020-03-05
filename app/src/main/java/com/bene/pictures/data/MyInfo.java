package com.bene.pictures.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.bene.pictures.model.MUser;
import com.bene.pictures.util.PrefMgr;
import com.yanzhenjie.album.AlbumFolder;

import java.util.ArrayList;

import static com.bene.pictures.data.Constant.LOGIN_ID;

public class MyInfo {

    private static MyInfo singleton;

    public MUser userInfo;
    public String fcm_token = "";
    public ArrayList<AlbumFolder> gAlbumFolders;

    public static MyInfo getInstance() {
        if (singleton == null) {
            singleton = new MyInfo();
        }

        if (singleton.userInfo == null) {
            singleton.userInfo = new MUser();

            if (singleton.userInfo.info == null) {
                singleton.userInfo.info = new MUser.UserInfo();
            }
        }

        return singleton;
    }

    public Boolean isValid() {
        return MyInfo.getInstance().userInfo.info.id > 0;
    }

    public void load(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PrefMgr.BENEPICTURE_PREFS,
                Context.MODE_PRIVATE);
        PrefMgr prefMgr = new PrefMgr(prefs);

        userInfo.info.login_type = prefMgr.getInt(PrefMgr.USER_LOGIN_TYPE, LOGIN_ID);
        userInfo.info.usr_id = prefMgr.getString(PrefMgr.USER_ID, "");
        userInfo.info.kt_id = prefMgr.getString(PrefMgr.KAKAO_ID, "");
        userInfo.info.pwd = prefMgr.getString(PrefMgr.USER_PWD, "");
        fcm_token = prefMgr.getString(PrefMgr.FCM_TOKEN, "");

        if (fcm_token.isEmpty() || fcm_token.equals("test_token")) {
            SharedPreferences preferences = context.getSharedPreferences(Constant.PREFERENCE_NAME, 0);
            fcm_token = preferences.getString(Constant.KEY_PUSH_TOKEN, Constant.NET_DEV_MODE == 1 ? "test_token" : "");
        }

    }

    public void save(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PrefMgr.BENEPICTURE_PREFS,
                Context.MODE_PRIVATE);
        PrefMgr prefMgr = new PrefMgr(prefs);

        prefMgr.put(PrefMgr.USER_LOGIN_TYPE, userInfo.info.login_type);
        prefMgr.put(PrefMgr.USER_ID, userInfo.info.usr_id);
        prefMgr.put(PrefMgr.KAKAO_ID, userInfo.info.kt_id);
        prefMgr.put(PrefMgr.USER_PWD, userInfo.info.pwd);
        prefMgr.put(PrefMgr.FCM_TOKEN, fcm_token);
    }

    public void saveToken(Context context, String token) {
        SharedPreferences prefs = context.getSharedPreferences(PrefMgr.BENEPICTURE_PREFS,
                Context.MODE_PRIVATE);
        PrefMgr prefMgr = new PrefMgr(prefs);

        fcm_token = token;
        prefMgr.put(PrefMgr.FCM_TOKEN, token);
    }

    public void savePush(Context context, int type, int id) {
        SharedPreferences prefs = context.getSharedPreferences(PrefMgr.BENEPICTURE_PREFS,
                Context.MODE_PRIVATE);
        PrefMgr prefMgr = new PrefMgr(prefs);

        prefMgr.put(PrefMgr.PUSH_TYPE, type);
        prefMgr.put(PrefMgr.PUSH_ID, id);
    }

    public void saveData(Context context, int data) {
        SharedPreferences prefs = context.getSharedPreferences(PrefMgr.BENEPICTURE_PREFS,
                Context.MODE_PRIVATE);
        PrefMgr prefMgr = new PrefMgr(prefs);

        prefMgr.put(PrefMgr.DATA_SET, data);
    }

    public void init(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PrefMgr.BENEPICTURE_PREFS,
                Context.MODE_PRIVATE);
        PrefMgr prefMgr = new PrefMgr(prefs);

        prefMgr.put(PrefMgr.USER_LOGIN_TYPE, LOGIN_ID);
        prefMgr.put(PrefMgr.USER_ID, "");
        prefMgr.put(PrefMgr.KAKAO_ID, "");
        prefMgr.put(PrefMgr.USER_PWD, "");
    }
}
