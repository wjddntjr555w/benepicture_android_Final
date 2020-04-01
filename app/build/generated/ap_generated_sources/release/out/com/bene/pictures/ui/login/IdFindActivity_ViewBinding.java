// Generated code from Butter Knife. Do not modify!
package com.bene.pictures.ui.login;

import android.support.annotation.UiThread;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.bene.pictures.R;
import com.bene.pictures.ui.base.BaseActivity_ViewBinding;
import com.bene.pictures.ui.widget.BaseEditText;
import com.bene.pictures.ui.widget.BaseTextView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class IdFindActivity_ViewBinding extends BaseActivity_ViewBinding {
  private IdFindActivity target;

  private View view2131296724;

  private View view2131296725;

  private View view2131296459;

  @UiThread
  public IdFindActivity_ViewBinding(IdFindActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public IdFindActivity_ViewBinding(final IdFindActivity target, View source) {
    super(target, source);

    this.target = target;

    View view;
    target.ui_llyStep1Bg = Utils.findRequiredViewAsType(source, R.id.lly_idfind_step1_bg, "field 'ui_llyStep1Bg'", LinearLayout.class);
    target.ui_rlyStep2Bg = Utils.findRequiredViewAsType(source, R.id.rly_idfind_step2_bg, "field 'ui_rlyStep2Bg'", RelativeLayout.class);
    view = Utils.findRequiredView(source, R.id.txv_authorize, "field 'ui_txvAuthorize' and method 'OnClickAuthorize'");
    target.ui_txvAuthorize = Utils.castView(view, R.id.txv_authorize, "field 'ui_txvAuthorize'", BaseTextView.class);
    view2131296724 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnClickAuthorize();
      }
    });
    target.ui_edtPhone = Utils.findRequiredViewAsType(source, R.id.edt_phone, "field 'ui_edtPhone'", BaseEditText.class);
    target.ui_edtAuthCode = Utils.findRequiredViewAsType(source, R.id.edt_authcode, "field 'ui_edtAuthCode'", BaseEditText.class);
    target.ui_edtId = Utils.findRequiredViewAsType(source, R.id.edt_id, "field 'ui_edtId'", BaseEditText.class);
    view = Utils.findRequiredView(source, R.id.txv_authsend, "method 'OnClickAuthSend'");
    view2131296725 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnClickAuthSend();
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
    IdFindActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.ui_llyStep1Bg = null;
    target.ui_rlyStep2Bg = null;
    target.ui_txvAuthorize = null;
    target.ui_edtPhone = null;
    target.ui_edtAuthCode = null;
    target.ui_edtId = null;

    view2131296724.setOnClickListener(null);
    view2131296724 = null;
    view2131296725.setOnClickListener(null);
    view2131296725 = null;
    view2131296459.setOnClickListener(null);
    view2131296459 = null;

    super.unbind();
  }
}
