// Generated code from Butter Knife. Do not modify!
package com.bene.pictures.ui.main.mypage;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.bene.pictures.R;
import com.bene.pictures.ui.widget.BaseEditText;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MypageKeywordFragment_ViewBinding implements Unbinder {
  private MypageKeywordFragment target;

  private View view2131296760;

  private View view2131296761;

  @UiThread
  public MypageKeywordFragment_ViewBinding(final MypageKeywordFragment target, View source) {
    this.target = target;

    View view;
    target.ui_rcvContents = Utils.findRequiredViewAsType(source, R.id.rcv_contents, "field 'ui_rcvContents'", RecyclerView.class);
    target.ui_rcvContentChild = Utils.findRequiredViewAsType(source, R.id.rcv_content_child, "field 'ui_rcvContentChild'", RecyclerView.class);
    target.ui_edtSearchKeyword = Utils.findRequiredViewAsType(source, R.id.edt_search_keyword, "field 'ui_edtSearchKeyword'", BaseEditText.class);
    view = Utils.findRequiredView(source, R.id.txv_search, "method 'OnClickSearch'");
    view2131296760 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnClickSearch();
      }
    });
    view = Utils.findRequiredView(source, R.id.txv_set_keyword, "method 'OnClickSetKeyword'");
    view2131296761 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnClickSetKeyword();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    MypageKeywordFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.ui_rcvContents = null;
    target.ui_rcvContentChild = null;
    target.ui_edtSearchKeyword = null;

    view2131296760.setOnClickListener(null);
    view2131296760 = null;
    view2131296761.setOnClickListener(null);
    view2131296761 = null;
  }
}
