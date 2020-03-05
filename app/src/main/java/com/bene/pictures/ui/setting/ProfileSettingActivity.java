package com.bene.pictures.ui.setting;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bene.pictures.R;
import com.bene.pictures.data.MyInfo;
import com.bene.pictures.model.MBase;
import com.bene.pictures.model.MCert;
import com.bene.pictures.model.MError;
import com.bene.pictures.model.MUser;
import com.bene.pictures.net.Net;
import com.bene.pictures.ui.base.BaseActivity;
import com.bene.pictures.ui.dialog.ConfirmDialogActivity;
import com.bene.pictures.ui.dialog.SignOutActivity;
import com.bene.pictures.ui.login.LoginActivity;
import com.bene.pictures.ui.main.takereward.TakeRewardReqtakeFragment;
import com.bene.pictures.ui.widget.BaseEditText;
import com.bene.pictures.ui.widget.BaseTextView;
import com.bene.pictures.util.Toaster;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.OnClick;

import static com.bene.pictures.data.Common.g_bankinfoArray;

public class ProfileSettingActivity extends BaseActivity {
    HttpURLConnection m_conn = null;
    String m_bankAccountNum = "", m_bankUserName = "", m_bankCode = "", m_bankName;
    boolean isCheckBankSts = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_setting);
        getProfile();
    }

    @BindView(R.id.edt_name)
    BaseEditText ui_edtName;

    @BindView(R.id.edt_birthday)
    BaseTextView ui_edtBirthday;

    @BindView(R.id.edt_phone)
    BaseEditText ui_edtPhone;

    @BindView(R.id.edt_authcode)
    BaseEditText ui_edtAuthCode;

    @BindView(R.id.edt_pwd)
    BaseEditText ui_edtPwd;

    @BindView(R.id.edt_pwdconf)
    BaseEditText ui_edtPwdConf;

    @BindView(R.id.edt_id)
    BaseEditText ui_edtId;

    @BindView(R.id.edt_bank_name)
    BaseTextView ui_edtBankName;

    @BindView(R.id.edt_bank)
    BaseEditText ui_edtBank;

    @BindView(R.id.txv_bankcheck)
    BaseTextView ui_txvBankCheck;

    int bankStatus = 0;

    private TimePickerView pvCustomLunar;

    public void initUI() {

        ui_edtName.setText(MyInfo.getInstance().userInfo.info.nickname);
        ui_edtBirthday.setText(MyInfo.getInstance().userInfo.info.birth);
        ui_edtPhone.setText(MyInfo.getInstance().userInfo.info.phone);
        ui_edtId.setText(MyInfo.getInstance().userInfo.info.usr_id);
        ui_edtBankName.setText(MyInfo.getInstance().userInfo.info.bank_name);
        ui_edtBank.setText(MyInfo.getInstance().userInfo.info.bank);
        updateGender();

        if (MyInfo.getInstance().userInfo.info.bank_status == 0) {
            ui_txvBankCheck.setText("계좌인증하기");
            ui_txvBankCheck.setBackground(getDrawable(R.drawable.xml_btn_rect_616161));
        } else {
            ui_txvBankCheck.setText("계좌변경하기");
            ui_txvBankCheck.setBackground(getDrawable(R.drawable.xml_btn_round_rect_1976d2));
        }

        Calendar startDate = Calendar.getInstance();
        startDate.set(1920, 1, 1);
        Calendar endDate = Calendar.getInstance();

        pvCustomLunar = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd", Locale.getDefault());
                ui_edtBirthday.setText(format.format(date));
            }
        })
                .setRangDate(startDate, endDate)
                .setLayoutRes(R.layout.pickerview_custom_lunar, new CustomListener() {
                    @Override
                    public void customLayout(final View v) {
                        final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomLunar.returnData();
                                pvCustomLunar.dismiss();
                            }
                        });
                    }
                })
                .setLabel("년", "월", "일", "시", "분",  "초")
                .setType(new boolean[]{true, true, true, false, false, false})
                .isCenterLabel(false)
                .setDividerColor(Color.RED)
                .build();
    }

    void getProfile() {

        if (!MyInfo.getInstance().isValid()) {
            return;
        }

        showProgress(this);
        Net.instance().api.getProfile(MyInfo.getInstance().userInfo.info.id, MyInfo.getInstance().userInfo.info.sess_token)
                .enqueue(new Net.ResponseCallBack<MUser>() {
                    @Override
                    public void onSuccess(MUser response) {
                        super.onSuccess(response);
                        hideProgress();
                        MyInfo.getInstance().userInfo.info = response.info;

                        initUI();
                    }

                    @Override
                    public void onFailure(MError response) {
                        super.onFailure(response);
                        hideProgress();
                        showErrorMsg(response);
//                        if (BuildConfig.DEBUG) {
//                            MyInfo.getInstance().userInfo.info.whole_winning_money = 486549663;
//                            MyInfo.getInstance().userInfo.info.nickname = "당첨이";
//                            MyInfo.getInstance().userInfo.info.available_ad_count = 4;
//                            MyInfo.getInstance().userInfo.info.subscribe_count = 11;
//                            MyInfo.getInstance().userInfo.info.agree_adkeyword = 1;
//                            MyInfo.getInstance().userInfo.info.agree_vibrate = 0;
//                            MyInfo.getInstance().userInfo.info.alarm_friend_msg = 1;
//                            MyInfo.getInstance().userInfo.info.alarm_puzzle = 0;
//                            MyInfo.getInstance().userInfo.info.alarm_notice = 1;
//
//                            updateProfileUI();
//                            updateMenu();
//                        }
                    }
                });
    }

    @BindView(R.id.imv_man)
    ImageView ui_imvMan;

    @BindView(R.id.imv_woman)
    ImageView ui_imvWoman;

    private void updateGender() {

        if (MyInfo.getInstance().userInfo.info.gender == 1) {
            ui_imvMan.setBackgroundResource(R.drawable.icon08);
            ui_imvWoman.setBackgroundResource(R.drawable.icon07);
        } else {
            ui_imvMan.setBackgroundResource(R.drawable.icon07);
            ui_imvWoman.setBackgroundResource(R.drawable.icon08);
        }
    }

    @OnClick(R.id.edt_birthday)
    void OnClickBirthday() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        String dateFrom = ui_edtBirthday.getText().toString();
        if (!dateFrom.isEmpty()) {
            try {
                year = Integer.valueOf(dateFrom.substring(0, 4));
                month = Integer.valueOf(dateFrom.substring(5, 7)) - 1;
                day = Integer.valueOf(dateFrom.substring(8));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

//        DatePickerDialog dlg = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                ui_edtBirthday.setText(String.format(Locale.getDefault(), "%d.%02d.%02d", year, month + 1, dayOfMonth));
//            }
//        }, year, month, day);
//        dlg.show();

        // fixed by Gambler 2019-10-17
        // 달력컨트럴을 날자피커로 변경
        Calendar selectDate = Calendar.getInstance();
        selectDate.set(Calendar.YEAR, year);
        selectDate.set(Calendar.MONTH, month);
        selectDate.set(Calendar.DAY_OF_MONTH, day);

        pvCustomLunar.setDate(selectDate);
        pvCustomLunar.show();
    }


    @BindArray(R.array.bank)
    String[] bankList;

    @OnClick(R.id.edt_bank_name)
    void OnClickBankNmae() {
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

        AlertDialog.Builder dialog = new AlertDialog.Builder(ProfileSettingActivity.this);
        dialog.setSingleChoiceItems(bankList, selectedPos, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ui_edtBankName.setText(bankList[which]);
                m_bankCode = g_bankinfoArray[which].code;
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    @OnClick(R.id.txv_change)
    void OnClickJoin() {

        String name = ui_edtName.getText().toString();
        if (name.isEmpty()) {
            Toaster.showShort(this, "이름을 입력해주세요.");
            return;
        }

        String birth = ui_edtBirthday.getText().toString();
        if (birth.isEmpty()) {
            Toaster.showShort(this, "생년월일을 입력해주세요.");
            return;
        }

        if (!Pattern.matches("\\d{4}\\.\\d{2}\\.\\d{2}", birth)) {
            Toaster.showShort(this, "생년월일을 정확히 입력해주세요");
            return;
        }

        String usr_id = ui_edtId.getText().toString();

        String phone_num = ui_edtPhone.getText().toString();
        if (phone_num.isEmpty()) {
            Toaster.showShort(this, "전화번호를 입력해주세요.");
            return;
        }

        String auth_num = ui_edtAuthCode.getText().toString();
        if (!phone_num.equals(MyInfo.getInstance().userInfo.info.phone) && auth_num.isEmpty()) {
            Toaster.showShort(this, "인증번호를 입력해주세요.");
            return;
        }

        final String pwd = ui_edtPwd.getText().toString();
        if (!pwd.isEmpty() && (pwd.length() < 8 || pwd.length() > 16)) {
            Toaster.showShort(this, "비밀번호는 8-16자로 입력해주세요.");
            return;
        }

        String pwdConf = ui_edtPwdConf.getText().toString();
        if (!pwd.equals(pwdConf)) {
            Toaster.showShort(this, "비밀번호를 확인해주세요.");
            return;
        }

        String bank = ui_edtBank.getText().toString();
        if (!bank.isEmpty() && bank.equals(MyInfo.getInstance().userInfo.info.bank)) {
            bankStatus = 1;
        }

        showProgress(this);
        Net.instance().api.changeProfile(MyInfo.getInstance().userInfo.info.id, name, usr_id, birth, phone_num, auth_num, pwd, MyInfo.getInstance().userInfo.info.gender, bank, DEVICE_ANDROID, MyInfo.getInstance().fcm_token, bankStatus)
                .enqueue(new Net.ResponseCallBack<MUser>() {
                    @Override
                    public void onSuccess(MUser response) {
                        super.onSuccess(response);
                        hideProgress();
                        MyInfo.getInstance().userInfo.info = response.info;

                        if (!pwd.isEmpty()) {
                            MyInfo.getInstance().userInfo.info.pwd = pwd;
                        }

                        MyInfo.getInstance().save(ProfileSettingActivity.this);
                        Toaster.showShort(ProfileSettingActivity.this, "수정되었습니다.");
                    }

                    @Override
                    public void onFailure(MError response) {
                        super.onFailure(response);
                        hideProgress();
                        showErrorMsg(response);
//                        if (BuildConfig.DEBUG) {
//                            Toaster.showShort(ProfileSettingActivity.this, "수정되었습니다.");
//                        }
                    }
                });
    }

    @OnClick(R.id.txv_authsend)
    void OnClickAuthSend() {

        String phone_num = ui_edtPhone.getText().toString();
        if (phone_num.isEmpty()) {
            Toaster.showShort(this, "전화번호를 입력해주세요.");
            return;
        }

        Net.instance().api.getKey(phone_num, "", 0)
                .enqueue(new Net.ResponseCallBack<MCert>() {
                    @Override
                    public void onSuccess(MCert response) {
                        super.onSuccess(response);

//                        if (BuildConfig.DEBUG) {
//                            Toaster.showShort(ProfileSettingActivity.this, response.cert_key);
//                        } else {
                        Toaster.showShort(ProfileSettingActivity.this, "전송되었습니다");
//                        }
                    }

                    @Override
                    public void onFailure(MError response) {
                        super.onFailure(response);
                        Toaster.showShort(ProfileSettingActivity.this, response.res_msg);
                    }
                });
    }

    @OnClick(R.id.txv_idcheck)
    void OnClickIdCheck() {

        final String usr_id = ui_edtId.getText().toString();
        if (usr_id.isEmpty()) {
            Toaster.showShort(ProfileSettingActivity.this, "아이디를 입력해주세요..");
            return;
        }

        Net.instance().api.checkId(usr_id)
                .enqueue(new Net.ResponseCallBack<MBase>() {
                    @Override
                    public void onSuccess(MBase response) {
                        super.onSuccess(response);

                        Toaster.showShort(ProfileSettingActivity.this, "사용가능한 아이디 입니다.");
                    }

                    @Override
                    public void onFailure(MError response) {
                        super.onFailure(response);
                        Toaster.showShort(ProfileSettingActivity.this, response.res_msg);
                    }
                });
    }

    @OnClick(R.id.txv_bankcheck)
    void OnClickBankCheck() {
        //TODO: 계좌가 인증되었으면 bankStatus = 1 아님 0으로 설정.
        if(MyInfo.getInstance().userInfo.info.bank_status == 0){
            m_bankUserName = ui_edtName.getText().toString().trim();
            if (m_bankUserName.isEmpty()) {
                Toaster.showShort(ProfileSettingActivity.this, "이름을 입력해주세요.");
                return;
            }

            m_bankName = ui_edtBankName.getText().toString().trim();
            if (m_bankName.isEmpty()) {
                Toaster.showShort(ProfileSettingActivity.this, "은행명을 입력해주세요.");
                return;
            }

            m_bankAccountNum = ui_edtBank.getText().toString();
            if (m_bankAccountNum.isEmpty()) {
                Toaster.showShort(ProfileSettingActivity.this, "계좌번호를 입력해주세요.");
                return;
            }

            //Toaster.showShort(this, "계좌인증하기");
            new checkBankAccountTask().execute("true");
        } else {
            //Toaster.showShort(this, "계좌변경하기");
            String account = ui_edtBank.getText().toString();
            if(account.isEmpty()){
                Toaster.showShort(ProfileSettingActivity.this, "계좌번호를 입력해주세요.");
                return;
            }
            change_account(account);
        }
    }

    private class checkBankAccountTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String resultMsg = "failed";

            OutputStream os = null;
            InputStream is = null;
            ByteArrayOutputStream baos = null;

            try{

                URL url = new URL("https://www.arspay.co.kr/acctInterfaceJson.acct");
                m_conn = (HttpURLConnection) url.openConnection();

                m_conn.setDoOutput(true);
                m_conn.setRequestMethod("POST");
                m_conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

                JSONObject parent=new JSONObject();
                parent.put("serviceMethod","01");
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
                        return  "success";
                    } else {
                        return responseObject.getString("resultMsg");
                    }

                }
            } catch(Exception e) {
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
                Toast.makeText(ProfileSettingActivity.this, "인증이 성공되었습니다.", Toast.LENGTH_SHORT).show();

                ui_txvBankCheck.setText("계좌변경하기");
                ui_txvBankCheck.setBackground(getDrawable(R.drawable.xml_btn_round_rect_1976d2));

                update_bank_info(1);
            } else {
                MyInfo.getInstance().userInfo.info.bank_status = 0;
                Toast.makeText(ProfileSettingActivity.this, "인증이 실패하였습니다. 입력하신 정보들을 다시 확인해주세요.", Toast.LENGTH_SHORT).show();
            }

        }
    }


    private void update_bank_info(int bank_status){
        showProgress(this);
        Net.instance().api.changeAccount(MyInfo.getInstance().userInfo.info.id, m_bankName, m_bankAccountNum, m_bankUserName, bank_status)
                .enqueue(new Net.ResponseCallBack<MBase>() {
                    @Override
                    public void onSuccess(MBase response) {
                        super.onSuccess(response);
                        hideProgress();
//                        Toaster.showShort(_activity, "조작이 성공하였습니다.");
                    }

                    @Override
                    public void onFailure(MError response) {
                        super.onFailure(response);
                        hideProgress();
                        Toaster.showShort(ProfileSettingActivity.this, response.res_msg);
                    }
                });
    }

    private void change_account(String account){
        showProgress(this);
        Net.instance().api.changeAccount(MyInfo.getInstance().userInfo.info.id, MyInfo.getInstance().userInfo.info.bank_name, account, MyInfo.getInstance().userInfo.info.depositor, MyInfo.getInstance().userInfo.info.bank_status)
                .enqueue(new Net.ResponseCallBack<MBase>() {
                    @Override
                    public void onSuccess(MBase response) {
                        super.onSuccess(response);
                        hideProgress();
                        Toaster.showShort(ProfileSettingActivity.this, "변경되었습니다.");
                    }

                    @Override
                    public void onFailure(MError response) {
                        super.onFailure(response);
                        hideProgress();
                        Toaster.showShort(ProfileSettingActivity.this, response.res_msg);
                    }
                });
    }



    private void goLogin() {
        Intent intent = new Intent(ProfileSettingActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.txv_signout)
    void OnClickSignout() {

        String strTitle = "정말로 탈퇴하시겠습니까?\n탈퇴 시 적립금은 자동 소멸됩니다.";

        Intent showConfirm = new Intent(ProfileSettingActivity.this, ConfirmDialogActivity.class);
        showConfirm.putExtra("content", strTitle);
        showConfirm.putExtra("span_start", 0);
        showConfirm.putExtra("span_end", 0);
        showConfirm.putExtra("no", "탈퇴하기");
        showConfirm.putExtra("yes", "닫기");
        startActivityForResult(showConfirm, 1015);
    }

    @OnClick(R.id.lly_man_bg)
    void OnClickManBg() {
        if (MyInfo.getInstance().userInfo.info.gender != 1) {
            MyInfo.getInstance().userInfo.info.gender = 1;
        }

        updateGender();
    }

    @OnClick(R.id.lly_woman_bg)
    void OnClickWomanBg() {
        if (MyInfo.getInstance().userInfo.info.gender != 2) {
            MyInfo.getInstance().userInfo.info.gender = 2;
        }

        updateGender();
    }

    @OnClick(R.id.imv_back)
    void OnClickBack() {
        finish();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 1015:
                if (resultCode == RESULT_CANCELED) {

                    Intent showConfirm = new Intent(ProfileSettingActivity.this, SignOutActivity.class);

                    startActivityForResult(showConfirm, 1016);
                }
                break;
            case 1016:
                if (resultCode == RESULT_OK) {
                    goLogin();
                }
                break;
        }
    }
}
