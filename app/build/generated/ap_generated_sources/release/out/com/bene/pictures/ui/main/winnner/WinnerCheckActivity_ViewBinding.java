// Generated code from Butter Knife. Do not modify!
package com.bene.pictures.ui.main.winnner;

import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.bene.pictures.R;
import com.bene.pictures.ui.base.BaseActivity_ViewBinding;
import com.bene.pictures.ui.widget.BaseTextView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class WinnerCheckActivity_ViewBinding extends BaseActivity_ViewBinding {
  private WinnerCheckActivity target;

  private View view2131296730;

  private View view2131296459;

  private View view2131296500;

  private View view2131296501;

  @UiThread
  public WinnerCheckActivity_ViewBinding(WinnerCheckActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public WinnerCheckActivity_ViewBinding(final WinnerCheckActivity target, View source) {
    super(target, source);

    this.target = target;

    View view;
    target.ui_txvTitle = Utils.findRequiredViewAsType(source, R.id.txv_title, "field 'ui_txvTitle'", BaseTextView.class);
    target.ui_txvDate = Utils.findRequiredViewAsType(source, R.id.txv_date, "field 'ui_txvDate'", BaseTextView.class);
    target.ui_txvPeriod = Utils.findRequiredViewAsType(source, R.id.txv_period, "field 'ui_txvPeriod'", BaseTextView.class);
    view = Utils.findRequiredView(source, R.id.txv_confirm, "field 'ui_txvConfirm' and method 'OnClickConfirm'");
    target.ui_txvConfirm = Utils.castView(view, R.id.txv_confirm, "field 'ui_txvConfirm'", BaseTextView.class);
    view2131296730 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnClickConfirm();
      }
    });
    target.ui_rcvReview = Utils.findRequiredViewAsType(source, R.id.rcv_review, "field 'ui_rcvReview'", RecyclerView.class);
    target.ui_rcvWinner = Utils.findRequiredViewAsType(source, R.id.rcv_contents, "field 'ui_rcvWinner'", RecyclerView.class);
    view = Utils.findRequiredView(source, R.id.imv_back, "method 'OnClickBack'");
    view2131296459 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnClickBack();
      }
    });
    view = Utils.findRequiredView(source, R.id.imv_title_left, "method 'OnClickLeft'");
    view2131296500 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnClickLeft();
      }
    });
    view = Utils.findRequiredView(source, R.id.imv_title_right, "method 'OnClickRight'");
    view2131296501 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnClickRight();
      }
    });
  }

  @Override
  public void unbind() {
    WinnerCheckActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.ui_txvTitle = null;
    target.ui_txvDate = null;
    target.ui_txvPeriod = null;
    target.ui_txvConfirm = null;
    target.ui_rcvReview = null;
    target.ui_rcvWinner = null;

    view2131296730.setOnClickListener(null);
    view2131296730 = null;
    view2131296459.setOnClickListener(null);
    view2131296459 = null;
    view2131296500.setOnClickListener(null);
    view2131296500 = null;
    view2131296501.setOnClickListener(null);
    view2131296501 = null;

    super.unbind();
  }
}
