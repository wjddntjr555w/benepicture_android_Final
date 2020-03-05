// Generated code from Butter Knife. Do not modify!
package com.bene.pictures.ui.main.mypage;

import android.support.annotation.UiThread;
import android.view.View;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.bene.pictures.R;
import com.bene.pictures.ui.base.BaseActivity_ViewBinding;
import com.bene.pictures.ui.widget.BaseTextView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SubscribeInfoDetailActivity_ViewBinding extends BaseActivity_ViewBinding {
  private SubscribeInfoDetailActivity target;

  private View view2131296768;

  private View view2131296729;

  private View view2131296418;

  private View view2131296528;

  @UiThread
  public SubscribeInfoDetailActivity_ViewBinding(SubscribeInfoDetailActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public SubscribeInfoDetailActivity_ViewBinding(final SubscribeInfoDetailActivity target,
      View source) {
    super(target, source);

    this.target = target;

    View view;
    target.ui_txvNumber = Utils.findRequiredViewAsType(source, R.id.txv_number, "field 'ui_txvNumber'", BaseTextView.class);
    target.ui_txvCreateDate = Utils.findRequiredViewAsType(source, R.id.txv_createdate, "field 'ui_txvCreateDate'", BaseTextView.class);
    target.ui_txvFundDate = Utils.findRequiredViewAsType(source, R.id.txv_funddate, "field 'ui_txvFundDate'", BaseTextView.class);
    target.ui_txvTakeDate = Utils.findRequiredViewAsType(source, R.id.txv_takedate, "field 'ui_txvTakeDate'", BaseTextView.class);
    target.ui_txvTakeMoney = Utils.findRequiredViewAsType(source, R.id.txv_takemoney, "field 'ui_txvTakeMoney'", BaseTextView.class);
    view = Utils.findRequiredView(source, R.id.txv_take, "method 'OnClickTake'");
    view2131296768 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnClickTake();
      }
    });
    view = Utils.findRequiredView(source, R.id.txv_close, "method 'OnClickClose'");
    view2131296729 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnClickClose();
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
    SubscribeInfoDetailActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.ui_txvNumber = null;
    target.ui_txvCreateDate = null;
    target.ui_txvFundDate = null;
    target.ui_txvTakeDate = null;
    target.ui_txvTakeMoney = null;

    view2131296768.setOnClickListener(null);
    view2131296768 = null;
    view2131296729.setOnClickListener(null);
    view2131296729 = null;
    view2131296418.setOnClickListener(null);
    view2131296418 = null;
    view2131296528.setOnClickListener(null);
    view2131296528 = null;

    super.unbind();
  }
}
