package com.bene.pictures.ui.main.friendlist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.bene.pictures.R;
import com.bene.pictures.data.Constant;
import com.bene.pictures.data.MyInfo;
import com.bene.pictures.fcm.EventMessage;
import com.bene.pictures.model.MBase;
import com.bene.pictures.model.MError;
import com.bene.pictures.model.MFriendsList;
import com.bene.pictures.model.MKakaoFriend;
import com.bene.pictures.model.MUser;
import com.bene.pictures.net.Net;
import com.bene.pictures.ui.adapter.FriendslistAdapter;
import com.bene.pictures.ui.widget.BaseEditText;
import com.bene.pictures.util.Toaster;
import com.kakao.auth.AuthType;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.friends.AppFriendContext;
import com.kakao.friends.response.AppFriendsResponse;
import com.kakao.kakaotalk.callback.TalkResponseCallback;
import com.kakao.kakaotalk.v2.KakaoTalkService;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.exception.KakaoException;
import com.kakao.util.helper.log.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.bene.pictures.MyApplication.g_isCallKakaoFriends;
import static com.bene.pictures.MyApplication.g_kakaoFriendList;

public class FriendListFragment extends FriendListBaseFragment {
    public FriendListFragment() {

    }

    public static FriendListFragment newInstance() {
        return new FriendListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ui_rootView = inflater.inflate(R.layout.fragment_friendlist,
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

    @BindView(R.id.edt_search)
    BaseEditText ui_edtSearch;

    @BindView(R.id.rcv_contents)
    RecyclerView ui_rcvContents;

    FriendslistAdapter adpFriendsList;

    private boolean loadingEnd = false;
    private boolean lockRcv = false;

    private void initUI() {

        MUser.UserInfo user = MyInfo.getInstance().userInfo.info;

        if (Constant.NET_DEV_MODE == 2) {
            if (MyInfo.getInstance().userInfo.info.kt_id.isEmpty()) {
                //  카톡련동이 안되어 잇는 경우
                ui_flyKakaoBg.setVisibility(g_isCallKakaoFriends ? View.GONE : View.VISIBLE);
            } else {
                ui_flyKakaoBg.setVisibility(View.GONE);
            }
        }


        adpFriendsList = new FriendslistAdapter(_activity, new ArrayList<MFriendsList.Info>(), new FriendslistAdapter.OnFriendsListItemListener() {
            @Override
            public void OnClickItem(int position) {

                MFriendsList.Info info = (MFriendsList.Info) adpFriendsList.getItem(position);
//                _activity.reqFriendAction(info.id, 0, "present", "");
                reqFriendAction(info.id, 0, "present", "");
            }
        }, 0);

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

    public void reqFriendAction(int friend_id, int msg_id, String kind, String kakao) {

        Net.instance().api.reqFriendAction(MyInfo.getInstance().userInfo.info.id, friend_id, msg_id, kind, kakao)
                .enqueue(new Net.ResponseCallBack<MBase>() {
                    @Override
                    public void onSuccess(MBase response) {
                        super.onSuccess(response);

                        Toaster.showShort(_activity, "선물을 보냈습니다.");
                        initData();
                    }

                    @Override
                    public void onFailure(MError response) {
                        super.onFailure(response);
//                        if (BuildConfig.DEBUG) {
//                            Toaster.showShort(FriendListActivity.this, "조작이 성공하였습니다.");
//                        } else {
                        //Toaster.showShort(FriendListActivity.this, response.res_msg);
//                        }
                    }
                });
    }

    private void updateKakaoBg() {
        ui_flyKakaoBg.setVisibility(View.GONE);
    }

    @Override
    public void refresh() {
        super.refresh();

        if (MyInfo.getInstance().userInfo.info.kt_id.isEmpty()) {
            //  카톡련동이 안되어 잇는 경우
            ui_flyKakaoBg.setVisibility(g_isCallKakaoFriends ? View.GONE : View.VISIBLE);
        } else {
            ui_flyKakaoBg.setVisibility(View.GONE);
        }

        if (adpFriendsList == null) {
            return;
        }

        initData();
    }

    void initData() {
        String ss = MyInfo.getInstance().userInfo.info.kt_id;
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

        String search = ui_edtSearch.getText().toString().trim();

        if (!MyInfo.getInstance().isValid()) {
            return;
        }

        _activity.showProgress(_activity);
        lockRcv = true;

        // 카톡친구 아이디리스트를 조회한다.
        List<String> ktIdList = new ArrayList<>();
        for (MKakaoFriend info : g_kakaoFriendList) {
            ktIdList.add(info.id);
        }

        //TODO: 4번째 파라미터에 카카오친구들의 카카오아이디를 콤마로 구분한 문짜열을 넘긴다.
        Net.instance().api.getFriendsList(MyInfo.getInstance().userInfo.info.id, 0, search, TextUtils.join(",", ktIdList), page_num)
                .enqueue(new Net.ResponseCallBack<MFriendsList>() {
                    @Override
                    public void onSuccess(MFriendsList response) {
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
//                            ArrayList<MFriendsList.Info> buffer = new ArrayList<>();
//
//                            for (int i = 0; i < 20; i++) {
//                                MFriendsList.Info bufInfo = new MFriendsList.Info();
//                                bufInfo.id = i;
//                                bufInfo.face = (i + 1) + "번째 친구의 프로필이미지";
//                                bufInfo.name = (i + 1) + "번째 친구의 이름";
//                                bufInfo.cnt_sendgift = 0;
//                                if (i == 4 || i == 8 || i == 10 || i == 14 || i == 17 || i == 18) {
//                                    bufInfo.cnt_sendgift = 1;
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

    private void kakao_profile_replace(ArrayList<MFriendsList.Info> list){
        for(int i =0; i< list.size(); i++){
            for(int j=0; j<g_kakaoFriendList.size(); j++){
                if(list.get(i).face.equals(g_kakaoFriendList.get(j).id)){
                    list.get(i).profile = g_kakaoFriendList.get(j).profile;
                }
            }
        }
    }

    @OnClick(R.id.txv_search)
    void OnClickSearch() {
//        page_num = 1;
//        adpFriendsList.clear();
//        getFriendsList();
        initData();
    }

    @OnClick({R.id.fly_kakao_bg, R.id.imv_kakao})
    void OnClickKaKaoBg() {
        _activity.onKakaoLogin_call();
    }

}