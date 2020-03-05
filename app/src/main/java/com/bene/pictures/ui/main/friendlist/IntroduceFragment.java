package com.bene.pictures.ui.main.friendlist;

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
import com.bene.pictures.model.MError;
import com.bene.pictures.model.MFriendsList;
import com.bene.pictures.model.MKakaoFriend;
import com.bene.pictures.net.Net;
import com.bene.pictures.ui.adapter.FriendslistAdapter;
import com.bene.pictures.ui.widget.BaseEditText;
import com.kakao.kakaolink.v2.KakaoLinkResponse;
import com.kakao.kakaolink.v2.KakaoLinkService;
import com.kakao.message.template.ButtonObject;
import com.kakao.message.template.ContentObject;
import com.kakao.message.template.FeedTemplate;
import com.kakao.message.template.LinkObject;
import com.kakao.network.ErrorResult;
import com.kakao.network.callback.ResponseCallback;
import com.kakao.util.helper.log.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.bene.pictures.MyApplication.g_isCallKakaoFriends;
import static com.bene.pictures.MyApplication.g_kakaoFriendList;

public class IntroduceFragment extends FriendListBaseFragment {
    private ResponseCallback<KakaoLinkResponse> callback;

    public IntroduceFragment() {

    }

    public static IntroduceFragment newInstance() {
        return new IntroduceFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ui_rootView = inflater.inflate(R.layout.fragment_introduce,
                container, false);

        ButterKnife.bind(this, ui_rootView);
        EventBus.getDefault().register(this);

        callback = new ResponseCallback<KakaoLinkResponse>() {
            @Override
            public void onFailure(ErrorResult errorResult) {
                Toast.makeText(_activity, errorResult.getErrorMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onSuccess(KakaoLinkResponse result) {
//                Toast.makeText(getApplicationContext(), "Successfully sent KakaoLink v2 message.", Toast.LENGTH_LONG).show();
            }
        };


        initUI();

        // 카카오 친구리스트를 블러 온다.
        initKakaoFriendList();

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
                //TODO: 카톡 친구초대를 진행, info.face 초대하려는 회원의 카톡아이디이다.
                MFriendsList.Info info = (MFriendsList.Info) adpFriendsList.getItem(position);

                sendKakaoTalkLink(info.face);

                //TODO: 친구초대가 성공한 콜백에서 아래의 메서드를 호출한다.
                _activity.reqFriendAction(info.id, 0, "introduce", info.face);
            }
        }, 1);

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

    /****************************************************************************************************
     *	카카오톡으로 추천링크 보내기
     ****************************************************************************************************/
    private void sendKakaoTalkLink(String friend_kt_id) {
        final String appPackageName = _activity.getPackageName();
        final String img_url = "http://15.164.112.250/upload/kakao_icon.png";
        String app_link = "https://play.google.com/store/apps/details?id=" + appPackageName;

        String title = "베네픽쳐";
        String content = "베네픽쳐에 초대합니다.";

        FeedTemplate params = FeedTemplate
                .newBuilder(ContentObject.newBuilder(title,
                        img_url,
                        LinkObject.newBuilder().setWebUrl(app_link)
                                .setMobileWebUrl(app_link).build())
                        .setDescrption(content)
                        .build())
                .addButton(new ButtonObject("사용하기", LinkObject.newBuilder().setWebUrl(app_link).setMobileWebUrl(app_link).build()))
                .build();

        KakaoLinkService.getInstance().sendDefault(_activity, params, callback);

        Map<String, String> serverCallbackArgs = new HashMap<String, String>();
        serverCallbackArgs.put("user_id", MyInfo.getInstance().userInfo.info.kt_id);
        serverCallbackArgs.put("product_id", friend_kt_id);

        KakaoLinkService.getInstance().sendDefault(getActivity(), params, new ResponseCallback<KakaoLinkResponse>() {
            @Override
            public void onFailure(ErrorResult errorResult) {
                Logger.e(errorResult.toString());
            }

            @Override
            public void onSuccess(KakaoLinkResponse result) {
                // 템플릿 밸리데이션과 쿼터 체크가 성공적으로 끝남. 톡에서 정상적으로 보내졌는지 보장은 할 수 없다. 전송 성공 유무는 서버콜백 기능을 이용하여야 한다.
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

        initKakaoFriendList();
    }

    List<MFriendsList.Info> kakaoFriendList = new ArrayList<>();

    void initKakaoFriendList() {
        kakaoFriendList = new ArrayList<>();

        // TODO: 카카오연동, 카카오 친구리스트를 블러 온다. id-0, name-카톡친구명, face-친구의 카톡아이디, cnt_*-0
        // offset = 0, limit = 100

        /// 2019.10.26
        for(int i=0; i<g_kakaoFriendList.size(); i++){
            MFriendsList.Info info = new MFriendsList.Info();
            info.id = 0;
            info.cnt_introduce = 0;
            info.cnt_sendgift = 0;
            info.face = g_kakaoFriendList.get(i).id;
            info.name = g_kakaoFriendList.get(i).name;
            info.profile = g_kakaoFriendList.get(i).profile;
            kakaoFriendList.add(info);
        }

        // TODO: 친구리스트 블러 오기를 완료한 시점에서 아래의 메서드를 호출한다.
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
            if(g_isCallKakaoFriends) {
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
            if(g_isCallKakaoFriends) {
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

        Net.instance().api.getFriendsList(MyInfo.getInstance().userInfo.info.id, 1, search, TextUtils.join(",", ktIdList), page_num)
                .enqueue(new Net.ResponseCallBack<MFriendsList>() {
                    @Override
                    public void onSuccess(MFriendsList response) {
                        super.onSuccess(response);

                        lockRcv = false;

                        if (page_num == 1) {
                            adpFriendsList.clear();
                        }

                        loadingEnd = true;


                        // 응답으로 내려오는 response.list 는 베네픽쳐회원이 아닌 카톡친구리스트이다.
                        List<String> ktList = new ArrayList<>();
                        for (MFriendsList.Info item : response.list) {
                            ktList.add(item.face);
                        }

                        // 나의 카톡친구리스트에서 베네픽쳐회원인 카톡친구는 배제한다.

                        Iterator<MFriendsList.Info> it = kakaoFriendList.iterator();
                        while (it.hasNext()) {
                            if (!ktList.contains(it.next().face)) {
                                it.remove();
                            }
                        }


                        adpFriendsList.addAll(kakaoFriendList);

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

    @OnClick(R.id.txv_search)
    void OnClickSearch() {
        page_num = 1;
        adpFriendsList.clear();
        getFriendsList();
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