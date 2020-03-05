// Generated code from Butter Knife. Do not modify!
package com.bene.pictures.ui.join;

import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.bene.pictures.R;
import com.bene.pictures.ui.base.BaseActivity_ViewBinding;
import com.bene.pictures.ui.widget.BaseTextView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class JoinAgreeActivity_ViewBinding extends BaseActivity_ViewBinding {
  private JoinAgreeActivity target;

  private View view2131296412;

  private View view2131296415;

  private View view2131296414;

  private View view2131296411;

  private View view2131296750;

  private View view2131296459;

  @UiThread
  public JoinAgreeActivity_ViewBinding(JoinAgreeActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public JoinAgreeActivity_ViewBinding(final JoinAgreeActivity target, View source) {
    super(target, source);

    this.target = target;

    View view;
    target.ui_scvUsetermBg = Utils.findRequiredViewAsType(source, R.id.scv_useterm_bg, "field 'ui_scvUsetermBg'", ScrollView.class);
    target.ui_scvPolicyBg = Utils.findRequiredViewAsType(source, R.id.scv_policy_bg, "field 'ui_scvPolicyBg'", ScrollView.class);
    target.ui_scvAdverBg = Utils.findRequiredViewAsType(source, R.id.scv_adver_bg, "field 'ui_scvAdverBg'", ScrollView.class);
    target.ui_imvAgreeAll = Utils.findRequiredViewAsType(source, R.id.imv_agree_all, "field 'ui_imvAgreeAll'", ImageView.class);
    target.ui_imvAgreeUseterm = Utils.findRequiredViewAsType(source, R.id.imv_agree_useterm, "field 'ui_imvAgreeUseterm'", ImageView.class);
    target.ui_txvUseTerm = Utils.findRequiredViewAsType(source, R.id.txv_useterm, "field 'ui_txvUseTerm'", BaseTextView.class);
    target.ui_imvAgreePolicy = Utils.findRequiredViewAsType(source, R.id.imv_agree_policy, "field 'ui_imvAgreePolicy'", ImageView.class);
    target.ui_txvPolicy = Utils.findRequiredViewAsType(source, R.id.txv_policy, "field 'ui_txvPolicy'", BaseTextView.class);
    target.ui_imvAgreeAdver = Utils.findRequiredViewAsType(source, R.id.imv_agree_adver, "field 'ui_imvAgreeAdver'", ImageView.class);
    target.ui_txvAdver = Utils.findRequiredViewAsType(source, R.id.txv_adver, "field 'ui_txvAdver'", BaseTextView.class);
    view = Utils.findRequiredView(source, R.id.fly_agree_all_bg, "method 'OnClickAgreeAll'");
    view2131296412 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnClickAgreeAll();
      }
    });
    view = Utils.findRequiredView(source, R.id.fly_agree_useterm_bg, "method 'OnClickAgreeUseTerm'");
    view2131296415 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnClickAgreeUseTerm();
      }
    });
    view = Utils.findRequiredView(source, R.id.fly_agree_policy_bg, "method 'OnClickAgreePolicy'");
    view2131296414 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnClickAgreePolicy();
      }
    });
    view = Utils.findRequiredView(source, R.id.fly_agree_adver_bg, "method 'OnClickAgreeAdver'");
    view2131296411 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnClickAgreeAdver();
      }
    });
    view = Utils.findRequiredView(source, R.id.txv_next, "method 'OnClickNext'");
    view2131296750 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnClickNext();
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
    JoinAgreeActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.ui_scvUsetermBg = null;
    target.ui_scvPolicyBg = null;
    target.ui_scvAdverBg = null;
    target.ui_imvAgreeAll = null;
    target.ui_imvAgreeUseterm = null;
    target.ui_txvUseTerm = null;
    target.ui_imvAgreePolicy = null;
    target.ui_txvPolicy = null;
    target.ui_imvAgreeAdver = null;
    target.ui_txvAdver = null;

    view2131296412.setOnClickListener(null);
    view2131296412 = null;
    view2131296415.setOnClickListener(null);
    view2131296415 = null;
    view2131296414.setOnClickListener(null);
    view2131296414 = null;
    view2131296411.setOnClickListener(null);
    view2131296411 = null;
    view2131296750.setOnClickListener(null);
    view2131296750 = null;
    view2131296459.setOnClickListener(null);
    view2131296459 = null;

    super.unbind();
  }
}
