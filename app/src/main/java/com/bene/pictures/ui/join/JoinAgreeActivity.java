package com.bene.pictures.ui.join;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.bene.pictures.R;
import com.bene.pictures.model.MError;
import com.bene.pictures.model.MJoinAgree;
import com.bene.pictures.model.MVersion;
import com.bene.pictures.net.Net;
import com.bene.pictures.ui.base.BaseActivity;
import com.bene.pictures.ui.widget.BaseTextView;
import com.bene.pictures.util.Toaster;

import butterknife.BindView;
import butterknife.OnClick;

public class JoinAgreeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_agree);

        initUI();

        getAgreeData();
    }

    @BindView(R.id.scv_useterm_bg)
    ScrollView ui_scvUsetermBg;

    @BindView(R.id.scv_policy_bg)
    ScrollView ui_scvPolicyBg;

    @BindView(R.id.scv_adver_bg)
    ScrollView ui_scvAdverBg;

    @BindView(R.id.imv_agree_all)
    ImageView ui_imvAgreeAll;

    @BindView(R.id.imv_agree_useterm)
    ImageView ui_imvAgreeUseterm;

    @BindView(R.id.txv_useterm)
    BaseTextView ui_txvUseTerm;

    @BindView(R.id.imv_agree_policy)
    ImageView ui_imvAgreePolicy;

    @BindView(R.id.txv_policy)
    BaseTextView ui_txvPolicy;

    @BindView(R.id.imv_agree_adver)
    ImageView ui_imvAgreeAdver;

    @BindView(R.id.txv_adver)
    BaseTextView ui_txvAdver;

    public void initUI() {
        ui_scvUsetermBg.setNestedScrollingEnabled(true);
        ui_scvPolicyBg.setNestedScrollingEnabled(true);
        ui_scvAdverBg.setNestedScrollingEnabled(true);

        updateUseTerm();
        updatePolicy();
        updateAdver();
        updateAgreeAll();
    }

    private void updateAgreeAll() {
        if (_nAgreeUseTerm == 1 && _nAgreePolicy == 1 && _nAgreeAdver == 1) {
            ui_imvAgreeAll.setBackgroundResource(R.drawable.icon06);
        } else {
            ui_imvAgreeAll.setBackgroundResource(R.drawable.icon05);
        }
    }

    private void updateUseTerm() {
        if (_nAgreeUseTerm == 1) {
            ui_imvAgreeUseterm.setBackgroundResource(R.drawable.icon06);
        } else {
            ui_imvAgreeUseterm.setBackgroundResource(R.drawable.icon05);
        }
    }

    private void updatePolicy() {
        if (_nAgreePolicy == 1) {
            ui_imvAgreePolicy.setBackgroundResource(R.drawable.icon06);
        } else {
            ui_imvAgreePolicy.setBackgroundResource(R.drawable.icon05);
        }
    }

    private void updateAdver() {
        if (_nAgreeAdver == 1) {
            ui_imvAgreeAdver.setBackgroundResource(R.drawable.icon06);
        } else {
            ui_imvAgreeAdver.setBackgroundResource(R.drawable.icon05);
        }
    }

    private void getAgreeData() {

        Net.instance().api.getJoinAgreeInfo(DEVICE_ANDROID)
                .enqueue(new Net.ResponseCallBack<MJoinAgree>() {
                    @Override
                    public void onSuccess(MJoinAgree response) {
                        super.onSuccess(response);

                        ui_txvUseTerm.setText(response.useterm);
                        ui_txvPolicy.setText(response.privacy);
                        ui_txvAdver.setText(response.adver);
                    }

                    @Override
                    public void onFailure(MError response) {
                        super.onFailure(response);
                        showErrorMsg(response);
                    }
                });
    }

    private int _nAgreeUseTerm = 0;
    private int _nAgreePolicy = 0;
    private int _nAgreeAdver = 0;

    @OnClick(R.id.fly_agree_all_bg)
    void OnClickAgreeAll() {
        if (_nAgreeUseTerm == 1 && _nAgreePolicy == 1 && _nAgreeAdver == 1) {
            _nAgreeUseTerm = 0;
            _nAgreePolicy = 0;
            _nAgreeAdver = 0;
        } else {
            _nAgreeUseTerm = 1;
            _nAgreePolicy = 1;
            _nAgreeAdver = 1;
        }

        updateUseTerm();
        updatePolicy();
        updateAdver();
        updateAgreeAll();
    }

    @OnClick(R.id.fly_agree_useterm_bg)
    void OnClickAgreeUseTerm() {
        if (_nAgreeUseTerm == 1) {
            _nAgreeUseTerm = 0;
        } else {
            _nAgreeUseTerm = 1;
        }

        updateUseTerm();
        updateAgreeAll();
    }

    @OnClick(R.id.fly_agree_policy_bg)
    void OnClickAgreePolicy() {
        if (_nAgreePolicy == 1) {
            _nAgreePolicy = 0;
        } else {
            _nAgreePolicy = 1;
        }

        updatePolicy();
        updateAgreeAll();
    }

    @OnClick(R.id.fly_agree_adver_bg)
    void OnClickAgreeAdver() {
        if (_nAgreeAdver == 1) {
            _nAgreeAdver = 0;
        } else {
            _nAgreeAdver = 1;
        }

        updateAdver();
        updateAgreeAll();
    }

    @OnClick(R.id.txv_next)
    void OnClickNext() {
        if (_nAgreeUseTerm != 1 || _nAgreePolicy != 1) {
            Toaster.showShort(this, "필수 항목 체크 바랍니다.");
        } else {
            Intent intent = new Intent(JoinAgreeActivity.this, JoinRegActivity.class);
            intent.putExtra("AgreeAdver", _nAgreeAdver);
            startActivity(intent);
            finish();
        }
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
