// Generated code from Butter Knife. Do not modify!
package com.bene.pictures.ui.setting;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.bene.pictures.R;
import com.bene.pictures.ui.base.BaseActivity_ViewBinding;
import com.bene.pictures.ui.widget.BaseEditText;
import com.bene.pictures.ui.widget.BaseTextView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ProfileSettingActivity_ViewBinding extends BaseActivity_ViewBinding {
  private ProfileSettingActivity target;

  private View view2131296379;

  private View view2131296378;

  private View view2131296726;

  private View view2131296728;

  private View view2131296725;

  private View view2131296741;

  private View view2131296763;

  private View view2131296535;

  private View view2131296538;

  private View view2131296459;

  @UiThread
  public ProfileSettingActivity_ViewBinding(ProfileSettingActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ProfileSettingActivity_ViewBinding(final ProfileSettingActivity target, View source) {
    super(target, source);

    this.target = target;

    View view;
    target.ui_edtName = Utils.findRequiredViewAsType(source, R.id.edt_name, "field 'ui_edtName'", BaseEditText.class);
    view = Utils.findRequiredView(source, R.id.edt_birthday, "field 'ui_edtBirthday' and method 'OnClickBirthday'");
    target.ui_edtBirthday = Utils.castView(view, R.id.edt_birthday, "field 'ui_edtBirthday'", BaseTextView.class);
    view2131296379 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnClickBirthday();
      }
    });
    target.ui_edtPhone = Utils.findRequiredViewAsType(source, R.id.edt_phone, "field 'ui_edtPhone'", BaseEditText.class);
    target.ui_edtAuthCode = Utils.findRequiredViewAsType(source, R.id.edt_authcode, "field 'ui_edtAuthCode'", BaseEditText.class);
    target.ui_edtPwd = Utils.findRequiredViewAsType(source, R.id.edt_pwd, "field 'ui_edtPwd'", BaseEditText.class);
    target.ui_edtPwdConf = Utils.findRequiredViewAsType(source, R.id.edt_pwdconf, "field 'ui_edtPwdConf'", BaseEditText.class);
    target.ui_edtId = Utils.findRequiredViewAsType(source, R.id.edt_id, "field 'ui_edtId'", BaseEditText.class);
    view = Utils.findRequiredView(source, R.id.edt_bank_name, "field 'ui_edtBankName' and method 'OnClickBankNmae'");
    target.ui_edtBankName = Utils.castView(view, R.id.edt_bank_name, "field 'ui_edtBankName'", BaseTextView.class);
    view2131296378 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnClickBankNmae();
      }
    });
    target.ui_edtBank = Utils.findRequiredViewAsType(source, R.id.edt_bank, "field 'ui_edtBank'", BaseEditText.class);
    view = Utils.findRequiredView(source, R.id.txv_bankcheck, "field 'ui_txvBankCheck' and method 'OnClickBankCheck'");
    target.ui_txvBankCheck = Utils.castView(view, R.id.txv_bankcheck, "field 'ui_txvBankCheck'", BaseTextView.class);
    view2131296726 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnClickBankCheck();
      }
    });
    target.ui_imvMan = Utils.findRequiredViewAsType(source, R.id.imv_man, "field 'ui_imvMan'", ImageView.class);
    target.ui_imvWoman = Utils.findRequiredViewAsType(source, R.id.imv_woman, "field 'ui_imvWoman'", ImageView.class);
    view = Utils.findRequiredView(source, R.id.txv_change, "method 'OnClickJoin'");
    view2131296728 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnClickJoin();
      }
    });
    view = Utils.findRequiredView(source, R.id.txv_authsend, "method 'OnClickAuthSend'");
    view2131296725 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnClickAuthSend();
      }
    });
    view = Utils.findRequiredView(source, R.id.txv_idcheck, "method 'OnClickIdCheck'");
    view2131296741 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnClickIdCheck();
      }
    });
    view = Utils.findRequiredView(source, R.id.txv_signout, "method 'OnClickSignout'");
    view2131296763 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnClickSignout();
      }
    });
    view = Utils.findRequiredView(source, R.id.lly_man_bg, "method 'OnClickManBg'");
    view2131296535 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnClickManBg();
      }
    });
    view = Utils.findRequiredView(source, R.id.lly_woman_bg, "method 'OnClickWomanBg'");
    view2131296538 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnClickWomanBg();
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

    Context context = source.getContext();
    Resources res = context.getResources();
    target.bankList = res.getStringArray(R.array.bank);
  }

  @Override
  public void unbind() {
    ProfileSettingActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.ui_edtName = null;
    target.ui_edtBirthday = null;
    target.ui_edtPhone = null;
    target.ui_edtAuthCode = null;
    target.ui_edtPwd = null;
    target.ui_edtPwdConf = null;
    target.ui_edtId = null;
    target.ui_edtBankName = null;
    target.ui_edtBank = null;
    target.ui_txvBankCheck = null;
    target.ui_imvMan = null;
    target.ui_imvWoman = null;

    view2131296379.setOnClickListener(null);
    view2131296379 = null;
    view2131296378.setOnClickListener(null);
    view2131296378 = null;
    view2131296726.setOnClickListener(null);
    view2131296726 = null;
    view2131296728.setOnClickListener(null);
    view2131296728 = null;
    view2131296725.setOnClickListener(null);
    view2131296725 = null;
    view2131296741.setOnClickListener(null);
    view2131296741 = null;
    view2131296763.setOnClickListener(null);
    view2131296763 = null;
    view2131296535.setOnClickListener(null);
    view2131296535 = null;
    view2131296538.setOnClickListener(null);
    view2131296538 = null;
    view2131296459.setOnClickListener(null);
    view2131296459 = null;

    super.unbind();
  }
}
