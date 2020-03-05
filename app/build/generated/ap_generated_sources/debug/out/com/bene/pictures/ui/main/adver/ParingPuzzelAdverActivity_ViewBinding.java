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

public class ParingPuzzelAdverActivity_ViewBinding extends BaseActivity_ViewBinding {
  private ParingPuzzelAdverActivity target;

  @UiThread
  public ParingPuzzelAdverActivity_ViewBinding(ParingPuzzelAdverActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ParingPuzzelAdverActivity_ViewBinding(ParingPuzzelAdverActivity target, View source) {
    super(target, source);

    this.target = target;

    target.ui_imvPuzzle1 = Utils.findRequiredViewAsType(source, R.id.imv_puzzle_1, "field 'ui_imvPuzzle1'", ImageView.class);
    target.ui_imvPuzzle2 = Utils.findRequiredViewAsType(source, R.id.imv_puzzle_2, "field 'ui_imvPuzzle2'", ImageView.class);
    target.ui_imvPuzzle3 = Utils.findRequiredViewAsType(source, R.id.imv_puzzle_3, "field 'ui_imvPuzzle3'", ImageView.class);
    target.ui_imvPuzzle4 = Utils.findRequiredViewAsType(source, R.id.imv_puzzle_4, "field 'ui_imvPuzzle4'", ImageView.class);
    target.ui_imvPuzzle5 = Utils.findRequiredViewAsType(source, R.id.imv_puzzle_5, "field 'ui_imvPuzzle5'", ImageView.class);
    target.ui_imvPuzzle6 = Utils.findRequiredViewAsType(source, R.id.imv_puzzle_6, "field 'ui_imvPuzzle6'", ImageView.class);
    target.ui_imvPuzzle7 = Utils.findRequiredViewAsType(source, R.id.imv_puzzle_7, "field 'ui_imvPuzzle7'", ImageView.class);
    target.ui_imvPuzzle8 = Utils.findRequiredViewAsType(source, R.id.imv_puzzle_8, "field 'ui_imvPuzzle8'", ImageView.class);
    target.ui_imvPuzzle9 = Utils.findRequiredViewAsType(source, R.id.imv_puzzle_9, "field 'ui_imvPuzzle9'", ImageView.class);
    target.ui_imvPuzzle10 = Utils.findRequiredViewAsType(source, R.id.imv_puzzle_10, "field 'ui_imvPuzzle10'", ImageView.class);
    target.ui_imvPuzzle11 = Utils.findRequiredViewAsType(source, R.id.imv_puzzle_11, "field 'ui_imvPuzzle11'", ImageView.class);
    target.ui_imvPuzzle12 = Utils.findRequiredViewAsType(source, R.id.imv_puzzle_12, "field 'ui_imvPuzzle12'", ImageView.class);
    target.ui_imvOriginal = Utils.findRequiredViewAsType(source, R.id.imv_original, "field 'ui_imvOriginal'", ImageView.class);
  }

  @Override
  public void unbind() {
    ParingPuzzelAdverActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.ui_imvPuzzle1 = null;
    target.ui_imvPuzzle2 = null;
    target.ui_imvPuzzle3 = null;
    target.ui_imvPuzzle4 = null;
    target.ui_imvPuzzle5 = null;
    target.ui_imvPuzzle6 = null;
    target.ui_imvPuzzle7 = null;
    target.ui_imvPuzzle8 = null;
    target.ui_imvPuzzle9 = null;
    target.ui_imvPuzzle10 = null;
    target.ui_imvPuzzle11 = null;
    target.ui_imvPuzzle12 = null;
    target.ui_imvOriginal = null;

    super.unbind();
  }
}
