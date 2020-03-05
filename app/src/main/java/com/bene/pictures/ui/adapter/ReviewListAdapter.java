package com.bene.pictures.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bene.pictures.R;
import com.bene.pictures.model.MReviewList;
import com.bene.pictures.ui.widget.BaseTextView;
import com.bene.pictures.util.Util;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReviewListAdapter extends ArrayAdapter {

    public interface OnReviewItemListener {
        void OnClickItem(int position);
    }

    private Context _context;
    private OnReviewItemListener _listener;

    public ReviewListAdapter(Context context, ArrayList<MReviewList.Info> items, OnReviewItemListener listener) {
        super(context, items);

        _context = context;
        _listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NoticeItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.row_review_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((NoticeItemViewHolder) holder).review = (MReviewList.Info) getItem(position);
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
        @BindView(R.id.txv_number)
        BaseTextView txvNumber;
        @BindView(R.id.txv_datetime)
        BaseTextView txvDatetime;
        @BindView(R.id.txv_money)
        BaseTextView txvMoney;
        @BindView(R.id.txv_content)
        BaseTextView txvContent;

        MReviewList.Info review;

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

            String adname = "";
            if (review.is_admin == 1) {
                adname = "관리자지급";
            } else {
                adname = review.subscribe_adname;
            }

            SpannableString strNo = new SpannableString("응모권번호 : " + adname + review.subscribe_number);
            ForegroundColorSpan colorSpan = new ForegroundColorSpan(0xff545ac0);
            strNo.setSpan(colorSpan, 8, 8 + adname.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            txvNumber.setText(strNo, TextView.BufferType.SPANNABLE);

            txvDatetime.setText("작성일 : " + review.create_date);
            txvMoney.setText("당첨금액 : " + Util.makeMoneyType(review.winning_money) + "원");
            txvContent.setText(review.content);
        }
    }
}

