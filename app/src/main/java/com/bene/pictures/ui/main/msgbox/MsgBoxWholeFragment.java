package com.bene.pictures.ui.main.msgbox;

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
import com.bene.pictures.model.MMsgList;
import com.bene.pictures.net.Net;
import com.bene.pictures.ui.adapter.MsglistAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_CANCELED;

public class MsgBoxWholeFragment extends MsgBoxBaseFragment {

    public MsgBoxWholeFragment() {

    }

    public static MsgBoxWholeFragment newInstance() {
        return new MsgBoxWholeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ui_rootView = inflater.inflate(R.layout.fragment_msgbox_whole,
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

    MsglistAdapter adpMsgList;

    private boolean loadingEnd = false;
    private boolean lockRcv = false;

    int selectedPos = -1;

    private void initUI() {

        adpMsgList = new MsglistAdapter(_activity, new ArrayList<MMsgList.Info>(), new MsglistAdapter.OnMsgListItemListener() {
            @Override
            public void OnClickItem(int position) {

                selectedPos = position;
                MMsgList.Info info = (MMsgList.Info) adpMsgList.getItem(position);

                Intent intent = new Intent(_activity, MsgDetailActivity.class);
                intent.putExtra("id", info.id);
                intent.putExtra("title", info.title);
                intent.putExtra("content", info.content);
                intent.putExtra("read", info.read);
                intent.putExtra("type", info.msg_type);
                _activity.startActivityForResult(intent, 1009);
            }
        });

        ui_rcvContents.setAdapter(adpMsgList);

        ui_rcvContents.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int firstVisibleItem = recyclerView.getChildAdapterPosition(recyclerView.getChildAt(0));
                int visibleCnt = recyclerView.getChildCount();
                int totalItemCnt = adpMsgList.getItemCount();

                if (loadingEnd) {
                    return;
                }

                if (totalItemCnt != 0 && firstVisibleItem >= (totalItemCnt - visibleCnt) && !lockRcv) {
                    getMsgList();
                }
            }
        });
    }

    @Override
    public void refresh() {
        super.refresh();

        if (adpMsgList == null) {
            return;
        }

        initData();
    }

    void initData() {

        page_num = 1;
        loadingEnd = false;
        lockRcv = false;

        adpMsgList.clear();
        adpMsgList.notifyDataSetChanged();

        getMsgList();
    }

    int page_num = 1;

    private void getMsgList() {

        if (!MyInfo.getInstance().isValid()) {
            return;
        }

        _activity.showProgress(_activity);
        lockRcv = true;

        Net.instance().api.getMsgList(MyInfo.getInstance().userInfo.info.id, 2, page_num)
                .enqueue(new Net.ResponseCallBack<MMsgList>() {
                    @Override
                    public void onSuccess(MMsgList response) {
                        super.onSuccess(response);

                        lockRcv = false;

                        if (page_num == 1) {
                            adpMsgList.clear();
                        }

                        if (response.page_cnt <= page_num) {
                            loadingEnd = true;
                        } else {
                            page_num++;
                        }

                        adpMsgList.addAll(response.list);

                        _activity.hideProgress();
                        adpMsgList.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(MError response) {
                        super.onFailure(response);

//                        if (BuildConfig.DEBUG) {
//
//                            ArrayList<MMsgList.Info> buffer = new ArrayList<>();
//
//                            for (int i = 0; i < 20; i++) {
//                                MMsgList.Info bufInfo = new MMsgList.Info();
//                                bufInfo.id = i;
//                                bufInfo.title = (i + 1) + "번째 메시지 제목입니다";
//                                bufInfo.content = (i + 1) + "번째 메시지 내용입니다";
//
//                                buffer.add(bufInfo);
//                            }
//
//                            adpMsgList.addAll(buffer);
//                            _activity.hideProgress();
//                            adpMsgList.notifyDataSetChanged();
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
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1009) {
            if (resultCode == RESULT_CANCELED) {
                if (selectedPos >= 0) {
                    adpMsgList.removeItem(selectedPos);
                    adpMsgList.notifyDataSetChanged();
                    selectedPos = -1;
                }
            }
        }
    }
}