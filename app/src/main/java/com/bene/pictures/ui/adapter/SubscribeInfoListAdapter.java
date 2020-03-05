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
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bene.pictures.R;
import com.bene.pictures.model.MSubscribeInfoList;
import com.bene.pictures.ui.widget.BaseTextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SubscribeInfoListAdapter extends ArrayAdapter {

    public interface OnSubscribeInfoItemListener {
        void OnClickItem(int position);
    }

    private Context _context;
    private OnSubscribeInfoItemListener _listener;

    public SubscribeInfoListAdapter(Context context, ArrayList<MSubscribeInfoList.Info> items, OnSubscribeInfoItemListener listener) {
        super(context, items);

        _context = context;
        _listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NoticeItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.row_subscribeinfo_item_new, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((NoticeItemViewHolder) holder).subscribeinfo = (MSubscribeInfoList.Info) getItem(position);
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

        @BindView(R.id.rly_bg)
        RelativeLayout ui_rlyBg;
        @BindView(R.id.frm_bg)
        FrameLayout ui_frmBg;
        @BindView(R.id.rly_header)
        RelativeLayout ui_rlyHeader;
        @BindView(R.id.txv_title)
        BaseTextView txvTitle;
        @BindView(R.id.txv_datetime)
        BaseTextView txvDatetime;
        @BindView(R.id.txv_subscribeno)
        BaseTextView txvSubscribeNo;
        @BindView(R.id.txv_ad_name)
        BaseTextView txvAdName;


        MSubscribeInfoList.Info subscribeinfo;

        NoticeItemViewHolder(View p_view) {
            super(p_view);
            ButterKnife.bind(this, p_view);
        }

        @SuppressLint("DefaultLocale")
        void bindItem(final int position) {

            ui_rlyBg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    _listener.OnClickItem(position);
                }
            });

//            if (subscribeinfo.is_admin == 1) {
//                txvTitle.setText("[운영자 선물]");
//            } else {
//                txvTitle.setText(subscribeinfo.subscribe_adname + " 광고 시청 응모권");
//            }
//            txvDatetime.setText("날짜 - " + subscribeinfo.create_datetime);

            if (subscribeinfo.is_winning == 1) {
                // 당첨됨
//                ui_rlyHeader.setBackgroundResource(R.drawable.xml_bg_subscribe_yellow);
                ui_frmBg.setBackgroundResource(R.drawable.bg_subscribe_yellow);
                txvTitle.setTextColor(_context.getResources().getColor(R.color.color_black));
                txvDatetime.setText("당첨");
                txvDatetime.setTextColor(_context.getResources().getColor(R.color.color_black));
                txvDatetime.setVisibility(View.VISIBLE);
            } else {
                // 미당첨
                if (subscribeinfo.current_round == subscribeinfo.round) {
                    // 현재 진행하고 있는 회차
//                    ui_rlyHeader.setBackgroundResource(R.drawable.xml_bg_subscribe_blue);
                    ui_frmBg.setBackgroundResource(R.drawable.bg_subscribe_blue);
                    txvTitle.setTextColor(_context.getResources().getColor(R.color.color_white));
                    txvDatetime.setTextColor(_context.getResources().getColor(R.color.color_white));
                    txvDatetime.setVisibility(View.GONE);
                } else {
//                    ui_rlyHeader.setBackgroundResource(R.drawable.xml_bg_subscribe_gray);
                    ui_frmBg.setBackgroundResource(R.drawable.bg_subscribe_gray);
                    txvTitle.setTextColor(_context.getResources().getColor(R.color.color_white));
                    txvDatetime.setText("미당첨");
                    txvDatetime.setTextColor(_context.getResources().getColor(R.color.color_white));
                    txvDatetime.setVisibility(View.VISIBLE);
                }
            }

            txvTitle.setText(String.format("%d회차 응모권", subscribeinfo.round));

            String adname = "";
            if (subscribeinfo.is_admin == 1) {
                adname = "관리자지급";
            } else {
                adname = subscribeinfo.subscribe_adname;
            }

            txvAdName.setText(adname);
            txvSubscribeNo.setText(subscribeinfo.subscribe_number);

//            SpannableString strNo = new SpannableString(adname + " " + subscribeinfo.subscribe_number);
//            ForegroundColorSpan colorSpan = new ForegroundColorSpan(0xff545ac0);
//            strNo.setSpan(colorSpan, 0, adname.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//            txvSubscribeNo.setText(strNo, TextView.BufferType.SPANNABLE);
        }
    }
}

