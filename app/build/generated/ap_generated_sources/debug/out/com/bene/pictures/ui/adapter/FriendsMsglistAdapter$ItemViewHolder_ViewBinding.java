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

public class FriendsMsglistAdapter$ItemViewHolder_ViewBinding implements Unbinder {
  private FriendsMsglistAdapter.ItemViewHolder target;

  @UiThread
  public FriendsMsglistAdapter$ItemViewHolder_ViewBinding(FriendsMsglistAdapter.ItemViewHolder target,
      View source) {
    this.target = target;

    target.ui_llyBg = Utils.findRequiredViewAsType(source, R.id.lly_bg, "field 'ui_llyBg'", LinearLayout.class);
    target.ui_imvFace = Utils.findRequiredViewAsType(source, R.id.imv_face, "field 'ui_imvFace'", ImageView.class);
    target.txvName = Utils.findRequiredViewAsType(source, R.id.txv_name, "field 'txvName'", BaseTextView.class);
    target.txvNo = Utils.findRequiredViewAsType(source, R.id.txv_no, "field 'txvNo'", BaseTextView.class);
    target.txvYes = Utils.findRequiredViewAsType(source, R.id.txv_yes, "field 'txvYes'", BaseTextView.class);
    target.ui_imvMark = Utils.findRequiredViewAsType(source, R.id.imv_mark, "field 'ui_imvMark'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    FriendsMsglistAdapter.ItemViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.ui_llyBg = null;
    target.ui_imvFace = null;
    target.txvName = null;
    target.txvNo = null;
    target.txvYes = null;
    target.ui_imvMark = null;
  }
}
