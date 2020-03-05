package com.bene.pictures.ui.main.friendlist;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import com.bene.pictures.MyApplication;
import com.bene.pictures.R;
import com.bene.pictures.data.Common;
import com.bene.pictures.data.MyInfo;
import com.bene.pictures.model.MBase;
import com.bene.pictures.model.MError;
import com.bene.pictures.model.MKakaoFriend;
import com.bene.pictures.model.MUser;
import com.bene.pictures.net.Net;
import com.bene.pictures.ui.base.BaseActivity;
import com.bene.pictures.ui.login.LoginActivity;
import com.bene.pictures.util.PrefMgr;
import com.bene.pictures.util.Toaster;
import com.google.gson.Gson;
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

import butterknife.BindView;
import butterknife.OnClick;

import static com.bene.pictures.MyApplication.g_kakaoFriendList;
import static com.bene.pictures.MyApplication.g_isCallKakaoFriends;


public class FriendListActivity extends BaseActivity {
    private SessionCallback_friend kakaoCallback_friend;

    boolean isKakaoCall = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);

        initKakao();

        initUI();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Session.getCurrentSession().removeCallback(kakaoCallback_friend);
    }

    @BindView(R.id.tbl_paging_menu)
    TabLayout ui_tblPagingMenu;

    @BindView(R.id.vpr_content)
    ViewPager ui_vprContentPages;

    private ContentsPageAdapter adp_ContentsPages;
    int activeTabInd = 0;

    public void initUI() {

        activeTabInd = getIntent().getIntExtra("tab", 0);

        InitFragments();

        adp_ContentsPages = new ContentsPageAdapter(getSupportFragmentManager());
        ui_vprContentPages.setAdapter(adp_ContentsPages);
        ui_vprContentPages.clearOnPageChangeListeners();
        ui_vprContentPages.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                _nCurPage = position;
                ui_tblPagingMenu.getTabAt(position).select();
                refreshFragment();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        ui_vprContentPages.setOffscreenPageLimit(3);
        ui_vprContentPages.setCurrentItem(activeTabInd);

        ui_tblPagingMenu.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                _nCurPage = tab.getPosition();
                ui_vprContentPages.setCurrentItem(_nCurPage);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        ui_tblPagingMenu.getTabAt(activeTabInd).select();

        UpdatePagingTabTextStyle();
    }

    private void UpdatePagingTabTextStyle() {
        Common.setTabItemFontAndSize(this, ui_tblPagingMenu, 0, "NanumBarunGothic.otf", 18, Typeface.BOLD);
        Common.setTabItemFontAndSize(this, ui_tblPagingMenu, 1, "NanumBarunGothic.otf", 18, Typeface.BOLD);
//        Common.setTabItemFontAndSize(this, ui_tblPagingMenu, 2, "NanumBarunGothic.otf", 18, Typeface.BOLD);
    }

    void refreshFragment() {

        switch (_nCurPage) {
            case FRIENDLIST_PAGE_FRIENDLIST:
                if (ctx_friendlistFragment != null) {
                    ctx_friendlistFragment.refresh();
                }
                break;
//            case FRIENDLIST_PAGE_INTRODUCE:
//                if (ctx_introduceFragment != null) {
//                    ctx_introduceFragment.refresh();
//                }
//                break;
//            case FRIENDLIST_PAGE_MSGBOX:
//                if (ctx_msgboxFragment != null) {
//                    ctx_msgboxFragment.refresh();
//                }
//                break;

            case FRIENDLIST_PAGE_INTRODUCE:
                if (ctx_msgboxFragment != null) {
                    ctx_msgboxFragment.refresh();
                }
                break;

        }
    }

    private static final int FRIENDLIST_PAGE_FRIENDLIST = 0;
    private static final int FRIENDLIST_PAGE_INTRODUCE = 1;
    private static final int FRIENDLIST_PAGE_MSGBOX = 2;

    private int _nCurPage = FRIENDLIST_PAGE_FRIENDLIST;

    FriendListFragment ctx_friendlistFragment;
    IntroduceFragment ctx_introduceFragment;
    FriendsMsgboxFragment ctx_msgboxFragment;

    private void InitFragments() {
        ctx_friendlistFragment = FriendListFragment.newInstance();
        ctx_friendlistFragment.SetActivity(this, onChooseListener);
        ctx_introduceFragment = IntroduceFragment.newInstance();
        ctx_introduceFragment.SetActivity(this, onChooseListener);
        ctx_msgboxFragment = FriendsMsgboxFragment.newInstance();
        ctx_msgboxFragment.SetActivity(this, onChooseListener);
    }

    private FriendListBaseFragment.OnChooseListener onChooseListener = new FriendListBaseFragment.OnChooseListener() {
        @Override
        public void OnChoose(String content) {
        }
    };

    public void reqFriendAction(int friend_id, int msg_id, String kind, String kakao) {

        Net.instance().api.reqFriendAction(MyInfo.getInstance().userInfo.info.id, friend_id, msg_id, kind, kakao)
                .enqueue(new Net.ResponseCallBack<MBase>() {
                    @Override
                    public void onSuccess(MBase response) {
                        super.onSuccess(response);

                        Toaster.showShort(FriendListActivity.this, "조작이 성공하였습니다.");
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

    @Override
    public void onBackPressed() {
        finish();
    }

    @OnClick(R.id.imv_back)
    void OnClickBack() {
        finish();
    }

    private class ContentsPageAdapter extends FragmentStatePagerAdapter {

        public ContentsPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment frag = null;
            switch (position) {
                case 0:
                    if (ctx_friendlistFragment == null) {
                        ctx_friendlistFragment = FriendListFragment.newInstance();
                        ctx_friendlistFragment.SetActivity(FriendListActivity.this, onChooseListener);
                    }
                    frag = ctx_friendlistFragment;
                    break;
//                case 1:
//                    if (ctx_introduceFragment == null) {
//                        ctx_introduceFragment = IntroduceFragment.newInstance();
//                        ctx_introduceFragment.SetActivity(FriendListActivity.this, onChooseListener);
//                    }
//                    frag = ctx_introduceFragment;
//                    break;
//                case 2:
//                    if (ctx_msgboxFragment == null) {
//                        ctx_msgboxFragment = FriendsMsgboxFragment.newInstance();
//                        ctx_msgboxFragment.SetActivity(FriendListActivity.this, onChooseListener);
//                    }
//                    frag = ctx_msgboxFragment;
//                    break;

                case 1:
                    if (ctx_msgboxFragment == null) {
                        ctx_msgboxFragment = FriendsMsgboxFragment.newInstance();
                        ctx_msgboxFragment.SetActivity(FriendListActivity.this, onChooseListener);
                    }
                    frag = ctx_msgboxFragment;
                    break;

            }
            return frag;
        }

        @Override
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }

        @Override
        public int getCount() {
            return 2;//3
        }
    }

    private void initKakao() {
        kakaoCallback_friend = new SessionCallback_friend();                  // 이 두개의 함수 중요함
        Session.getCurrentSession().addCallback(kakaoCallback_friend);
        Session.getCurrentSession().checkAndImplicitOpen();
    }

    // 카톡로그인 콜백
    private class SessionCallback_friend implements ISessionCallback {

        @Override
        public void onSessionOpened() {
            UserManagement.getInstance().requestMe(new MeResponseCallback() {
                @Override
                public void onFailure(ErrorResult errorResult) {
                    String message = "failed to get user info. msg=" + errorResult;
                    //Toast.makeText(FriendListActivity.this, message, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onSessionClosed(ErrorResult errorResult) {
                    //Toast.makeText(FriendListActivity.this, "SessionClosed", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNotSignedUp() {
                } // 카카오톡 회원이 아닐 시 showSignup(); 호출해야함

                @Override
                public void onSuccess(UserProfile userProfile) {  //성공 시 userProfile 형태로 반환
                    Logger.d("UserProfile : " + userProfile);

                    MUser.UserInfo user = MyInfo.getInstance().userInfo.info;

                    final String kakao_id = "" + userProfile.getId();
                    final String kakao_email = userProfile.getEmail() == null ? "" : userProfile.getEmail();
                    final String kakao_name = "" + userProfile.getNickname();

                    MyInfo.getInstance().load(FriendListActivity.this);
                    MyInfo.getInstance().userInfo.info.kt_id = kakao_id;

                    MyInfo.getInstance().save(FriendListActivity.this);

                    getKakaoFriendList();
                }
            });
        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            if (exception != null) {
                Logger.e(exception);
            }
            Toast.makeText(FriendListActivity.this, "SessionOpenFailed", Toast.LENGTH_SHORT).show();
        }
    }

    //
    private void getKakaoFriendList(){
        AppFriendContext friendContext = new AppFriendContext(true, 0, 20, "asc");
        KakaoTalkService.getInstance().requestAppFriends(friendContext,
                new TalkResponseCallback<AppFriendsResponse>() {
                    @Override
                    public void onNotKakaoTalkUser() {
                        UserManagement.getInstance().requestLogout(null);
                        Toast.makeText(FriendListActivity.this, "not a KakaoTalk user", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSessionClosed(ErrorResult errorResult) {
                        UserManagement.getInstance().requestLogout(null);
                        Toast.makeText(FriendListActivity.this, "onSessionClosed", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNotSignedUp() {
                        UserManagement.getInstance().requestLogout(null);
                        Toast.makeText(FriendListActivity.this, "onNotSignedUp", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(ErrorResult errorResult) {
                        UserManagement.getInstance().requestLogout(null);
                        Logger.e("onFailure: " + errorResult.toString());
                    }

                    @Override
                    public void onSuccess(AppFriendsResponse result) {
                        // 친구 목록
                        g_kakaoFriendList.clear();
                        Logger.e("Friends: " + result.getFriends().toString());
                        if(!result.getFriends().isEmpty()){
                            for(int i=0; i<result.getFriends().size(); i++){
                                //result.getFriends().get(i).getId()
                                MKakaoFriend friend = new MKakaoFriend();
                                friend.id = String.format("%d", result.getFriends().get(i).getId());
                                friend.profile = result.getFriends().get(i).getProfileThumbnailImage();
                                friend.name = result.getFriends().get(i).getProfileNickname();
                                g_kakaoFriendList.add(friend);
                            }
                        }

                        g_isCallKakaoFriends = true;

                        // 로컬에 저장
                        SharedPreferences prefs = getSharedPreferences(PrefMgr.BENEPICTURE_PREFS,
                                Context.MODE_PRIVATE);
                        PrefMgr prefMgr = new PrefMgr(prefs);
                        prefMgr.put(PrefMgr.IS_CALL_KAKAO_FRIEND, true);

                        // 친구리스트 저장
                        Gson gson = new Gson();
                        String jsonFriends = gson.toJson(g_kakaoFriendList);
                        prefMgr.put(PrefMgr.KAKAO_FRIEND, jsonFriends);
                        ////////

                        UserManagement.getInstance().requestLogout(null);

                        switch (_nCurPage){
                            case FRIENDLIST_PAGE_FRIENDLIST:
                                ctx_friendlistFragment.refresh();
                                break;
//                            case FRIENDLIST_PAGE_INTRODUCE:
//                                ctx_introduceFragment.refresh();
//                                break;
//                            case FRIENDLIST_PAGE_MSGBOX:
//                                ctx_msgboxFragment.refresh();
//                                break;
                            case FRIENDLIST_PAGE_INTRODUCE:
                                ctx_msgboxFragment.refresh();
                                break;
                        }

                    }
                });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }
    }

    public void onKakaoLogin_call(){
        isKakaoCall = true;
        Session.getCurrentSession().open(AuthType.KAKAO_TALK, this);
    }
}
