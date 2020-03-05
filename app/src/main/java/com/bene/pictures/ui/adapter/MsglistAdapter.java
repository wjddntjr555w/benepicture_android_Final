package com.bene.pictures.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bene.pictures.R;
import com.bene.pictures.model.MMsgList;
import com.bene.pictures.ui.widget.BaseTextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MsglistAdapter extends ArrayAdapter {

    public interface OnMsgListItemListener {
        void OnClickItem(int position);
    }

    private Context _context;
    private OnMsgListItemListener _listener;

    public MsglistAdapter(Context context, ArrayList<MMsgList.Info> items, OnMsgListItemListener listener) {
        super(context, items);

        _context = context;
        _listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NoticeItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.row_msg_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((NoticeItemViewHolder) holder).msg = (MMsgList.Info) getItem(position);
        ((NoticeItemViewHolder) holder).bindItem(position);
    }

    @Override
    public int getItemCount() {
        return this.mItems.size();
    }

    public void removeItem(int position) {
        mItems.remove(position);
        notifyDataSetChanged();
    }

    class NoticeItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.lly_bg)
        LinearLayout ui_llyBg;
        @BindView(R.id.txv_title)
        BaseTextView txvTitle;
        @BindView(R.id.txv_content)
        BaseTextView txvContent;

        MMsgList.Info msg;

        NoticeItemViewHolder(View p_view) {
            super(p_view);
            ButterKnife.bind(this, p_view);
        }

        void bindItem(final int position) {

            ui_llyBg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    _listener.OnClickItem(position);
                }
            });

            txvTitle.setText(msg.title);
            txvContent.setText(msg.content);
        }
    }
}

