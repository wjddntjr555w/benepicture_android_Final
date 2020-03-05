package com.bene.pictures.net;

import com.bene.pictures.model.MAdver;
import com.bene.pictures.model.MBase;
import com.bene.pictures.model.MCert;
import com.bene.pictures.model.MFindUsrId;
import com.bene.pictures.model.MFriendMsgList;
import com.bene.pictures.model.MFriendsList;
import com.bene.pictures.model.MJoinAgree;
import com.bene.pictures.model.MKeywordList;
import com.bene.pictures.model.MLotteryList;
import com.bene.pictures.model.MMsgList;
import com.bene.pictures.model.MReviewList;
import com.bene.pictures.model.MSign;
import com.bene.pictures.model.MSubscribeInfoList;
import com.bene.pictures.model.MTakeHistoryList;
import com.bene.pictures.model.MUser;
import com.bene.pictures.model.MVersion;
import com.bene.pictures.model.MWholeReward;
import com.bene.pictures.model.MWinnerSubscribeList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface Api {

    // 버전정보얻기
    @FormUrlEncoded
    @POST("./base/version")
    Call<MVersion> getVersionInfo(@Field("device") int device);

    // 로그인
    @FormUrlEncoded
    @POST("./user/signin")
    Call<MSign> signIn(@Field("type") int type,                 // 로그인타입(1-아이디, 2-카톡아이디)
                       @Field("usr_id") String usr_id,                  // 아이디/카톡아이디
                       @Field("pwd") String pwd,                // 비밀번호
                       @Field("device") int device,             // 디바이스타입(1-안드, 2-아이폰)
                       @Field("token") String token);           // FCM 토큰

    // 회원가입을 위한 이용약관정보 얻기
    @FormUrlEncoded
    @POST("./base/term")
    Call<MJoinAgree> getJoinAgreeInfo(
            @Field("device") int device            // 디바이스타입(1-안드, 2-아이폰)
    );

    // 아이디중복체크
    @FormUrlEncoded
    @POST("./user/check")
    Call<MBase> checkId(@Field("usr_id") String usr_id);             // 체크하려는 아이디

    // 비밀번호체크
    @FormUrlEncoded
    @POST("./user/check_pwd")
    Call<MBase> checkPwd(@Field("usr_id") String usr_id, // 회원 아이디
                         @Field("pwd") String pwd);      // 비밀번호

    //인증코드 생성
    @GET("./base/get_key")
    Call<MCert> getKey(@Query("phone") String phone,            // 전화번호
                       @Query("token") String token,            // 로그인 했을시 발급받은 토큰, 로그아웃시 빈 문짜열
                       @Query("check") int check);              // 0-중복확인, 1-존재확인

    // 인증번호 확인
    @GET("./base/check_key")
    Call<MBase> checkKey(@Query("phone") String phone,          // 전화번호
                         @Query("cert_key") String cert_key);   // 인증번호

    // 회원가입
    @FormUrlEncoded
    @POST("./user/signup")
    Call<MBase> signUp(@Field("type") int type,                 // 	1-아이디, 2-카톡
                       @Field("name") String name,      // 이름
                       @Field("usr_id") String usr_id,      // 아이디
                       @Field("birthday") String birthday,            // 생년월일(1990.01.01)
                       @Field("phone") String phone,            // 전화번호(전화번호에 의한 가입일 경우 필수)
                       @Field("auth") String auth,            // 전화번호(전화번호에 의한 가입일 경우 필수)
                       @Field("pwd") String pwd,                // 비밀번호(전번/이메일에 의한 가입일시 필수)
                       @Field("gender") int gender,             // 성별(1-남, 2-여)
                       @Field("adver") int adver,             // 광고성정보수신동의(1-동의, 0-거절)
                       @Field("device") int device,             // 디바이스타입(1-안드, 2-아이폰)
                       @Field("token") String token,//토큰
                       @Field("kakao_id") String kakao_id);

    // 본인의 프로필정보얻기
    @FormUrlEncoded
    @POST("./user/get_profile")
    Call<MUser> getProfile(@Field("id") int id,                 // 로그인한 회원아이디
                           @Field("token") String token);       // 로그인시 발급받은 토큰

    // 비번재설정 (로그아웃상태)
    @FormUrlEncoded
    @POST("./user/reset_pwd")
    Call<MBase> resetPwd(@Field("phone") String phone,            // 전화번호
                         @Field("pwd") String pwd);               // 	비밀번호

    // 아이디 찾기
    @FormUrlEncoded
    @POST("./user/find_id")
    Call<MFindUsrId> findId(@Field("phone") String phone,          // 전화번호
                            @Field("cert_key") String cert_key);               // 	인증코드

    // 광고내리적재하기
    @FormUrlEncoded
    @POST("./user/get_adver")
    Call<MAdver> getAdver(@Field("id") int id,                 // 로그인한 회원아이디
                          @Field("token") String token);       // 로그인시 발급받은 토큰

    // 광고시청완료 보고
    @FormUrlEncoded
    @POST("./user/ad_complete")
    Call<MBase> reqAdComplelte(@Field("id") int id,                 // 로그인한 회원아이디
                               @Field("ad_id") int ad_id,           // 완료한 광고아이디
                               @Field("log") int log,           // 완료한 광고아이디
                               @Field("link") int link);          // 링크클릭여부

    // 알림설정
    @FormUrlEncoded
    @POST("./user/alarm_set")
    Call<MBase> reqAlarmSet(@Field("id") int id,                 // 로그인한 회원아이디
                            @Field("kind") String kind,// 알림종류("gamead:게임광고, videoad:비디오광고, adkeyword:맞춤키워드, vibrate:진동", "winningresult":당첨결과 "friendmsg":친구메시지 "notice":공지사항 "puzzle":퍼즐
                            @Field("value") int value);       // 설정값(0:off, 1:on)

    // 회원정보수정
    @FormUrlEncoded
    @POST("./user/change_profile")
    Call<MUser> changeProfile(@Field("id") int id,                 // 	회원아이디
                              @Field("name") String name,      // 이름
                              @Field("usr_id") String usr_id,      // 아이디
                              @Field("birthday") String birthday,            // 생년월일(1990.01.01)
                              @Field("phone") String phone,            // 전화번호(전화번호에 의한 가입일 경우 필수)
                              @Field("auth") String auth,            // 전화번호(전화번호에 의한 가입일 경우 필수)
                              @Field("pwd") String pwd,                // 비밀번호(전번/이메일에 의한 가입일시 필수)
                              @Field("gender") int gender,             // 성별(1-남, 2-여)
                              @Field("bank") String bank,             // 은행계좌
                              @Field("device") int device,             // 디바이스타입(1-안드, 2-아이폰)
                              @Field("token") String token,          //토큰
                              @Field("bank_status") int bank_status);          //계좌인증여부


    // 회원 계좌정보 변경
    @FormUrlEncoded
    @POST("./user/change_account")
    Call<MBase> changeAccount(@Field("id") int id,                    // 회원아이디
                              @Field("bank") String bank,             // 은행명
                              @Field("account") String account,       // 은행계좌 번호
                              @Field("depositor") String depositor,   // 예금주명
                              @Field("bank_status") int bank_status);  // 계좌인증여부

    // 회원탈퇴
    @FormUrlEncoded
    @POST("./user/signout")
    Call<MBase> signout(@Field("id") int id, @Field("pwd") String pwd);   // 인증번호

    // 메시지함 목록 얻기
    @GET("./contents/msg_list")
    Call<MMsgList> getMsgList(@Query("usr_id") int usr_id,
                              @Query("type") int type, //0:unread, 1: read, 2:whole
                              @Query("page_num") int page_num);

    // 메시지함 확인
    @GET("./contents/msg_read")
    Call<MBase> readMsg(@Query("id") int usr_id,
                        @Query("msg") int msg_id);

    // 메시지함 삭제
    @GET("./contents/msg_delete")
    Call<MBase> delMsg(@Query("usr_id") int usr_id,
                       @Query("msg_id") int msg_id);

    // 친구에게 액션
    @FormUrlEncoded
    @POST("./user/req_friend_action")
    Call<MBase> reqFriendAction(@Field("id") int id,                            // 로그인한 회원아이디
                                @Field("friend") int friend_id,                 // 친구의 회원아이디
                                @Field("msg_id") int msg_id,                    // 메시지id - 친구목록과 초대하기에서는 이값이 0이고 메시지함에서는 이값이 >0 임.
                                @Field("action") String kind,                   // 액션종류 ("present":선물하기, "introduce":초대하기, "reject_gift":선물거절, "accept_gift":선물받기)
                                @Field("kakao") String kakao);                  // 친구초대시 초대하는 친구의 카톡아이디

    // 친구 목록 얻기
    @FormUrlEncoded
    @POST("./user/friends_list")
    Call<MFriendsList> getFriendsList(@Field("usr_id") int usr_id,
                                      @Field("type") int type, //0:친구목록, 1: 초대하기
                                      @Field("search") String search,
                                      @Field("friend") String friend, // 카카오친구아이디를 콤마로 구분함 문짜열
                                      @Field("page_num") int page_num);

    // 친구 목록 얻기
    @GET("./user/friend_msg_list")
    Call<MFriendMsgList> getFriendMsgList(@Query("usr_id") int usr_id,
                                          @Query("search") String search,
                                          @Query("page_num") int page_num);

    // 응모권정보목록
    @GET("./contents/subscribe_info_list")
    Call<MSubscribeInfoList> getSubscribeInfoList(@Query("usr_id") int usr_id,
                                                  @Query("page_num") int page_num);

    //상금 수령 내역 목록
    @GET("./contents/take_history_list")
    Call<MTakeHistoryList> getTakeRewardHistoryList(@Query("usr_id") int usr_id,
                                                    @Query("page_num") int page_num,
                                                    @Query("type") int type);

    // 리뷰목록
    @GET("./contents/review_list")
    Call<MReviewList> getReviewList(@Query("usr_id") int usr_id,
                                    @Query("lottery") int lottery,//상금수령의 리뷰목록에서는 이 값이 0이고, 당첨내역확인에서는 이 값이 존재함.
                                    @Query("page_num") int page_num);

    // 상금수령신청
    @FormUrlEncoded
    @POST("./contents/take_reward")
    Call<MBase> takeReward(@Field("id") int id,                 // 	회원아이디
                           @Field("name") String name,      // 이름
                           @Field("number") String number,      // 응모권번호
                           @Field("bank") String bank,            // 은행명
                           @Field("account") String account,            // 계좌번호
                           @Field("review") String review,            // 후기
                           @Field("token") String token,          //토큰
                           @Field("cost") float cost);            // 신청금액

    // 추첨목록
    @GET("./contents/lottery")
    Call<MLotteryList> getLotteryList(@Query("usr_id") int usr_id);

    // 광고신청
    @Multipart
    @POST("./contents/register")
    Call<MBase> registerAd(
            @Part("id") RequestBody id,
            @Part("type") RequestBody type,
            @Part("from") RequestBody from,
            @Part("to") RequestBody to,
            @Part("title") RequestBody title,
            @Part("url") RequestBody url,
            @Part("count") RequestBody count,
            @Part("cost") RequestBody cost,
            @Part MultipartBody.Part file);

    // 광고시청
    @GET("./user/view_ad")
    Call<MBase> viewAd(@Query("id") int usr_id,
                       @Query("ad") int ad,
                       @Query("log") int log);

    // 맞춤키워드리스트
    @GET("./contents/keyword_list")
    Call<MKeywordList> keywordList(@Query("id") int usr_id);

    // 키워드 설정
    @FormUrlEncoded
    @POST("./contents/save_keyword")
    Call<MBase> saveKeyword(@Field("id") int usr_id,
                            @Field("keyword") String type);

    // 당첨된 응모권리스트
    @GET("./contents/winner_list")
    Call<MWinnerSubscribeList> winnerList(@Query("id") int usr_id,
                                          @Query("round") int round,
                                          @Query("page_num") int page_num);


    // 당첨값 얻기
    @GET("./user/get_whole_reward")
    Call<MWholeReward> getWholeReward();

}
