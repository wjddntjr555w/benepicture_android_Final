// Generated code from Butter Knife. Do not modify!
package com.bene.pictures.ui.main.takereward;

import android.support.annotation.UiThread;
import android.view.View;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.bene.pictures.R;
import com.bene.pictures.ui.base.BaseActivity_ViewBinding;
import com.bene.pictures.ui.widget.BaseTextView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ReviewDetailActivity_ViewBinding extends BaseActivity_ViewBinding {
  private ReviewDetailActivity target;

  private View view2131296418;

  private View view2131296528;

  @UiThread
  public ReviewDetailActivity_ViewBinding(ReviewDetailActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ReviewDetailActivity_ViewBinding(final ReviewDetailActivity target, View source) {
    super(target, source);

    this.target = target;

    View view;
    target.ui_txvTitle = Utils.findRequiredViewAsType(source, R.id.txv_title, "field 'ui_txvTitle'", BaseTextView.class);
    target.ui_txvContent = Utils.findRequiredViewAsType(source, R.id.txv_content, "field 'ui_txvContent'", BaseTextView.class);
    target.ui_txvDateTime = Utils.findRequiredViewAsType(source, R.id.txv_datetime, "field 'ui_txvDateTime'", BaseTextView.class);
    target.ui_txvMoney = Utils.findRequiredViewAsType(source, R.id.txv_money, "field 'ui_txvMoney'", BaseTextView.class);
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
    ReviewDetailActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.ui_txvTitle = null;
    target.ui_txvContent = null;
    target.ui_txvDateTime = null;
    target.ui_txvMoney = null;

    view2131296418.setOnClickListener(null);
    view2131296418 = null;
    view2131296528.setOnClickListener(null);
    view2131296528 = null;

    super.unbind();
  }
}
