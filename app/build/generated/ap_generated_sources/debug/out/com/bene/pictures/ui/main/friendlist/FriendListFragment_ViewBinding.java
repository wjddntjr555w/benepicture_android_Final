// Generated code from Butter Knife. Do not modify!
package com.bene.pictures.ui.main.friendlist;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.bene.pictures.R;
import com.bene.pictures.ui.widget.BaseEditText;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FriendListFragment_ViewBinding implements Unbinder {
  private FriendListFragment target;

  private View view2131296422;

  private View view2131296760;

  private View view2131296473;

  @UiThread
  public FriendListFragment_ViewBinding(final FriendListFragment target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.fly_kakao_bg, "field 'ui_flyKakaoBg' and method 'OnClickKaKaoBg'");
    target.ui_flyKakaoBg = Utils.castView(view, R.id.fly_kakao_bg, "field 'ui_flyKakaoBg'", FrameLayout.class);
    view2131296422 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnClickKaKaoBg();
      }
    });
    target.ui_edtSearch = Utils.findRequiredViewAsType(source, R.id.edt_search, "field 'ui_edtSearch'", BaseEditText.class);
    target.ui_rcvContents = Utils.findRequiredViewAsType(source, R.id.rcv_contents, "field 'ui_rcvContents'", RecyclerView.class);
    view = Utils.findRequiredView(source, R.id.txv_search, "method 'OnClickSearch'");
    view2131296760 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnClickSearch();
      }
    });
    view = Utils.findRequiredView(source, R.id.imv_kakao, "method 'OnClickKaKaoBg'");
    view2131296473 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnClickKaKaoBg();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    FriendListFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.ui_flyKakaoBg = null;
    target.ui_edtSearch = null;
    target.ui_rcvContents = null;

    view2131296422.setOnClickListener(null);
    view2131296422 = null;
    view2131296760.setOnClickListener(null);
    view2131296760 = null;
    view2131296473.setOnClickListener(null);
    view2131296473 = null;
  }
}
