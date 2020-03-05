package com.bene.pictures.ui.splash;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.bene.pictures.R;
import com.bene.pictures.data.AppInfo;
import com.bene.pictures.data.MyInfo;
import com.bene.pictures.model.MError;
import com.bene.pictures.model.MSign;
import com.bene.pictures.model.MUser;
import com.bene.pictures.model.MVersion;
import com.bene.pictures.net.Net;
import com.bene.pictures.ui.base.BaseActivity;
import com.bene.pictures.ui.dialog.ConfirmDialogActivity;
import com.bene.pictures.ui.login.LoginActivity;
import com.bene.pictures.ui.main.MainActivity;
import com.bene.pictures.ui.widget.BaseTextView;
import com.bene.pictures.util.Toaster;
import com.bene.pictures.util.Util;

import java.io.File;

import butterknife.BindView;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        setIndeterminate(true);

        getAppVersion();

        createFolder();
    }

    @BindView(R.id.imv_progress)
    ImageView ui_imvProgress;

    private void setIndeterminate(boolean flag) {
        if (flag)
            ui_imvProgress.startAnimation(AnimationUtils.loadAnimation(this, R.anim.rotationx));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    void getAppVersion() {
        String appVersionName = Util.getAppVersionName(this);
    }

    private void createFolder() {
        String str = Environment.getExternalStorageState();

        if (str.equals(Environment.MEDIA_MOUNTED)) {
            String path = Environment.getExternalStorageDirectory()
                    .getAbsolutePath() + "/" + SDCARD_FOLDER + "/";
            AppInfo.getInstance().setAppPath(path);
            File file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }
        }
    }

    @Override
    protected void initUI() {
        super.initUI();

        checkVersion();
    }

    private void checkVersion() {

        Net.instance().api.getVersionInfo(DEVICE_ANDROID)
                .enqueue(new Net.ResponseCallBack<MVersion>() {
                    @Override
                    public void onSuccess(MVersion response) {
                        super.onSuccess(response);

                        version = response;
                        procVersion();
                    }

                    @Override
                    public void onFailure(MError response) {
                        super.onFailure(response);
                        showErrorMsg(response);
//                        if (BuildConfig.DEBUG) {
//                            version = new MVersion();
//                            procVersion();
//                        }
                    }
                });
    }

    MVersion version;

    private void procVersion() {
        int versionCode = Util.getAppVersionCode(this);
        if (versionCode < version.version_code) {

            String strNoti = "";

            if (version.force_update == 1) {
                strNoti = getString(R.string.str_update_noti1);
            } else {
                strNoti = getString(R.string.str_update_noti2);
            }

            Intent showConfirm = new Intent(SplashActivity.this, ConfirmDialogActivity.class);
            showConfirm.putExtra("content", strNoti);
            showConfirm.putExtra("no", "취소");
            showConfirm.putExtra("yes", "업데이트");
            startActivityForResult(showConfirm, 1125);
        } else {
            nextStep();
        }
    }

    void nextStep() {
        homeHandler.sendEmptyMessageDelayed(0, 3000);
    }

    private void signIn() {

        // fixed by Gambler 2019-10-22
        // 앱을 뒤로 가기 두번으로 끄기 한후 다시 시동시 자동로그인이 안되여 추가
        MyInfo.getInstance().load(SplashActivity.this);

        String id = "";
        String pwd = MyInfo.getInstance().userInfo.info.pwd;

        switch (MyInfo.getInstance().userInfo.info.login_type) {
            case LOGIN_ID:
                id = MyInfo.getInstance().userInfo.info.usr_id;
                break;
            case LOGIN_KAKAO:
                id = MyInfo.getInstance().userInfo.info.kt_id;
//                id = MyInfo.getInstance().userInfo.info.usr_id;
//                MyInfo.getInstance().userInfo.info.kt_id = id;
                break;
        }

        Net.instance().api.signIn(MyInfo.getInstance().userInfo.info.login_type, id, pwd, DEVICE_ANDROID, MyInfo.getInstance().fcm_token)
                .enqueue(new Net.ResponseCallBack<MSign>() {
                    @Override
                    public void onSuccess(MSign response) {
                        super.onSuccess(response);
                        hideProgress();
                        MyInfo.getInstance().userInfo.info.id = response.id;
                        MyInfo.getInstance().userInfo.info.sess_token = response.token;

                        go2Home();
                    }

                    @Override
                    public void onFailure(MError response) {
                        super.onFailure(response);
                        hideProgress();

                        go2Login();
                    }
                });

    }

    void go2Home() {
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        finish();
    }

    void go2Login() {
        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
        finish();
    }

    Handler homeHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            signIn();
            return false;
        }
    });

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 1125:
                if (resultCode == RESULT_CANCELED) {
                    if (version.force_update == 1) {
                        finish();
                    } else {
                        nextStep();
                    }
                } else {
                    try {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + Util.getPackageName(SplashActivity.this)));
                        startActivity(intent);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    finish();
                }
                break;
        }
    }
}
