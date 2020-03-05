package com.bene.pictures.ui.login;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bene.pictures.BuildConfig;
import com.bene.pictures.R;
import com.bene.pictures.model.MCert;
import com.bene.pictures.model.MError;
import com.bene.pictures.model.MFindUsrId;
import com.bene.pictures.net.Net;
import com.bene.pictures.ui.base.BaseActivity;
import com.bene.pictures.ui.widget.BaseEditText;
import com.bene.pictures.ui.widget.BaseTextView;
import com.bene.pictures.util.Toaster;

import butterknife.BindView;
import butterknife.OnClick;

public class IdFindActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_id_find);

        initUI();
    }

    @BindView(R.id.lly_idfind_step1_bg)
    LinearLayout ui_llyStep1Bg;

    @BindView(R.id.rly_idfind_step2_bg)
    RelativeLayout ui_rlyStep2Bg;

    @BindView(R.id.txv_authorize)
    BaseTextView ui_txvAuthorize;

    @BindView(R.id.edt_phone)
    BaseEditText ui_edtPhone;

    @BindView(R.id.edt_authcode)
    BaseEditText ui_edtAuthCode;

    @BindView(R.id.edt_id)
    BaseEditText ui_edtId;

    public void initUI() {
        updateIdFindByStep();
    }

    private int _nStep = 1;

    private void updateIdFindByStep() {

        if (_nStep == 1) {
            ui_llyStep1Bg.setVisibility(View.VISIBLE);
            ui_rlyStep2Bg.setVisibility(View.GONE);

            ui_txvAuthorize.setText("인증");

        } else {
            ui_llyStep1Bg.setVisibility(View.GONE);
            ui_rlyStep2Bg.setVisibility(View.VISIBLE);

            ui_txvAuthorize.setText("로그인 화면으로 이동");
        }
    }

    @OnClick(R.id.txv_authsend)
    void OnClickAuthSend() {

        String phone_num = ui_edtPhone.getText().toString();
        if (phone_num.isEmpty()) {
            Toaster.showShort(this, "휴대폰 번호를 입력해주세요.");
            return;
        }

        showProgress(this);
        Net.instance().api.getKey(phone_num, "", 1)
                .enqueue(new Net.ResponseCallBack<MCert>() {
                    @Override
                    public void onSuccess(MCert response) {
                        super.onSuccess(response);
                        hideProgress();
//                        if (BuildConfig.DEBUG) {
//                            Toaster.showShort(IdFindActivity.this, response.cert_key);
//                        } else {
                        Toaster.showShort(IdFindActivity.this, "인증번호가 전송되었습니다");
//                        }
                    }

                    @Override
                    public void onFailure(MError response) {
                        super.onFailure(response);
                        hideProgress();
                        Toaster.showShort(IdFindActivity.this, response.res_msg);
                    }
                });
    }

    @OnClick(R.id.txv_authorize)
    void OnClickAuthorize() {

        if (_nStep == 1) {

            String phone_num = ui_edtPhone.getText().toString();
            if (phone_num.isEmpty()) {
                Toaster.showShort(this, "휴대폰 번호를 입력해주세요.");
                return;
            }

            String auth_num = ui_edtAuthCode.getText().toString();
            if (auth_num.isEmpty()) {
                Toaster.showShort(this, "인증번호를 입력해주세요.");
                return;
            }

            Net.instance().api.findId(phone_num, auth_num)
                    .enqueue(new Net.ResponseCallBack<MFindUsrId>() {
                        @Override
                        public void onSuccess(MFindUsrId response) {
                            super.onSuccess(response);

                            _nStep = 2;
                            ui_edtId.setText(response.usr_id);
                            updateIdFindByStep();
                        }

                        @Override
                        public void onFailure(MError response) {
                            super.onFailure(response);
                            Toaster.showShort(IdFindActivity.this, response.res_msg);
//                            if (BuildConfig.DEBUG) {
//                                _nStep = 2;
//                                ui_edtId.setText("tester001");
//                                updateIdFindByStep();
//                            }
                        }
                    });

        } else {
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
