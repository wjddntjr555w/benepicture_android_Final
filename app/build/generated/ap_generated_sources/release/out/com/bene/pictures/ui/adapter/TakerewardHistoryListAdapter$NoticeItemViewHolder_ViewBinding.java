// Generated code from Butter Knife. Do not modify!
package com.bene.pictures.ui.adapter;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.bene.pictures.R;
import com.bene.pictures.ui.widget.BaseTextView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class TakerewardHistoryListAdapter$NoticeItemViewHolder_ViewBinding implements Unbinder {
  private TakerewardHistoryListAdapter.NoticeItemViewHolder target;

  @UiThread
  public TakerewardHistoryListAdapter$NoticeItemViewHolder_ViewBinding(TakerewardHistoryListAdapter.NoticeItemViewHolder target,
      View source) {
    this.target = target;

    target.ui_llyBg = Utils.findRequiredViewAsType(source, R.id.lly_bg, "field 'ui_llyBg'", LinearLayout.class);
    target.txvTitle = Utils.findRequiredViewAsType(source, R.id.txv_title, "field 'txvTitle'", BaseTextView.class);
    target.txvDatetime = Utils.findRequiredViewAsType(source, R.id.txv_datetime, "field 'txvDatetime'", BaseTextView.class);
    target.txvSubscribeNo = Utils.findRequiredViewAsType(source, R.id.txv_subscribeno, "field 'txvSubscribeNo'", BaseTextView.class);
    target.txvMoney = Utils.findRequiredViewAsType(source, R.id.txv_money, "field 'txvMoney'", BaseTextView.class);
    target.imvMark = Utils.findRequiredViewAsType(source, R.id.imv_mark, "field 'imvMark'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    TakerewardHistoryListAdapter.NoticeItemViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.ui_llyBg = null;
    target.txvTitle = null;
    target.txvDatetime = null;
    target.txvSubscribeNo = null;
    target.txvMoney = null;
    target.imvMark = null;
  }
}
