package com.bene.pictures.ui.main.msgbox;

import android.support.v4.app.Fragment;
import android.view.View;

public class MsgBoxBaseFragment extends Fragment {

    public View ui_rootView;

    public MsgBoxActivity _activity;

    public interface OnChooseMsgListener {
        void OnChoose(String msg);
    }

    public OnChooseMsgListener onChooseMsgListener;

    public void SetActivity(MsgBoxActivity activity, OnChooseMsgListener onChooseMsgListener) {
        _activity = activity;
        this.onChooseMsgListener = onChooseMsgListener;
    }

    public void refresh() {

    }
}
