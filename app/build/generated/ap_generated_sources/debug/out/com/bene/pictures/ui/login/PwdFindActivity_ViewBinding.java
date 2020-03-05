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

public class PwdFindActivity_ViewBinding extends BaseActivity_ViewBinding {
  private PwdFindActivity target;

  private View view2131296725;

  private View view2131296724;

  private View view2131296459;

  @UiThread
  public PwdFindActivity_ViewBinding(PwdFindActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public PwdFindActivity_ViewBinding(final PwdFindActivity target, View source) {
    super(target, source);

    this.target = target;

    View view;
    target.ui_edtPhone = Utils.findRequiredViewAsType(source, R.id.edt_phone, "field 'ui_edtPhone'", BaseEditText.class);
    target.ui_edtAuthCode = Utils.findRequiredViewAsType(source, R.id.edt_authcode, "field 'ui_edtAuthCode'", BaseEditText.class);
    view = Utils.findRequiredView(source, R.id.txv_authsend, "method 'OnClickAuthSend'");
    view2131296725 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnClickAuthSend();
      }
    });
    view = Utils.findRequiredView(source, R.id.txv_authorize, "method 'OnClickAuthorize'");
    view2131296724 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnClickAuthorize();
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
    PwdFindActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.ui_edtPhone = null;
    target.ui_edtAuthCode = null;

    view2131296725.setOnClickListener(null);
    view2131296725 = null;
    view2131296724.setOnClickListener(null);
    view2131296724 = null;
    view2131296459.setOnClickListener(null);
    view2131296459 = null;

    super.unbind();
  }
}
