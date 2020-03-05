package com.bene.pictures.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bene.pictures.R;
import com.bene.pictures.model.MReviewList;
import com.bene.pictures.ui.widget.BaseTextView;
import com.bene.pictures.util.Util;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WinnerReviewListAdapter extends ArrayAdapter {

    public interface OnReviewItemListener {
        void OnClickItem(int position);
    }

    private Context _context;
    private OnReviewItemListener _listener;

    public WinnerReviewListAdapter(Context context, ArrayList<MReviewList.Info> items, OnReviewItemListener listener) {
        super(context, items);

        _context = context;
        _listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NoticeItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.row_winner_review_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((NoticeItemViewHolder) holder).bindItem();
    }

    @Override
    public int getItemCount() {
        return this.mItems.size();
    }

    class NoticeItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txv_title)
        BaseTextView txvTitle;
//        @BindView(R.id.txv_user_id)
//        BaseTextView txvUserId;
        @BindView(R.id.txv_cost)
        BaseTextView txvMoney;
        @BindView(R.id.txv_ad_name)
        BaseTextView txvAdName;
        @BindView(R.id.txv_content)
        BaseTextView txvContent;

        MReviewList.Info review;

        NoticeItemViewHolder(View p_view) {
            super(p_view);
            ButterKnife.bind(this, p_view);
        }

        @SuppressLint("DefaultLocale")
        void bindItem() {
            review = (MReviewList.Info) getItem(getAdapterPosition());

            if (review == null) {
                return;
            }

            String adname = "";
            if (review.is_admin == 1) {
                adname = "관리자지급";
            } else {
                adname = review.subscribe_adname;
            }

            txvTitle.setText(String.format("%d회차 당첨자 당첨 후기 ", review.round));
//            txvUserId.setText(review.user_id);
            txvMoney.setText(String.format("당첨금액 : %s", Util.makeMoneyType(review.winning_money)));

            SpannableString strNo = new SpannableString("응모권번호 : " + adname + "-" + review.subscribe_number);
            ForegroundColorSpan colorSpan = new ForegroundColorSpan(0xff545ac0);
            strNo.setSpan(colorSpan, 8, 8 + adname.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            txvAdName.setText(strNo, TextView.BufferType.SPANNABLE);

            txvContent.setText(review.content);
        }

        @OnClick(R.id.lly_bg)
        void OnClickBG() {
            if (_listener != null) {
                _listener.OnClickItem(getAdapterPosition());
            }
        }
    }
}

