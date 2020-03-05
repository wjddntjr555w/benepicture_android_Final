// Generated code from Butter Knife. Do not modify!
package com.bene.pictures.ui.main.regadver;

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

public class RegAdverActivity_ViewBinding extends BaseActivity_ViewBinding {
  private RegAdverActivity target;

  private View view2131296387;

  private View view2131296388;

  private View view2131296452;

  private View view2131296459;

  private View view2131296532;

  private View view2131296537;

  private View view2131296427;

  private View view2131296482;

  private View view2131296428;

  private View view2131296483;

  private View view2131296739;

  private View view2131296758;

  @UiThread
  public RegAdverActivity_ViewBinding(RegAdverActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public RegAdverActivity_ViewBinding(final RegAdverActivity target, View source) {
    super(target, source);

    this.target = target;

    View view;
    target.ui_imvGame = Utils.findRequiredViewAsType(source, R.id.imv_game, "field 'ui_imvGame'", ImageView.class);
    target.ui_imvVideo = Utils.findRequiredViewAsType(source, R.id.imv_video, "field 'ui_imvVideo'", ImageView.class);
    view = Utils.findRequiredView(source, R.id.edt_period_from, "field 'ui_edtPeriodFrom' and method 'OnClickPeriodFromCal'");
    target.ui_edtPeriodFrom = Utils.castView(view, R.id.edt_period_from, "field 'ui_edtPeriodFrom'", BaseTextView.class);
    view2131296387 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnClickPeriodFromCal();
      }
    });
    view = Utils.findRequiredView(source, R.id.edt_period_to, "field 'ui_edtPeriodTo' and method 'OnClickPeriodToCal'");
    target.ui_edtPeriodTo = Utils.castView(view, R.id.edt_period_to, "field 'ui_edtPeriodTo'", BaseTextView.class);
    view2131296388 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnClickPeriodToCal();
      }
    });
    target.ui_edtFileName = Utils.findRequiredViewAsType(source, R.id.edt_filename, "field 'ui_edtFileName'", BaseTextView.class);
    target.ui_edtAdName = Utils.findRequiredViewAsType(source, R.id.edt_adname, "field 'ui_edtAdName'", BaseEditText.class);
    target.ui_edtLink = Utils.findRequiredViewAsType(source, R.id.edt_link, "field 'ui_edtLink'", BaseEditText.class);
    target.ui_edtCount = Utils.findRequiredViewAsType(source, R.id.edt_count, "field 'ui_edtCount'", BaseEditText.class);
    target.ui_edtBudget = Utils.findRequiredViewAsType(source, R.id.edt_budget, "field 'ui_edtBudget'", BaseEditText.class);
    view = Utils.findRequiredView(source, R.id.imv_agree_adterm, "field 'ui_imvAgreeAdTerm' and method 'OnClickAgreeAdver'");
    target.ui_imvAgreeAdTerm = Utils.castView(view, R.id.imv_agree_adterm, "field 'ui_imvAgreeAdTerm'", ImageView.class);
    view2131296452 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnClickAgreeAdver();
      }
    });
    target.ui_txvAdTerm = Utils.findRequiredViewAsType(source, R.id.txv_adterm, "field 'ui_txvAdTerm'", BaseTextView.class);
    view = Utils.findRequiredView(source, R.id.imv_back, "method 'OnClickBack'");
    view2131296459 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnClickBack();
      }
    });
    view = Utils.findRequiredView(source, R.id.lly_game_bg, "method 'OnClickManBg'");
    view2131296532 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnClickManBg();
      }
    });
    view = Utils.findRequiredView(source, R.id.lly_video_bg, "method 'OnClickWomanBg'");
    view2131296537 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnClickWomanBg();
      }
    });
    view = Utils.findRequiredView(source, R.id.fly_period_from, "method 'OnClickPeriodFromCal'");
    view2131296427 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnClickPeriodFromCal();
      }
    });
    view = Utils.findRequiredView(source, R.id.imv_period_from_cal, "method 'OnClickPeriodFromCal'");
    view2131296482 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnClickPeriodFromCal();
      }
    });
    view = Utils.findRequiredView(source, R.id.fly_period_to, "method 'OnClickPeriodToCal'");
    view2131296428 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnClickPeriodToCal();
      }
    });
    view = Utils.findRequiredView(source, R.id.imv_period_to_cal, "method 'OnClickPeriodToCal'");
    view2131296483 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnClickPeriodToCal();
      }
    });
    view = Utils.findRequiredView(source, R.id.txv_getfile, "method 'OnClickGetFile'");
    view2131296739 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnClickGetFile();
      }
    });
    view = Utils.findRequiredView(source, R.id.txv_regadver, "method 'OnClickRegAdver'");
    view2131296758 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnClickRegAdver();
      }
    });
  }

  @Override
  public void unbind() {
    RegAdverActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.ui_imvGame = null;
    target.ui_imvVideo = null;
    target.ui_edtPeriodFrom = null;
    target.ui_edtPeriodTo = null;
    target.ui_edtFileName = null;
    target.ui_edtAdName = null;
    target.ui_edtLink = null;
    target.ui_edtCount = null;
    target.ui_edtBudget = null;
    target.ui_imvAgreeAdTerm = null;
    target.ui_txvAdTerm = null;

    view2131296387.setOnClickListener(null);
    view2131296387 = null;
    view2131296388.setOnClickListener(null);
    view2131296388 = null;
    view2131296452.setOnClickListener(null);
    view2131296452 = null;
    view2131296459.setOnClickListener(null);
    view2131296459 = null;
    view2131296532.setOnClickListener(null);
    view2131296532 = null;
    view2131296537.setOnClickListener(null);
    view2131296537 = null;
    view2131296427.setOnClickListener(null);
    view2131296427 = null;
    view2131296482.setOnClickListener(null);
    view2131296482 = null;
    view2131296428.setOnClickListener(null);
    view2131296428 = null;
    view2131296483.setOnClickListener(null);
    view2131296483 = null;
    view2131296739.setOnClickListener(null);
    view2131296739 = null;
    view2131296758.setOnClickListener(null);
    view2131296758 = null;

    super.unbind();
  }
}
