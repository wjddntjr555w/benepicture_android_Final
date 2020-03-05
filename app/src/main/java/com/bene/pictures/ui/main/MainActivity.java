package com.bene.pictures.ui.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.ImageView;

import com.bene.pictures.R;
import com.bene.pictures.data.MyInfo;
import com.bene.pictures.model.MAdver;
import com.bene.pictures.model.MBase;
import com.bene.pictures.model.MCert;
import com.bene.pictures.model.MError;
import com.bene.pictures.model.MUser;
import com.bene.pictures.model.MWholeReward;
import com.bene.pictures.net.Net;
import com.bene.pictures.ui.base.BaseActivity;
import com.bene.pictures.ui.dialog.ConfirmDialog1Activity;
import com.bene.pictures.ui.dialog.ConfirmDialogActivity;
import com.bene.pictures.ui.dialog.UserCheckActivity;
import com.bene.pictures.ui.login.LoginActivity;
import com.bene.pictures.ui.main.adver.ParingPuzzelAdverActivity;
import com.bene.pictures.ui.main.adver.SwitchingPuzzleAdverActivity;
import com.bene.pictures.ui.main.adver.VideoAdverViewActivity;
import com.bene.pictures.ui.main.friendlist.FriendListActivity;
import com.bene.pictures.ui.main.msgbox.MsgBoxActivity;
import com.bene.pictures.ui.main.mypage.MypageActivity;
import com.bene.pictures.ui.main.regadver.RegAdverActivity;
import com.bene.pictures.ui.main.takereward.TakeRewardActivity;
import com.bene.pictures.ui.main.winnner.WinnerCheckActivity;
import com.bene.pictures.ui.setting.AppAlarmSettingActivity;
import com.bene.pictures.ui.setting.ProfileSettingActivity;
import com.bene.pictures.ui.widget.BaseTextView;
import com.bene.pictures.util.PrefMgr;
import com.bene.pictures.util.Toaster;
import com.bene.pictures.util.Util;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.kakao.kakaolink.v2.KakaoLinkResponse;
import com.kakao.kakaolink.v2.KakaoLinkService;
import com.kakao.message.template.ButtonObject;
import com.kakao.message.template.ContentObject;
import com.kakao.message.template.FeedTemplate;
import com.kakao.message.template.LinkObject;
import com.kakao.network.ErrorResult;
import com.kakao.network.callback.ResponseCallback;
import com.kakao.util.helper.log.Logger;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static com.bene.pictures.MyApplication.g_isCallKakaoFriends;
import static com.bene.pictures.MyApplication.g_kakaoFriendList;
import static com.bene.pictures.fcm.FirebaseMessagingService.MSG_TYPE_ADMIN;
import static com.bene.pictures.fcm.FirebaseMessagingService.MSG_TYPE_APPLY;
import static com.bene.pictures.fcm.FirebaseMessagingService.MSG_TYPE_FRIEND;
import static com.bene.pictures.fcm.FirebaseMessagingService.MSG_TYPE_KEYWORD;
import static com.bene.pictures.fcm.FirebaseMessagingService.MSG_TYPE_NOTICE;
import static com.bene.pictures.fcm.FirebaseMessagingService.MSG_TYPE_PUZZLE;
import static com.bene.pictures.fcm.FirebaseMessagingService.MSG_TYPE_WIN;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();

        getWholeReward();
        mHandler.sendEmptyMessageDelayed(MSG_TIMER, 60000);

        updateMenu();

        getNewAdver();

        checkPush();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getProfile();
    }

    @BindView(R.id.dly_main)
    DrawerLayout ui_dlyMain;

    @BindView(R.id.imv_guide)
    ImageView ui_imvGuide;

    public void initUI() {

        updateGuideImage();
    }

    void checkPush() {
        SharedPreferences prefs = getSharedPreferences(PrefMgr.BENEPICTURE_PREFS,
                Context.MODE_PRIVATE);
        PrefMgr prefMgr = new PrefMgr(prefs);

        int push_type = prefMgr.getInt(PrefMgr.PUSH_TYPE, 0);

        if (push_type > 0) {

            // 푸시이력 포맷
            MyInfo.getInstance().savePush(this, 0, 0);

            Intent intent = null;
            switch (push_type) {
                case MSG_TYPE_KEYWORD:                 // 맞춤 키워드 알림
                case MSG_TYPE_PUZZLE:                  // 잔여 퍼즐 알림
                case MSG_TYPE_APPLY:                   // 응모 횟수 알림
                    break;
                case MSG_TYPE_FRIEND:                  // 친구 메시지 알림
                    intent = new Intent(this, FriendListActivity.class);
                    break;
                case MSG_TYPE_WIN:                     // 당첨 결과 알림
                    intent = new Intent(this, WinnerCheckActivity.class);
                    break;
                case MSG_TYPE_NOTICE:                  // 공지 알림
                case MSG_TYPE_ADMIN:                   // 관리자 메시지 알림
                    intent = new Intent(this, MsgBoxActivity.class);
                    break;
            }

            if (intent != null) {
                startActivity(intent);
            }
        }
    }

    @BindView(R.id.txv_whole_reward)
    BaseTextView ui_txvWholeReward; //총상금

    @BindView(R.id.txv_advercnt)
    BaseTextView ui_txvAdverCnt; //광고시청권

    @BindView(R.id.txv_subscribecnt)
    BaseTextView ui_txvSubscribeCnt; //응모권

    @SuppressLint("DefaultLocale")
    private void updateProfileUI() {
        //ui_txvWholeReward.setText(Util.makeMoneyType(MyInfo.getInstance().userInfo.info.whole_winning_money));
        ui_txvAdverCnt.setText(String.format("%02d", MyInfo.getInstance().userInfo.info.available_ad_count));
        ui_txvSubscribeCnt.setText(String.format("%02d", MyInfo.getInstance().userInfo.info.subscribe_count));
    }

    @BindView(R.id.imv_adver_thumb)
    ImageView ui_imvAdverThumb; //광고썸네일

    private MAdver.Info _adver;

    private void updateAdverUI() {

        if (_adver.ad_image.isEmpty()) {
            return;
        }

        Glide.with(this)
                .load(_adver.ad_image)
                .apply(new RequestOptions().fitCenter().error(R.drawable.bg1))
                .into(ui_imvAdverThumb);
    }

    private void updateGuideImage() {

        int wasGuideShow = prefMgr.getInt(PrefMgr.MAIN_GUIDE_SHOW, 0);
        if (wasGuideShow == 0) {
            ui_imvGuide.setVisibility(View.VISIBLE);
        } else {
            ui_imvGuide.setVisibility(View.GONE);
        }
    }

    @BindView(R.id.txv_usrname)
    BaseTextView ui_txvName;

    @BindView(R.id.txv_menu_version)
    BaseTextView ui_txvVersion;

    private void updateMenu() {

        if (MyInfo.getInstance().isValid()) {
            ui_txvName.setText(MyInfo.getInstance().userInfo.info.nickname);
        }

        String versionName = Util.getAppVersionName(this);
        ui_txvVersion.setText(versionName);

        updateGameAdSetting();

        updateVideoAdSetting();

        updateVibrateSetting();

        updateKeywordSetting();
    }

    void getProfile() {

        if (!MyInfo.getInstance().isValid()) {
            return;
        }

        showProgress(this);
        Net.instance().api.getProfile(MyInfo.getInstance().userInfo.info.id, MyInfo.getInstance().userInfo.info.sess_token)
                .enqueue(new Net.ResponseCallBack<MUser>() {
                    @Override
                    public void onSuccess(MUser response) {
                        super.onSuccess(response);
                        hideProgress();
                        MyInfo.getInstance().userInfo.info = response.info;

                        updateProfileUI();
                        updateMenu();
                    }

                    @Override
                    public void onFailure(MError response) {
                        super.onFailure(response);
                        hideProgress();
                        showErrorMsg(response);
//                        if (BuildConfig.DEBUG) {
//                            MyInfo.getInstance().userInfo.info.whole_winning_money = 486549663;
//                            MyInfo.getInstance().userInfo.info.nickname = "당첨이";
//                            MyInfo.getInstance().userInfo.info.available_ad_count = 4;
//                            MyInfo.getInstance().userInfo.info.subscribe_count = 11;
//                            MyInfo.getInstance().userInfo.info.agree_adkeyword = 1;
//                            MyInfo.getInstance().userInfo.info.agree_vibrate = 0;
//                            MyInfo.getInstance().userInfo.info.alarm_friend_msg = 1;
//                            MyInfo.getInstance().userInfo.info.alarm_puzzle = 0;
//                            MyInfo.getInstance().userInfo.info.alarm_notice = 1;
//
//                            updateProfileUI();
//                            updateMenu();
//                        }
                    }
                });
    }

    void getNewAdver() {

        if (!MyInfo.getInstance().isValid()) {
            return;
        }

        //showProgress(this);
        Net.instance().api.getAdver(MyInfo.getInstance().userInfo.info.id, MyInfo.getInstance().userInfo.info.sess_token)
                .enqueue(new Net.ResponseCallBack<MAdver>() {
                    @Override
                    public void onSuccess(MAdver response) {
                        super.onSuccess(response);
                        //hideProgress();

                        _adver = response.info;
                        if (!_adver.url.startsWith("http")) {
                            _adver.url = "http://" + _adver.url;
                        }

                        updateAdverUI();
                    }

                    @Override
                    public void onFailure(MError response) {
                        super.onFailure(response);
                        ///hideProgress();

//                        if (BuildConfig.DEBUG) {
//
//                            _adver = new MAdver.Info();
//                            _adver.ad_image = DEV_SERVER_URL + "test_17.jpg";
//                            _adver.ad_type = 3;
//                            _adver.id = 1;
//
//                            updateAdverUI();
//                        }
                    }
                });
    }

    private void reqAlarmSet(final String kind, final int value) {

        Net.instance().api.reqAlarmSet(MyInfo.getInstance().userInfo.info.id, kind, value)
                .enqueue(new Net.ResponseCallBack<MBase>() {
                    @Override
                    public void onSuccess(MBase response) {
                        super.onSuccess(response);

                        String msg = "";
                        switch (kind) {
                            case "gemead":
                                msg = "게임광고가 " + (value == 1 ? "활성화" : "비활성화") + "되었습니다.";
                                break;
                            case "videoad":
                                msg = "영상광고가 " + (value == 1 ? "활성화" : "비활성화") + "되었습니다.";
                                break;
                            case "adkeyword":
                                msg = "맞춤 키워드 자동등록이 " + (value == 1 ? "활성화" : "비활성화") + "되었습니다.";
                                break;
                            case "vibrate":
                                msg = "진동이 " + (value == 1 ? "활성화" : "비활성화") + "되었습니다.";
                                break;
                            case "winningresult":
                                msg = "당첨결과 알림이 " + (value == 1 ? "활성화" : "비활성화") + "되었습니다.";
                                break;
                            case "friendmsg":
                                msg = "친구 메시지알림이 " + (value == 1 ? "활성화" : "비활성화") + "되었습니다.";
                                break;
                            case "notice":
                                msg = "공지사항 알림이 " + (value == 1 ? "활성화" : "비활성화") + "되었습니다.";
                                break;
                            case "puzzle":
                                msg = "잔여Puzzle 알림이 " + (value == 1 ? "활성화" : "비활성화") + "되었습니다.";
                                break;
                            default:
                                msg = "게임광고가 " + (value == 1 ? "활성화" : "비활성화") + "되었습니다.";
                        }

                        Toaster.showShort(MainActivity.this, msg);
                    }

                    @Override
                    public void onFailure(MError response) {
                        super.onFailure(response);
                        showErrorMsg(response);
                    }
                });
    }

    void viewAdvertise() {
        updateProfileUI();

        go2AdPage();

//        showProgress(this);
//        Net.instance().api.viewAd(MyInfo.getInstance().userInfo.info.id, _adver.id, _adver.log)
//                .enqueue(new Net.ResponseCallBack<MBase>() {
//                    @Override
//                    public void onSuccess(MBase response) {
//                        super.onSuccess(response);
//                        hideProgress();
//
//                        updateProfileUI();
//
//                        go2AdPage();
//                    }
//
//                    @Override
//                    public void onFailure(MError response) {
//                        super.onFailure(response);
//                        hideProgress();
//                        showErrorMsg(response);
//                    }
//                });
    }

    void reqAdComplete(int urlClicked) {
        showProgress(this);
        Net.instance().api.reqAdComplelte(MyInfo.getInstance().userInfo.info.id, _adver.id, _adver.log, urlClicked)
                .enqueue(new Net.ResponseCallBack<MBase>() {
                    @Override
                    public void onSuccess(MBase response) {
                        super.onSuccess(response);
                        hideProgress();

                        // fixed by Gambler 2019-10-24
                        // 광고시청완료시 설정한 광고적립금을 실시간으로 반영되도록 수정
                        // 회원의 총상금과 응모권갯수를 리셋
                        getProfile();
                    }

                    @Override
                    public void onFailure(MError response) {
                        super.onFailure(response);
                        hideProgress();
                        showErrorMsg(response);
                    }
                });
    }

    void go2AdPage() {

        if (_adver.id < 1) {
            return;
        }

        Intent intent = null;

        switch (_adver.ad_type) {
            case 1: //video
                intent = new Intent(MainActivity.this, VideoAdverViewActivity.class);
                break;

            case 2: //switching
                intent = new Intent(MainActivity.this, SwitchingPuzzleAdverActivity.class);
                break;

            case 3: //paring
                intent = new Intent(MainActivity.this, ParingPuzzelAdverActivity.class);
                break;
        }

        if (intent != null) {
            intent.putExtra("adver", _adver);
            startActivityForResult(intent, 2048);
        }
    }

    @BindView(R.id.imv_gamead_setting)
    ImageView ui_imvGameAdSetting;

    private void updateGameAdSetting() {

        if (MyInfo.getInstance().userInfo.info.agree_gamead == 1) {
            ui_imvGameAdSetting.setBackgroundResource(R.drawable.icon18_on);
        } else {
            ui_imvGameAdSetting.setBackgroundResource(R.drawable.icon18);
        }
    }

    @BindView(R.id.imv_videoad_setting)
    ImageView ui_imvVideoAdSetting;

    private void updateVideoAdSetting() {

        if (MyInfo.getInstance().userInfo.info.agree_videoad == 1) {
            ui_imvVideoAdSetting.setBackgroundResource(R.drawable.icon18_on);
        } else {
            ui_imvVideoAdSetting.setBackgroundResource(R.drawable.icon18);
        }
    }

    @BindView(R.id.imv_vibrate_setting)
    ImageView ui_imvVibrateSetting;

    private void updateVibrateSetting() {

        if (MyInfo.getInstance().userInfo.info.agree_vibrate == 1) {
            ui_imvVibrateSetting.setBackgroundResource(R.drawable.icon18_on);
        } else {
            ui_imvVibrateSetting.setBackgroundResource(R.drawable.icon18);
        }
    }

    @BindView(R.id.imv_keyword_setting)
    ImageView ui_imvKeywordSetting;

    private void updateKeywordSetting() {

        if (MyInfo.getInstance().userInfo.info.agree_adkeyword == 1) {
            ui_imvKeywordSetting.setBackgroundResource(R.drawable.icon18_on);
        } else {
            ui_imvKeywordSetting.setBackgroundResource(R.drawable.icon18);
        }
    }

    @Override
    public void onBackPressed() {

        if (ui_dlyMain.isDrawerOpen(GravityCompat.END)) {
            ui_dlyMain.closeDrawer(GravityCompat.END);
            return;
        }

        super.onBackPressed();
    }

    @OnClick(R.id.imv_menu)
    void onClickMenu() {
        ui_dlyMain.openDrawer(GravityCompat.END);
    }

    @OnClick(R.id.rly_drawer)
    void OnClickDrawerBg() {
        ui_dlyMain.closeDrawer(GravityCompat.END);
    }

    @OnClick(R.id.imv_help)
    void OnClickHelp() {
        ui_imvGuide.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.imv_guide)
    void OnClickGuide() {
        prefMgr.put(PrefMgr.MAIN_GUIDE_SHOW, 1);
        updateGuideImage();
    }

    @OnClick(R.id.fly_gamead_setting_bg)
    void OnClickGameAdSetting() {
        if (MyInfo.getInstance().userInfo.info.agree_videoad == 0 && MyInfo.getInstance().userInfo.info.agree_gamead == 1) {
            Toaster.showShort(MainActivity.this, "게임과 영상을 모두 비활성화 할 수 없습니다.");
            return;
        }

        if (MyInfo.getInstance().userInfo.info.agree_gamead == 1) {
            MyInfo.getInstance().userInfo.info.agree_gamead = 0;
        } else {
            MyInfo.getInstance().userInfo.info.agree_gamead = 1;
        }

        updateGameAdSetting();
        reqAlarmSet("gamead", MyInfo.getInstance().userInfo.info.agree_gamead);
    }

    @OnClick(R.id.fly_videoad_setting_bg)
    void OnClickVideoAdSetting() {
        if (MyInfo.getInstance().userInfo.info.agree_videoad == 1 && MyInfo.getInstance().userInfo.info.agree_gamead == 0) {
            Toaster.showShort(MainActivity.this, "게임과 영상을 모두 비활성화 할 수 없습니다.");
            return;
        }

        if (MyInfo.getInstance().userInfo.info.agree_videoad == 1) {
            MyInfo.getInstance().userInfo.info.agree_videoad = 0;
        } else {
            MyInfo.getInstance().userInfo.info.agree_videoad = 1;
        }

        updateVideoAdSetting();
        reqAlarmSet("videoad", MyInfo.getInstance().userInfo.info.agree_videoad);
    }

    @OnClick(R.id.fly_vibrate_setting_bg)
    void OnClickVibrateSetting() {
        if (MyInfo.getInstance().userInfo.info.agree_vibrate == 1) {
            MyInfo.getInstance().userInfo.info.agree_vibrate = 0;
        } else {
            MyInfo.getInstance().userInfo.info.agree_vibrate = 1;
        }

        updateVibrateSetting();
        reqAlarmSet("vibrate", MyInfo.getInstance().userInfo.info.agree_vibrate);
    }

    @OnClick(R.id.fly_keyword_setting_bg)
    void OnClickKeywordSetting() {
        if (MyInfo.getInstance().userInfo.info.agree_adkeyword == 1) {
            MyInfo.getInstance().userInfo.info.agree_adkeyword = 0;
        } else {
            MyInfo.getInstance().userInfo.info.agree_adkeyword = 1;
        }

        updateKeywordSetting();
        reqAlarmSet("adkeyword", MyInfo.getInstance().userInfo.info.agree_adkeyword);
    }

    @OnClick(R.id.fly_appalarm_setting_bg)
    void OnClickAppalarmSetting() {
        startActivity(new Intent(MainActivity.this, AppAlarmSettingActivity.class));
        ui_dlyMain.closeDrawer(GravityCompat.END);
    }

    @OnClick(R.id.fly_msgbox_setting_bg)
    void OnClickMsgBoxSetting() {
        startActivity(new Intent(MainActivity.this, MsgBoxActivity.class));
        ui_dlyMain.closeDrawer(GravityCompat.END);
    }

    @OnClick(R.id.fly_friends_setting_bg)
    void OnClickFriendsSetting() {
        startActivity(new Intent(MainActivity.this, FriendListActivity.class));
        ui_dlyMain.closeDrawer(GravityCompat.END);
    }

    @OnClick(R.id.fly_profile_setting_bg)
    void OnClickProfileSetting() {
        if (!MyInfo.getInstance().isValid()) {
            return;
        }

//        if (MyInfo.getInstance().userInfo.info.login_type == LOGIN_KAKAO) {
//            startActivity(new Intent(MainActivity.this, ProfileSettingActivity.class));
//        } else {
            Intent intent = new Intent((MainActivity.this), UserCheckActivity.class);
            startActivityForResult(intent, 1016);
//        }

        ui_dlyMain.closeDrawer(GravityCompat.END);
    }

    @OnClick(R.id.fly_adver_request)
    void OnClickAdRequest() {
        startActivity(new Intent(MainActivity.this, RegAdverActivity.class));
        ui_dlyMain.closeDrawer(GravityCompat.END);
    }

    @OnClick(R.id.fly_share)
    void OnShare() {
        final String appPackageName = getPackageName();
        final String img_url = "http://15.164.112.250/upload/kakao_icon.png";
        String app_link = "https://play.google.com/store/apps/details?id=" + appPackageName;

        String title = "베네픽쳐";
        String content = "베네픽쳐에 초대합니다.";

        FeedTemplate params = FeedTemplate
                .newBuilder(ContentObject.newBuilder(title,
                        img_url,
                        LinkObject.newBuilder().setWebUrl(app_link)
                                .setMobileWebUrl(app_link).build())
                        .setDescrption(content)
                        .build())
                .addButton(new ButtonObject("사용하기", LinkObject.newBuilder().setWebUrl(app_link).setMobileWebUrl(app_link).build()))
                .build();

//        KakaoLinkService.getInstance().sendDefault(this, params, callback);

//        Map<String, String> serverCallbackArgs = new HashMap<String, String>();
//        serverCallbackArgs.put("user_id", MyInfo.getInstance().userInfo.info.kt_id);
//        serverCallbackArgs.put("product_id", friend_kt_id);

        KakaoLinkService.getInstance().sendDefault(this, params, new ResponseCallback<KakaoLinkResponse>() {
            @Override
            public void onFailure(ErrorResult errorResult) {
                Logger.e(errorResult.toString());
            }

            @Override
            public void onSuccess(KakaoLinkResponse result) {
                // 템플릿 밸리데이션과 쿼터 체크가 성공적으로 끝남. 톡에서 정상적으로 보내졌는지 보장은 할 수 없다. 전송 성공 유무는 서버콜백 기능을 이용하여야 한다.
            }
        });
    }

    @OnClick(R.id.fly_logout)
    void OnClickLogout() {
        MyInfo.getInstance().init(this);
        g_kakaoFriendList.clear();
        g_isCallKakaoFriends = false;

        // kakao FriendList 초기화
        SharedPreferences prefs = getSharedPreferences(PrefMgr.BENEPICTURE_PREFS,
                Context.MODE_PRIVATE);
        PrefMgr prefMgr = new PrefMgr(prefs);
        prefMgr.put(PrefMgr.IS_CALL_KAKAO_FRIEND, false);
        prefMgr.put(PrefMgr.KAKAO_FRIEND, "");
        //

        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @OnClick(R.id.imv_adver_thumb)
    void OnClickAdverThumb() {
        if (_adver.id < 1) {
            return;
        }

        // 일일광고시청가능횟수가 충분한지 체크
        if (MyInfo.getInstance().userInfo.info.available_ad_count < 1) {
            String strTitle = "남아있는 퍼즐이 없습니다. 친구를 초대하고 선물을 통해 퍼즐을 획득하세요!";

            Intent showConfirm = new Intent(MainActivity.this, ConfirmDialogActivity.class);
            showConfirm.putExtra("content", strTitle);
            showConfirm.putExtra("span_start", 31);
            showConfirm.putExtra("span_end", 33);
            showConfirm.putExtra("no", "선물하기");
            showConfirm.putExtra("yes", "초대하기");
            startActivityForResult(showConfirm, 1003);
        }

        if (MyInfo.getInstance().userInfo.info.available_ad_count > 0) {
            String strTitle = "광고를 시청하시겠습니까?\n광고시청 시 퍼즐이 줄어듭니다.";

            Intent showConfirm = new Intent(MainActivity.this, ConfirmDialogActivity.class);
            showConfirm.putExtra("content", strTitle);
            showConfirm.putExtra("span_start", 21);
            showConfirm.putExtra("span_end", 23);
            showConfirm.putExtra("no", "닫기");
            showConfirm.putExtra("yes", "시청하기");
            startActivityForResult(showConfirm, 1001);
        }
    }

    @OnClick(R.id.rly_advercnt_bg)
    void OnClickAdverCntBg() {
    }

    void showAnswerPopup() {
//        String strTitle = "[" + _adver.name + "]" + "에서 당신에게 응모권을 선물하였습니다. ";
//
//        Intent showConfirm = new Intent(MainActivity.this, ConfirmDialogActivity.class);
//        showConfirm.putExtra("content", strTitle);
//        showConfirm.putExtra("span_start", 1);
//        showConfirm.putExtra("span_end", _adver.name.length() + 1);
//        showConfirm.putExtra("no", "닫기");
//        showConfirm.putExtra("yes", "보러가기");
//        startActivityForResult(showConfirm, 1005);
        String strTitle = "[" + _adver.name + "]" + "에서 당신에게 응모권을";

        Intent showConfirm = new Intent(MainActivity.this, ConfirmDialog1Activity.class);
        showConfirm.putExtra("content", strTitle);
        showConfirm.putExtra("content1", "선물하였습니다.");
        showConfirm.putExtra("span_start", 1);
        showConfirm.putExtra("span_end", _adver.name.length() + 1);
        showConfirm.putExtra("no", "닫기");
        showConfirm.putExtra("yes", "보러가기");
        startActivityForResult(showConfirm, 1005);

    }

    @OnClick(R.id.rly_subscribecnt_bg)
    void OnClickSubscribeCntBg() {
    }

    @OnClick(R.id.imv_bot_mypage)
    void OnClickMyPage() {
        startActivity(new Intent(MainActivity.this, MypageActivity.class));
    }

    @OnClick(R.id.imv_bot_winners)
    void OnClickWinners() {
        startActivity(new Intent(MainActivity.this, WinnerCheckActivity.class));
    }

    @OnClick(R.id.imv_bot_takereward)
    void OnClickTakeReward() {
        startActivity(new Intent(MainActivity.this, TakeRewardActivity.class));
    }

    @OnClick(R.id.imv_bot_reqadver)
    void OnClickReqAdver() {
        startActivity(new Intent(MainActivity.this, RegAdverActivity.class));
    }

    @OnClick(R.id.imv_bot_friends)
    void OnClickFriends() {
        startActivity(new Intent(MainActivity.this, FriendListActivity.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 1001:

                if (resultCode == RESULT_OK) {
                    viewAdvertise();
                }

                break;

            case 1003:

//                if (resultCode == RESULT_OK) {
                // 친구초대/선물하기 페이지로 이행
                Intent intent = new Intent(MainActivity.this, FriendListActivity.class);
                intent.putExtra("tab", resultCode == RESULT_CANCELED ? 0 : 1);
                startActivity(intent);
//                }

                break;

            case 1005:

                if (resultCode == RESULT_OK) {
                    // 응모권 보러 가기
//                    startActivity(new Intent(MainActivity.this, MypageActivity.class));

                    // fixed by Gambler 2019-10-13 보러가기 클릭시 광고에서 설정해둔 링크로 이동하도록 수정

                    try {
                        reqAdComplete(1);
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(_adver.url)));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    reqAdComplete(0);
                }

                break;

            case 2048:
                if (resultCode == RESULT_OK) {
                    showAnswerPopup();
                    getNewAdver();
                } else {
                    Toaster.showShort(MainActivity.this, "광고가 중단되어 응모권이 적립되지 않았습니다.");
                }
                break;

            case 1016:
                if (resultCode == RESULT_OK) {
                    startActivity(new Intent(MainActivity.this, ProfileSettingActivity.class));
                }
                break;
        }
    }

    private void getWholeReward(){
        showProgress(this);
        Net.instance().api.getWholeReward()
                .enqueue(new Net.ResponseCallBack<MWholeReward>() {
                    @Override
                    public void onSuccess(MWholeReward response) {
                        super.onSuccess(response);
                        hideProgress();

                        ui_txvWholeReward.setText(Util.makeMoneyType(response.whole_reward));
                    }

                    @Override
                    public void onFailure(MError response) {
                        super.onFailure(response);
                        hideProgress();
                        showErrorMsg(response);
                    }
                });

    }

    private static final int MSG_TIMER = 0;
    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == MSG_TIMER) {
                getWholeReward();
                this.sendEmptyMessageDelayed(MSG_TIMER, 60000);
            }
        }
    };


    @Override
    protected void onDestroy() {
        mHandler.removeMessages(MSG_TIMER);
        super.onDestroy();
    }
}
