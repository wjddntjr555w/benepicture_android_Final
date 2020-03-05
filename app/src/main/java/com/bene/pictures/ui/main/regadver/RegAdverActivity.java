package com.bene.pictures.ui.main.regadver;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.DatePicker;
import android.widget.ImageView;

import com.bene.pictures.R;
import com.bene.pictures.data.MyInfo;
import com.bene.pictures.model.MBase;
import com.bene.pictures.model.MError;
import com.bene.pictures.model.MJoinAgree;
import com.bene.pictures.net.Net;
import com.bene.pictures.ui.base.BaseActivity;
import com.bene.pictures.ui.widget.BaseEditText;
import com.bene.pictures.ui.widget.BaseTextView;
import com.bene.pictures.util.MediaManager;
import com.bene.pictures.util.Toaster;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class RegAdverActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_adver);

        initUI();

        initData();
    }

    @BindView(R.id.imv_game)
    ImageView ui_imvGame;

    @BindView(R.id.imv_video)
    ImageView ui_imvVideo;

    @BindView(R.id.edt_period_from)
    BaseTextView ui_edtPeriodFrom;

    @BindView(R.id.edt_period_to)
    BaseTextView ui_edtPeriodTo;

    @BindView(R.id.edt_filename)
    BaseTextView ui_edtFileName;

    @BindView(R.id.edt_adname)
    BaseEditText ui_edtAdName;

    @BindView(R.id.edt_link)
    BaseEditText ui_edtLink;

    @BindView(R.id.edt_count)
    BaseEditText ui_edtCount;

    @BindView(R.id.edt_budget)
    BaseEditText ui_edtBudget;

    @BindView(R.id.imv_agree_adterm)
    ImageView ui_imvAgreeAdTerm;

    @BindView(R.id.txv_adterm)
    BaseTextView ui_txvAdTerm;

    int adCost = 0;


    public void initUI() {

        mediaManager = new MediaManager(this);
        mediaManager.setMediaCallback(new MediaManager.MediaCallback() {
            @Override
            public void onSelected(Boolean isVideo, File file, Bitmap bitmap, String videoPath, String thumbPath) {
                if (isVideo) {
                    adFile = new File(videoPath);
                    if (!adFile.getName().endsWith("mp4")) {
                        Toaster.showShort(RegAdverActivity.this, "mp4파일로 선택해주세요");
                        adFile = null;
                        return;
                    }

                    ui_edtFileName.setText(adFile.getName());
                } else if (file != null) {
                    adFile = file;
                    if (!adFile.getName().endsWith("jpg") && !adFile.getName().endsWith("jpeg")) {
                        Toaster.showShort(RegAdverActivity.this, "jpg파일로 선택해주세요");
                        adFile = null;
                        return;
                    }

                    ui_edtFileName.setText(adFile.getName());
                }
            }

            @Override
            public void onFailed(int code, String err) {

            }

            @Override
            public void onDelete() {

            }
        });

        ui_edtCount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    int count = Integer.valueOf(ui_edtCount.getText().toString());
                    ui_edtBudget.setText(String.valueOf(count * adCost));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    ui_edtBudget.setText("");
                }
            }
        });

    }

    void initData() {
        Net.instance().api.getJoinAgreeInfo(DEVICE_ANDROID)
                .enqueue(new Net.ResponseCallBack<MJoinAgree>() {
                    @Override
                    public void onSuccess(MJoinAgree response) {
                        super.onSuccess(response);

                        ui_txvAdTerm.setText(response.adver);
                        adCost = response.cost;
                    }

                    @Override
                    public void onFailure(MError response) {
                        super.onFailure(response);
                        showErrorMsg(response);
                    }
                });
    }

    private void updateAdKind() {

        if (_adKind == 1) {
            ui_imvGame.setBackgroundResource(R.drawable.icon08);
            ui_imvVideo.setBackgroundResource(R.drawable.icon07);
        } else {
            ui_imvGame.setBackgroundResource(R.drawable.icon07);
            ui_imvVideo.setBackgroundResource(R.drawable.icon08);
        }
    }

    private int _adKind = 1; //1: game, 2:video

    @OnClick(R.id.imv_back)
    void OnClickBack() {
        finish();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @OnClick(R.id.lly_game_bg)
    void OnClickManBg() {
        if (_adKind != 1) {
            _adKind = 1;
        }

        updateAdKind();
    }

    @OnClick(R.id.lly_video_bg)
    void OnClickWomanBg() {
        if (_adKind != 2) {
            _adKind = 2;
        }

        updateAdKind();
    }

    @OnClick({R.id.fly_period_from, R.id.edt_period_from, R.id.imv_period_from_cal})
    void OnClickPeriodFromCal() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        String dateFrom = ui_edtPeriodFrom.getText().toString();
        if (!dateFrom.isEmpty()) {
            try {
                year = Integer.valueOf(dateFrom.substring(0, 4));
                month = Integer.valueOf(dateFrom.substring(5, 7)) - 1;
                day = Integer.valueOf(dateFrom.substring(8));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        DatePickerDialog dlg = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                ui_edtPeriodFrom.setText(String.format(Locale.getDefault(), "%d-%02d-%02d", year, month + 1, dayOfMonth));
            }
        }, year, month, day);
        dlg.show();
    }

    @OnClick({R.id.fly_period_to, R.id.edt_period_to, R.id.imv_period_to_cal})
    void OnClickPeriodToCal() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        String dateFrom = ui_edtPeriodTo.getText().toString();
        if (!dateFrom.isEmpty()) {
            try {
                year = Integer.valueOf(dateFrom.substring(0, 4));
                month = Integer.valueOf(dateFrom.substring(5, 7)) - 1;
                day = Integer.valueOf(dateFrom.substring(8));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        DatePickerDialog dlg = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                ui_edtPeriodTo.setText(String.format(Locale.getDefault(), "%d-%02d-%02d", year, month + 1, dayOfMonth));
            }
        }, year, month, day);
        dlg.show();
    }

    @Override
    protected void onPermissionGranted(int code) {
        super.onPermissionGranted(code);
        if (code == PERMISSIONS_MEDIA_REQUEST) {
            getFile();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MediaManager.CROP_IMAGE
                || requestCode == MediaManager.SET_CAMERA
                || requestCode == MediaManager.SET_GALLERY
                || requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            mediaManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    MediaManager mediaManager;
    File adFile = null;

    void getFile() {
        mediaManager.getMediaFromGallery(_adKind == 2/*, _adKind == 2 ? "video/mpeg4" : "image/jpeg"*/);
    }

    @OnClick(R.id.txv_getfile)
    void OnClickGetFile() {
        requestPermission(PERMISSION_STORAGE, PERMISSIONS_MEDIA_REQUEST);
    }

    @OnClick(R.id.txv_regadver)
    void OnClickRegAdver() {
        String periodFrom = ui_edtPeriodFrom.getText().toString();
        String periodTo = ui_edtPeriodTo.getText().toString();
        String title = ui_edtAdName.getText().toString();
        String url = ui_edtLink.getText().toString();
        String count = ui_edtCount.getText().toString();
        String cost = ui_edtBudget.getText().toString();

        if (periodFrom.isEmpty() || periodTo.isEmpty()) {
            Toaster.showShort(this, "광고기간을 선택해주세요.");
            return;
        }
        if (periodFrom.compareTo(periodTo) > 0) {
            Toaster.showShort(this, "광고기간을 확인해주세요.");
            return;
        }
        if (adFile == null) {
            Toaster.showShort(this, "파일을 선택해주세요.");
            return;
        }
        if (title.isEmpty()) {
            Toaster.showShort(this, "광고상품명을 입력해주세요.");
            return;
        }
        if (url.isEmpty()) {
            Toaster.showShort(this, "광고기간을 확인해주세요.");
            return;
        }
        if (count.isEmpty()) {
            Toaster.showShort(this, "광고제공인원을 입력해주세요.");
            return;
        }
//        if (_nAgreeAdver == 0) {
//            Toaster.showShort(this, "광고신청약관에 동의해주세요.");
//            return;
//        }


        RequestBody usrId = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(MyInfo.getInstance().userInfo.info.id));
        RequestBody type = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(_adKind));
        RequestBody from = RequestBody.create(MediaType.parse("multipart/form-data"), periodFrom);
        RequestBody to = RequestBody.create(MediaType.parse("multipart/form-data"), periodTo);
        RequestBody adName = RequestBody.create(MediaType.parse("multipart/form-data"), title);
        RequestBody adUrl = RequestBody.create(MediaType.parse("multipart/form-data"), url);
        RequestBody adCount = RequestBody.create(MediaType.parse("multipart/form-data"), count);
        RequestBody adCost = RequestBody.create(MediaType.parse("multipart/form-data"), cost);
        RequestBody file = RequestBody.create(MediaType.parse("multipart/form-data"), adFile);
        MultipartBody.Part profile = MultipartBody.Part.createFormData("file", adFile.getName(), file);

        showProgress(this);
        Net.instance().api.registerAd(usrId, type, from, to, adName, adUrl, adCount, adCost, profile)
                .enqueue(new Net.ResponseCallBack<MBase>() {
                    @Override
                    public void onSuccess(MBase response) {
                        super.onSuccess(response);
                        hideProgress();
                        Toaster.showShort(RegAdverActivity.this, "광고신청되었습니다.");
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

    @OnClick(R.id.imv_agree_adterm)
    void OnClickAgreeAdver() {
        _nAgreeAdver = 1 - _nAgreeAdver;
        updateAdver();
    }

    private int _nAgreeAdver = 0;

    private void updateAdver() {
        if (_nAgreeAdver == 1) {
            ui_imvAgreeAdTerm.setBackgroundResource(R.drawable.icon06);
        } else {
            ui_imvAgreeAdTerm.setBackgroundResource(R.drawable.icon05);
        }
    }

}
