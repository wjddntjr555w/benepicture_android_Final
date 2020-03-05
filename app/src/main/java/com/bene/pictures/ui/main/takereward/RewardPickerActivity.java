package com.bene.pictures.ui.main.takereward;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.bene.pictures.R;
import com.bene.pictures.data.MyInfo;
import com.bene.pictures.model.MError;
import com.bene.pictures.model.MTakeHistoryList;
import com.bene.pictures.net.Net;
import com.bene.pictures.ui.adapter.TakerewardHistoryListAdapter;
import com.bene.pictures.ui.base.BaseActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class RewardPickerActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward_picker);

        initUI();

        initData();
    }

    @BindView(R.id.rcv_contents)
    RecyclerView ui_rcvContents;

    TakerewardHistoryListAdapter adpSubscribeInfoList;

    private boolean loadingEnd = false;
    private boolean lockRcv = false;

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        finish();
    }

    @OnClick(R.id.imv_back)
    void OnClickBack() {
        onBackPressed();
    }

    public void initUI() {

        adpSubscribeInfoList = new TakerewardHistoryListAdapter(this, new ArrayList<MTakeHistoryList.Info>(), new TakerewardHistoryListAdapter.OnhistoryItemListener() {
            @Override
            public void OnClickItem(final int position) {
                MTakeHistoryList.Info info = (MTakeHistoryList.Info) adpSubscribeInfoList.getItem(position);

                Intent intent = new Intent();
                intent.putExtra("info", info);
                setResult(RESULT_OK, intent);
                finish();
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
                    getTakeRewardHistoryList();
                }
            }
        });
    }

    void initData() {

        page_num = 1;
        loadingEnd = false;
        lockRcv = false;

        adpSubscribeInfoList.clear();
        adpSubscribeInfoList.notifyDataSetChanged();

        getTakeRewardHistoryList();
    }

    int page_num = 1;

    private void getTakeRewardHistoryList() {

        if (!MyInfo.getInstance().isValid()) {
            return;
        }

        showProgress(this);
        lockRcv = true;

        Net.instance().api.getTakeRewardHistoryList(MyInfo.getInstance().userInfo.info.id, page_num, 2)
                .enqueue(new Net.ResponseCallBack<MTakeHistoryList>() {
                    @Override
                    public void onSuccess(MTakeHistoryList response) {
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

                        hideProgress();
                        adpSubscribeInfoList.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(MError response) {
                        super.onFailure(response);
//
//                        if (BuildConfig.DEBUG) {
//
//                            ArrayList<MTakeHistoryList.Info> buffer = new ArrayList<>();
//
//                            for (int i = 0; i < 20; i++) {
//                                MTakeHistoryList.Info bufInfo = new MTakeHistoryList.Info();
//                                bufInfo.id = i;
//                                bufInfo.create_datetime = "2019.09.15 12:00";
//                                bufInfo.subscribe_adname = "삼성갤럭시";
//                                bufInfo.subscribe_number = "158935";
//                                if (i == 4 || i == 8 || i == 16) {
//                                    bufInfo.is_admin = 1;
//                                }
//
//                                bufInfo.take_datetime = "2019.09.17 12:50";
//                                bufInfo.status = 0;
//                                if (i == 6 || i == 18 || i == 16) {
//                                    bufInfo.status = 1;
//                                } else if (i == 2 || i == 4 || i == 15) {
//                                    bufInfo.status = 2;
//                                } else if (i == 5 || i == 13 || i == 17) {
//                                    bufInfo.status = 3;
//                                }
//
//                                bufInfo.winning_money = 5000;
//
//                                buffer.add(bufInfo);
//                            }
//
//                            adpSubscribeInfoList.addAll(buffer);
//                            hideProgress();
//                            adpSubscribeInfoList.notifyDataSetChanged();
//
//                        } else {
                        hideProgress();
                        lockRcv = false;
                        showErrorMsg(response);
//                        }
                    }
                });
    }
}
