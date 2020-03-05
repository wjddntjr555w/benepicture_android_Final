package com.bene.pictures.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class MTakeHistoryList extends MBase {

    public static class Info implements Parcelable {

        public int id;              // 응모권 아이디
        public String create_datetime;      // 생성날자시간
        public String subscribe_adname;     // 응모권의 광고명
        public String subscribe_number;      // 응모권번호
        public int is_admin;          //관리자지급인가? 0:아니, 1: 예
        public String take_datetime;      // 수령날자시간
        public int status; //0:표준, 1: 수령신청, 2: 수령완료, 3: 자동소멸
        public float winning_money; //당첨금

        public Info() {

        }

        protected Info(Parcel in) {
            id = in.readInt();
            create_datetime = in.readString();
            subscribe_adname = in.readString();
            subscribe_number = in.readString();
            is_admin = in.readInt();
            take_datetime = in.readString();
            status = in.readInt();
            winning_money = in.readFloat();
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
            dest.writeString(create_datetime);
            dest.writeString(subscribe_adname);
            dest.writeString(subscribe_number);
            dest.writeInt(is_admin);
            dest.writeString(take_datetime);
            dest.writeInt(status);
            dest.writeFloat(winning_money);
        }
    }

    public ArrayList<Info> list;
    public int page_cnt;
}
