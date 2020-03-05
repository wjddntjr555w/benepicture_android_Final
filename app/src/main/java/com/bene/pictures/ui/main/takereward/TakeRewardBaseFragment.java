package com.bene.pictures.ui.main.takereward;

import android.support.v4.app.Fragment;
import android.view.View;

import com.bene.pictures.ui.main.mypage.MypageActivity;

public class TakeRewardBaseFragment extends Fragment {

    public View ui_rootView;

    public TakeRewardActivity _activity;

    public interface OnChoosePageListener {
        void OnChoose(String msg);
    }

    public OnChoosePageListener OnChoosePageListener;

    public void SetActivity(TakeRewardActivity activity, OnChoosePageListener OnChoosePageListener) {
        _activity = activity;
        this.OnChoosePageListener = OnChoosePageListener;
    }

    public void refresh() {

    }
}
