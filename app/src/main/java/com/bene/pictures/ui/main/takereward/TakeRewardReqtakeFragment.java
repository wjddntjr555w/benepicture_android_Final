package com.bene.pictures.ui.main.takereward;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bene.pictures.R;
import com.bene.pictures.data.MyInfo;
import com.bene.pictures.model.MBase;
import com.bene.pictures.model.MError;
import com.bene.pictures.model.MJoinAgree;
import com.bene.pictures.model.MTakeHistoryList;
import com.bene.pictures.model.MUser;
import com.bene.pictures.net.Net;
import com.bene.pictures.ui.widget.BaseEditText;
import com.bene.pictures.ui.widget.BaseTextView;
import com.bene.pictures.util.Toaster;
import com.bene.pictures.util.Util;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;
import static com.bene.pictures.data.Common.g_bankinfoArray;
import static com.bene.pictures.data.Constant.DEVICE_ANDROID;

public class TakeRewardReqtakeFragment extends TakeRewardBaseFragment {
    HttpURLConnection m_conn = null;
    String m_bankAccountNum = "", m_bankUserName = "", m_bankCode = "", m_bankName;
    boolean isCheckBankSts = false;

    public TakeRewardReqtakeFragment() {

    }

    public static TakeRewardReqtakeFragment newInstance() {
        return new TakeRewardReqtakeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ui_rootView = inflater.inflate(R.layout.fragment_takereward_reqtake,
                container, false);

        ButterKnife.bind(this, ui_rootView);

        initUI();

        initData();

        return ui_rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void refresh() {
        super.refresh();
    }

    @BindView(R.id.edt_name)
    BaseEditText ui_edtName;

    @BindView(R.id.edt_subscribe_number)
    BaseTextView ui_edtNumber;

    @BindView(R.id.edt_bank)
    BaseTextView ui_edtBank;

    @BindView(R.id.edt_account)
    BaseEditText ui_edtAccount;

    @BindView(R.id.edt_review)
    BaseEditText ui_edtReview;

    @BindView(R.id.imv_agree_guide)
    ImageView ui_imvAgreeGuide;

    @BindView(R.id.txv_guide)
    BaseTextView ui_txvGuide;

    @BindView(R.id.txv_takable_money)
    BaseTextView ui_txvTakableMoney;

    @BindView(R.id.edt_citizen)
    BaseEditText ui_edtCitizen;

    float m_cost = 0;

    void initUI() {
    }

    void initData() {
        Net.instance().api.getJoinAgreeInfo(DEVICE_ANDROID)
                .enqueue(new Net.ResponseCallBack<MJoinAgree>() {
                    @Override
                    public void onSuccess(MJoinAgree response) {
                        super.onSuccess(response);

                        ui_txvGuide.setText(response.winner);
                        getProfile();
                    }

                    @Override
                    public void onFailure(MError response) {
                        super.onFailure(response);
                        _activity.showErrorMsg(response);
                    }
                });
    }

    int account_status = 0; // 계좌인증여부

    void getProfile() {

        if (!MyInfo.getInstance().isValid()) {
            return;
        }

        _activity.showProgress(_activity);
        Net.instance().api.getProfile(MyInfo.getInstance().userInfo.info.id, MyInfo.getInstance().userInfo.info.sess_token)
                .enqueue(new Net.ResponseCallBack<MUser>() {
                    @Override
                    public void onSuccess(MUser response) {
                        super.onSuccess(response);
                        _activity.hideProgress();

                        MyInfo.getInstance().userInfo.info = response.info;
                        account_status = response.info.bank_status;

                        ui_txvTakableMoney.setText(Util.makeMoneyType(response.info.subscribe_cost));
                        m_cost = response.info.subscribe_cost;

                        if (response.info.bank_status == 1) {
                            ui_edtBank.setText(response.info.bank_name);
                            ui_edtAccount.setText(response.info.bank);
                            ui_txvAccountAuth.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(MError response) {
                        super.onFailure(response);
                        _activity.hideProgress();
                        _activity.showErrorMsg(response);
                    }
                });
    }

    private int _nAgreeGuide = 0;

    private void updateUseTerm() {
        if (_nAgreeGuide == 1) {
            ui_imvAgreeGuide.setBackgroundResource(R.drawable.icon06);
        } else {
            ui_imvAgreeGuide.setBackgroundResource(R.drawable.icon05);
        }
    }

    @OnClick(R.id.edt_subscribe_number)
    void OnClickSubScribe() {
        // 응모권선택
        _activity.startActivityForResult(new Intent(_activity, RewardPickerActivity.class), 2005);
    }

    @OnClick({R.id.fly_agree_guide_bg, R.id.imv_agree_guide})
    void OnClickAgreeGuide() {

        if (_nAgreeGuide == 1) {
            _nAgreeGuide = 0;
        } else {
            _nAgreeGuide = 1;
        }

        updateUseTerm();
    }

    @BindView(R.id.txv_account_auth)
    BaseTextView ui_txvAccountAuth;

    @OnClick(R.id.txv_account_auth)
    void OnClickAccountAuth() {
//        String citizen_num = ui_edtCitizen.getText().toString().trim();
//        if(citizen_num.length() < 13){
//            Toaster.showShort(_activity, "주민번호를 입력해주세요. 주민번호는 13자리 이어야 합니다.");
//            return;
//        }

        m_bankUserName = ui_edtName.getText().toString().trim();
        if (m_bankUserName.isEmpty()) {
            Toaster.showShort(_activity, "이름을 입력해주세요.");
            return;
        }

        m_bankName = ui_edtBank.getText().toString().trim();
        if (m_bankName.isEmpty()) {
            Toaster.showShort(_activity, "은행명을 입력해주세요.");
            return;
        }

        m_bankAccountNum = ui_edtAccount.getText().toString();
        if (m_bankAccountNum.isEmpty()) {
            Toaster.showShort(_activity, "계좌번호를 입력해주세요.");
            return;
        }

        new checkBankAccountTask().execute("true");
    }

    private class checkBankAccountTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String resultMsg = "failed";

            OutputStream os = null;
            InputStream is = null;
            ByteArrayOutputStream baos = null;

            try {

                URL url = new URL("https://www.arspay.co.kr/acctInterfaceJson.acct");
                m_conn = (HttpURLConnection) url.openConnection();

                m_conn.setDoOutput(true);
                m_conn.setRequestMethod("POST");
                m_conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

                JSONObject parent = new JSONObject();
                parent.put("serviceMethod", "01");
                parent.put("iacctNm", m_bankUserName);
                parent.put("iacctNo", m_bankAccountNum);
                parent.put("merchantNo", "10041");
                parent.put("licenseKey", "GjwQRf1r34igT4gZkwVzxX+8961rrhoqYI8SuWfYe5o8K/5ACTEU22pWF+U4UhYyTN6ho0no9YpoYe7bo3JHeg==");
                parent.put("bankCd", m_bankCode);

                String input = parent.toString();
                os = m_conn.getOutputStream();
                os.write(input.getBytes());
                os.flush();

                int responseCode = m_conn.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {

                    is = m_conn.getInputStream();
                    baos = new ByteArrayOutputStream();
                    byte[] byteBuffer = new byte[1024];
                    byte[] byteData = null;
                    int nLength = 0;
                    while ((nLength = is.read(byteBuffer, 0, byteBuffer.length)) != -1) {
                        baos.write(byteBuffer, 0, nLength);
                    }
                    byteData = baos.toByteArray();

                    String response = new String(byteData);

                    JSONObject responseObject = new JSONObject(response);
                    if (responseObject.getInt("resultCode") == 0) {
                        return "success";
                    } else {
                        return responseObject.getString("resultMsg");
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return resultMsg;
        }

        @Override
        protected void onPostExecute(String curAddress) {
            // Call onRefreshComplete when the list has been refreshed.
            if (curAddress.equals("success")) {
                isCheckBankSts = true;
                MyInfo.getInstance().userInfo.info.bank_status = 1;
                account_status = 1;
                Toast.makeText(_activity, "인증이 성공되었습니다.", Toast.LENGTH_SHORT).show();
                update_bank_info(account_status);
            } else {
                MyInfo.getInstance().userInfo.info.bank_status = 0;
                account_status = 0;
                Toast.makeText(_activity, "인증이 실패하였습니다. 입력하신 정보들을 다시 확인해주세요.", Toast.LENGTH_SHORT).show();
            }

        }
    }


    private void update_bank_info(int bank_status) {
        _activity.showProgress(_activity);
        Net.instance().api.changeAccount(MyInfo.getInstance().userInfo.info.id, m_bankName, m_bankAccountNum, m_bankUserName, bank_status)
                .enqueue(new Net.ResponseCallBack<MBase>() {
                    @Override
                    public void onSuccess(MBase response) {
                        super.onSuccess(response);
                        _activity.hideProgress();
//                        Toaster.showShort(_activity, "조작이 성공하였습니다.");
                    }

                    @Override
                    public void onFailure(MError response) {
                        super.onFailure(response);
                        _activity.hideProgress();
                        Toaster.showShort(_activity, response.res_msg);
                    }
                });
    }

    @BindArray(R.array.bank)
    String[] bankList;

    @OnClick(R.id.edt_bank)
    void OnClickBank() {
        String bankName = ui_edtBank.getText().toString();
        int selectedPos = 0;

        if (!bankName.isEmpty()) {
            for (int i = 0; i < bankList.length; i++) {
                if (bankList[i].equals(bankName)) {
                    selectedPos = i;
                    break;
                }
            }
        }

        AlertDialog.Builder dialog = new AlertDialog.Builder(_activity);
        dialog.setSingleChoiceItems(bankList, selectedPos, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ui_edtBank.setText(bankList[which]);
                m_bankCode = g_bankinfoArray[which].code;
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @OnClick(R.id.txv_next)
    void OnClickTake() {

        String name = ui_edtName.getText().toString();
        if (name.isEmpty()) {
            Toaster.showShort(_activity, "이름을 입력해주세요.");
            return;
        }

        String number = ui_edtNumber.getText().toString();
        if (number.isEmpty()) {
            Toaster.showShort(_activity, "응모권번호를 입력해주세요.");
            return;
        }

        String bank = ui_edtBank.getText().toString();

        if (bank.isEmpty()) {
            Toaster.showShort(_activity, "은행명을 입력해주세요.");
            return;
        }

        String account = ui_edtAccount.getText().toString();

        if (account.isEmpty()) {
            Toaster.showShort(_activity, "계좌번호를 입력해주세요.");
            return;
        }

        if (account_status == 0) {
            Toaster.showShort(_activity, "계좌번호를 인증해주세요.");
            return;
        }

        String review = ui_edtReview.getText().toString();

        if (review.length() < 30 && m_cost > 5000) {// 수령금액이 5000 이상인 경우에만
            Toaster.showShort(_activity, "후기를 30자이상 입력해주세요.");
            return;
        }

        if (_nAgreeGuide == 0) {
            Toaster.showShort(_activity, "상금수령관련 필수 고지 사항 및 안내내용을 확인해주세요.");
            return;
        }

        _activity.showProgress(_activity);
        Net.instance().api.takeReward(MyInfo.getInstance().userInfo.info.id, name, subscribe_number, bank, account, review, MyInfo.getInstance().fcm_token, cost)
                .enqueue(new Net.ResponseCallBack<MBase>() {
                    @Override
                    public void onSuccess(MBase response) {
                        super.onSuccess(response);
                        _activity.hideProgress();

                        ui_txvTakableMoney.setText(Util.makeMoneyType(MyInfo.getInstance().userInfo.info.subscribe_cost - cost));
                        ui_edtName.setText("");
                        ui_edtNumber.setText("");
                        ui_txvAccountAuth.setText("");
                        ui_edtReview.setText("");

                        subscribe_number = "";
                        cost = 0;

                        Toaster.showShort(_activity, "신청되었습니다.");
                    }

                    @Override
                    public void onFailure(MError response) {
                        super.onFailure(response);
                        _activity.hideProgress();
                        Toaster.showShort(_activity, response.res_msg);
                    }
                });
    }

    String subscribe_number = "";
    float cost = 0;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2005 && resultCode == RESULT_OK) {
            MTakeHistoryList.Info info = data.getParcelableExtra("info");

            if (info != null) {
                subscribe_number = info.subscribe_number;
                cost = info.winning_money;

                ui_edtNumber.setText(subscribe_number);
//                ui_txvTakableMoney.setText(Util.makeMoneyType(cost));
            }
        }
    }
}