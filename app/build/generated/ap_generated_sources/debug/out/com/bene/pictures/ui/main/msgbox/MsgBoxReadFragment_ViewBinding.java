// Generated code from Butter Knife. Do not modify!
package com.bene.pictures.ui.main.msgbox;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.bene.pictures.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MsgBoxReadFragment_ViewBinding implements Unbinder {
  private MsgBoxReadFragment target;

  @UiThread
  public MsgBoxReadFragment_ViewBinding(MsgBoxReadFragment target, View source) {
    this.target = target;

    target.ui_rcvContents = Utils.findRequiredViewAsType(source, R.id.rcv_contents, "field 'ui_rcvContents'", RecyclerView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    MsgBoxReadFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.ui_rcvContents = null;
  }
}
