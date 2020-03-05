package com.bene.pictures.ui.main.msgbox;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bene.pictures.R;
import com.bene.pictures.data.MyInfo;
import com.bene.pictures.model.MBase;
import com.bene.pictures.model.MError;
import com.bene.pictures.net.Net;
import com.bene.pictures.ui.base.BaseActivity;
import com.bene.pictures.ui.widget.BaseTextView;
import com.bene.pictures.util.Toaster;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MsgDetailActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msgdetail_dialog);

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

    int _nId;
    String _strTitle;
    String _strContent;
    int _nRead;
    int _nType = 0;

    @BindView(R.id.txv_title)
    BaseTextView ui_txvTitle;

    @BindView(R.id.txv_content)
    BaseTextView ui_txvContent;

    @BindView(R.id.txv_delete)
    BaseTextView ui_txvDelete;

    public void initUI() {

        _nId = getIntent().getIntExtra("id", 0);
        _nRead = getIntent().getIntExtra("read", 0);

        _strContent = getIntent().getStringExtra("content");
        _strTitle = getIntent().getStringExtra("title");
        _nType = getIntent().getIntExtra("type", 0);

        ui_txvTitle.setText(_strTitle);
        ui_txvContent.setText(_strContent);

        if (_nRead == 0) {
            readMsg();
        }

        if (_nType > 0) {
            ui_txvDelete.setVisibility(View.VISIBLE);
        } else {
            ui_txvDelete.setVisibility(View.GONE);
        }
    }

    boolean lockNet = false;

    void readMsg() {
        if (_nId < 1) {
            return;
        }

        if (lockNet) {
            return;
        }

        lockNet = true;
        Net.instance().api.readMsg(MyInfo.getInstance().userInfo.info.id, _nId)
                .enqueue(new Callback<MBase>() {
                    @Override
                    public void onResponse(Call<MBase> call, Response<MBase> response) {
                        lockNet = false;
                    }

                    @Override
                    public void onFailure(Call<MBase> call, Throwable throwable) {
                        lockNet = false;
                    }
                });
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        finish();
    }

//    @OnClick(R.id.fly_bg)
//    void OnClickWholeBg() {
//        setResult(RESULT_OK);
//        finish();
//    }

    @OnClick(R.id.txv_close)
    void OnClickBg() {
        setResult(RESULT_OK);
        finish();
    }

    @OnClick(R.id.txv_delete)
    void OnClickDelete() {
        showProgress(this);

        Net.instance().api.delMsg(MyInfo.getInstance().userInfo.info.id, _nId)
                .enqueue(new Net.ResponseCallBack<MBase>() {
                    @Override
                    public void onSuccess(MBase response) {
                        super.onSuccess(response);
                        hideProgress();
                        Toaster.showShort(MsgDetailActivity.this, "삭제되었습니다.");
                        setResult(RESULT_CANCELED);
                        finish();
                    }

                    @Override
                    public void onFailure(MError response) {
                        super.onFailure(response);
                        hideProgress();
                        showErrorMsg(response);
                    }
                });
    }
}
