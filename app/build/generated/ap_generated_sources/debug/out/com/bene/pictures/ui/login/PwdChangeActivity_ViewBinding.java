// Generated code from Butter Knife. Do not modify!
package com.bene.pictures.ui.login;

import android.support.annotation.UiThread;
import android.view.View;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.bene.pictures.R;
import com.bene.pictures.ui.base.BaseActivity_ViewBinding;
import com.bene.pictures.ui.widget.BaseEditText;
import java.lang.IllegalStateException;
import java.lang.Override;

public class PwdChangeActivity_ViewBinding extends BaseActivity_ViewBinding {
  private PwdChangeActivity target;

  private View view2131296728;

  private View view2131296459;

  @UiThread
  public PwdChangeActivity_ViewBinding(PwdChangeActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public PwdChangeActivity_ViewBinding(final PwdChangeActivity target, View source) {
    super(target, source);

    this.target = target;

    View view;
    target.ui_edtPwd = Utils.findRequiredViewAsType(source, R.id.edt_pwd, "field 'ui_edtPwd'", BaseEditText.class);
    target.ui_edtPwdConf = Utils.findRequiredViewAsType(source, R.id.edt_pwdconf, "field 'ui_edtPwdConf'", BaseEditText.class);
    view = Utils.findRequiredView(source, R.id.txv_change, "method 'OnClickChange'");
    view2131296728 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnClickChange();
      }
    });
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
    PwdChangeActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.ui_edtPwd = null;
    target.ui_edtPwdConf = null;

    view2131296728.setOnClickListener(null);
    view2131296728 = null;
    view2131296459.setOnClickListener(null);
    view2131296459 = null;

    super.unbind();
  }
}
