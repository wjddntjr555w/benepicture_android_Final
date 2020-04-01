// Generated code from Butter Knife. Do not modify!
package com.bene.pictures.ui.main.msgbox;

import android.support.annotation.UiThread;
import android.view.View;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.bene.pictures.R;
import com.bene.pictures.ui.base.BaseActivity_ViewBinding;
import com.bene.pictures.ui.widget.BaseTextView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MsgDetailActivity_ViewBinding extends BaseActivity_ViewBinding {
  private MsgDetailActivity target;

  private View view2131296737;

  private View view2131296729;

  @UiThread
  public MsgDetailActivity_ViewBinding(MsgDetailActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public MsgDetailActivity_ViewBinding(final MsgDetailActivity target, View source) {
    super(target, source);

    this.target = target;

    View view;
    target.ui_txvTitle = Utils.findRequiredViewAsType(source, R.id.txv_title, "field 'ui_txvTitle'", BaseTextView.class);
    target.ui_txvContent = Utils.findRequiredViewAsType(source, R.id.txv_content, "field 'ui_txvContent'", BaseTextView.class);
    view = Utils.findRequiredView(source, R.id.txv_delete, "field 'ui_txvDelete' and method 'OnClickDelete'");
    target.ui_txvDelete = Utils.castView(view, R.id.txv_delete, "field 'ui_txvDelete'", BaseTextView.class);
    view2131296737 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnClickDelete();
      }
    });
    view = Utils.findRequiredView(source, R.id.txv_close, "method 'OnClickBg'");
    view2131296729 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnClickBg();
      }
    });
  }

  @Override
  public void unbind() {
    MsgDetailActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.ui_txvTitle = null;
    target.ui_txvContent = null;
    target.ui_txvDelete = null;

    view2131296737.setOnClickListener(null);
    view2131296737 = null;
    view2131296729.setOnClickListener(null);
    view2131296729 = null;

    super.unbind();
  }
}
