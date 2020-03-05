package com.bene.pictures.ui.dialog;

import android.content.Intent;
import android.os.Bundle;

import com.bene.pictures.R;
import com.bene.pictures.data.MyInfo;
import com.bene.pictures.model.MBase;
import com.bene.pictures.model.MError;
import com.bene.pictures.net.Net;
import com.bene.pictures.ui.base.BaseActivity;
import com.bene.pictures.ui.widget.BaseEditText;
import com.bene.pictures.ui.widget.BaseTextView;
import com.bene.pictures.util.Toaster;

import butterknife.BindView;
import butterknife.OnClick;

public class SignOutActivity extends BaseActivity {

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
        String strNo = "";
        String strYes = "";
        if(getIntent().hasExtra("no"))
            strNo = getIntent().getStringExtra("no");
        if(getIntent().hasExtra("yes"))
            strYes = getIntent().getStringExtra("yes");

        ui_txvNo.setText(strNo.isEmpty() ? "탈퇴하기" : strNo);
        ui_txvYes.setText(strYes.isEmpty() ? "취소" : strYes);
    }

    private void signout() {

        Net.instance().api.signout(MyInfo.getInstance().userInfo.info.id, ui_edtPwd.getText().toString())
                .enqueue(new Net.ResponseCallBack<MBase>() {
                    @Override
                    public void onSuccess(MBase response) {
                        super.onSuccess(response);

                        Toaster.showShort(SignOutActivity.this, "탈퇴가 완료되었습니다.");
                        setResult(RESULT_OK);
                        finish();
                    }

                    @Override
                    public void onFailure(MError response) {
                        super.onFailure(response);
//                        if (BuildConfig.DEBUG) {
//
//                            Toaster.showShort(SignOutActivity.this, "조작이 성공하였습니다.");
//                            setResult(RESULT_OK);
//                            finish();
//                        } else {
                        Toaster.showShort(SignOutActivity.this, response.res_msg);
//                        }
                    }
                });
    }

    @OnClick(R.id.txv_no)
    void OnClickNo() {
        signout();
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
