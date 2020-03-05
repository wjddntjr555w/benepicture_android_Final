package com.bene.pictures.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bene.pictures.R;
import com.bene.pictures.model.MFriendMsgList;
import com.bene.pictures.ui.widget.BaseTextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FriendsMsglistAdapter extends ArrayAdapter {

    public interface OnFriendsListItemListener {
        void OnClickItem(int yesno, int position); //yes = 1, no = 0
    }

    private Context _context;
    private OnFriendsListItemListener _listener;

    public FriendsMsglistAdapter(Context context, ArrayList<MFriendMsgList.Info> items, OnFriendsListItemListener listener) {
        super(context, items);

        _context = context;
        _listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.row_friendlist_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ItemViewHolder) holder).friend = (MFriendMsgList.Info) getItem(position);
        ((ItemViewHolder) holder).bindItem(position);
    }

    @Override
    public int getItemCount() {
        return this.mItems.size();
    }

    public void removeItem(int position) {
        mItems.remove(position);
        notifyDataSetChanged();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.lly_bg)
        LinearLayout ui_llyBg;
        @BindView(R.id.imv_face)
        ImageView ui_imvFace;
        @BindView(R.id.txv_name)
        BaseTextView txvName;
        @BindView(R.id.txv_no)
        BaseTextView txvNo;
        @BindView(R.id.txv_yes)
        BaseTextView txvYes;
        @BindView(R.id.imv_mark)
        ImageView ui_imvMark;

        MFriendMsgList.Info friend;

        ItemViewHolder(View p_view) {
            super(p_view);
            ButterKnife.bind(this, p_view);
        }

        void bindItem(final int position) {

            txvNo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    _listener.OnClickItem(0, position);
                }
            });

            txvYes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    _listener.OnClickItem(1, position);
                }
            });

            //TODO: friend.face가 카톡아이디이므로 실지 URL은 앞에 URL Prefix를 붙인다.
            if(friend.profile != null && !friend.profile.isEmpty()){
                Glide.with(_context)
//                        .load("https://kakao.com/profile?" + friend.face)
                        .load(friend.profile)
                        .apply(new RequestOptions().centerCrop().dontAnimate())
                        .into(ui_imvFace);
            }

            txvName.setText(friend.name);

            if (friend.receivegift_status == 0) {
                txvNo.setText("선물거절");
                txvYes.setText("선물받기");
                txvYes.setVisibility(View.VISIBLE);
                txvNo.setVisibility(View.VISIBLE);
            } else {
                txvNo.setText("선물받기완료");
                txvYes.setText("선물받기");
                txvYes.setVisibility(View.GONE);
                txvNo.setVisibility(View.VISIBLE);
            }
            ui_imvMark.setVisibility(View.VISIBLE);
        }
    }
}

