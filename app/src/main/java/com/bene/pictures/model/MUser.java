package com.bene.pictures.model;

import android.os.Parcel;
import android.os.Parcelable;

public class MUser extends MBase {

    public static class UserInfo implements Parcelable {

        public int id;
        public String nickname;         // 닉네임
        public String usr_id;
        public String pwd;
        public String birth;
        public String phone;
        public int gender; //1:남자, 2:여자
        public String bank; //은행계좌
        public String bank_name; //은행명
        public String depositor;// 예금주명
        public int bank_status; //은행계좌인증여부
        public String kt_id;            // 카톡아이디
        public String sess_token = "";
        public int login_type;          // 1-아이디, 2-카톡

        public float whole_winning_money; //총상금
        public float subscribe_cost; //수령가능한 금액
        public int available_ad_count; //볼수 있는 광고갯수
        public int subscribe_count; //응모권갯수
        public int agree_gamead;//게임광고 설정? 0: 아니, 1:예
        public int agree_videoad;//영상광고 설정? 0: 아니, 1:예
        public int agree_adkeyword;//맞춤키워드 설정? 0: 아니, 1:예
        public int agree_vibrate;//진동 설정? 0: 아니, 1:예
        public int alarm_wining_result;//당첨결과 알람설정? 0: 아니, 1:예
        public int alarm_friend_msg;//친구 메시지 설정? 0: 아니, 1:예
        public int alarm_notice;//공지사항알람 설정? 0: 아니, 1:예
        public int alarm_puzzle;//잔여퍼즐 알람 설정? 0: 아니, 1:예
        public int puzzle_time;//퍼즐 알람 시간

        public UserInfo() {

        }

        protected UserInfo(Parcel in) {
            id = in.readInt();
            nickname = in.readString();
            usr_id = in.readString();
            pwd = in.readString();
            birth = in.readString();
            phone = in.readString();
            gender = in.readInt();
            bank = in.readString();
            bank_name = in.readString();
            depositor = in.readString();
            bank_status = in.readInt();
            kt_id = in.readString();
            sess_token = in.readString();
            login_type = in.readInt();

            whole_winning_money = in.readFloat();
            subscribe_cost = in.readFloat();
            available_ad_count = in.readInt();
            subscribe_count = in.readInt();
            agree_gamead = in.readInt();
            agree_videoad = in.readInt();
            agree_adkeyword = in.readInt();
            agree_vibrate = in.readInt();
            alarm_wining_result = in.readInt();
            alarm_friend_msg = in.readInt();
            alarm_notice = in.readInt();
            alarm_puzzle = in.readInt();
            puzzle_time = in.readInt();
        }

        public static final Creator<UserInfo> CREATOR = new Creator<UserInfo>() {
            @Override
            public UserInfo createFromParcel(Parcel in) {
                return new UserInfo(in);
            }

            @Override
            public UserInfo[] newArray(int size) {
                return new UserInfo[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(id);
            dest.writeString(nickname);
            dest.writeString(usr_id);
            dest.writeString(pwd);
            dest.writeString(birth);
            dest.writeString(phone);
            dest.writeInt(gender);
            dest.writeString(bank);
            dest.writeString(bank_name);
            dest.writeString(depositor);
            dest.writeInt(bank_status);
            dest.writeString(kt_id);
            dest.writeString(sess_token);
            dest.writeInt(login_type);

            dest.writeFloat(whole_winning_money);
            dest.writeFloat(subscribe_cost);
            dest.writeInt(available_ad_count);
            dest.writeInt(subscribe_count);
            dest.writeInt(agree_gamead);
            dest.writeInt(agree_videoad);
            dest.writeInt(agree_adkeyword);
            dest.writeInt(agree_vibrate);
            dest.writeInt(alarm_wining_result);
            dest.writeInt(alarm_friend_msg);
            dest.writeInt(alarm_notice);
            dest.writeInt(alarm_puzzle);
            dest.writeInt(puzzle_time);
        }
    }

    public MUser.UserInfo info;
}
