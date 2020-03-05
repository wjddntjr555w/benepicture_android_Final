package com.bene.pictures.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bene.pictures.R;
import com.bene.pictures.model.MWinnerSubscribeList;
import com.bene.pictures.ui.widget.BaseTextView;
import com.bene.pictures.util.Util;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WinnerSubscribeListAdapter extends ArrayAdapter {

    public interface OnWinnerItemClickListener {
        void OnClickItem(int position);
    }

    private Context _context;
    private OnWinnerItemClickListener _listener;

    public WinnerSubscribeListAdapter(Context context, ArrayList<MWinnerSubscribeList.Info> items, OnWinnerItemClickListener listener) {
        super(context, items);

        _context = context;
        _listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NoticeItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.row_winner_subscribe_item, parent, false));
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

        @BindView(R.id.txv_ad_name)
        BaseTextView txvAdName;
        @BindView(R.id.txv_usr_id)
        BaseTextView txvUsrId;
        @BindView(R.id.txv_subscribe_no)
        BaseTextView txvSubscribeNo;
        @BindView(R.id.txv_phone)
        BaseTextView txvPhone;
        @BindView(R.id.txv_cost)
        BaseTextView txvCost;
        @BindView(R.id.imv_mark)
        ImageView imvMark;

        MWinnerSubscribeList.Info history;

        NoticeItemViewHolder(View p_view) {
            super(p_view);
            ButterKnife.bind(this, p_view);
        }

        void bindItem() {

            history = (MWinnerSubscribeList.Info) getItem(getAdapterPosition());

            if (history != null) {
                txvAdName.setText(String.format("%s 응모권", history.ad_name));
                txvUsrId.setText(Util.convertHiddenString(history.usr_id));
                txvSubscribeNo.setText(String.format("응모권번호 : %s %s", history.ad_name, history.no));
                txvPhone.setText(String.format("휴대폰 뒷자리 : %s", history.phone));
                txvCost.setText(String.format("당첨 : %s원", Util.makeMoneyType(history.cost)));
            }

            if (getAdapterPosition() == 0) {
                imvMark.setBackgroundColor(_context.getResources().getColor(R.color.color_ffffd600));
            } else {
                imvMark.setBackgroundColor(_context.getResources().getColor(R.color.color_ff1976d2));
            }

        }
    }
}

