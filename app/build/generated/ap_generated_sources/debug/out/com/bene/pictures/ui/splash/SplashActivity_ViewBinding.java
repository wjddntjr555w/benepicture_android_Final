// Generated code from Butter Knife. Do not modify!
package com.bene.pictures.ui.splash;

import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import butterknife.internal.Utils;
import com.bene.pictures.R;
import com.bene.pictures.ui.base.BaseActivity_ViewBinding;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SplashActivity_ViewBinding extends BaseActivity_ViewBinding {
  private SplashActivity target;

  @UiThread
  public SplashActivity_ViewBinding(SplashActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public SplashActivity_ViewBinding(SplashActivity target, View source) {
    super(target, source);

    this.target = target;

    target.ui_imvProgress = Utils.findRequiredViewAsType(source, R.id.imv_progress, "field 'ui_imvProgress'", ImageView.class);
  }

  @Override
  public void unbind() {
    SplashActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.ui_imvProgress = null;

    super.unbind();
  }
}
