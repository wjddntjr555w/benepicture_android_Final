package com.bene.pictures.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class MFriendsList extends MBase {

    public static class Info implements Parcelable {

        public int id;              // 친구의 user 아이디
        public String name;         // 친구의 이름
        public String face;         // 친구 카톡아이디 - 실지 사진은 앞에 카톡 URL 을 붙임.
        public int cnt_sendgift;    // 오늘 내가 선물한 회수
        public int cnt_introduce;   // 내가 초대한 회수
        public String profile;      // 친구 카톡 프로필

        public Info() {

        }

        protected Info(Parcel in) {
            id = in.readInt();
            name = in.readString();
            face = in.readString();
            cnt_sendgift = in.readInt();
            cnt_introduce = in.readInt();
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
            dest.writeString(name);
            dest.writeString(face);
            dest.writeInt(cnt_sendgift);
            dest.writeInt(cnt_introduce);
        }
    }

    public ArrayList<Info> list;
    public int page_cnt;
}
