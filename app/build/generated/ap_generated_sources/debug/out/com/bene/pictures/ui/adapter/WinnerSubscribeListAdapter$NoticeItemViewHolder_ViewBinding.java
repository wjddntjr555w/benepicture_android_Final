// Generated code from Butter Knife. Do not modify!
package com.bene.pictures.ui.adapter;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.bene.pictures.R;
import com.bene.pictures.ui.widget.BaseTextView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class WinnerSubscribeListAdapter$NoticeItemViewHolder_ViewBinding implements Unbinder {
  private WinnerSubscribeListAdapter.NoticeItemViewHolder target;

  @UiThread
  public WinnerSubscribeListAdapter$NoticeItemViewHolder_ViewBinding(WinnerSubscribeListAdapter.NoticeItemViewHolder target,
      View source) {
    this.target = target;

    target.txvAdName = Utils.findRequiredViewAsType(source, R.id.txv_ad_name, "field 'txvAdName'", BaseTextView.class);
    target.txvUsrId = Utils.findRequiredViewAsType(source, R.id.txv_usr_id, "field 'txvUsrId'", BaseTextView.class);
    target.txvSubscribeNo = Utils.findRequiredViewAsType(source, R.id.txv_subscribe_no, "field 'txvSubscribeNo'", BaseTextView.class);
    target.txvPhone = Utils.findRequiredViewAsType(source, R.id.txv_phone, "field 'txvPhone'", BaseTextView.class);
    target.txvCost = Utils.findRequiredViewAsType(source, R.id.txv_cost, "field 'txvCost'", BaseTextView.class);
    target.imvMark = Utils.findRequiredViewAsType(source, R.id.imv_mark, "field 'imvMark'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    WinnerSubscribeListAdapter.NoticeItemViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.txvAdName = null;
    target.txvUsrId = null;
    target.txvSubscribeNo = null;
    target.txvPhone = null;
    target.txvCost = null;
    target.imvMark = null;
  }
}
