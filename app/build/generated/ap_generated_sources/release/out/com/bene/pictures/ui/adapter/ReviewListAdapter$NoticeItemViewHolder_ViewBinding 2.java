// Generated code from Butter Knife. Do not modify!
package com.bene.pictures.ui.adapter;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.LinearLayout;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.bene.pictures.R;
import com.bene.pictures.ui.widget.BaseTextView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ReviewListAdapter$NoticeItemViewHolder_ViewBinding implements Unbinder {
  private ReviewListAdapter.NoticeItemViewHolder target;

  @UiThread
  public ReviewListAdapter$NoticeItemViewHolder_ViewBinding(ReviewListAdapter.NoticeItemViewHolder target,
      View source) {
    this.target = target;

    target.ui_llyBg = Utils.findRequiredViewAsType(source, R.id.lly_bg, "field 'ui_llyBg'", LinearLayout.class);
    target.txvNumber = Utils.findRequiredViewAsType(source, R.id.txv_number, "field 'txvNumber'", BaseTextView.class);
    target.txvDatetime = Utils.findRequiredViewAsType(source, R.id.txv_datetime, "field 'txvDatetime'", BaseTextView.class);
    target.txvMoney = Utils.findRequiredViewAsType(source, R.id.txv_money, "field 'txvMoney'", BaseTextView.class);
    target.txvContent = Utils.findRequiredViewAsType(source, R.id.txv_content, "field 'txvContent'", BaseTextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ReviewListAdapter.NoticeItemViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.ui_llyBg = null;
    target.txvNumber = null;
    target.txvDatetime = null;
    target.txvMoney = null;
    target.txvContent = null;
  }
}
