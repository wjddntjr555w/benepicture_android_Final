package com.bene.pictures.ui.main.friendlist;

import android.support.v4.app.Fragment;
import android.view.View;

public class FriendListBaseFragment extends Fragment {

    public View ui_rootView;

    public FriendListActivity _activity;

    public interface OnChooseListener {
        void OnChoose(String msg);
    }

    public OnChooseListener OnChooseListener;

    public void SetActivity(FriendListActivity activity, OnChooseListener OnChooseListener) {
        _activity = activity;
        this.OnChooseListener = OnChooseListener;
    }

    public void refresh() {

    }
}
