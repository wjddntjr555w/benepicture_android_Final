package com.bene.pictures.ui.main.adver;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.android.tedcoder.wkvideoplayer.model.Video;
import com.android.tedcoder.wkvideoplayer.model.VideoUrl;
import com.android.tedcoder.wkvideoplayer.view.SuperVideoPlayer;
import com.bene.pictures.R;
import com.bene.pictures.model.MAdver;
import com.bene.pictures.ui.base.BaseActivity;
import com.bene.pictures.ui.dialog.ConfirmDialogActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import butterknife.BindView;

public class VideoAdverViewActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_adver_view);

        initUI();
    }

    @Override
    public void onBackPressed() {
        String strTitle = "광고시청을 중단하시겠습니까?\n광고시청을 중간에 중단해도 퍼즐이 줄어들지 않습니다.";

        Intent showConfirm = new Intent(VideoAdverViewActivity.this, ConfirmDialogActivity.class);
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

    MAdver.Info _adver = null;

    @BindView(R.id.imv_ad_videothumb)
    ImageView ui_imvAdVideoThumb;
//
//    @BindView(R.id.imv_ad_videoplay)
//    ImageView ui_imvAdVideoPlay;

    @BindView(R.id.svp_ad_videoplayer)
    SuperVideoPlayer ui_svpAdVideoPlayer;

    public void initUI() {
        super.initUI();

        Intent intent = getIntent();
        _adver = intent.getParcelableExtra("adver");

        if (_adver == null) {
            setResult(RESULT_CANCELED);
            finish();
        }

        Glide.with(this)
                .load(_adver.ad_image)
                .apply(new RequestOptions().centerCrop())
                .into(ui_imvAdVideoThumb);

        ui_svpAdVideoPlayer.setVideoPlayCallback(new SuperVideoPlayer.VideoPlayCallbackImpl() {
            @Override
            public void onCloseVideo() {
                setResult(RESULT_OK);
                finish();
            }

            @Override
            public void onSwitchPageType() {

            }

            @Override
            public void onPlayFinish() {
                onCloseVideo();
            }

            @Override
            public void onVideoPrepared() {

            }

            @Override
            public void onStopVideo() {
                onCloseVideo();
            }
        });

        Video video = new Video();
        VideoUrl videoUrl1 = new VideoUrl();
        videoUrl1.setFormatName("720p");
        videoUrl1.setFormatUrl(_adver.ad_video);//
        ArrayList<VideoUrl> arrayList1 = new ArrayList<>();
        arrayList1.add(videoUrl1);
        video.setVideoName(_adver.ad_video);
        video.setVideoUrl(arrayList1);

        ArrayList<Video> videoArrayList = new ArrayList<>();
        videoArrayList.add(video);

        ui_svpAdVideoPlayer.loadMultipleVideo(videoArrayList, 0, 0, 0);
    }
}
