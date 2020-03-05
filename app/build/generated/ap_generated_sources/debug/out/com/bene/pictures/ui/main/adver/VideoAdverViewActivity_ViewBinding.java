// Generated code from Butter Knife. Do not modify!
package com.bene.pictures.ui.main.adver;

import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import butterknife.internal.Utils;
import com.android.tedcoder.wkvideoplayer.view.SuperVideoPlayer;
import com.bene.pictures.R;
import com.bene.pictures.ui.base.BaseActivity_ViewBinding;
import java.lang.IllegalStateException;
import java.lang.Override;

public class VideoAdverViewActivity_ViewBinding extends BaseActivity_ViewBinding {
  private VideoAdverViewActivity target;

  @UiThread
  public VideoAdverViewActivity_ViewBinding(VideoAdverViewActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public VideoAdverViewActivity_ViewBinding(VideoAdverViewActivity target, View source) {
    super(target, source);

    this.target = target;

    target.ui_imvAdVideoThumb = Utils.findRequiredViewAsType(source, R.id.imv_ad_videothumb, "field 'ui_imvAdVideoThumb'", ImageView.class);
    target.ui_svpAdVideoPlayer = Utils.findRequiredViewAsType(source, R.id.svp_ad_videoplayer, "field 'ui_svpAdVideoPlayer'", SuperVideoPlayer.class);
  }

  @Override
  public void unbind() {
    VideoAdverViewActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.ui_imvAdVideoThumb = null;
    target.ui_svpAdVideoPlayer = null;

    super.unbind();
  }
}
