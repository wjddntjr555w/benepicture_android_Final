package com.bene.pictures.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bene.pictures.R;
import com.bene.pictures.model.MTakeHistoryList;
import com.bene.pictures.ui.widget.BaseTextView;

import java.text.NumberFormat;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TakerewardHistoryListAdapter extends ArrayAdapter {

    public interface OnhistoryItemListener {
        void OnClickItem(int position);
    }

    private Context _context;
    private OnhistoryItemListener _listener;

    public TakerewardHistoryListAdapter(Context context, ArrayList<MTakeHistoryList.Info> items, OnhistoryItemListener listener) {
        super(context, items);

        _context = context;
        _listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NoticeItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.row_takereward_his_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((NoticeItemViewHolder) holder).history = (MTakeHistoryList.Info) getItem(position);
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
        @BindView(R.id.txv_datetime)
        BaseTextView txvDatetime;
        @BindView(R.id.txv_subscribeno)
        BaseTextView txvSubscribeNo;
        @BindView(R.id.txv_money)
        BaseTextView txvMoney;
        @BindView(R.id.imv_mark)
        ImageView imvMark;

        MTakeHistoryList.Info history;

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

            if (history.status == 0) {
                if (history.is_admin == 1) {
                    txvTitle.setText("[수령]운영자선물");
                } else {
                    txvTitle.setText("[수령] " + history.subscribe_adname + "광고 시청 응모권");
                }
                txvDatetime.setText("날짜 - " + history.create_datetime);
                txvMoney.setText("당첨 : " + NumberFormat.getInstance().format(history.winning_money) + "원");
                imvMark.setBackgroundColor(0xff25d367);
            } else if (history.status == 1) {
                txvTitle.setText("상금 수령 신청");
                txvDatetime.setText("처리날짜 - " + history.take_datetime);
                txvMoney.setText("신청금액 : " + NumberFormat.getInstance().format(history.winning_money) + "원");
                imvMark.setBackgroundColor(0xffffd600);
            } else if (history.status == 2) {
                txvTitle.setText("상금 수령 처리 완료");
                txvDatetime.setText("처리날짜 - " + history.take_datetime);
                txvMoney.setText("처리금액 : " + NumberFormat.getInstance().format(history.winning_money) + "원");
                imvMark.setBackgroundColor(0xffff7b11);
            } else if (history.status == 3) {
                txvTitle.setText("자동소멸");
                txvDatetime.setText("처리날짜 - " + history.take_datetime);
                txvMoney.setText("소멸금액 : " + NumberFormat.getInstance().format(history.winning_money) + "원");
                imvMark.setBackgroundColor(0xffffd600);
            }

            String adname = "";
            if (history.is_admin == 1) {
                adname = "관리자지급";
            } else {
                adname = history.subscribe_adname;
            }

            SpannableString strNo = new SpannableString("응모권번호 : " + adname + history.subscribe_number);
            ForegroundColorSpan colorSpan = new ForegroundColorSpan(0xff4c52c5);//0xff545ac0
            strNo.setSpan(colorSpan, 8, 8 + adname.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            txvSubscribeNo.setText(strNo, TextView.BufferType.SPANNABLE);

            if (history.status > 0) {
                txvSubscribeNo.setVisibility(View.GONE);
            } else {
                txvSubscribeNo.setVisibility(View.VISIBLE);
            }
        }
    }
}

