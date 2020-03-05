package com.bene.pictures.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class MReviewList extends MBase {

    public static class Info implements Parcelable {

        public int id;              // 리뷰아이디
        public String user_id;      // 리뷰작성자아이디
        public String create_date;      // 작성일자
        public float winning_money; //당첨금
        public String subscribe_adname;     // 응모권의 광고명
        public String subscribe_number;      // 응모권번호
        public int is_admin;          //관리자지급인가? 0:아니, 1: 예
        public int round;          //회차수
        public String content;

        public Info() {

        }

        protected Info(Parcel in) {
            id = in.readInt();
            user_id = in.readString();
            create_date = in.readString();
            winning_money = in.readFloat();
            subscribe_adname = in.readString();
            subscribe_number = in.readString();
            is_admin = in.readInt();
            round = in.readInt();
            content = in.readString();
        }

        public static final Creator<Info> CREATOR = new Creator<Info>() {
            @Override
            public Info createFromParcel(Parcel in) {
                return new Info(in);
            }

            @Override
            public Info[] newArray(int size) {
                return new Info[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(id);
            dest.writeString(user_id);
            dest.writeString(create_date);
            dest.writeFloat(winning_money);
            dest.writeString(subscribe_adname);
            dest.writeString(subscribe_number);
            dest.writeInt(is_admin);
            dest.writeInt(round);
            dest.writeString(content);
        }
    }

    public ArrayList<Info> list;
    public int page_cnt;
}
