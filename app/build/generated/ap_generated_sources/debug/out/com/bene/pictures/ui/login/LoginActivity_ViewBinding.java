// Generated code from Butter Knife. Do not modify!
package com.bene.pictures.ui.login;

import android.support.annotation.UiThread;
import android.view.View;
import android.widget.CheckBox;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.bene.pictures.R;
import com.bene.pictures.ui.base.BaseActivity_ViewBinding;
import com.bene.pictures.ui.widget.BaseEditText;
import java.lang.IllegalStateException;
import java.lang.Override;

public class LoginActivity_ViewBinding extends BaseActivity_ViewBinding {
  private LoginActivity target;

  private View view2131296472;

  private View view2131296498;

  private View view2131296746;

  private View view2131296742;

  private View view2131296757;

  private View view2131296759;

  private View view2131296611;

  @UiThread
  public LoginActivity_ViewBinding(LoginActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public LoginActivity_ViewBinding(final LoginActivity target, View source) {
    super(target, source);

    this.target = target;

    View view;
    target.ui_edtId = Utils.findRequiredViewAsType(source, R.id.edt_id, "field 'ui_edtId'", BaseEditText.class);
    target.ui_edtPwd = Utils.findRequiredViewAsType(source, R.id.edt_pwd, "field 'ui_edtPwd'", BaseEditText.class);
    target.ui_cbAutoLogin = Utils.findRequiredViewAsType(source, R.id.cb_auto_login, "field 'ui_cbAutoLogin'", CheckBox.class);
    view = Utils.findRequiredView(source, R.id.imv_id_delete, "method 'OnClickIdDelete'");
    view2131296472 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnClickIdDelete();
      }
    });
    view = Utils.findRequiredView(source, R.id.imv_pwd_delete, "method 'OnClickPwdDelete'");
    view2131296498 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnClickPwdDelete();
      }
    });
    view = Utils.findRequiredView(source, R.id.txv_login, "method 'OnClickLogin'");
    view2131296746 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnClickLogin();
      }
    });
    view = Utils.findRequiredView(source, R.id.txv_idfind, "method 'OnClickIdFind'");
    view2131296742 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnClickIdFind();
      }
    });
    view = Utils.findRequiredView(source, R.id.txv_pwdfind, "method 'OnClickPwdFind'");
    view2131296757 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnClickPwdFind();
      }
    });
    view = Utils.findRequiredView(source, R.id.txv_register, "method 'OnClickRegister'");
    view2131296759 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnClickRegister();
      }
    });
    view = Utils.findRequiredView(source, R.id.rly_kakao, "method 'OnClickKakao'");
    view2131296611 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnClickKakao();
      }
    });
  }

  @Override
  public void unbind() {
    LoginActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.ui_edtId = null;
    target.ui_edtPwd = null;
    target.ui_cbAutoLogin = null;

    view2131296472.setOnClickListener(null);
    view2131296472 = null;
    view2131296498.setOnClickListener(null);
    view2131296498 = null;
    view2131296746.setOnClickListener(null);
    view2131296746 = null;
    view2131296742.setOnClickListener(null);
    view2131296742 = null;
    view2131296757.setOnClickListener(null);
    view2131296757 = null;
    view2131296759.setOnClickListener(null);
    view2131296759 = null;
    view2131296611.setOnClickListener(null);
    view2131296611 = null;

    super.unbind();
  }
}
