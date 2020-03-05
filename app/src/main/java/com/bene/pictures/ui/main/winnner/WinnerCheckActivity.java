package com.bene.pictures.ui.main.winnner;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bene.pictures.R;
import com.bene.pictures.data.MyInfo;
import com.bene.pictures.model.MError;
import com.bene.pictures.model.MLotteryList;
import com.bene.pictures.model.MReviewList;
import com.bene.pictures.model.MWinnerSubscribeList;
import com.bene.pictures.net.Net;
import com.bene.pictures.ui.adapter.WinnerReviewListAdapter;
import com.bene.pictures.ui.adapter.WinnerSubscribeListAdapter;
import com.bene.pictures.ui.base.BaseActivity;
import com.bene.pictures.ui.main.takereward.TakeRewardActivity;
import com.bene.pictures.ui.widget.BaseTextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

import static com.bene.pictures.util.Util.convertHiddenString;

public class WinnerCheckActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winner_check);

        initUI();

        initData();
    }

    void initData() {
        getLotteryList();
    }

    void initWinnerList() {
        page_num = 1;
        loadingEnd = false;
        lockRcv = false;

        adpWinnerList.clear();
        adpWinnerList.notifyDataSetChanged();

        getWinnerList();
    }

    void initReviewList() {
        review_page_num = 1;
        loadingReviewEnd = false;
        lockReviewRcv = false;

        adpReviewList.clear();
        adpReviewList.notifyDataSetChanged();

        getReviewList();
    }

    @BindView(R.id.txv_title)
    BaseTextView ui_txvTitle;

    @BindView(R.id.txv_date)
    BaseTextView ui_txvDate;

    @BindView(R.id.txv_period)
    BaseTextView ui_txvPeriod;

    @BindView(R.id.txv_confirm)
    BaseTextView ui_txvConfirm;

    @BindView(R.id.rcv_review)
    RecyclerView ui_rcvReview;

    @BindView(R.id.rcv_contents)
    RecyclerView ui_rcvWinner;

    WinnerSubscribeListAdapter adpWinnerList;
    WinnerReviewListAdapter adpReviewList;

    private boolean loadingReviewEnd = false;
    private boolean lockReviewRcv = false;

    private boolean loadingEnd = false;
    private boolean lockRcv = false;

    public void initUI() {
        LinearLayoutManager layoutManager = (LinearLayoutManager) ui_rcvReview.getLayoutManager();
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        ui_rcvReview.setLayoutManager(layoutManager);

        adpReviewList = new WinnerReviewListAdapter(WinnerCheckActivity.this, new ArrayList<MReviewList.Info>(), new WinnerReviewListAdapter.OnReviewItemListener() {
            @Override
            public void OnClickItem(int position) {
                startActivity(new Intent(WinnerCheckActivity.this, TakeRewardActivity.class));
            }
        });

        ui_rcvReview.setAdapter(adpReviewList);
        ui_rcvReview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int firstVisibleItem = recyclerView.getChildAdapterPosition(recyclerView.getChildAt(0));
                int visibleCnt = recyclerView.getChildCount();
                int totalItemCnt = adpReviewList.getItemCount();

                if (loadingReviewEnd) {
                    return;
                }

                if (totalItemCnt != 0 && firstVisibleItem >= (totalItemCnt - visibleCnt) && !lockReviewRcv) {
                    getReviewList();
                }
            }
        });

        adpWinnerList = new WinnerSubscribeListAdapter(WinnerCheckActivity.this, new ArrayList<MWinnerSubscribeList.Info>(), new WinnerSubscribeListAdapter.OnWinnerItemClickListener() {
            @Override
            public void OnClickItem(final int position) {

//                MReviewList.Info info = (MReviewList.Info) adpWinnerList.getItem(position);
//
//                String adname = "";
//                if (info.is_admin == 1) {
//                    adname = "관리자지급";
//                } else {
//                    adname = info.subscribe_adname;
//                }
//
//                Intent intent = new Intent(WinnerCheckActivity.this, MsgDetailActivity.class);
//                intent.putExtra("title", "응모권번호 : " + adname + info.subscribe_number);
//                intent.putExtra("content", info.content);
//                intent.putExtra("date", "작성일 : " + info.content);
//                intent.putExtra("money", "당첨금액 : " + info.content);
//                startActivity(intent);
            }
        });

        ui_rcvWinner.setAdapter(adpWinnerList);
        ui_rcvWinner.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int firstVisibleItem = recyclerView.getChildAdapterPosition(recyclerView.getChildAt(0));
                int visibleCnt = recyclerView.getChildCount();
                int totalItemCnt = adpWinnerList.getItemCount();

                if (loadingEnd) {
                    return;
                }

                if (totalItemCnt != 0 && firstVisibleItem >= (totalItemCnt - visibleCnt) && !lockRcv) {
                    getWinnerList();
                }
            }
        });

    }

    private int nIdxLottery = 0;

    @SuppressLint("DefaultLocale")
    private void updateLottery() {

        MLotteryList.Info buf;

        try {
            buf = _arlLottery.get(nIdxLottery);

            if (buf == null)
                return;

            ui_txvTitle.setText(String.format("%d회차 당첨 응모권 번호", buf.id));
            ui_txvDate.setText(String.format("추첨: %s", buf.date));
            ui_txvPeriod.setText(String.format("지급기한: %d달", buf.period));

            switch (buf.status) {
                case 0:
                    ui_txvConfirm.setBackgroundResource(R.drawable.xml_btn_round_rect_78bffd);
                    ui_txvConfirm.setTextColor(0xffffffff);
                    ui_txvConfirm.setText("당첨 확인");
                    break;
                case 1:
                    ui_txvConfirm.setBackgroundResource(R.drawable.xml_bg3_rect_ffffff_stroke);
                    ui_txvConfirm.setTextColor(0xffbdbdbd);
                    ui_txvConfirm.setText("당첨내역이 없습니다.");
                    break;
                case 2:
                    ui_txvConfirm.setBackgroundResource(R.drawable.xml_btn_round_rect_1976d2);
                    ui_txvConfirm.setTextColor(0xffffffff);
                    ui_txvConfirm.setText("당첨되었습니다.");
                    break;
                case 3:
                    ui_txvConfirm.setBackgroundResource(R.drawable.xml_btn_round_rect_ffd600);
                    ui_txvConfirm.setTextColor(0xffffffff);
                    ui_txvConfirm.setText("1등에 당첨되었습니다.");
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private ArrayList<MLotteryList.Info> _arlLottery = new ArrayList<>();

    private void getLotteryList() {

        showProgress(this);

        Net.instance().api.getLotteryList(MyInfo.getInstance().userInfo.info.id)
                .enqueue(new Net.ResponseCallBack<MLotteryList>() {
                    @Override
                    public void onSuccess(MLotteryList response) {
                        super.onSuccess(response);

                        hideProgress();

                        _arlLottery = response.list;

                        if(_arlLottery.size() > 0)// 가장 최신회차담청결과가 우선적으로 나오도록 이동
                            nIdxLottery = _arlLottery.size() - 1;
                        else
                            nIdxLottery = 0;

                                    updateLottery();
                        initWinnerList();
                        initReviewList();
                    }

                    @Override
                    public void onFailure(MError response) {
                        super.onFailure(response);
                        hideProgress();
                        showErrorMsg(response);
                    }
                });
    }

    int page_num = 1;
    int review_page_num = 1;

    private void getWinnerList() {

        if (!MyInfo.getInstance().isValid()) {
            return;
        }

        MLotteryList.Info buf;
        try {
            buf = _arlLottery.get(nIdxLottery);

            if (buf == null)
                return;

            showProgress(this);
            lockRcv = true;

            Net.instance().api.winnerList(MyInfo.getInstance().userInfo.info.id, buf.id, page_num)
                    .enqueue(new Net.ResponseCallBack<MWinnerSubscribeList>() {
                        @Override
                        public void onSuccess(MWinnerSubscribeList response) {
                            super.onSuccess(response);

                            lockRcv = false;

                            if (page_num == 1) {
                                adpWinnerList.clear();
                            }

                            if (response.page_cnt <= page_num) {
                                loadingEnd = true;
                            } else {
                                page_num++;
                            }

                            adpWinnerList.addAll(response.list);

                            hideProgress();
                            adpWinnerList.notifyDataSetChanged();
                        }

                        @Override
                        public void onFailure(MError response) {
                            super.onFailure(response);
                            hideProgress();
                            lockRcv = false;
                            showErrorMsg(response);
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getReviewList() {

        if (!MyInfo.getInstance().isValid()) {
            return;
        }

        MLotteryList.Info buf;
        try {
            buf = _arlLottery.get(nIdxLottery);

            if (buf == null)
                return;

            showProgress(this);
            lockReviewRcv = true;

            Net.instance().api.getReviewList(MyInfo.getInstance().userInfo.info.id, buf.id, review_page_num)
                    .enqueue(new Net.ResponseCallBack<MReviewList>() {
                        @Override
                        public void onSuccess(MReviewList response) {
                            lockReviewRcv = false;

                            if (review_page_num == 1) {
                                adpReviewList.clear();
                            }

                            if (review_page_num == 1 && response.list.size() < 1) {
                                ui_rcvReview.setVisibility(View.GONE);
                            } else {
                                ui_rcvReview.setVisibility(View.VISIBLE);
                            }

                            if (response.page_cnt <= review_page_num) {
                                loadingReviewEnd = true;
                            } else {
                                review_page_num++;
                            }

                            adpReviewList.addAll(response.list);

                            hideProgress();
                            adpReviewList.notifyDataSetChanged();
                        }

                        @Override
                        public void onFailure(MError response) {
                            super.onFailure(response);
                            hideProgress();
                            lockReviewRcv = false;
                            showErrorMsg(response);
                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onBackPressed() {
        finish();
    }

    @OnClick(R.id.imv_back)
    void OnClickBack() {
        finish();
    }

    @OnClick(R.id.imv_title_left)
    void OnClickLeft() {

        if (nIdxLottery > 0) {
            nIdxLottery--;
            updateLottery();
            initWinnerList();
            initReviewList();
        }
    }

    @OnClick(R.id.imv_title_right)
    void OnClickRight() {
        if (nIdxLottery < _arlLottery.size() - 1) {
            nIdxLottery++;
            updateLottery();
            initWinnerList();
            initReviewList();
        }
    }

    @OnClick(R.id.txv_confirm)
    void OnClickConfirm() {

        MLotteryList.Info buf;
        try {
            buf = _arlLottery.get(nIdxLottery);
            if (buf == null)
                return;

            switch (buf.status) {
                case 0:
                case 1:
                    break;
                case 2:
                case 3:
                    Intent intent = new Intent(WinnerCheckActivity.this, TakeRewardActivity.class);
                    intent.putExtra("tab", 1);
                    startActivity(intent);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
