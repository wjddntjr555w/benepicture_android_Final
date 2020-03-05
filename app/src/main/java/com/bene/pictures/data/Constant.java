package com.bene.pictures.data;

import com.bene.pictures.BuildConfig;

public interface Constant {

    String SDCARD_FOLDER = "BenePictures";

    int NET_DEV_MODE = 2;       // 1-개발모드, 2-서비스모드

    int PERMISSIONS_MEDIA_REQUEST = 1002;       // 갤러리 퍼미션 코드
    int PERMISSIONS_CAMERA_REQUEST = 1003;      // 카메라 퍼미션 코드
    int PERMISSIONS_VIDEO_REQUEST = 1004;       // 비디오 퍼미션 코드
    int PERMISSIONS_CONTACT_READ = 1005;        // 연락처 퍼미션 코드

    int EVENT_MSG_PROFILE_MODIFIED = 2001;      // 프로필수정

    String FONT_NANUM = "NanumBarunGothic.otf";
    String FONT_NANUMBOLD = "NanumBarunGothicBold.otf";

    int INTRO_TIME = 3000;

    String SERVER_URL = "http://15.164.112.250/api/";
    String DEV_SERVER_URL = "http://15.164.112.250/api/";//BuildConfig.WORK_ENV.equals("YJWORK") ? SERVER_URL : "http://192.168.0.176:8091/benepicture/api/";

    String KAKAO_SHARE_ICON = "http://15.164.112.250/upload/kakao_share.png";
    String KAKAO_ICON = "http://15.164.112.250/upload/kakao_icon.png";
    String APP_LINK = "";


    int LOGIN_ID = 1;        // 아이디 의한 로그인
    int LOGIN_KAKAO = 2;        // 카톡에 의한 로그인

    int DEVICE_ANDROID = 1;
    int DEVICE_IPHONE = 2;

    // 푸시토큰 저장관련 - add by blas 20190625
    public static final String PREFERENCE_NAME = "settings";
    public static final String KEY_PUSH_TOKEN = "push_token";
}
