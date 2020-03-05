package com.bene.pictures.ui.login;

import android.content.Intent;
import android.os.Bundle;

import com.bene.pictures.BuildConfig;
import com.bene.pictures.R;
import com.bene.pictures.model.MBase;
import com.bene.pictures.model.MError;
import com.bene.pictures.net.Net;
import com.bene.pictures.ui.base.BaseActivity;
import com.bene.pictures.ui.join.JoinRegActivity;
import com.bene.pictures.ui.widget.BaseEditText;
import com.bene.pictures.util.Toaster;

import butterknife.BindView;
import butterknife.OnClick;

public class PwdChangeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pwd_change);

        initUI();
    }

    @BindView(R.id.edt_pwd)
    BaseEditText ui_edtPwd;

    @BindView(R.id.edt_pwdconf)
    BaseEditText ui_edtPwdConf;

    String phonenumber = "";

    public void initUI() {

        Intent intent = getIntent();
        phonenumber = intent.getStringExtra("phonenumber");
    }

    @OnClick(R.id.txv_change)
    void OnClickChange() {

        String pwd = ui_edtPwd.getText().toString();
        if (pwd.length() < 8 || pwd.length() > 16) {
            Toaster.showShort(this, "비밀번호는 8-16자로 입력해주세요.");
            return;
        }

        String pwdConf = ui_edtPwdConf.getText().toString();
        if (!pwd.equals(pwdConf)) {
            Toaster.showShort(this, "비밀번호를 확인해주세요.");
            return;
        }

        Net.instance().api.resetPwd(phonenumber, pwd)
                .enqueue(new Net.ResponseCallBack<MBase>() {
                    @Override
                    public void onSuccess(MBase response) {
                        super.onSuccess(response);

                        Toaster.showShort(PwdChangeActivity.this, "비밀번호가 설정되었습니다.");
                        finish();
                    }

                    @Override
                    public void onFailure(MError response) {
                        super.onFailure(response);
//                        if (BuildConfig.DEBUG) {
//                            Toaster.showShort(PwdChangeActivity.this, "비밀번호가 설정되었습니다.");
//                            finish();
//                        } else {
                            Toaster.showShort(PwdChangeActivity.this, response.res_msg);
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
