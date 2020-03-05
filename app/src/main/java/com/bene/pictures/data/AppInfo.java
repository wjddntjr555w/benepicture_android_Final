package com.bene.pictures.data;

public class AppInfo {

    private static AppInfo singleton;

    public static AppInfo getInstance() {
        if (singleton == null) {
            singleton = new AppInfo();
        }

        return singleton;
    }

    public static final int PAGE_MAIN = 1;

    private int _nCurPageIdx = PAGE_MAIN;

    public int getCurPageIdx() {
        return _nCurPageIdx;
    }

    public void setCurPageIdx(int page_idx) {
        _nCurPageIdx = page_idx;
    }

    private String _strAppPath = "";

    public String getAppPath() {
        return _strAppPath;
    }

    public void setAppPath(String path) {
        _strAppPath = path;
    }
}
