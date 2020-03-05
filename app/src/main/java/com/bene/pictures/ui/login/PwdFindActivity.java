package com.bene.pictures.ui.login;

import android.content.Intent;
import android.os.Bundle;

import com.bene.pictures.BuildConfig;
import com.bene.pictures.R;
import com.bene.pictures.model.MCert;
import com.bene.pictures.model.MError;
import com.bene.pictures.model.MFindUsrId;
import com.bene.pictures.net.Net;
import com.bene.pictures.ui.base.BaseActivity;
import com.bene.pictures.ui.widget.BaseEditText;
import com.bene.pictures.util.Toaster;

import butterknife.BindView;
import butterknife.OnClick;

public class PwdFindActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pwd_find);

        initUI();
    }

    @BindView(R.id.edt_phone)
    BaseEditText ui_edtPhone;

    @BindView(R.id.edt_authcode)
    BaseEditText ui_edtAuthCode;

    public void initUI() {

    }

    @OnClick(R.id.txv_authsend)
    void OnClickAuthSend() {

        String phone_num = ui_edtPhone.getText().toString();
        if (phone_num.isEmpty()) {
            Toaster.showShort(this, "휴대폰 번호를 입력해주세요.");
            return;
        }

        Net.instance().api.getKey(phone_num, "", 1)
                .enqueue(new Net.ResponseCallBack<MCert>() {
                    @Override
                    public void onSuccess(MCert response) {
                        super.onSuccess(response);

//                        if (BuildConfig.DEBUG) {
//                            Toaster.showShort(PwdFindActivity.this, response.cert_key);
//                        } else {
                            Toaster.showShort(PwdFindActivity.this, "인증번호가 전송되었습니다");
//                        }
                    }

                    @Override
                    public void onFailure(MError response) {
                        super.onFailure(response);
                        Toaster.showShort(PwdFindActivity.this, response.res_msg);
                    }
                });
    }

    @OnClick(R.id.txv_authorize)
    void OnClickAuthorize() {

        final String phone_num = ui_edtPhone.getText().toString();
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

                        Intent intent = new Intent(PwdFindActivity.this, PwdChangeActivity.class);
                        intent.putExtra("phonenumber", phone_num);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onFailure(MError response) {
                        super.onFailure(response);
                        Toaster.showShort(PwdFindActivity.this, response.res_msg);
//                        if (BuildConfig.DEBUG) {
//                            Intent intent = new Intent(PwdFindActivity.this, PwdChangeActivity.class);
//                            intent.putExtra("phonenumber", phone_num);
//                            startActivity(intent);
//                            finish();
//                        }
                    }
                });
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
