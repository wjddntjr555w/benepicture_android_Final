package com.bene.pictures.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class MMsgList extends MBase {

    public static class Info implements Parcelable {

        public int id;              // 메시지아이디
        public String datetime;      // 작성날자
        public String title;     // 제목
        public String content;      // 내용
        public int read;          //확인하였는가? 0: 미확인, 1: 확인
        public int msg_type;          // 0-공지,1이상이면 관리자메세지

        public Info() {

        }

        protected Info(Parcel in) {
            id = in.readInt();
            datetime = in.readString();
            title = in.readString();
            content = in.readString();
            read = in.readInt();
            msg_type = in.readInt();
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
            dest.writeString(datetime);
            dest.writeString(title);
            dest.writeString(content);
            dest.writeInt(read);
            dest.writeInt(msg_type);
        }
    }

    public ArrayList<Info> list;
    public int page_cnt;
}
