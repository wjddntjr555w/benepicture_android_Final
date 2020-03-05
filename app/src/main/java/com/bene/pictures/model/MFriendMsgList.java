package com.bene.pictures.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class MFriendMsgList extends MBase {

    public static class Info implements Parcelable {

        public int id;              // 메시지의 id
        public int friend_id;              // 친구의 uer아이디
        public String name;      // 친구의 이름
        public String face; //친구사진 url
        public int receivegift_status; //내가 받은 선물의 상태 0:수령하지 않음, 1: 수령함
        public String profile;      // 친구 카톡 프로필

        public Info() {

        }

        protected Info(Parcel in) {
            id = in.readInt();
            friend_id = in.readInt();
            name = in.readString();
            face = in.readString();
            receivegift_status = in.readInt();
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
            dest.writeInt(friend_id);
            dest.writeString(name);
            dest.writeString(face);
            dest.writeInt(receivegift_status);
        }
    }

    public ArrayList<Info> list;
    public int page_cnt;
}
