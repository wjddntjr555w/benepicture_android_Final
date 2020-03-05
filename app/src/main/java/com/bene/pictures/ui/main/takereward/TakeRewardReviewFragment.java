package com.bene.pictures.ui.main.takereward;

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
import com.bene.pictures.model.MReviewList;
import com.bene.pictures.net.Net;
import com.bene.pictures.ui.adapter.ReviewListAdapter;
import com.bene.pictures.ui.main.msgbox.MsgDetailActivity;
import com.bene.pictures.util.Util;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TakeRewardReviewFragment extends TakeRewardBaseFragment {

    public TakeRewardReviewFragment() {

    }

    public static TakeRewardReviewFragment newInstance() {
        return new TakeRewardReviewFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ui_rootView = inflater.inflate(R.layout.fragment_takereward_review,
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

    ReviewListAdapter adpReviewList;

    private boolean loadingEnd = false;
    private boolean lockRcv = false;

    private void initUI() {

        adpReviewList = new ReviewListAdapter(_activity, new ArrayList<MReviewList.Info>(), new ReviewListAdapter.OnReviewItemListener() {
            @Override
            public void OnClickItem(final int position) {

                MReviewList.Info info = (MReviewList.Info) adpReviewList.getItem(position);

                String adname = "";
                if (info.is_admin == 1) {
                    adname = "관리자지급";
                } else {
                    adname = info.subscribe_adname;
                }

                Intent intent = new Intent(_activity, MsgDetailActivity.class);
                intent.putExtra("title", "응모권번호 : " + adname + info.subscribe_number);
                intent.putExtra("content", info.content);
                intent.putExtra("date", "작성일 : " + info.create_date);
                intent.putExtra("money", "당첨금액 : " + Util.makeMoneyType(info.winning_money) + "원");
                startActivity(intent);
            }
        });

        ui_rcvContents.setAdapter(adpReviewList);

        ui_rcvContents.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int firstVisibleItem = recyclerView.getChildAdapterPosition(recyclerView.getChildAt(0));
                int visibleCnt = recyclerView.getChildCount();
                int totalItemCnt = adpReviewList.getItemCount();

                if (loadingEnd) {
                    return;
                }

                if (totalItemCnt != 0 && firstVisibleItem >= (totalItemCnt - visibleCnt) && !lockRcv) {
                    getReviewList();
                }
            }
        });
    }

    @Override
    public void refresh() {
        super.refresh();

        if (adpReviewList == null) {
            return;
        }

        initData();
    }

    void initData() {

        page_num = 1;
        loadingEnd = false;
        lockRcv = false;

        adpReviewList.clear();
        adpReviewList.notifyDataSetChanged();

        getReviewList();
    }

    int page_num = 1;

    private void getReviewList() {

        if (!MyInfo.getInstance().isValid()) {
            return;
        }

        _activity.showProgress(_activity);
        lockRcv = true;

        Net.instance().api.getReviewList(MyInfo.getInstance().userInfo.info.id, 0, page_num)
                .enqueue(new Net.ResponseCallBack<MReviewList>() {
                    @Override
                    public void onSuccess(MReviewList response) {
                        super.onSuccess(response);

                        lockRcv = false;

                        if (page_num == 1) {
                            adpReviewList.clear();
                        }

                        if (response.page_cnt <= page_num) {
                            loadingEnd = true;
                        } else {
                            page_num++;
                        }

                        adpReviewList.addAll(response.list);

                        _activity.hideProgress();
                        adpReviewList.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(MError response) {
                        super.onFailure(response);

//                        if (BuildConfig.DEBUG) {
//
//                            ArrayList<MReviewList.Info> buffer = new ArrayList<>();
//
//                            for (int i = 0; i < 20; i++) {
//                                MReviewList.Info bufInfo = new MReviewList.Info();
//                                bufInfo.id = i;
//                                bufInfo.create_date = "2019.09.15";
//                                bufInfo.subscribe_adname = "삼성갤럭시";
//                                bufInfo.subscribe_number = "158935";
//                                if (i == 4 || i == 8 || i == 16) {
//                                    bufInfo.is_admin = 1;
//                                }
//
//                                bufInfo.content = "아니 정말 광고를 보기만 해도 오천원을 주는것이 사실인가요? 우앙! 개꿀이네요.\n 아니 정말 광고를 보기만 해도 오천원을 주는것이 사실인가요? 우앙! 개꿀이네요.\n아니 정말 광고를 보기만 해도 오천원을 주는것이 사실인가요? 우앙! 개꿀이네요.";
//
//                                bufInfo.winning_money = 5000;
//
//                                buffer.add(bufInfo);
//                            }
//
//                            adpReviewList.addAll(buffer);
//                            _activity.hideProgress();
//                            adpReviewList.notifyDataSetChanged();
//
//                        } else {
                        _activity.hideProgress();
                        lockRcv = false;
                        _activity.showErrorMsg(response);
//                        }
                    }
                });
    }
}