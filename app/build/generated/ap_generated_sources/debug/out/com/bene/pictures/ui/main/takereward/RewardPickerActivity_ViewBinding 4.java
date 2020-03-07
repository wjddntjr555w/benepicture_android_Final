// Generated code from Butter Knife. Do not modify!
package com.bene.pictures.ui.main.takereward;

import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.bene.pictures.R;
import com.bene.pictures.ui.base.BaseActivity_ViewBinding;
import java.lang.IllegalStateException;
import java.lang.Override;

public class RewardPickerActivity_ViewBinding extends BaseActivity_ViewBinding {
  private RewardPickerActivity target;

  private View view2131296459;

  @UiThread
  public RewardPickerActivity_ViewBinding(RewardPickerActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public RewardPickerActivity_ViewBinding(final RewardPickerActivity target, View source) {
    super(target, source);

    this.target = target;

    View view;
    target.ui_rcvContents = Utils.findRequiredViewAsType(source, R.id.rcv_contents, "field 'ui_rcvContents'", RecyclerView.class);
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
    RewardPickerActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.ui_rcvContents = null;

    view2131296459.setOnClickListener(null);
    view2131296459 = null;

    super.unbind();
  }
}
