package com.bene.pictures.ui.main.adver;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

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

public class SwitchingPuzzleAdverActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switching_puzzle_adver);

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
                    bmp = Util.LoadRoundedImageFromWebUrl(SwitchingPuzzleAdverActivity.this, _adver.ad_image, 1);
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

        Intent showConfirm = new Intent(SwitchingPuzzleAdverActivity.this, ConfirmDialogActivity.class);
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

    @BindView(R.id.vw_ani1)
    View ui_vwAni1;
    @BindView(R.id.vw_ani2)
    View ui_vwAni2;
    @BindView(R.id.vw_ani3)
    View ui_vwAni3;
    @BindView(R.id.vw_ani4)
    View ui_vwAni4;
    @BindView(R.id.vw_ani5)
    View ui_vwAni5;
    @BindView(R.id.vw_ani6)
    View ui_vwAni6;
    @BindView(R.id.vw_ani7)
    View ui_vwAni7;
    @BindView(R.id.vw_ani8)
    View ui_vwAni8;
    @BindView(R.id.vw_ani9)
    View ui_vwAni9;

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

    ArrayList<ImageView> arlImageViews = new ArrayList<>();
    ArrayList<View> arlVwViews = new ArrayList<>();
    private boolean isOneChecked = false;
    private int checkedOneIdx = 0;

    private boolean isMotion = false;

    public void initGame() {
        arlVwViews.add(ui_vwAni1);
        arlVwViews.add(ui_vwAni2);
        arlVwViews.add(ui_vwAni3);
        arlVwViews.add(ui_vwAni4);
        arlVwViews.add(ui_vwAni5);
        arlVwViews.add(ui_vwAni6);
        arlVwViews.add(ui_vwAni7);
        arlVwViews.add(ui_vwAni8);
        arlVwViews.add(ui_vwAni9);

        arlImageViews.add(ui_imvPuzzle1);
        arlImageViews.add(ui_imvPuzzle2);
        arlImageViews.add(ui_imvPuzzle3);
        arlImageViews.add(ui_imvPuzzle4);
        arlImageViews.add(ui_imvPuzzle5);
        arlImageViews.add(ui_imvPuzzle6);
        arlImageViews.add(ui_imvPuzzle7);
        arlImageViews.add(ui_imvPuzzle8);
        arlImageViews.add(ui_imvPuzzle9);

        int index = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {

                FrameLayout.LayoutParams fparam = (FrameLayout.LayoutParams) arlImageViews.get(index).getLayoutParams();
                fparam.leftMargin = screen_width / 3 * j;
                fparam.topMargin = screen_height / 3 * i;
                fparam.width = screen_width / 3;
                fparam.height = screen_height / 3;
                arlImageViews.get(index).setLayoutParams(fparam);

                arlVwViews.get(index).setLayoutParams(fparam);

                index++;
            }
        }

        for (int i = 0; i < arlImageViews.size(); i++) {
            final int idxCard = i;

            arlImageViews.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isMotion)
                        return;

                    if (isOneChecked) {
                        hideAllAniViewe();
                        arlVwViews.get(checkedOneIdx).setVisibility(View.INVISIBLE);

                        isMotion = true;
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Collections.swap(arlShuffleInts, checkedOneIdx, idxCard);
                                reorderImageViews();

                                if (arlShuffleInts.equals(arlInts)) {
                                    Toaster.showShort(SwitchingPuzzleAdverActivity.this, "축하드립니다.");
//                            reqAdComplete();
                                    setResult(RESULT_OK);
                                    finish();
                                } else {
                                    isOneChecked = false;
                                }
                                isMotion = false;
                            }
                        }, 1000);

                    } else {
                        arlVwViews.get(idxCard).setVisibility(View.VISIBLE);
                        arlVwViews.get(idxCard).setBackgroundResource(R.drawable.xml_animation_rect);
                        AnimationDrawable frameAni = (AnimationDrawable) arlVwViews.get(idxCard).getBackground();
                        frameAni.start();

                        isOneChecked = true;
                        checkedOneIdx = idxCard;
                    }
                }
            });
        }

        reorderImageViews();
    }

    private void animationFlipApply90(int idxCard){
        ObjectAnimator animator = ObjectAnimator.ofFloat(arlImageViews.get(idxCard), "rotationY", 0, 90);
        animator.setDuration(500);
        animator.start();
    }

    private void animationFlipApply180(int idxCard){
        ObjectAnimator animator = ObjectAnimator.ofFloat(arlImageViews.get(idxCard), "rotationY", 270, 180);
        animator.setDuration(1000);
        animator.start();
    }

    private void hideAllAniViewe(){
        for(int i=0; i<arlVwViews.size(); i++) {
            arlVwViews.get(i).setBackgroundColor(getResources().getColor(R.color.color_transparent));
            arlVwViews.get(i).setVisibility(View.INVISIBLE);
        }
    }

    private void reorderImageViews() {
        for (int i = 0; i < arlImageViews.size(); i++) {
            arlImageViews.get(i).setImageBitmap(arlBmps.get(arlShuffleInts.get(i)));
            arlImageViews.get(i).setScaleType(ImageView.ScaleType.FIT_XY);
        }
    }

    private int screen_width;
    private int screen_height;
    private int statusbar_height;

    private Bitmap bmp;
    private ArrayList<Bitmap> arlBmps = new ArrayList<>();
    private ArrayList<Integer> arlInts = new ArrayList<>();
    private ArrayList<Integer> arlShuffleInts = new ArrayList<>();

    private void initData() {

        int bmpWidth = bmp.getWidth();
        int bmpHeight = bmp.getHeight();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {

                Bitmap buf = Bitmap.createBitmap(bmp, bmpWidth / 3 * j, bmpHeight / 3 * i, bmpWidth / 3, bmpHeight / 3);
                arlBmps.add(buf);
            }
        }

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        statusbar_height = 0;
        if (resourceId > 0) {
            statusbar_height = getResources().getDimensionPixelSize(resourceId);
        }

        screen_width = metrics.widthPixels;
        screen_height = metrics.heightPixels + statusbar_height;

        arlInts.add(0);
        arlInts.add(1);
        arlInts.add(2);
        arlInts.add(3);
        arlInts.add(4);
        arlInts.add(5);
        arlInts.add(6);
        arlInts.add(7);
        arlInts.add(8);

        arlShuffleInts.add(0);
        arlShuffleInts.add(1);
        arlShuffleInts.add(2);
        arlShuffleInts.add(3);
        arlShuffleInts.add(4);
        arlShuffleInts.add(5);
        arlShuffleInts.add(6);
        arlShuffleInts.add(7);
        arlShuffleInts.add(8);

        Collections.shuffle(arlShuffleInts);

        initGame();
    }

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
                        Toaster.showShort(SwitchingPuzzleAdverActivity.this, "서버상태가 불안정하여 광고시청결과가 등록되지 못하였습니다.");
                        setResult(RESULT_CANCELED);
                        finish();
//                        }
                    }
                });
    }
}
