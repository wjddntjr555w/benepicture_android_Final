// Generated code from Butter Knife. Do not modify!
package com.bene.pictures.ui.dialog;

import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.bene.pictures.R;
import com.bene.pictures.ui.base.BaseActivity_ViewBinding;
import com.bene.pictures.ui.widget.BaseTextView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ConfirmDialog1Activity_ViewBinding extends BaseActivity_ViewBinding {
  private ConfirmDialog1Activity target;

  private View view2131296751;

  private View view2131296776;

  private View view2131296418;

  private View view2131296528;

  @UiThread
  public ConfirmDialog1Activity_ViewBinding(ConfirmDialog1Activity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ConfirmDialog1Activity_ViewBinding(final ConfirmDialog1Activity target, View source) {
    super(target, source);

    this.target = target;

    View view;
    target.ui_txvContent = Utils.findRequiredViewAsType(source, R.id.txv_content, "field 'ui_txvContent'", BaseTextView.class);
    target.ui_txvContent1 = Utils.findRequiredViewAsType(source, R.id.txv_content1, "field 'ui_txvContent1'", BaseTextView.class);
    view = Utils.findRequiredView(source, R.id.txv_no, "field 'ui_txvNo' and method 'OnClickNo'");
    target.ui_txvNo = Utils.castView(view, R.id.txv_no, "field 'ui_txvNo'", BaseTextView.class);
    view2131296751 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnClickNo();
      }
    });
    view = Utils.findRequiredView(source, R.id.txv_yes, "field 'ui_txvYes' and method 'OnClickYes'");
    target.ui_txvYes = Utils.castView(view, R.id.txv_yes, "field 'ui_txvYes'", BaseTextView.class);
    view2131296776 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnClickYes();
      }
    });
    target.ui_iv_Line02 = Utils.findRequiredViewAsType(source, R.id.iv_line02, "field 'ui_iv_Line02'", ImageView.class);
    target.ui_iv_Line03 = Utils.findRequiredViewAsType(source, R.id.iv_line03, "field 'ui_iv_Line03'", ImageView.class);
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
    ConfirmDialog1Activity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.ui_txvContent = null;
    target.ui_txvContent1 = null;
    target.ui_txvNo = null;
    target.ui_txvYes = null;
    target.ui_iv_Line02 = null;
    target.ui_iv_Line03 = null;

    view2131296751.setOnClickListener(null);
    view2131296751 = null;
    view2131296776.setOnClickListener(null);
    view2131296776 = null;
    view2131296418.setOnClickListener(null);
    view2131296418 = null;
    view2131296528.setOnClickListener(null);
    view2131296528 = null;

    super.unbind();
  }
}
