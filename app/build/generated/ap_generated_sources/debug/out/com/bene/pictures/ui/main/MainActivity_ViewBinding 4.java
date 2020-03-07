// Generated code from Butter Knife. Do not modify!
package com.bene.pictures.ui.main;

import android.support.annotation.UiThread;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.ImageView;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.bene.pictures.R;
import com.bene.pictures.ui.base.BaseActivity_ViewBinding;
import com.bene.pictures.ui.widget.BaseTextView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MainActivity_ViewBinding extends BaseActivity_ViewBinding {
  private MainActivity target;

  private View view2131296470;

  private View view2131296450;

  private View view2131296478;

  private View view2131296608;

  private View view2131296471;

  private View view2131296421;

  private View view2131296433;

  private View view2131296432;

  private View view2131296423;

  private View view2131296417;

  private View view2131296425;

  private View view2131296420;

  private View view2131296429;

  private View view2131296410;

  private View view2131296431;

  private View view2131296424;

  private View view2131296606;

  private View view2131296612;

  private View view2131296461;

  private View view2131296464;

  private View view2131296463;

  private View view2131296462;

  private View view2131296460;

  @UiThread
  public MainActivity_ViewBinding(MainActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public MainActivity_ViewBinding(final MainActivity target, View source) {
    super(target, source);

    this.target = target;

    View view;
    target.ui_dlyMain = Utils.findRequiredViewAsType(source, R.id.dly_main, "field 'ui_dlyMain'", DrawerLayout.class);
    view = Utils.findRequiredView(source, R.id.imv_guide, "field 'ui_imvGuide' and method 'OnClickGuide'");
    target.ui_imvGuide = Utils.castView(view, R.id.imv_guide, "field 'ui_imvGuide'", ImageView.class);
    view2131296470 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnClickGuide();
      }
    });
    target.ui_txvWholeReward = Utils.findRequiredViewAsType(source, R.id.txv_whole_reward, "field 'ui_txvWholeReward'", BaseTextView.class);
    target.ui_txvAdverCnt = Utils.findRequiredViewAsType(source, R.id.txv_advercnt, "field 'ui_txvAdverCnt'", BaseTextView.class);
    target.ui_txvSubscribeCnt = Utils.findRequiredViewAsType(source, R.id.txv_subscribecnt, "field 'ui_txvSubscribeCnt'", BaseTextView.class);
    view = Utils.findRequiredView(source, R.id.imv_adver_thumb, "field 'ui_imvAdverThumb' and method 'OnClickAdverThumb'");
    target.ui_imvAdverThumb = Utils.castView(view, R.id.imv_adver_thumb, "field 'ui_imvAdverThumb'", ImageView.class);
    view2131296450 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnClickAdverThumb();
      }
    });
    target.ui_txvName = Utils.findRequiredViewAsType(source, R.id.txv_usrname, "field 'ui_txvName'", BaseTextView.class);
    target.ui_txvVersion = Utils.findRequiredViewAsType(source, R.id.txv_menu_version, "field 'ui_txvVersion'", BaseTextView.class);
    target.ui_imvGameAdSetting = Utils.findRequiredViewAsType(source, R.id.imv_gamead_setting, "field 'ui_imvGameAdSetting'", ImageView.class);
    target.ui_imvVideoAdSetting = Utils.findRequiredViewAsType(source, R.id.imv_videoad_setting, "field 'ui_imvVideoAdSetting'", ImageView.class);
    target.ui_imvVibrateSetting = Utils.findRequiredViewAsType(source, R.id.imv_vibrate_setting, "field 'ui_imvVibrateSetting'", ImageView.class);
    target.ui_imvKeywordSetting = Utils.findRequiredViewAsType(source, R.id.imv_keyword_setting, "field 'ui_imvKeywordSetting'", ImageView.class);
    view = Utils.findRequiredView(source, R.id.imv_menu, "method 'onClickMenu'");
    view2131296478 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickMenu();
      }
    });
    view = Utils.findRequiredView(source, R.id.rly_drawer, "method 'OnClickDrawerBg'");
    view2131296608 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnClickDrawerBg();
      }
    });
    view = Utils.findRequiredView(source, R.id.imv_help, "method 'OnClickHelp'");
    view2131296471 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnClickHelp();
      }
    });
    view = Utils.findRequiredView(source, R.id.fly_gamead_setting_bg, "method 'OnClickGameAdSetting'");
    view2131296421 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnClickGameAdSetting();
      }
    });
    view = Utils.findRequiredView(source, R.id.fly_videoad_setting_bg, "method 'OnClickVideoAdSetting'");
    view2131296433 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnClickVideoAdSetting();
      }
    });
    view = Utils.findRequiredView(source, R.id.fly_vibrate_setting_bg, "method 'OnClickVibrateSetting'");
    view2131296432 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnClickVibrateSetting();
      }
    });
    view = Utils.findRequiredView(source, R.id.fly_keyword_setting_bg, "method 'OnClickKeywordSetting'");
    view2131296423 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnClickKeywordSetting();
      }
    });
    view = Utils.findRequiredView(source, R.id.fly_appalarm_setting_bg, "method 'OnClickAppalarmSetting'");
    view2131296417 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnClickAppalarmSetting();
      }
    });
    view = Utils.findRequiredView(source, R.id.fly_msgbox_setting_bg, "method 'OnClickMsgBoxSetting'");
    view2131296425 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnClickMsgBoxSetting();
      }
    });
    view = Utils.findRequiredView(source, R.id.fly_friends_setting_bg, "method 'OnClickFriendsSetting'");
    view2131296420 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnClickFriendsSetting();
      }
    });
    view = Utils.findRequiredView(source, R.id.fly_profile_setting_bg, "method 'OnClickProfileSetting'");
    view2131296429 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnClickProfileSetting();
      }
    });
    view = Utils.findRequiredView(source, R.id.fly_adver_request, "method 'OnClickAdRequest'");
    view2131296410 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnClickAdRequest();
      }
    });
    view = Utils.findRequiredView(source, R.id.fly_share, "method 'OnShare'");
    view2131296431 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnShare();
      }
    });
    view = Utils.findRequiredView(source, R.id.fly_logout, "method 'OnClickLogout'");
    view2131296424 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnClickLogout();
      }
    });
    view = Utils.findRequiredView(source, R.id.rly_advercnt_bg, "method 'OnClickAdverCntBg'");
    view2131296606 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnClickAdverCntBg();
      }
    });
    view = Utils.findRequiredView(source, R.id.rly_subscribecnt_bg, "method 'OnClickSubscribeCntBg'");
    view2131296612 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnClickSubscribeCntBg();
      }
    });
    view = Utils.findRequiredView(source, R.id.imv_bot_mypage, "method 'OnClickMyPage'");
    view2131296461 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnClickMyPage();
      }
    });
    view = Utils.findRequiredView(source, R.id.imv_bot_winners, "method 'OnClickWinners'");
    view2131296464 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnClickWinners();
      }
    });
    view = Utils.findRequiredView(source, R.id.imv_bot_takereward, "method 'OnClickTakeReward'");
    view2131296463 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnClickTakeReward();
      }
    });
    view = Utils.findRequiredView(source, R.id.imv_bot_reqadver, "method 'OnClickReqAdver'");
    view2131296462 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnClickReqAdver();
      }
    });
    view = Utils.findRequiredView(source, R.id.imv_bot_friends, "method 'OnClickFriends'");
    view2131296460 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OnClickFriends();
      }
    });
  }

  @Override
  public void unbind() {
    MainActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.ui_dlyMain = null;
    target.ui_imvGuide = null;
    target.ui_txvWholeReward = null;
    target.ui_txvAdverCnt = null;
    target.ui_txvSubscribeCnt = null;
    target.ui_imvAdverThumb = null;
    target.ui_txvName = null;
    target.ui_txvVersion = null;
    target.ui_imvGameAdSetting = null;
    target.ui_imvVideoAdSetting = null;
    target.ui_imvVibrateSetting = null;
    target.ui_imvKeywordSetting = null;

    view2131296470.setOnClickListener(null);
    view2131296470 = null;
    view2131296450.setOnClickListener(null);
    view2131296450 = null;
    view2131296478.setOnClickListener(null);
    view2131296478 = null;
    view2131296608.setOnClickListener(null);
    view2131296608 = null;
    view2131296471.setOnClickListener(null);
    view2131296471 = null;
    view2131296421.setOnClickListener(null);
    view2131296421 = null;
    view2131296433.setOnClickListener(null);
    view2131296433 = null;
    view2131296432.setOnClickListener(null);
    view2131296432 = null;
    view2131296423.setOnClickListener(null);
    view2131296423 = null;
    view2131296417.setOnClickListener(null);
    view2131296417 = null;
    view2131296425.setOnClickListener(null);
    view2131296425 = null;
    view2131296420.setOnClickListener(null);
    view2131296420 = null;
    view2131296429.setOnClickListener(null);
    view2131296429 = null;
    view2131296410.setOnClickListener(null);
    view2131296410 = null;
    view2131296431.setOnClickListener(null);
    view2131296431 = null;
    view2131296424.setOnClickListener(null);
    view2131296424 = null;
    view2131296606.setOnClickListener(null);
    view2131296606 = null;
    view2131296612.setOnClickListener(null);
    view2131296612 = null;
    view2131296461.setOnClickListener(null);
    view2131296461 = null;
    view2131296464.setOnClickListener(null);
    view2131296464 = null;
    view2131296463.setOnClickListener(null);
    view2131296463 = null;
    view2131296462.setOnClickListener(null);
    view2131296462 = null;
    view2131296460.setOnClickListener(null);
    view2131296460 = null;

    super.unbind();
  }
}
