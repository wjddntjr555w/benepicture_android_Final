package com.bene.pictures.ui.dialog;

import android.content.Intent;
import android.os.Bundle;

import com.bene.pictures.R;
import com.bene.pictures.data.MyInfo;
import com.bene.pictures.model.MBase;
import com.bene.pictures.model.MError;
import com.bene.pictures.model.MSign;
import com.bene.pictures.net.Net;
import com.bene.pictures.ui.base.BaseActivity;
import com.bene.pictures.ui.join.JoinRegActivity;
import com.bene.pictures.ui.widget.BaseEditText;
import com.bene.pictures.ui.widget.BaseTextView;
import com.bene.pictures.util.Toaster;

import butterknife.BindView;
import butterknife.OnClick;

public class UserCheckActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signout_dialog);

        initLayout();
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

    @BindView(R.id.edt_pwd)
    BaseEditText ui_edtPwd;

    @BindView(R.id.txv_yes)
    BaseTextView ui_txvYes;

    @BindView(R.id.txv_no)
    BaseTextView ui_txvNo;

    private void initLayout() {
        ui_txvNo.setText("확인");
        ui_txvYes.setText("취소");
    }

    private void signout() {

        Net.instance().api.signIn(LOGIN_ID, MyInfo.getInstance().userInfo.info.usr_id, ui_edtPwd.getText().toString(), DEVICE_ANDROID, MyInfo.getInstance().fcm_token)
                .enqueue(new Net.ResponseCallBack<MSign>() {
                    @Override
                    public void onSuccess(MSign response) {
                        super.onSuccess(response);
                        MyInfo.getInstance().userInfo.info.sess_token = response.token;

                        setResult(RESULT_OK);
                        finish();
                    }

                    @Override
                    public void onFailure(MError response) {
                        super.onFailure(response);
                        Toaster.showShort(UserCheckActivity.this, response.res_msg);
                    }
                });
    }

    @OnClick(R.id.txv_no)
    void OnClickNo() {
        if (ui_edtPwd.getText().toString().isEmpty()) {
            Toaster.showShort(this, "비밀번호를 입력해주세요");
            return;
        }

        Net.instance().api.checkPwd(MyInfo.getInstance().userInfo.info.usr_id, ui_edtPwd.getText().toString())
                .enqueue(new Net.ResponseCallBack<MBase>() {
                    @Override
                    public void onSuccess(MBase response) {
                        super.onSuccess(response);

                        setResult(RESULT_OK);
                        finish();
                    }

                    @Override
                    public void onFailure(MError response) {
                        super.onFailure(response);

                        Toaster.showShort(UserCheckActivity.this, response.res_msg);
                    }
                });
    }

    @OnClick(R.id.txv_yes)
    void OnClickYes() {
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
