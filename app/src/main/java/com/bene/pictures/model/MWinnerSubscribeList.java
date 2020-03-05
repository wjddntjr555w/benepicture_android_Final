package com.bene.pictures.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class MWinnerSubscribeList extends MBase {

    public static class Info implements Parcelable {

        public int id;              // 응모권아이디
        public String ad_name;      // 광고명
        public String no;           // 응모권번호
        public String phone;        // 휴대폰 뒷자리
        public float cost;            // 당첨된 금액
        public String usr_id;       // 당첨자 아이디

        protected Info(Parcel in) {
            id = in.readInt();
            ad_name = in.readString();
            no = in.readString();
            phone = in.readString();
            cost = in.readFloat();
            usr_id = in.readString();
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
            dest.writeString(ad_name);
            dest.writeString(no);
            dest.writeString(phone);
            dest.writeFloat(cost);
            dest.writeString(usr_id);
        }
    }

    public ArrayList<Info> list;
    public int page_cnt;
}
