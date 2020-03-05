package com.bene.pictures.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bene.pictures.R;
import com.bene.pictures.model.MKeywordList;
import com.bene.pictures.ui.widget.BaseTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class KeywordListAdapter extends ArrayAdapter {

    public interface OnKeywordItemListener {
        void OnClickItem(int parent, int like);
    }

    private Context _context;
    private OnKeywordItemListener _listener;

    public KeywordListAdapter(Context context, ArrayList<MKeywordList.Keyword> items, OnKeywordItemListener listener) {
        super(context, items);

        _context = context;
        _listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new KeywordItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.row_keyword_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((KeywordItemViewHolder) holder).bindItem();
    }

    @Override
    public int getItemCount() {
        return this.mItems.size();
    }

    public int getChildPos(int parent) {
        for (int ind = 0; ind < getItemCount(); ind++) {
            MKeywordList.Keyword keyword = (MKeywordList.Keyword) getItem(ind);

            if (keyword != null) {
                if (keyword.parent == parent) {
                    return ind;
                }
            }
        }
        return 0;
    }

    public List<Integer> getSelectedList() {
        List<Integer> result = new ArrayList<>();

        for (int ind = 0; ind < getItemCount(); ind++) {
            MKeywordList.Keyword keyword = (MKeywordList.Keyword) getItem(ind);

            if (keyword != null) {
                if (keyword.like == 1) {
                    result.add(keyword.id);
                }

//                for (MKeywordList.Keyword child : keyword.child) {
//                    if (child.like == 1) {
//                        result.add(child.id);
//                    }
//                }
            }
        }

        return result;
    }

    public void setChildKeyword(int parent, int like) {
        for (int ind = 0; ind < getItemCount(); ind++) {
            MKeywordList.Keyword keyword = (MKeywordList.Keyword) getItem(ind);

            if (keyword != null) {
                if (keyword.parent == parent) {
                    keyword.like = like;
                }
            }
        }
        notifyDataSetChanged();
    }

    class KeywordItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.lly_bg)
        LinearLayout ui_llyBg;
        @BindView(R.id.imv_check)
        ImageView ui_imvCheck;
        @BindView(R.id.txv_name)
        BaseTextView ui_txvName;

        MKeywordList.Keyword keyword;

        KeywordItemViewHolder(View p_view) {
            super(p_view);
            ButterKnife.bind(this, p_view);
        }

        void bindItem() {
            keyword = (MKeywordList.Keyword) getItem(getAdapterPosition());

            if (keyword != null) {
                ui_imvCheck.setSelected(keyword.like == 1);
                ui_txvName.setText(keyword.name);
            }
        }

        @OnClick(R.id.lly_bg)
        void OnClickItem() {
            keyword.like = 1 - keyword.like;
            notifyItemChanged(getAdapterPosition());

            if (_listener != null) {
                _listener.OnClickItem(keyword.id, keyword.like);
            }
        }
    }


}