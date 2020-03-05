package com.bene.pictures.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class MLotteryList extends MBase {

    public static class Info implements Parcelable {

        public int id;              // 추첨의 id
        public String title;      // 추첨의 이름
        public String date; //추첨날자
        public int period; //지급기한(달수)
        public int status; //0:처리안함, 1:당첨내역없음, 2: 당첨되었음,3: 1등에 당첨됨.

        public Info() {

        }

        protected Info(Parcel in) {
            id = in.readInt();
            title = in.readString();
            date = in.readString();
            period = in.readInt();
            status = in.readInt();
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
            dest.writeString(title);
            dest.writeString(date);
            dest.writeInt(period);
            dest.writeInt(status);
        }
    }

    public ArrayList<Info> list;
}
