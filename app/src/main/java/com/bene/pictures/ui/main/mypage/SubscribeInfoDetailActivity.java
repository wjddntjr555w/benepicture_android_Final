package com.bene.pictures.ui.main.mypage;

import android.content.Intent;
import android.os.Bundle;

import com.bene.pictures.R;
import com.bene.pictures.model.MSubscribeInfoList;
import com.bene.pictures.ui.base.BaseActivity;
import com.bene.pictures.ui.widget.BaseTextView;
import com.bene.pictures.util.Util;

import butterknife.BindView;
import butterknife.OnClick;

//import org.cybergarage.util.StringUtil;

public class SubscribeInfoDetailActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscribeinfo_detail);

        initUI();
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.fade, R.anim.hold);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.hold, R.anim.fadeout);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

    @BindView(R.id.txv_number)
    BaseTextView ui_txvNumber;

    @BindView(R.id.txv_createdate)
    BaseTextView ui_txvCreateDate;

    @BindView(R.id.txv_funddate)
    BaseTextView ui_txvFundDate;

    @BindView(R.id.txv_takedate)
    BaseTextView ui_txvTakeDate;

    @BindView(R.id.txv_takemoney)
    BaseTextView ui_txvTakeMoney;

    MSubscribeInfoList.Info info = null;

    public void initUI() {

        info = getIntent().getParcelableExtra("subscribe_info");

        if (info.is_admin == 1) {
            ui_txvNumber.setText("응모권 번호 : " + "[운영자 선물]" + info.subscribe_number);
        } else {
            ui_txvNumber.setText("응모권 번호 : " + info.subscribe_adname + info.subscribe_number);
        }

        ui_txvCreateDate.setText("생성일 : " + info.create_datetime);
        ui_txvFundDate.setText("적립 : " + info.fund_datetime);
        ui_txvTakeDate.setText("수령 : " + info.take_datetime);
        ui_txvTakeMoney.setText("당첨내역 : " + (info.is_winning == 1 ? "당첨 (" + Util.makeMoneyType(info.winning_money) + "원)" : "미당첨"));
    }

    @OnClick(R.id.txv_take)
    void OnClickTake() {
        if (info.is_winning == 1) {
            setResult(RESULT_OK);
        } else {
            setResult(RESULT_CANCELED);
        }

        finish();
    }

    @OnClick(R.id.txv_close)
    void OnClickClose() {
        setResult(RESULT_CANCELED);
        finish();
    }

    @OnClick(R.id.fly_bg)
    void OnClickWholeBg() {
        setResult(RESULT_CANCELED);
        finish();
    }

    @OnClick(R.id.lly_bg)
    void OnClickBg() {

    }
}
