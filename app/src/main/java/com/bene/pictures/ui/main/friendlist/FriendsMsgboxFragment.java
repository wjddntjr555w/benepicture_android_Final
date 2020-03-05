package com.bene.pictures.ui.main.friendlist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.bene.pictures.R;
import com.bene.pictures.data.Constant;
import com.bene.pictures.data.MyInfo;
import com.bene.pictures.fcm.EventMessage;
import com.bene.pictures.model.MError;
import com.bene.pictures.model.MFriendMsgList;
import com.bene.pictures.model.MFriendsList;
import com.bene.pictures.net.Net;
import com.bene.pictures.ui.adapter.FriendsMsglistAdapter;
import com.kakao.auth.AuthType;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.exception.KakaoException;
import com.kakao.util.helper.log.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.bene.pictures.MyApplication.g_isCallKakaoFriends;
import static com.bene.pictures.MyApplication.g_kakaoFriendList;

public class FriendsMsgboxFragment extends FriendListBaseFragment {
    public FriendsMsgboxFragment() {

    }

    public static FriendsMsgboxFragment newInstance() {
        return new FriendsMsgboxFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ui_rootView = inflater.inflate(R.layout.fragment_friends_msgbox,
                container, false);

        ButterKnife.bind(this, ui_rootView);
        EventBus.getDefault().register(this);

        initUI();

        initData();

        return ui_rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onMessaage(EventMessage msg) {

        if (msg.nWhat == 1001) {

        }
    }

    @BindView(R.id.fly_kakao_bg)
    FrameLayout ui_flyKakaoBg;

    @BindView(R.id.rcv_contents)
    RecyclerView ui_rcvContents;

    FriendsMsglistAdapter adpFriendsList;

    private boolean loadingEnd = false;
    private boolean lockRcv = false;

    private void initUI() {

        if (Constant.NET_DEV_MODE == 2) {
            if (MyInfo.getInstance().userInfo.info.kt_id.isEmpty()) {
                //  카톡련동이 안되어 잇는 경우
                ui_flyKakaoBg.setVisibility(g_isCallKakaoFriends ? View.GONE : View.VISIBLE);
            } else {
                ui_flyKakaoBg.setVisibility(View.GONE);
            }
        }

        adpFriendsList = new FriendsMsglistAdapter(_activity, new ArrayList<MFriendMsgList.Info>(), new FriendsMsglistAdapter.OnFriendsListItemListener() {
            @Override
            public void OnClickItem(int yesno, int position) {
                MFriendMsgList.Info info = (MFriendMsgList.Info) adpFriendsList.getItem(position);
                if (yesno == 0) {
                    _activity.reqFriendAction(info.friend_id, info.id, "reject_gift", "");
                } else {
                    _activity.reqFriendAction(info.friend_id, info.id, "accept_gift", "");
                }
            }
        });

        ui_rcvContents.setAdapter(adpFriendsList);

        ui_rcvContents.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int firstVisibleItem = recyclerView.getChildAdapterPosition(recyclerView.getChildAt(0));
                int visibleCnt = recyclerView.getChildCount();
                int totalItemCnt = adpFriendsList.getItemCount();

                if (loadingEnd) {
                    return;
                }

                if (totalItemCnt != 0 && firstVisibleItem >= (totalItemCnt - visibleCnt) && !lockRcv) {
                    getFriendsList();
                }
            }
        });
    }

    private void updateKakaoBg() {
        ui_flyKakaoBg.setVisibility(View.GONE);
    }

    @Override
    public void refresh() {
        super.refresh();

        if (adpFriendsList == null) {
            return;
        }

        initData();
    }

    void initData() {
        if (MyInfo.getInstance().userInfo.info.kt_id.isEmpty()) {
            //  카톡련동이 안되어 잇는 경우
            ui_flyKakaoBg.setVisibility(g_isCallKakaoFriends ? View.GONE : View.VISIBLE);
        } else {
            ui_flyKakaoBg.setVisibility(View.GONE);
        }

        if(!MyInfo.getInstance().userInfo.info.kt_id.isEmpty()){
            if(g_isCallKakaoFriends){
                page_num = 1;
                loadingEnd = false;
                lockRcv = false;

                adpFriendsList.clear();
                adpFriendsList.notifyDataSetChanged();

                getFriendsList();
            } else {
                _activity.onKakaoLogin_call();
            }
        } else {
            if(g_isCallKakaoFriends){
                page_num = 1;
                loadingEnd = false;
                lockRcv = false;

                adpFriendsList.clear();
                adpFriendsList.notifyDataSetChanged();

                getFriendsList();
            }
        }
    }

    int page_num = 1;

    private void getFriendsList() {

        if (!MyInfo.getInstance().isValid()) {
            return;
        }

        _activity.showProgress(_activity);
        lockRcv = true;

        Net.instance().api.getFriendMsgList(MyInfo.getInstance().userInfo.info.id, "", page_num)
                .enqueue(new Net.ResponseCallBack<MFriendMsgList>() {
                    @Override
                    public void onSuccess(MFriendMsgList response) {
                        super.onSuccess(response);

                        lockRcv = false;

                        if (page_num == 1) {
                            adpFriendsList.clear();
                        }

                        if (response.page_cnt <= page_num) {
                            loadingEnd = true;
                        } else {
                            page_num++;
                        }

                        kakao_profile_replace(response.list);

                        adpFriendsList.addAll(response.list);

                        _activity.hideProgress();
                        adpFriendsList.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(MError response) {
                        super.onFailure(response);

//                        if (BuildConfig.DEBUG) {
//
//                            ArrayList<MFriendMsgList.Info> buffer = new ArrayList<>();
//
//                            for (int i = 0; i < 20; i++) {
//                                MFriendMsgList.Info bufInfo = new MFriendMsgList.Info();
//                                bufInfo.id = i;
//                                bufInfo.face = (i + 1) + "번째 친구의 프로필이미지";
//                                bufInfo.name = (i + 1) + "번째 친구의 이름";
//                                bufInfo.receivegift_status = 0;
//                                if (i == 4 || i == 8 || i == 10 || i == 14 || i == 17 || i == 18) {
//                                    bufInfo.receivegift_status = 1;
//                                }
//                                buffer.add(bufInfo);
//                            }
//
//                            adpFriendsList.addAll(buffer);
//                            _activity.hideProgress();
//                            adpFriendsList.notifyDataSetChanged();
//
//                        } else {
                        _activity.hideProgress();
                        lockRcv = false;
                        _activity.showErrorMsg(response);
//                        }
                    }
                });
    }

    private void kakao_profile_replace(ArrayList<MFriendMsgList.Info> list){
        for(int i =0; i< list.size(); i++){
            for(int j=0; j<g_kakaoFriendList.size(); j++){
                if(list.get(i).face.equals(g_kakaoFriendList.get(j).id)){
                    list.get(i).profile = g_kakaoFriendList.get(j).profile;
                }
            }
        }
    }

    @OnClick(R.id.fly_kakao_bg)
    void OnClickKakaoBg() {
//        _activity.onKakaoLogin_call();
    }

    @OnClick(R.id.imv_kakao)
    void OnClickKakaoLogin() {
        _activity.onKakaoLogin_call();
    }

}