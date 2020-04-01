// Generated code from Butter Knife. Do not modify!
package com.bene.pictures.ui.main.adver;

import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import butterknife.internal.Utils;
import com.bene.pictures.R;
import com.bene.pictures.ui.base.BaseActivity_ViewBinding;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SwitchingPuzzleAdverActivity_ViewBinding extends BaseActivity_ViewBinding {
  private SwitchingPuzzleAdverActivity target;

  @UiThread
  public SwitchingPuzzleAdverActivity_ViewBinding(SwitchingPuzzleAdverActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public SwitchingPuzzleAdverActivity_ViewBinding(SwitchingPuzzleAdverActivity target,
      View source) {
    super(target, source);

    this.target = target;

    target.ui_vwAni1 = Utils.findRequiredView(source, R.id.vw_ani1, "field 'ui_vwAni1'");
    target.ui_vwAni2 = Utils.findRequiredView(source, R.id.vw_ani2, "field 'ui_vwAni2'");
    target.ui_vwAni3 = Utils.findRequiredView(source, R.id.vw_ani3, "field 'ui_vwAni3'");
    target.ui_vwAni4 = Utils.findRequiredView(source, R.id.vw_ani4, "field 'ui_vwAni4'");
    target.ui_vwAni5 = Utils.findRequiredView(source, R.id.vw_ani5, "field 'ui_vwAni5'");
    target.ui_vwAni6 = Utils.findRequiredView(source, R.id.vw_ani6, "field 'ui_vwAni6'");
    target.ui_vwAni7 = Utils.findRequiredView(source, R.id.vw_ani7, "field 'ui_vwAni7'");
    target.ui_vwAni8 = Utils.findRequiredView(source, R.id.vw_ani8, "field 'ui_vwAni8'");
    target.ui_vwAni9 = Utils.findRequiredView(source, R.id.vw_ani9, "field 'ui_vwAni9'");
    target.ui_imvPuzzle1 = Utils.findRequiredViewAsType(source, R.id.imv_puzzle_1, "field 'ui_imvPuzzle1'", ImageView.class);
    target.ui_imvPuzzle2 = Utils.findRequiredViewAsType(source, R.id.imv_puzzle_2, "field 'ui_imvPuzzle2'", ImageView.class);
    target.ui_imvPuzzle3 = Utils.findRequiredViewAsType(source, R.id.imv_puzzle_3, "field 'ui_imvPuzzle3'", ImageView.class);
    target.ui_imvPuzzle4 = Utils.findRequiredViewAsType(source, R.id.imv_puzzle_4, "field 'ui_imvPuzzle4'", ImageView.class);
    target.ui_imvPuzzle5 = Utils.findRequiredViewAsType(source, R.id.imv_puzzle_5, "field 'ui_imvPuzzle5'", ImageView.class);
    target.ui_imvPuzzle6 = Utils.findRequiredViewAsType(source, R.id.imv_puzzle_6, "field 'ui_imvPuzzle6'", ImageView.class);
    target.ui_imvPuzzle7 = Utils.findRequiredViewAsType(source, R.id.imv_puzzle_7, "field 'ui_imvPuzzle7'", ImageView.class);
    target.ui_imvPuzzle8 = Utils.findRequiredViewAsType(source, R.id.imv_puzzle_8, "field 'ui_imvPuzzle8'", ImageView.class);
    target.ui_imvPuzzle9 = Utils.findRequiredViewAsType(source, R.id.imv_puzzle_9, "field 'ui_imvPuzzle9'", ImageView.class);
  }

  @Override
  public void unbind() {
    SwitchingPuzzleAdverActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.ui_vwAni1 = null;
    target.ui_vwAni2 = null;
    target.ui_vwAni3 = null;
    target.ui_vwAni4 = null;
    target.ui_vwAni5 = null;
    target.ui_vwAni6 = null;
    target.ui_vwAni7 = null;
    target.ui_vwAni8 = null;
    target.ui_vwAni9 = null;
    target.ui_imvPuzzle1 = null;
    target.ui_imvPuzzle2 = null;
    target.ui_imvPuzzle3 = null;
    target.ui_imvPuzzle4 = null;
    target.ui_imvPuzzle5 = null;
    target.ui_imvPuzzle6 = null;
    target.ui_imvPuzzle7 = null;
    target.ui_imvPuzzle8 = null;
    target.ui_imvPuzzle9 = null;

    super.unbind();
  }
}
