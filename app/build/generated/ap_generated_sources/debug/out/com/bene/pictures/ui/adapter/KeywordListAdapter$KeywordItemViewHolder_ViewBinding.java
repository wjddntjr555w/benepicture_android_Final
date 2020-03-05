// Generated code from Butter Knife. Do not modify!
package com.bene.pictures.ui.adapter;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.bene.pictures.R;
import com.bene.pictures.ui.widget.BaseTextView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class KeywordListAdapter$KeywordItemViewHolder_ViewBinding implements Unbinder {
  private KeywordListAdapter.KeywordItemViewHolder target;

  private View view2131296528;

  @UiThread
  public KeywordListAdapter$KeywordItemViewHolder_ViewBinding(final KeywordListAdapter.KeywordItemViewHolder target,
      View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.lly_bg, "field 'ui_llyBg' and method 'OnClickItem'");
    target.ui_llyBg = Utils.castView(view, R.id.lly_bg, "field 'ui_llyBg'", LinearLayout.class);
    view2131296528 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnClickItem();
      }
    });
    target.ui_imvCheck = Utils.findRequiredViewAsType(source, R.id.imv_check, "field 'ui_imvCheck'", ImageView.class);
    target.ui_txvName = Utils.findRequiredViewAsType(source, R.id.txv_name, "field 'ui_txvName'", BaseTextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    KeywordListAdapter.KeywordItemViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.ui_llyBg = null;
    target.ui_imvCheck = null;
    target.ui_txvName = null;

    view2131296528.setOnClickListener(null);
    view2131296528 = null;
  }
}
