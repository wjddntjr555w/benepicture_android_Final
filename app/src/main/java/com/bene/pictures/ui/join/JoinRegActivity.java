package com.bene.pictures.ui.join;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bene.pictures.MyApplication;
import com.bene.pictures.R;
import com.bene.pictures.data.MyInfo;
import com.bene.pictures.model.MBase;
import com.bene.pictures.model.MCert;
import com.bene.pictures.model.MError;
import com.bene.pictures.model.MSign;
import com.bene.pictures.net.Net;
import com.bene.pictures.ui.base.BaseActivity;
import com.bene.pictures.ui.login.LoginActivity;
import com.bene.pictures.ui.widget.BaseEditText;
import com.bene.pictures.ui.widget.BaseTextView;
import com.bene.pictures.util.Toaster;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;

public class JoinRegActivity extends BaseActivity {

    private String m_strKakaoName = "";
    private String m_strKakaoId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_reg);

        initUI();
    }

    private int _nAgreeAdver = 0;

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

    private TimePickerView pvCustomLunar;

    private boolean isIdChecked = false;

    public void initUI() {

        Intent intent = getIntent();
        _nAgreeAdver = intent.getIntExtra("AgreeAdver", 0);

        m_strKakaoId = ((MyApplication) getApplication()).getKakaoId();
        m_strKakaoName = ((MyApplication) getApplication()).getKakaoName();

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

    @BindView(R.id.imv_man)
    ImageView ui_imvMan;

    @BindView(R.id.imv_woman)
    ImageView ui_imvWoman;

    private int _nGender = 1; //1: 남자, 2:여자

    private void updateGender() {

        if (_nGender == 1) {
            ui_imvMan.setBackgroundResource(R.drawable.icon08);
            ui_imvWoman.setBackgroundResource(R.drawable.icon07);
        } else {
            ui_imvMan.setBackgroundResource(R.drawable.icon07);
            ui_imvWoman.setBackgroundResource(R.drawable.icon08);
        }
    }

    private void go2Login() {
        Intent intent = new Intent(JoinRegActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    void procSiginin() {

        final String uid = ui_edtId.getText().toString();
        final String pwd = ui_edtPwd.getText().toString();

        if (uid.isEmpty()) {
            Toaster.showShort(this, R.string.str_id_hint);
            return;
        }

        if (pwd.isEmpty()) {
            Toaster.showShort(this, R.string.str_pwd_hint);
            return;
        }

        showProgress(this);
        Net.instance().api.signIn(LOGIN_ID, uid, pwd, DEVICE_ANDROID, MyInfo.getInstance().fcm_token)
                .enqueue(new Net.ResponseCallBack<MSign>() {
                    @Override
                    public void onSuccess(MSign response) {
                        super.onSuccess(response);
                        hideProgress();

                        MyInfo.getInstance().userInfo.info.id = response.id;
                        MyInfo.getInstance().userInfo.info.sess_token = response.token;
                        MyInfo.getInstance().userInfo.info.login_type = LOGIN_ID;
                        MyInfo.getInstance().userInfo.info.usr_id = uid;
                        MyInfo.getInstance().userInfo.info.pwd = pwd;

                        MyInfo.getInstance().save(JoinRegActivity.this);

                        go2Login();
                    }

                    @Override
                    public void onFailure(MError response) {
                        super.onFailure(response);
                        hideProgress();
                        Toaster.showShort(JoinRegActivity.this, response.res_msg);
//                        if (BuildConfig.DEBUG) {
//                            MyInfo.getInstance().userInfo.info.id = 10;
//                            MyInfo.getInstance().userInfo.info.sess_token = "test";
//                            MyInfo.getInstance().userInfo.info.login_type = LOGIN_ID;
//                            MyInfo.getInstance().userInfo.info.usr_id = uid;
//                            MyInfo.getInstance().userInfo.info.pwd = pwd;
//
//                            MyInfo.getInstance().save(JoinRegActivity.this);
//
//                            go2Login();
//                        }
                    }
                });
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

        // fixed by Gambler 2019-10-16
        // 달력컨트럴을 날자피커로 변경
        Calendar selectDate = Calendar.getInstance();
        selectDate.set(Calendar.YEAR, year);
        selectDate.set(Calendar.MONTH, month);
        selectDate.set(Calendar.DAY_OF_MONTH, day);

        pvCustomLunar.setDate(selectDate);
        pvCustomLunar.show();
    }

    @OnClick(R.id.txv_join)
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

        if (!isIdChecked) {
            Toaster.showShort(this, "아이디 중복확인을 해주세요.");
            return;
        }

        String phone_num = ui_edtPhone.getText().toString();
        if (phone_num.isEmpty()) {
            Toaster.showShort(this, "휴대폰번호를 입력해주세요.");
            return;
        }

        String auth_num = ui_edtAuthCode.getText().toString();
        if (auth_num.isEmpty()) {
            Toaster.showShort(this, "인증번호를 입력해주세요.");
            return;
        }

        String pwd = ui_edtPwd.getText().toString();
        if (pwd.length() < 8 || pwd.length() > 16) {
            Toaster.showShort(this, "비밀번호는 8-16자로 입력해주세요.");
            return;
        }

        String pwdConf = ui_edtPwdConf.getText().toString();
        if (!pwd.equals(pwdConf)) {
            Toaster.showShort(this, "비밀번호가 다릅니다. 확인해주세요.");
            return;
        }


        int login_type = LOGIN_ID;
        if (!m_strKakaoId.isEmpty()) {
            login_type = LOGIN_KAKAO;
            //usr_id = m_strKakaoId;
        }

        showProgress(this);
        Net.instance().api.signUp(login_type, name, usr_id, birth, phone_num, auth_num, pwd, _nGender, _nAgreeAdver, 1, MyInfo.getInstance().fcm_token, m_strKakaoId)
                .enqueue(new Net.ResponseCallBack<MBase>() {
                    @Override
                    public void onSuccess(MBase response) {
                        super.onSuccess(response);
                        hideProgress();
                        Toaster.showShort(JoinRegActivity.this, "가입이 완료되었습니다. 로그인 바랍니다.");
//                        procSiginin();
//                        go2Login();
                        finish();
                    }

                    @Override
                    public void onFailure(MError response) {
                        super.onFailure(response);
                        hideProgress();
                        showErrorMsg(response);
//                        if (BuildConfig.DEBUG) {
//                            procSiginin();
//                        }
                    }
                });

    }

    @OnClick(R.id.txv_authsend)
    void OnClickAuthSend() {

        String phone_num = ui_edtPhone.getText().toString();
        if (phone_num.isEmpty()) {
            Toaster.showShort(this, "휴대폰 번호를 입력해주세요.");
            return;
        }

        Net.instance().api.getKey(phone_num, "", 0)
                .enqueue(new Net.ResponseCallBack<MCert>() {
                    @Override
                    public void onSuccess(MCert response) {
                        super.onSuccess(response);

//                        if (NET_DEV_MODE == 1) {
//                            Toaster.showShort(JoinRegActivity.this, response.cert_key);
//                        } else {
                        Toaster.showShort(JoinRegActivity.this, "인증번호가 전송되었습니다");
//                        }
                    }

                    @Override
                    public void onFailure(MError response) {
                        super.onFailure(response);
                        Toaster.showShort(JoinRegActivity.this, response.res_msg);
                    }
                });
    }

    @OnClick(R.id.txv_idcheck)
    void OnClickIdCheck() {

        final String usr_id = ui_edtId.getText().toString();
        if (usr_id.isEmpty()) {
            Toaster.showShort(JoinRegActivity.this, "아이디를 입력해주세요.");
            return;
        }

        Net.instance().api.checkId(usr_id)
                .enqueue(new Net.ResponseCallBack<MBase>() {
                    @Override
                    public void onSuccess(MBase response) {
                        super.onSuccess(response);

                        isIdChecked = true;
                        Toaster.showShort(JoinRegActivity.this, "사용가능한 아이디 입니다.");
                    }

                    @Override
                    public void onFailure(MError response) {
                        super.onFailure(response);

//                        if (BuildConfig.DEBUG) {
//                            isIdChecked = true;
//                            Toaster.showShort(JoinRegActivity.this, "사용가능한 아이디 입니다.");
//                        } else {
                        isIdChecked = false;
                        Toaster.showShort(JoinRegActivity.this, response.res_msg);
//                        }
                    }
                });
    }

    @OnClick(R.id.lly_man_bg)
    void OnClickManBg() {
        if (_nGender != 1) {
            _nGender = 1;
        }

        updateGender();
    }

    @OnClick(R.id.lly_woman_bg)
    void OnClickWomanBg() {
        if (_nGender != 2) {
            _nGender = 2;
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
}
