// Generated code from Butter Knife. Do not modify!
package com.bene.pictures.ui.main.friendlist;

import android.support.annotation.UiThread;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.bene.pictures.R;
import com.bene.pictures.ui.base.BaseActivity_ViewBinding;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FriendListActivity_ViewBinding extends BaseActivity_ViewBinding {
  private FriendListActivity target;

  private View view2131296459;

  @UiThread
  public FriendListActivity_ViewBinding(FriendListActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public FriendListActivity_ViewBinding(final FriendListActivity target, View source) {
    super(target, source);

    this.target = target;

    View view;
    target.ui_tblPagingMenu = Utils.findRequiredViewAsType(source, R.id.tbl_paging_menu, "field 'ui_tblPagingMenu'", TabLayout.class);
    target.ui_vprContentPages = Utils.findRequiredViewAsType(source, R.id.vpr_content, "field 'ui_vprContentPages'", ViewPager.class);
    view = Utils.findRequiredView(source, R.id.imv_back, "method 'OnClickBack'");
    view2131296459 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnClickBack();
      }
    });
  }

  @Override
  public void unbind() {
    FriendListActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.ui_tblPagingMenu = null;
    target.ui_vprContentPages = null;

    view2131296459.setOnClickListener(null);
    view2131296459 = null;

    super.unbind();
  }
}
