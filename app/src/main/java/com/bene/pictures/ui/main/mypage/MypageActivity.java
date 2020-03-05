package com.bene.pictures.ui.main.mypage;

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

public class MypageActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

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
//                ui_tblPagingMenu.getTabAt(position).select();
                refreshFragment();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        ui_vprContentPages.setOffscreenPageLimit(3);
        ui_vprContentPages.setCurrentItem(MYPAGE_SUBSCRIBE);
        ui_tblPagingMenu.setupWithViewPager(ui_vprContentPages);

//        ui_tblPagingMenu.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                _nCurPage = tab.getPosition();
//                ui_vprContentPages.setCurrentItem(_nCurPage);
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//            }
//        });

//        ui_tblPagingMenu.getTabAt(0).select();

        UpdatePagingTabTextStyle();
    }

    private void UpdatePagingTabTextStyle() {
        Common.setTabItemFontAndSize(this, ui_tblPagingMenu, 0, "NanumBarunGothic.otf", 18, Typeface.BOLD);
        Common.setTabItemFontAndSize(this, ui_tblPagingMenu, 1, "NanumBarunGothic.otf", 18, Typeface.BOLD);
        Common.setTabItemFontAndSize(this, ui_tblPagingMenu, 2, "NanumBarunGothic.otf", 18, Typeface.BOLD);
    }

    void refreshFragment() {

        switch (_nCurPage) {
            case MYPAGE_SUBSCRIBE:
                if (ctx_subscribeFragment != null) {
                    ctx_subscribeFragment.refresh();
                }
                break;
            case MYPAGE_TAKEREWARD:
                if (ctx_takerewardFragment != null) {
                    ctx_takerewardFragment.refresh();
                }
                break;
            case MYPAGE_KEYWORD:
                if (ctx_keywordFragment != null) {
                    ctx_keywordFragment.refresh();
                }
                break;
        }
    }

    private static final int MYPAGE_SUBSCRIBE = 0;
    private static final int MYPAGE_TAKEREWARD = 1;
    private static final int MYPAGE_KEYWORD = 2;

    private int _nCurPage = MYPAGE_SUBSCRIBE;

    MypageSubscribeInfoFragment ctx_subscribeFragment;
    MypageTakerewardHistoryFragment ctx_takerewardFragment;
    MypageKeywordFragment ctx_keywordFragment;

    private void InitFragments() {
        ctx_subscribeFragment = MypageSubscribeInfoFragment.newInstance();
        ctx_subscribeFragment.SetActivity(this, onChooseMsgListener);
        ctx_takerewardFragment = MypageTakerewardHistoryFragment.newInstance();
        ctx_takerewardFragment.SetActivity(this, onChooseMsgListener);
        ctx_keywordFragment = MypageKeywordFragment.newInstance();
        ctx_keywordFragment.SetActivity(this, onChooseMsgListener);
    }

    private MypageBaseFragment.OnChooseMsgListener onChooseMsgListener = new MypageBaseFragment.OnChooseMsgListener() {
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
                    if (ctx_subscribeFragment == null) {
                        ctx_subscribeFragment = MypageSubscribeInfoFragment.newInstance();
                        ctx_subscribeFragment.SetActivity(MypageActivity.this, onChooseMsgListener);
                    }
                    frag = ctx_subscribeFragment;
                    break;
                case 1:
                    if (ctx_takerewardFragment == null) {
                        ctx_takerewardFragment = MypageTakerewardHistoryFragment.newInstance();
                        ctx_takerewardFragment.SetActivity(MypageActivity.this, onChooseMsgListener);
                    }
                    frag = ctx_takerewardFragment;
                    break;
                case 2:
                    if (ctx_keywordFragment == null) {
                        ctx_keywordFragment = MypageKeywordFragment.newInstance();
                        ctx_keywordFragment.SetActivity(MypageActivity.this, onChooseMsgListener);
                    }
                    frag = ctx_keywordFragment;
                    break;
            }
            return frag;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String pageTitle = "";
            switch (position) {
                case 0:
                    pageTitle = "응모권 정보";
                    break;
                case 1:
                    pageTitle = "상금 수령내역";
                    break;
                case 2:
                    pageTitle = "맞춤 키워드";
                    break;
            }

            return pageTitle;
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

    public static final int MYPAGE_SUBSCRIBEINFO_DETAIL = 1202;
    public static final int MYPAGE_TAKEREWARDHISTORY_DETAIL = 1203;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MYPAGE_SUBSCRIBEINFO_DETAIL) {
            if (ctx_subscribeFragment != null) {
                ctx_subscribeFragment.onActivityResult(requestCode, resultCode, data);
            }
        } else if (requestCode == MYPAGE_TAKEREWARDHISTORY_DETAIL) {
            if (ctx_takerewardFragment != null) {
                ctx_takerewardFragment.onActivityResult(requestCode, resultCode, data);
            }
        }
    }
}
