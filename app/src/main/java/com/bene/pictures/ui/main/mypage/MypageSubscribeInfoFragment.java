package com.bene.pictures.ui.main.mypage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bene.pictures.R;
import com.bene.pictures.data.MyInfo;
import com.bene.pictures.fcm.EventMessage;
import com.bene.pictures.model.MError;
import com.bene.pictures.model.MSubscribeInfoList;
import com.bene.pictures.net.Net;
import com.bene.pictures.ui.adapter.SubscribeInfoListAdapter;
import com.bene.pictures.ui.main.takereward.TakeRewardActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MypageSubscribeInfoFragment extends MypageBaseFragment {

    public MypageSubscribeInfoFragment() {

    }

    public static MypageSubscribeInfoFragment newInstance() {
        return new MypageSubscribeInfoFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ui_rootView = inflater.inflate(R.layout.fragment_mypage_subscribe_info,
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

    @BindView(R.id.rcv_contents)
    RecyclerView ui_rcvContents;

    SubscribeInfoListAdapter adpSubscribeInfoList;

    private boolean loadingEnd = false;
    private boolean lockRcv = false;

    private void initUI() {

        adpSubscribeInfoList = new SubscribeInfoListAdapter(_activity, new ArrayList<MSubscribeInfoList.Info>(), new SubscribeInfoListAdapter.OnSubscribeInfoItemListener() {
            @Override
            public void OnClickItem(final int position) {
                Intent intent = new Intent(_activity, SubscribeInfoDetailActivity.class);
                intent.putExtra("subscribe_info", (MSubscribeInfoList.Info) adpSubscribeInfoList.getItem(position));
                _activity.startActivityForResult(intent, MypageActivity.MYPAGE_SUBSCRIBEINFO_DETAIL);
            }
        });

        ui_rcvContents.setAdapter(adpSubscribeInfoList);

        ui_rcvContents.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int firstVisibleItem = recyclerView.getChildAdapterPosition(recyclerView.getChildAt(0));
                int visibleCnt = recyclerView.getChildCount();
                int totalItemCnt = adpSubscribeInfoList.getItemCount();

                if (loadingEnd) {
                    return;
                }

                if (totalItemCnt != 0 && firstVisibleItem >= (totalItemCnt - visibleCnt) && !lockRcv) {
                    getSubscribeInfoList();
                }
            }
        });
    }

    @Override
    public void refresh() {
        super.refresh();

        if (adpSubscribeInfoList == null) {
            return;
        }

        initData();
    }

    void initData() {

        page_num = 1;
        loadingEnd = false;
        lockRcv = false;

        adpSubscribeInfoList.clear();
//        adpSubscribeInfoList.notifyDataSetChanged();

        getSubscribeInfoList();
    }

    int page_num = 1;

    private void getSubscribeInfoList() {

        if (!MyInfo.getInstance().isValid()) {
            return;
        }

        _activity.showProgress(_activity);
        lockRcv = true;

        Net.instance().api.getSubscribeInfoList(MyInfo.getInstance().userInfo.info.id, page_num)
                .enqueue(new Net.ResponseCallBack<MSubscribeInfoList>() {
                    @Override
                    public void onSuccess(MSubscribeInfoList response) {
                        super.onSuccess(response);

                        lockRcv = false;

                        if (page_num == 1) {
                            adpSubscribeInfoList.clear();
                        }

                        if (response.page_cnt <= page_num) {
                            loadingEnd = true;
                        } else {
                            page_num++;
                        }

                        adpSubscribeInfoList.addAll(response.list);

                        _activity.hideProgress();
                        adpSubscribeInfoList.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(MError response) {
                        super.onFailure(response);

//                        if (BuildConfig.DEBUG) {
//
//                            ArrayList<MSubscribeInfoList.Info> buffer = new ArrayList<>();
//
//                            for (int i = 0; i < 20; i++) {
//                                MSubscribeInfoList.Info bufInfo = new MSubscribeInfoList.Info();
//                                bufInfo.id = i;
//                                bufInfo.create_datetime = "2019.09.15 12:00";
//                                bufInfo.subscribe_adname = "삼성갤럭시";
//                                bufInfo.subscribe_number = "158935";
//                                if (i == 4 || i == 8 || i == 16) {
//                                    bufInfo.is_admin = 1;
//                                }
//
//                                bufInfo.fund_datetime = "2019.09.16 19:20";
//                                bufInfo.take_datetime = "2019.09.17 12:50";
//                                bufInfo.is_winning = 0;
//                                if (i == 6 || i == 18 || i == 16) {
//                                    bufInfo.is_winning = 1;
//                                }
//
//                                bufInfo.winning_money = 5000;
//
//                                buffer.add(bufInfo);
//                            }
//
//                            adpSubscribeInfoList.addAll(buffer);
//                            _activity.hideProgress();
//                            adpSubscribeInfoList.notifyDataSetChanged();
//
//                        } else {
                        _activity.hideProgress();
                        lockRcv = false;
                        _activity.showErrorMsg(response);
//                        }
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MypageActivity.MYPAGE_SUBSCRIBEINFO_DETAIL) {
            if (resultCode == Activity.RESULT_OK) {
                Intent intent = new Intent(_activity, TakeRewardActivity.class);
                intent.putExtra("tab", 1);
                _activity.startActivity(intent);
            }
        }
    }
}