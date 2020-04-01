// Generated code from Butter Knife. Do not modify!
package com.bene.pictures.ui.adapter;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.bene.pictures.R;
import com.bene.pictures.ui.widget.BaseTextView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SubscribeInfoListAdapter$NoticeItemViewHolder_ViewBinding implements Unbinder {
  private SubscribeInfoListAdapter.NoticeItemViewHolder target;

  @UiThread
  public SubscribeInfoListAdapter$NoticeItemViewHolder_ViewBinding(SubscribeInfoListAdapter.NoticeItemViewHolder target,
      View source) {
    this.target = target;

    target.ui_rlyBg = Utils.findRequiredViewAsType(source, R.id.rly_bg, "field 'ui_rlyBg'", RelativeLayout.class);
    target.ui_frmBg = Utils.findRequiredViewAsType(source, R.id.frm_bg, "field 'ui_frmBg'", FrameLayout.class);
    target.ui_rlyHeader = Utils.findRequiredViewAsType(source, R.id.rly_header, "field 'ui_rlyHeader'", RelativeLayout.class);
    target.txvTitle = Utils.findRequiredViewAsType(source, R.id.txv_title, "field 'txvTitle'", BaseTextView.class);
    target.txvDatetime = Utils.findRequiredViewAsType(source, R.id.txv_datetime, "field 'txvDatetime'", BaseTextView.class);
    target.txvSubscribeNo = Utils.findRequiredViewAsType(source, R.id.txv_subscribeno, "field 'txvSubscribeNo'", BaseTextView.class);
    target.txvAdName = Utils.findRequiredViewAsType(source, R.id.txv_ad_name, "field 'txvAdName'", BaseTextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    SubscribeInfoListAdapter.NoticeItemViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.ui_rlyBg = null;
    target.ui_frmBg = null;
    target.ui_rlyHeader = null;
    target.txvTitle = null;
    target.txvDatetime = null;
    target.txvSubscribeNo = null;
    target.txvAdName = null;
  }
}
