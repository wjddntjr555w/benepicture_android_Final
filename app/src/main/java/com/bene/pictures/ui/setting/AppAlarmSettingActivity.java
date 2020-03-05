package com.bene.pictures.ui.setting;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.ImageView;

import com.bene.pictures.R;
import com.bene.pictures.data.MyInfo;
import com.bene.pictures.model.MBase;
import com.bene.pictures.model.MError;
import com.bene.pictures.net.Net;
import com.bene.pictures.ui.base.BaseActivity;
import com.bene.pictures.ui.widget.BaseTextView;
import com.bene.pictures.util.Toaster;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.OnClick;

public class AppAlarmSettingActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_alarm_setting);

        initUI();
    }

    public void initUI() {

        updateWinningResultSetting();

        updateFriendsMsgSetting();

        updateNoticeSetting();

        updatePuzzleSetting();

        updateAlarmAll();

    }

    @BindView(R.id.imv_winning_result_setting)
    ImageView ui_imvWinningResultSetting;

    private void updateWinningResultSetting() {

        if (MyInfo.getInstance().userInfo.info.alarm_wining_result == 1) {
            ui_imvWinningResultSetting.setBackgroundResource(R.drawable.icon18_on);
        } else {
            ui_imvWinningResultSetting.setBackgroundResource(R.drawable.icon18);
        }
    }

    @BindView(R.id.imv_friends_msg_setting)
    ImageView ui_imvFriendsMsgSetting;

    private void updateFriendsMsgSetting() {

        if (MyInfo.getInstance().userInfo.info.alarm_friend_msg == 1) {
            ui_imvFriendsMsgSetting.setBackgroundResource(R.drawable.icon18_on);
        } else {
            ui_imvFriendsMsgSetting.setBackgroundResource(R.drawable.icon18);
        }
    }

    @BindView(R.id.imv_notice_setting)
    ImageView ui_imvNoticeSetting;

    private void updateNoticeSetting() {

        if (MyInfo.getInstance().userInfo.info.alarm_notice == 1) {
            ui_imvNoticeSetting.setBackgroundResource(R.drawable.icon18_on);
        } else {
            ui_imvNoticeSetting.setBackgroundResource(R.drawable.icon18);
        }
    }

    @BindView(R.id.imv_puzzle_setting)
    ImageView ui_imvPuzzleSetting;
    @BindView(R.id.txv_puzzle_time)
    BaseTextView ui_txvPuzzleTime;

    @SuppressLint("DefaultLocale")
    private void updatePuzzleSetting() {
        ui_txvPuzzleTime.setText(String.format("%d시", MyInfo.getInstance().userInfo.info.puzzle_time));

        if (MyInfo.getInstance().userInfo.info.alarm_puzzle == 1) {
            ui_imvPuzzleSetting.setBackgroundResource(R.drawable.icon18_on);
        } else {
            ui_imvPuzzleSetting.setBackgroundResource(R.drawable.icon18);
        }
    }

    @BindArray(R.array.time)
    String[] timeList;

    @OnClick(R.id.txv_puzzle_time)
    void OnClickBank() {
        String bankName = ui_txvPuzzleTime.getText().toString();
        int selectedPos = 0;

        if (!bankName.isEmpty()) {
            for (int i = 0; i < timeList.length; i++) {
                if (timeList[i].equals(bankName)) {
                    selectedPos = i;
                    break;
                }
            }
        }

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setSingleChoiceItems(timeList, selectedPos, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ui_txvPuzzleTime.setText(timeList[which]);
                MyInfo.getInstance().userInfo.info.puzzle_time = Integer.valueOf(timeList[which].replace("시", ""));
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @BindView(R.id.imv_alarm_all)
    ImageView ui_imvAlarmAll;
    int alarmAllSetting = 0;

    private void updateAlarmAll() {
        alarmAllSetting = MyInfo.getInstance().userInfo.info.alarm_wining_result *
                MyInfo.getInstance().userInfo.info.alarm_friend_msg *
                MyInfo.getInstance().userInfo.info.alarm_notice *
                MyInfo.getInstance().userInfo.info.alarm_puzzle;

        if (alarmAllSetting == 1) {
            ui_imvAlarmAll.setBackgroundResource(R.drawable.icon18_on);
        } else {
            ui_imvAlarmAll.setBackgroundResource(R.drawable.icon18);
        }
    }

    private void reqAlarmSet(String kind, int value) {

        Net.instance().api.reqAlarmSet(MyInfo.getInstance().userInfo.info.id, kind, value)
                .enqueue(new Net.ResponseCallBack<MBase>() {
                    @Override
                    public void onSuccess(MBase response) {
                        super.onSuccess(response);
                    }

                    @Override
                    public void onFailure(MError response) {
                        super.onFailure(response);
                    }
                });
    }

    @OnClick(R.id.imv_winning_result_setting)
    void OnClickWinningResult() {

        if (MyInfo.getInstance().userInfo.info.alarm_wining_result == 1) {
            MyInfo.getInstance().userInfo.info.alarm_wining_result = 0;
        } else {
            MyInfo.getInstance().userInfo.info.alarm_wining_result = 1;
        }

        updateWinningResultSetting();
        updateAlarmAll();
    }

    @OnClick(R.id.imv_friends_msg_setting)
    void OnClickFriendsMsg() {

        if (MyInfo.getInstance().userInfo.info.alarm_friend_msg == 1) {
            MyInfo.getInstance().userInfo.info.alarm_friend_msg = 0;
        } else {
            MyInfo.getInstance().userInfo.info.alarm_friend_msg = 1;
        }

        updateFriendsMsgSetting();
        updateAlarmAll();
    }

    @OnClick(R.id.imv_notice_setting)
    void OnClickNotice() {

        if (MyInfo.getInstance().userInfo.info.alarm_notice == 1) {
            MyInfo.getInstance().userInfo.info.alarm_notice = 0;
        } else {
            MyInfo.getInstance().userInfo.info.alarm_notice = 1;
        }

        updateNoticeSetting();
        updateAlarmAll();
    }

    @OnClick(R.id.imv_puzzle_setting)
    void OnClickPuzzle() {

        if (MyInfo.getInstance().userInfo.info.alarm_puzzle == 1) {
            MyInfo.getInstance().userInfo.info.alarm_puzzle = 0;
        } else {
            MyInfo.getInstance().userInfo.info.alarm_puzzle = 1;
        }

        updatePuzzleSetting();
        updateAlarmAll();
    }

    @OnClick(R.id.imv_alarm_all)
    void OnClickAll() {
        alarmAllSetting = 1 - alarmAllSetting;

        MyInfo.getInstance().userInfo.info.alarm_wining_result = alarmAllSetting;
        MyInfo.getInstance().userInfo.info.alarm_friend_msg = alarmAllSetting;
        MyInfo.getInstance().userInfo.info.alarm_notice = alarmAllSetting;
        MyInfo.getInstance().userInfo.info.alarm_puzzle = alarmAllSetting;

        updateFriendsMsgSetting();
        updateNoticeSetting();
        updateWinningResultSetting();
        updatePuzzleSetting();
        updateAlarmAll();
    }

    @OnClick(R.id.txv_setting)
    void OnClickChange() {

        reqAlarmSet("winningresult", MyInfo.getInstance().userInfo.info.alarm_wining_result);
        reqAlarmSet("friendmsg", MyInfo.getInstance().userInfo.info.alarm_friend_msg);
        reqAlarmSet("notice", MyInfo.getInstance().userInfo.info.alarm_notice);
        reqAlarmSet("puzzle", MyInfo.getInstance().userInfo.info.alarm_puzzle);
        reqAlarmSet("puzzle_time", MyInfo.getInstance().userInfo.info.puzzle_time);

        Toaster.showShort(this, "저장되었습니다.");
        finish();
    }

    @OnClick(R.id.imv_back)
    void OnClickBack() {
        finish();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
