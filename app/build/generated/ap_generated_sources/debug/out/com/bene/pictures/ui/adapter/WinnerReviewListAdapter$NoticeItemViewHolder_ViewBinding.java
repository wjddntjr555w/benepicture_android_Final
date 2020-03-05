// Generated code from Butter Knife. Do not modify!
package com.bene.pictures.ui.adapter;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.bene.pictures.R;
import com.bene.pictures.ui.widget.BaseTextView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class WinnerReviewListAdapter$NoticeItemViewHolder_ViewBinding implements Unbinder {
  private WinnerReviewListAdapter.NoticeItemViewHolder target;

  private View view2131296528;

  @UiThread
  public WinnerReviewListAdapter$NoticeItemViewHolder_ViewBinding(final WinnerReviewListAdapter.NoticeItemViewHolder target,
      View source) {
    this.target = target;

    View view;
    target.txvTitle = Utils.findRequiredViewAsType(source, R.id.txv_title, "field 'txvTitle'", BaseTextView.class);
    target.txvUserId = Utils.findRequiredViewAsType(source, R.id.txv_user_id, "field 'txvUserId'", BaseTextView.class);
    target.txvMoney = Utils.findRequiredViewAsType(source, R.id.txv_cost, "field 'txvMoney'", BaseTextView.class);
    target.txvAdName = Utils.findRequiredViewAsType(source, R.id.txv_ad_name, "field 'txvAdName'", BaseTextView.class);
    target.txvContent = Utils.findRequiredViewAsType(source, R.id.txv_content, "field 'txvContent'", BaseTextView.class);
    view = Utils.findRequiredView(source, R.id.lly_bg, "method 'OnClickBG'");
    view2131296528 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnClickBG();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    WinnerReviewListAdapter.NoticeItemViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.txvTitle = null;
    target.txvUserId = null;
    target.txvMoney = null;
    target.txvAdName = null;
    target.txvContent = null;

    view2131296528.setOnClickListener(null);
    view2131296528 = null;
  }
}
