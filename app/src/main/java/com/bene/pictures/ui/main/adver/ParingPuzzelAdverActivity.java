package com.bene.pictures.ui.main.adver;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bene.pictures.R;
import com.bene.pictures.data.MyInfo;
import com.bene.pictures.model.MAdver;
import com.bene.pictures.model.MBase;
import com.bene.pictures.model.MError;
import com.bene.pictures.net.Net;
import com.bene.pictures.ui.base.BaseActivity;
import com.bene.pictures.ui.dialog.ConfirmDialogActivity;
import com.bene.pictures.util.Toaster;
import com.bene.pictures.util.Util;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;

public class ParingPuzzelAdverActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paring_puzzel_adver);

        initAdver();
    }

    MAdver.Info _adver = null;

    private void initAdver() {

        Intent intent = getIntent();
        _adver = intent.getParcelableExtra("adver");

        if (_adver == null) {
            setResult(RESULT_CANCELED);
            finish();
        }

        new Thread(new Runnable() {

            public void run() {

                try {
                    bmp = Util.LoadRoundedImageFromWebUrl(ParingPuzzelAdverActivity.this, _adver.ad_image, 1);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                _handler.sendEmptyMessage(1568);
            }
        }).start();
    }

    Handler _handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {

            if (bmp == null) {
                setResult(RESULT_CANCELED);
                finish();
            }

            initData();

            return false;
        }
    });

    @Override
    public void onBackPressed() {
        String strTitle = "광고시청을 중단하시겠습니까?\n광고시청을 중간에 중단해도 퍼즐이 줄어들지 않습니다.";

        Intent showConfirm = new Intent(ParingPuzzelAdverActivity.this, ConfirmDialogActivity.class);
        showConfirm.putExtra("content", strTitle);
        showConfirm.putExtra("span_start", 31);
        showConfirm.putExtra("span_end", 33);
        showConfirm.putExtra("no", "네");
        showConfirm.putExtra("yes", "아니요");
        startActivityForResult(showConfirm, 1015);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1015 && resultCode == RESULT_CANCELED) {
            setResult(RESULT_CANCELED);
            finish();
        }
    }

    @BindView(R.id.imv_puzzle_1)
    ImageView ui_imvPuzzle1;
    @BindView(R.id.imv_puzzle_2)
    ImageView ui_imvPuzzle2;
    @BindView(R.id.imv_puzzle_3)
    ImageView ui_imvPuzzle3;
    @BindView(R.id.imv_puzzle_4)
    ImageView ui_imvPuzzle4;
    @BindView(R.id.imv_puzzle_5)
    ImageView ui_imvPuzzle5;
    @BindView(R.id.imv_puzzle_6)
    ImageView ui_imvPuzzle6;
    @BindView(R.id.imv_puzzle_7)
    ImageView ui_imvPuzzle7;
    @BindView(R.id.imv_puzzle_8)
    ImageView ui_imvPuzzle8;
    @BindView(R.id.imv_puzzle_9)
    ImageView ui_imvPuzzle9;
    @BindView(R.id.imv_puzzle_10)
    ImageView ui_imvPuzzle10;
    @BindView(R.id.imv_puzzle_11)
    ImageView ui_imvPuzzle11;
    @BindView(R.id.imv_puzzle_12)
    ImageView ui_imvPuzzle12;

    ArrayList<ImageView> arlPuzzleImvs = new ArrayList<>();
    ArrayList<Integer> arClickIds = new ArrayList<>();

    @BindView(R.id.imv_original)
    ImageView ui_imvOriginal;

    private boolean isOneChecked = false;
    private int checkedOneIdx = 0;
    private int checkedSecondIdx = 0;

    private int successCnt = 0;

    public void initGame() {

        ui_imvOriginal.setImageBitmap(bmp);

        arlPuzzleImvs.add(ui_imvPuzzle1);
        arlPuzzleImvs.add(ui_imvPuzzle2);
        arlPuzzleImvs.add(ui_imvPuzzle3);
        arlPuzzleImvs.add(ui_imvPuzzle4);
        arlPuzzleImvs.add(ui_imvPuzzle5);
        arlPuzzleImvs.add(ui_imvPuzzle6);
        arlPuzzleImvs.add(ui_imvPuzzle7);
        arlPuzzleImvs.add(ui_imvPuzzle8);
        arlPuzzleImvs.add(ui_imvPuzzle9);
        arlPuzzleImvs.add(ui_imvPuzzle10);
        arlPuzzleImvs.add(ui_imvPuzzle11);
        arlPuzzleImvs.add(ui_imvPuzzle12);

        Collections.shuffle(arlPuzzleImvs);

        int index = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {

                FrameLayout.LayoutParams fparam = (FrameLayout.LayoutParams) arlPuzzleImvs.get(index).getLayoutParams();
                fparam.leftMargin = screen_width / 3 * j;
                fparam.topMargin = screen_height / 4 * i;
                fparam.width = screen_width / 3;
                fparam.height = screen_height / 4;
                arlPuzzleImvs.get(index).setLayoutParams(fparam);

                index++;
            }
        }

        for (int i = 0; i < arlPuzzleImvs.size(); i++) {
            final int idxCard = i;

            arlPuzzleImvs.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (!isRun) {
                        return;
                    }

                    if(isMotion){
                        return;
                    }

                    if(arClickIds.indexOf(idxCard) >= 0 ){
                        return;
                    }

                    if (isOneChecked && checkedOneIdx != idxCard) {// 같은 타일을 두번 누르면 짝이 맞은걸로 취급되는 오류 수정
                        changeBackground(idxCard);

                        int tag1 = (int) arlPuzzleImvs.get(checkedOneIdx).getTag();
                        int tag2 = (int) arlPuzzleImvs.get(idxCard).getTag();

                        if (tag1 == tag2) {
                            isMotion = true;
                            successCnt++;
                            animationFlipApply180(checkedOneIdx);
                            animationFlipApply180(idxCard);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if (successCnt == 6) {
                                        Toaster.showShort(ParingPuzzelAdverActivity.this, "축하드립니다.");
                                        gonePuzzleViews();
                                        isRun = false;
                                        homeHandler.sendEmptyMessageDelayed(1, 3000);
                                    } else {
                                        arClickIds.add(checkedOneIdx);
                                        arClickIds.add(idxCard);
                                        arlPuzzleImvs.get(checkedOneIdx).setBackgroundColor(getResources().getColor(R.color.color_transparent));
                                        arlPuzzleImvs.get(idxCard).setBackgroundColor(getResources().getColor(R.color.color_transparent));
                                        Toaster.showShort(ParingPuzzelAdverActivity.this, "빙고!");

                                    }

                                    isMotion = false;
                                }
                            }, 1000);


                        } else {
                            checkedSecondIdx = idxCard;
                            isRun = false;
                            homeHandler.sendEmptyMessageDelayed(0, 1500);
                            Toaster.showShort(ParingPuzzelAdverActivity.this, "틀렸어요 -_-");
                        }

                        isOneChecked = false;

                    } else {
                        isOneChecked = true;
                        checkedOneIdx = idxCard;

                        changeBackground(idxCard);
                    }
                }
            });
        }

        for (int i = 0; i < arlPuzzleImvs.size(); i++) {
            arlPuzzleImvs.get(i).setImageResource(R.drawable.icon12);
            arlPuzzleImvs.get(i).setBackgroundResource(R.drawable.xml_bg3_rect_ffffff_stroke);
            arlPuzzleImvs.get(i).setScaleType(ImageView.ScaleType.CENTER);
        }

        ui_imvPuzzle1.setTag(1);
        ui_imvPuzzle2.setTag(1);

        ui_imvPuzzle3.setTag(2);
        ui_imvPuzzle4.setTag(2);

        ui_imvPuzzle5.setTag(3);
        ui_imvPuzzle6.setTag(3);

        ui_imvPuzzle7.setTag(4);
        ui_imvPuzzle8.setTag(4);

        ui_imvPuzzle9.setTag(5);
        ui_imvPuzzle10.setTag(5);

        ui_imvPuzzle11.setTag(6);
        ui_imvPuzzle12.setTag(6);

        isRun = true;
    }

    private void animationFlipApply90(int idxCard){
        ObjectAnimator animator = ObjectAnimator.ofFloat(arlPuzzleImvs.get(idxCard), "rotationY", 0, 90);
        animator.setDuration(500);
        animator.start();
    }

    private void animationFlipApply180(int idxCard){
        ObjectAnimator animator = ObjectAnimator.ofFloat(arlPuzzleImvs.get(idxCard), "rotationY", 0, 180);
        animator.setDuration(1000);
        animator.start();
    }

    private void rollBackBackground(int idxCard) {

        arlPuzzleImvs.get(idxCard).setImageResource(R.drawable.icon12);
        arlPuzzleImvs.get(idxCard).setBackgroundResource(R.drawable.xml_bg3_rect_ffffff_stroke);
    }

    private void gonePuzzleViews() {
        for (int i = 0; i < arlPuzzleImvs.size(); i++) {
            arlPuzzleImvs.get(i).setVisibility(View.GONE);
        }
    }

    private void changeBackground(int idxCard) {

        arlPuzzleImvs.get(idxCard).setImageResource(0);
        int tag = (int) arlPuzzleImvs.get(idxCard).getTag();
        switch (tag) {
            case 1:
                arlPuzzleImvs.get(idxCard).setBackgroundResource(R.drawable.xml_paring_bg1);
                break;
            case 2:
                arlPuzzleImvs.get(idxCard).setBackgroundResource(R.drawable.xml_paring_bg2);
                break;
            case 3:
                arlPuzzleImvs.get(idxCard).setBackgroundResource(R.drawable.xml_paring_bg3);
                break;
            case 4:
                arlPuzzleImvs.get(idxCard).setBackgroundResource(R.drawable.xml_paring_bg4);
                break;
            case 5:
                arlPuzzleImvs.get(idxCard).setBackgroundResource(R.drawable.xml_paring_bg5);
                break;
            case 6:
                arlPuzzleImvs.get(idxCard).setBackgroundResource(R.drawable.xml_paring_bg6);
                break;
        }
    }

    private int screen_width;
    private int screen_height;
    private Bitmap bmp;

    private void initData() {

       /* try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPurgeable = true; // declare as purgeable to disk
            bmp = BitmapFactory.decodeResource(getResources(), R.drawable.bg1,
                    options);
        } catch (RuntimeException e1) {
            e1.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        screen_width = metrics.widthPixels;
        screen_height = metrics.heightPixels;

        initGame();
    }

    boolean isRun = false;
    boolean isMotion = false;


    Handler homeHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {

            if (msg.what == 0) {
                isRun = true;

                rollBackBackground(checkedSecondIdx);
                rollBackBackground(checkedOneIdx);
            } else if (msg.what == 1) {
//                reqAdComplete();
                setResult(RESULT_OK);
                finish();
            }
            return false;
        }
    });

    private void reqAdComplete() {
        showProgress(this);
        Net.instance().api.reqAdComplelte(MyInfo.getInstance().userInfo.info.id, _adver.id, _adver.log, 0)
                .enqueue(new Net.ResponseCallBack<MBase>() {
                    @Override
                    public void onSuccess(MBase response) {
                        super.onSuccess(response);
                        hideProgress();

                        setResult(RESULT_OK);
                        finish();
                    }

                    @Override
                    public void onFailure(MError response) {
                        super.onFailure(response);
//                        if (BuildConfig.DEBUG) {
//                            setResult(RESULT_OK);
//                            finish();
//                        } else {
                        hideProgress();

                        Toaster.showShort(ParingPuzzelAdverActivity.this, "서버상태가 불안정하여 광고시청결과가 등록되지 못하였습니다.");
                        setResult(RESULT_CANCELED);
                        finish();
//                        }
                    }
                });
    }
}
