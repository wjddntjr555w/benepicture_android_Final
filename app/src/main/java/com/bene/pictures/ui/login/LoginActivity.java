package com.bene.pictures.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.Toast;

import com.bene.pictures.BuildConfig;
import com.bene.pictures.MyApplication;
import com.bene.pictures.R;
import com.bene.pictures.data.MyInfo;
import com.bene.pictures.model.MError;
import com.bene.pictures.model.MSign;
import com.bene.pictures.net.Net;
import com.bene.pictures.ui.base.BaseActivity;
import com.bene.pictures.ui.join.JoinAgreeActivity;
import com.bene.pictures.ui.main.MainActivity;
import com.bene.pictures.ui.widget.BaseEditText;
import com.bene.pictures.util.Toaster;
import com.kakao.auth.AuthType;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.friends.AppFriendContext;
import com.kakao.friends.response.AppFriendsResponse;
import com.kakao.kakaotalk.callback.TalkResponseCallback;
import com.kakao.kakaotalk.v2.KakaoTalkService;
import com.kakao.network.ErrorResult;
import com.kakao.network.callback.ResponseCallback;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.exception.KakaoException;
import com.kakao.util.helper.log.Logger;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {
    private SessionCallback kakaoCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        intUI();
        initKakao();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Session.getCurrentSession().removeCallback(kakaoCallback);
    }

    @BindView(R.id.edt_id)
    BaseEditText ui_edtId;

    @BindView(R.id.edt_pwd)
    BaseEditText ui_edtPwd;

    @BindView(R.id.cb_auto_login)
    CheckBox ui_cbAutoLogin;

    public void intUI() {
        ui_edtId.setText("");
        ui_edtPwd.setText("");
        ui_cbAutoLogin.setChecked(false);
    }

    private void goMain() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }

    @OnClick(R.id.imv_id_delete)
    void OnClickIdDelete() {
        ui_edtId.setText("");
    }

    @OnClick(R.id.imv_pwd_delete)
    void OnClickPwdDelete() {
        ui_edtPwd.setText("");
    }

    @OnClick(R.id.txv_login)
    void OnClickLogin() {

        final String uid = ui_edtId.getText().toString();
        final String pwd = ui_edtPwd.getText().toString();

        if (uid.isEmpty()) {
            Toaster.showShort(this, R.string.str_id_hint);
            return;
        }

        if (pwd.isEmpty()) {
            Toaster.showShort(this, R.string.str_pwd_hint);
            return;
        }

        showProgress(this);
        Net.instance().api.signIn(LOGIN_ID, uid, pwd, DEVICE_ANDROID, MyInfo.getInstance().fcm_token)
                .enqueue(new Net.ResponseCallBack<MSign>() {
                    @Override
                    public void onSuccess(MSign response) {
                        super.onSuccess(response);
                        hideProgress();

                        MyInfo.getInstance().userInfo.info.id = response.id;
                        MyInfo.getInstance().userInfo.info.sess_token = response.token;
                        MyInfo.getInstance().userInfo.info.usr_id = uid;

                        if (ui_cbAutoLogin.isChecked()) {
                            MyInfo.getInstance().userInfo.info.login_type = LOGIN_ID;
                            MyInfo.getInstance().userInfo.info.pwd = pwd;
                        }

                        MyInfo.getInstance().save(LoginActivity.this);

                        goMain();
                    }

                    @Override
                    public void onFailure(MError response) {
                        super.onFailure(response);
                        hideProgress();
                        Toaster.showShort(LoginActivity.this, response.res_msg);
//                        if (BuildConfig.DEBUG) {
//                            MyInfo.getInstance().userInfo.info.id = 10;
//                            MyInfo.getInstance().userInfo.info.sess_token = "test";
//                            MyInfo.getInstance().userInfo.info.login_type = LOGIN_ID;
//                            MyInfo.getInstance().userInfo.info.usr_id = uid;
//                            MyInfo.getInstance().userInfo.info.pwd = pwd;
//
//                            MyInfo.getInstance().save(LoginActivity.this);
//
//                            goMain();
//                        }
                    }
                });
    }

    @OnClick(R.id.txv_idfind)
    void OnClickIdFind() {
        startActivity(new Intent(LoginActivity.this, IdFindActivity.class));
    }

    @OnClick(R.id.txv_pwdfind)
    void OnClickPwdFind() {
        startActivity(new Intent(LoginActivity.this, PwdFindActivity.class));
    }

    @OnClick(R.id.txv_register)
    void OnClickRegister() {
        startActivity(new Intent(LoginActivity.this, JoinAgreeActivity.class));
    }

    @OnClick(R.id.rly_kakao)
    void OnClickKakao() {
        // TODO: 카톡로그인진행
        String kakao_id = ((MyApplication) getApplication()).getKakaoId();
        String kakao_name = ((MyApplication) getApplication()).getKakaoName();
        if(kakao_id.isEmpty()){
            Session.getCurrentSession().open(AuthType.KAKAO_TALK, LoginActivity.this);
        } else {
            loginKakao(kakao_name, kakao_id);
        }
    }

    private void initKakao() {
        kakaoCallback = new SessionCallback();                  // 이 두개의 함수 중요함
        Session.getCurrentSession().addCallback(kakaoCallback);
        Session.getCurrentSession().checkAndImplicitOpen();
    }

    private void loginKakao(final String kakao_name, final String kakao_id) {
        final String pwd = "password";//ui_edtPwd.getText().toString();

        showProgress(this);
        Net.instance().api.signIn(LOGIN_KAKAO, kakao_id, pwd, DEVICE_ANDROID, MyInfo.getInstance().fcm_token)
                .enqueue(new Net.ResponseCallBack<MSign>() {
                    @Override
                    public void onSuccess(MSign response) {
                        super.onSuccess(response);
                        hideProgress();

                        MyInfo.getInstance().userInfo.info.id = response.id;
                        MyInfo.getInstance().userInfo.info.sess_token = response.token;
                        MyInfo.getInstance().userInfo.info.usr_id = response.usr_id;

                        if (ui_cbAutoLogin.isChecked()) {
                            MyInfo.getInstance().userInfo.info.login_type = LOGIN_KAKAO;
                            MyInfo.getInstance().userInfo.info.kt_id = kakao_id;
                            MyInfo.getInstance().userInfo.info.pwd = pwd;
                        }

                        MyInfo.getInstance().save(LoginActivity.this);

                        goMain();
                    }

                    @Override
                    public void onFailure(MError response) {
                        super.onFailure(response);
                        hideProgress();

                        Intent intent = new Intent(LoginActivity.this, JoinAgreeActivity.class);
                        startActivity(intent);
//                        Toaster.showShort(LoginActivity.this, response.res_msg);

                    }
                });

    }

    // 카톡로그인 콜백
    private class SessionCallback implements ISessionCallback {

        @Override
        public void onSessionOpened() {
            UserManagement.getInstance().me(new MeV2ResponseCallback() {
                @Override
                public void onFailure(ErrorResult errorResult) {
                    String message = "failed to get user info. msg=" + errorResult;
                    Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onSessionClosed(ErrorResult errorResult) {
                    Toast.makeText(LoginActivity.this, "SessionClosed", Toast.LENGTH_SHORT).show();
                }

//                @Override
//                public void onNotSignedUp() {
//                } // 카카오톡 회원이 아닐 시 showSignup(); 호출해야함

                @Override
                public void onSuccess(MeV2Response userProfile) {  //성공 시 userProfile 형태로 반환
                    Logger.d("UserProfile : " + userProfile);


                    final String kakao_id = "" + userProfile.getId();
//                    final String kakao_email = userProfile.getEmail() == null ? "" : userProfile.getEmail();
                    final String kakao_name = "" + userProfile.getNickname();

                    UserManagement.getInstance().requestLogout(null);

                    ((MyApplication)getApplication()).setKakaoInfo(kakao_id, kakao_name);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            loginKakao(kakao_name, kakao_id);
                        }
                    });
                }
            });
        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            if (exception != null) {
                Logger.e(exception);
            }
            Toast.makeText(getApplicationContext(), "로그인 도중 오류가 발생했습니다. 인터넷 연결을 확인해주세요: "+exception.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }
    }
}
