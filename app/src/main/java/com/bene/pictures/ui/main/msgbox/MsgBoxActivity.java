package com.bene.pictures.ui.main.msgbox;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.bene.pictures.R;
import com.bene.pictures.data.Common;
import com.bene.pictures.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class MsgBoxActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg_box);

        initUI();
    }

    @BindView(R.id.tbl_paging_menu)
    TabLayout ui_tblPagingMenu;

    @BindView(R.id.vpr_content)
    ViewPager ui_vprContentPages;

    private ContentsPageAdapter adp_ContentsPages;

    public void initUI() {

        InitFragments();

        adp_ContentsPages = new ContentsPageAdapter(getSupportFragmentManager());
        ui_vprContentPages.setAdapter(adp_ContentsPages);
        ui_vprContentPages.clearOnPageChangeListeners();
        ui_vprContentPages.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                _nCurPage = position;
                ui_tblPagingMenu.getTabAt(position).select();
                refreshFragment();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        ui_vprContentPages.setOffscreenPageLimit(3);
        ui_vprContentPages.setCurrentItem(MSGBOX_PAGE_UNREAD);

        ui_tblPagingMenu.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                _nCurPage = tab.getPosition();
                ui_vprContentPages.setCurrentItem(_nCurPage);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        ui_tblPagingMenu.getTabAt(0).select();

        UpdatePagingTabTextStyle();
    }

    private void UpdatePagingTabTextStyle() {
        Common.setTabItemFontAndSize(this, ui_tblPagingMenu, 0, "NanumBarunGothic.otf", 18, Typeface.BOLD);
        Common.setTabItemFontAndSize(this, ui_tblPagingMenu, 1, "NanumBarunGothic.otf", 18, Typeface.BOLD);
        Common.setTabItemFontAndSize(this, ui_tblPagingMenu, 2, "NanumBarunGothic.otf", 18, Typeface.BOLD);
    }

    void refreshFragment() {

        switch (_nCurPage) {
            case MSGBOX_PAGE_UNREAD:
                if (ctx_unreadFragment != null) {
                    ctx_unreadFragment.refresh();
                }
                break;
            case MSGBOX_PAGE_READ:
                if (ctx_readFragment != null) {
                    ctx_readFragment.refresh();
                }
                break;
            case MSGBOX_PAGE_WHOLE:
                if (ctx_wholeFragment != null) {
                    ctx_wholeFragment.refresh();
                }
                break;
        }
    }

    private static final int MSGBOX_PAGE_UNREAD = 0;
    private static final int MSGBOX_PAGE_READ = 1;
    private static final int MSGBOX_PAGE_WHOLE = 2;

    private int _nCurPage = MSGBOX_PAGE_UNREAD;

    MsgBoxUnreadFragment ctx_unreadFragment;
    MsgBoxReadFragment ctx_readFragment;
    MsgBoxWholeFragment ctx_wholeFragment;

    private void InitFragments() {
        ctx_unreadFragment = MsgBoxUnreadFragment.newInstance();
        ctx_unreadFragment.SetActivity(this, onChooseMsgListener);
        ctx_readFragment = MsgBoxReadFragment.newInstance();
        ctx_readFragment.SetActivity(this, onChooseMsgListener);
        ctx_wholeFragment = MsgBoxWholeFragment.newInstance();
        ctx_wholeFragment.SetActivity(this, onChooseMsgListener);
    }

    private MsgBoxBaseFragment.OnChooseMsgListener onChooseMsgListener = new MsgBoxBaseFragment.OnChooseMsgListener() {
        @Override
        public void OnChoose(String content) {
        }
    };

    @Override
    public void onBackPressed() {
        finish();
    }

    @OnClick(R.id.imv_back)
    void OnClickBack() {
        finish();
    }

    private class ContentsPageAdapter extends FragmentStatePagerAdapter {

        public ContentsPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment frag = null;
            switch (position) {
                case 0:
                    if (ctx_unreadFragment == null) {
                        ctx_unreadFragment = MsgBoxUnreadFragment.newInstance();
                        ctx_unreadFragment.SetActivity(MsgBoxActivity.this, onChooseMsgListener);
                    }
                    frag = ctx_unreadFragment;
                    break;
                case 1:
                    if (ctx_readFragment == null) {
                        ctx_readFragment = MsgBoxReadFragment.newInstance();
                        ctx_readFragment.SetActivity(MsgBoxActivity.this, onChooseMsgListener);
                    }
                    frag = ctx_readFragment;
                    break;
                case 2:
                    if (ctx_wholeFragment == null) {
                        ctx_wholeFragment = MsgBoxWholeFragment.newInstance();
                        ctx_wholeFragment.SetActivity(MsgBoxActivity.this, onChooseMsgListener);
                    }
                    frag = ctx_wholeFragment;
                    break;
            }
            return frag;
        }

        @Override
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
        if (requestCode == 1009) {
            if (ctx_unreadFragment != null) {
                ctx_unreadFragment.onActivityResult(requestCode, resultCode, data);
            }
            if (ctx_readFragment != null) {
                ctx_readFragment.onActivityResult(requestCode, resultCode, data);
            }
            if (ctx_wholeFragment != null) {
                ctx_wholeFragment.onActivityResult(requestCode, resultCode, data);
            }
        }
    }
}
