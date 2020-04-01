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

public class MsglistAdapter$NoticeItemViewHolder_ViewBinding implements Unbinder {
  private MsglistAdapter.NoticeItemViewHolder target;

  @UiThread
  public MsglistAdapter$NoticeItemViewHolder_ViewBinding(MsglistAdapter.NoticeItemViewHolder target,
      View source) {
    this.target = target;

    target.ui_llyBg = Utils.findRequiredViewAsType(source, R.id.lly_bg, "field 'ui_llyBg'", LinearLayout.class);
    target.txvTitle = Utils.findRequiredViewAsType(source, R.id.txv_title, "field 'txvTitle'", BaseTextView.class);
    target.txvContent = Utils.findRequiredViewAsType(source, R.id.txv_content, "field 'txvContent'", BaseTextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    MsglistAdapter.NoticeItemViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.ui_llyBg = null;
    target.txvTitle = null;
    target.txvContent = null;
  }
}
