// Generated code from Butter Knife. Do not modify!
package com.bene.pictures.ui.dialog;

import android.support.annotation.UiThread;
import android.view.View;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.bene.pictures.R;
import com.bene.pictures.ui.base.BaseActivity_ViewBinding;
import com.bene.pictures.ui.widget.BaseEditText;
import com.bene.pictures.ui.widget.BaseTextView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class UserCheckActivity_ViewBinding extends BaseActivity_ViewBinding {
  private UserCheckActivity target;

  private View view2131296777;

  private View view2131296751;

  private View view2131296418;

  private View view2131296528;

  @UiThread
  public UserCheckActivity_ViewBinding(UserCheckActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public UserCheckActivity_ViewBinding(final UserCheckActivity target, View source) {
    super(target, source);

    this.target = target;

    View view;
    target.ui_edtPwd = Utils.findRequiredViewAsType(source, R.id.edt_pwd, "field 'ui_edtPwd'", BaseEditText.class);
    view = Utils.findRequiredView(source, R.id.txv_yes, "field 'ui_txvYes' and method 'OnClickYes'");
    target.ui_txvYes = Utils.castView(view, R.id.txv_yes, "field 'ui_txvYes'", BaseTextView.class);
    view2131296777 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnClickYes();
      }
    });
    view = Utils.findRequiredView(source, R.id.txv_no, "field 'ui_txvNo' and method 'OnClickNo'");
    target.ui_txvNo = Utils.castView(view, R.id.txv_no, "field 'ui_txvNo'", BaseTextView.class);
    view2131296751 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnClickNo();
      }
    });
    view = Utils.findRequiredView(source, R.id.fly_bg, "method 'OnClickWholeBg'");
    view2131296418 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnClickWholeBg();
      }
    });
    view = Utils.findRequiredView(source, R.id.lly_bg, "method 'OnClickBg'");
    view2131296528 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnClickBg();
      }
    });
  }

  @Override
  public void unbind() {
    UserCheckActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.ui_edtPwd = null;
    target.ui_txvYes = null;
    target.ui_txvNo = null;

    view2131296777.setOnClickListener(null);
    view2131296777 = null;
    view2131296751.setOnClickListener(null);
    view2131296751 = null;
    view2131296418.setOnClickListener(null);
    view2131296418 = null;
    view2131296528.setOnClickListener(null);
    view2131296528 = null;

    super.unbind();
  }
}
