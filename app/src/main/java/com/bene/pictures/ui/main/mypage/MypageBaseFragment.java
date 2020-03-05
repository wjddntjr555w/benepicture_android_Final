package com.bene.pictures.ui.main.mypage;

import android.support.v4.app.Fragment;
import android.view.View;

public class MypageBaseFragment extends Fragment {

    public View ui_rootView;

    public MypageActivity _activity;

    public interface OnChooseMsgListener {
        void OnChoose(String msg);
    }

    public OnChooseMsgListener onChooseMsgListener;

    public void SetActivity(MypageActivity activity, OnChooseMsgListener onChooseMsgListener) {
        _activity = activity;
        this.onChooseMsgListener = onChooseMsgListener;
    }

    public void refresh() {

    }
}
