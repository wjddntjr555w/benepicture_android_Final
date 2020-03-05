package com.bene.pictures.ui.main.takereward;

import android.content.Intent;
import android.os.Bundle;

import com.bene.pictures.R;
import com.bene.pictures.ui.base.BaseActivity;
import com.bene.pictures.ui.widget.BaseTextView;

import butterknife.BindView;
import butterknife.OnClick;

public class ReviewDetailActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviewdetail_dialog);

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

    String _strTitle;
    String _strContent;
    String _strDate;
    String _strMoney;

    @BindView(R.id.txv_title)
    BaseTextView ui_txvTitle;

    @BindView(R.id.txv_content)
    BaseTextView ui_txvContent;

    @BindView(R.id.txv_datetime)
    BaseTextView ui_txvDateTime;

    @BindView(R.id.txv_money)
    BaseTextView ui_txvMoney;

    public void initUI() {

        _strTitle = getIntent().getStringExtra("title");
        _strContent = getIntent().getStringExtra("content");
        _strDate = getIntent().getStringExtra("date");
        _strMoney = getIntent().getStringExtra("money");

        ui_txvTitle.setText(_strTitle);
        ui_txvContent.setText(_strContent);
        ui_txvDateTime.setText(_strDate);
        ui_txvMoney.setText(_strMoney);
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        finish();
    }

    @OnClick(R.id.fly_bg)
    void OnClickWholeBg() {
        setResult(RESULT_OK);
        finish();
    }

    @OnClick(R.id.lly_bg)
    void OnClickBg() {
        setResult(RESULT_OK);
        finish();
    }
}
