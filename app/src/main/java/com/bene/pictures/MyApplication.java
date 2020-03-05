package com.bene.pictures;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.support.multidex.MultiDexApplication;

import com.bene.pictures.data.Constant;
import com.bene.pictures.data.MyInfo;
import com.bene.pictures.kakao.KakaoSDKAdapter;
import com.bene.pictures.model.MFriendsList;
import com.bene.pictures.model.MKakaoFriend;
import com.bene.pictures.util.PrefMgr;
import com.bene.pictures.util.Util;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.kakao.auth.KakaoSDK;
//import com.kakao.auth.KakaoSDK;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.bene.pictures.data.Constant.SDCARD_FOLDER;

// should be opened
//import com.kakao.auth.KakaoSDK;

public class MyApplication extends MultiDexApplication {

    private static volatile MyApplication instance = null;
    private static volatile Activity currentActivity = null;

    public static List<MKakaoFriend> g_kakaoFriendList = new ArrayList<>();
    public static boolean g_isCallKakaoFriends = false;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        // SD 카드안의 파일 모두 삭제
        deleteSDCard();

        // 로그인한 유저정보 로드
        MyInfo.getInstance().load(this);

        // should be opened
        KakaoSDK.init(new KakaoSDKAdapter());

        // 카카오 친구 리스트 로드
        loadKakaoFriends();
    }

    @Override
    public void onTerminate() {
        instance = null;
        super.onTerminate();
    }

    private void deleteSDCard() {
        File rootDir = new File(Environment.getExternalStorageDirectory()
                + File.separator + SDCARD_FOLDER);
        if (rootDir.exists()) {
            Util.deleteDir(rootDir.getPath());
        }
    }

    public static synchronized MyApplication getInstance() {
        return instance;
    }

    public void setPushToken(String token) {
        SharedPreferences preferences = getSharedPreferences(Constant.PREFERENCE_NAME, 0);
        SharedPreferences.Editor preferencesEditor = preferences.edit();

        preferencesEditor.putString(Constant.KEY_PUSH_TOKEN, token);
        preferencesEditor.commit();

        MyInfo.getInstance().saveToken(this, token);
    }

    public void setKakaoInfo(String kakao_id, String kakao_name){
        SharedPreferences prefs = getSharedPreferences(PrefMgr.BENEPICTURE_PREFS, 0);
        PrefMgr prefMgr = new PrefMgr(prefs);
        prefMgr.put(PrefMgr.KAKAO_ID, kakao_id);
        prefMgr.put(PrefMgr.KAKAO_NAME, kakao_name);
    }

    public String getKakaoId(){
        SharedPreferences prefs = getSharedPreferences(PrefMgr.BENEPICTURE_PREFS, 0);
        PrefMgr prefMgr = new PrefMgr(prefs);
        return  prefMgr.getString(PrefMgr.KAKAO_ID, "");
    }

    public String getKakaoName(){
        SharedPreferences prefs = getSharedPreferences(PrefMgr.BENEPICTURE_PREFS, 0);
        PrefMgr prefMgr = new PrefMgr(prefs);
        return  prefMgr.getString(PrefMgr.KAKAO_NAME, "");
    }

    private void loadKakaoFriends() {
        SharedPreferences prefs = getSharedPreferences(PrefMgr.BENEPICTURE_PREFS,
                Context.MODE_PRIVATE);
        PrefMgr prefMgr = new PrefMgr(prefs);
        g_isCallKakaoFriends = prefMgr.getBoolean(PrefMgr.IS_CALL_KAKAO_FRIEND, false);

        // 친구리스트 저장
        if(g_isCallKakaoFriends) {
            String jsonFriends = prefMgr.getString(PrefMgr.KAKAO_FRIEND, "");
            Gson gson = new Gson();
            Type type = new TypeToken<List<MKakaoFriend>>(){}.getType();
            g_kakaoFriendList = gson.fromJson(jsonFriends, type);
        }
    }

    /**
     * singleton 애플리케이션 객체를 얻는다.
     *
     * @return singleton 애플리케이션 객체
     */
    public static MyApplication getGlobalApplicationContext( ) {
        if (instance == null)
            throw new IllegalStateException("this application does not inherit com.kakao.GlobalApplication");
        return instance;
    }

    public static Activity getCurrentActivity() {
        return currentActivity;
    }

    // Activity가 올라올때마다 Activity의 onCreate에서 호출해줘야한다.
    public static void setCurrentActivity(Activity currentActivity) {
        MyApplication.currentActivity = currentActivity;
    }

}
