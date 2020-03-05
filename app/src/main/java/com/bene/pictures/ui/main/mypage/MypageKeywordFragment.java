package com.bene.pictures.ui.main.mypage;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bene.pictures.R;
import com.bene.pictures.data.MyInfo;
import com.bene.pictures.model.MBase;
import com.bene.pictures.model.MError;
import com.bene.pictures.model.MKeywordList;
import com.bene.pictures.net.Net;
import com.bene.pictures.ui.adapter.KeywordListAdapter;
import com.bene.pictures.ui.widget.BaseEditText;
import com.bene.pictures.util.Toaster;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MypageKeywordFragment extends MypageBaseFragment {

    public MypageKeywordFragment() {

    }

    public static MypageKeywordFragment newInstance() {
        return new MypageKeywordFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ui_rootView = inflater.inflate(R.layout.fragment_mypage_keyword,
                container, false);

        ButterKnife.bind(this, ui_rootView);

        initUI();

        initData();

        return ui_rootView;
    }

    @BindView(R.id.rcv_contents)
    RecyclerView ui_rcvContents;

    @BindView(R.id.rcv_content_child)
    RecyclerView ui_rcvContentChild;

    @BindView(R.id.edt_search_keyword)
    BaseEditText ui_edtSearchKeyword;

    KeywordListAdapter adpKeywordList;
    KeywordListAdapter adpChildKeywordList;

    ArrayList<MKeywordList.Keyword> keywordList = new ArrayList<>();

    @Override
    public void onResume() {
        super.onResume();
    }

    private void initUI() {

        adpKeywordList = new KeywordListAdapter(_activity, new ArrayList<MKeywordList.Keyword>(), new KeywordListAdapter.OnKeywordItemListener() {
            @Override
            public void OnClickItem(int parent, int like) {
                ui_rcvContentChild.smoothScrollToPosition(adpChildKeywordList.getChildPos(parent));
                adpChildKeywordList.setChildKeyword(parent, like);
            }
        });
        ui_rcvContents.setAdapter(adpKeywordList);

        adpChildKeywordList = new KeywordListAdapter(_activity, new ArrayList<MKeywordList.Keyword>(), new KeywordListAdapter.OnKeywordItemListener() {
            @Override
            public void OnClickItem(int child, int like) {
            }
        });
        ui_rcvContentChild.setAdapter(adpChildKeywordList);

    }

    @Override
    public void refresh() {
        super.refresh();

        if (adpKeywordList == null) {
            return;
        }

        initData();
    }

    @OnClick(R.id.txv_search)
    void OnClickSearch() {
        getKeywordList();
    }

    @OnClick(R.id.txv_set_keyword)
    void OnClickSetKeyword() {
        List<Integer> parents = adpKeywordList.getSelectedList();
        List<Integer> children = adpChildKeywordList.getSelectedList();

        parents.addAll(children);

        Net.instance().api.saveKeyword(MyInfo.getInstance().userInfo.info.id, parents.toString().replace("[", "").replace("]", "").replaceAll(" ", ""))
                .enqueue(new Net.ResponseCallBack<MBase>() {
                    @Override
                    public void onSuccess(MBase response) {
                        super.onSuccess(response);
                        Toaster.showShort(_activity, "저장되었습니다.");
                    }

                    @Override
                    public void onFailure(MError response) {
                        super.onFailure(response);
                        _activity.showErrorMsg(response);
                    }
                });
    }


    void initData() {

        keywordList.clear();

        adpKeywordList.clear();
        adpKeywordList.notifyDataSetChanged();

        adpChildKeywordList.clear();
        adpChildKeywordList.notifyDataSetChanged();

        getKeywordList();
    }

    private void getKeywordList() {

        if (!MyInfo.getInstance().isValid()) {
            return;
        }

        _activity.showProgress(_activity);

        Net.instance().api.keywordList(MyInfo.getInstance().userInfo.info.id)
                .enqueue(new Net.ResponseCallBack<MKeywordList>() {
                    @Override
                    public void onSuccess(MKeywordList response) {
                        super.onSuccess(response);

                        keywordList.clear();
                        keywordList = response.list;

                        _activity.hideProgress();

                        refreshList();
                    }

                    @Override
                    public void onFailure(MError response) {
                        super.onFailure(response);
                        _activity.hideProgress();
                        _activity.showErrorMsg(response);
                    }
                });
    }

    void refreshList() {
        adpKeywordList.clear();
        adpChildKeywordList.clear();

        if (keywordList.size() < 1) {
            adpKeywordList.notifyDataSetChanged();
            adpChildKeywordList.notifyDataSetChanged();
            return;
        }

        String keyword = ui_edtSearchKeyword.getText().toString();
        if (keyword.isEmpty()) {
            List<MKeywordList.Keyword> childKeyword = new ArrayList<>();
            for (MKeywordList.Keyword parent : keywordList) {
                childKeyword.addAll(parent.child);
            }

            adpKeywordList.addAll(keywordList);
            adpKeywordList.notifyDataSetChanged();

            adpChildKeywordList.addAll(childKeyword);
            adpChildKeywordList.notifyDataSetChanged();

            return;
        }

        ArrayList<MKeywordList.Keyword> tempList = new ArrayList<>(keywordList);
        for (int ind = tempList.size() - 1; ind >= 0; ind--) {
            MKeywordList.Keyword item = tempList.get(ind);
            boolean shouldRemove = true;

            if (item.name.contains(keyword)) {
                shouldRemove = false;
            }

            for (int childInd = item.child.size() - 1; childInd >= 0; childInd--) {
                MKeywordList.Keyword childItem = item.child.get(childInd);

                if (childItem.name.contains(keyword)) {
                    shouldRemove = false;
                } else {
                    tempList.get(ind).child.remove(childInd);
                }
            }

            if (shouldRemove) {
                tempList.remove(ind);
            }

        }

        adpKeywordList.addAll(tempList);
        adpKeywordList.notifyDataSetChanged();

        List<MKeywordList.Keyword> childKeyword = new ArrayList<>();
        for (MKeywordList.Keyword parent : keywordList) {
            childKeyword.addAll(parent.child);
        }

        adpChildKeywordList.addAll(childKeyword);
        adpChildKeywordList.notifyDataSetChanged();
    }

}