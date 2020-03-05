package com.bene.pictures.ui.main.takereward;

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

public class TakeRewardActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_reward);

        initUI();
    }

    @BindView(R.id.tbl_paging_menu)
    TabLayout ui_tblPagingMenu;

    @BindView(R.id.vpr_content)
    ViewPager ui_vprContentPages;

    private ContentsPageAdapter adp_ContentsPages;

    int tabIndex = 0;

    public void initUI() {

        tabIndex = getIntent().getIntExtra("tab", 0);

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
        ui_vprContentPages.setCurrentItem(tabIndex);

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

        ui_tblPagingMenu.getTabAt(tabIndex).select();

        UpdatePagingTabTextStyle();
    }

    private void UpdatePagingTabTextStyle() {
        Common.setTabItemFontAndSize(this, ui_tblPagingMenu, 0, "NanumBarunGothic.otf", 18, Typeface.BOLD);
        Common.setTabItemFontAndSize(this, ui_tblPagingMenu, 1, "NanumBarunGothic.otf", 18, Typeface.BOLD);
        Common.setTabItemFontAndSize(this, ui_tblPagingMenu, 2, "NanumBarunGothic.otf", 18, Typeface.BOLD);
    }

    void refreshFragment() {

        switch (_nCurPage) {
            case TAKEREWARD_REVIEW:
                if (ctx_reviewFragment != null) {
                    ctx_reviewFragment.refresh();
                }
                break;
            case TAKEREWARD_REQTAKE:
                if (ctx_reqtakeFragment != null) {
                    ctx_reqtakeFragment.refresh();
                }
                break;
        }
    }

    private static final int TAKEREWARD_REVIEW = 0;
    private static final int TAKEREWARD_REQTAKE = 1;

    private int _nCurPage = TAKEREWARD_REVIEW;

    TakeRewardReviewFragment ctx_reviewFragment;
    TakeRewardReqtakeFragment ctx_reqtakeFragment;

    private void InitFragments() {
        ctx_reviewFragment = TakeRewardReviewFragment.newInstance();
        ctx_reviewFragment.SetActivity(this, OnChoosePageListener);
        ctx_reqtakeFragment = TakeRewardReqtakeFragment.newInstance();
        ctx_reqtakeFragment.SetActivity(this, OnChoosePageListener);
    }

    private TakeRewardBaseFragment.OnChoosePageListener OnChoosePageListener = new TakeRewardBaseFragment.OnChoosePageListener() {
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
                    if (ctx_reviewFragment == null) {
                        ctx_reviewFragment = TakeRewardReviewFragment.newInstance();
                        ctx_reviewFragment.SetActivity(TakeRewardActivity.this, OnChoosePageListener);
                    }
                    frag = ctx_reviewFragment;
                    break;
                case 1:
                    if (ctx_reqtakeFragment == null) {
                        ctx_reqtakeFragment = TakeRewardReqtakeFragment.newInstance();
                        ctx_reqtakeFragment.SetActivity(TakeRewardActivity.this, OnChoosePageListener);
                    }
                    frag = ctx_reqtakeFragment;
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
            return 2;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (ctx_reqtakeFragment != null) {
            ctx_reqtakeFragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}
