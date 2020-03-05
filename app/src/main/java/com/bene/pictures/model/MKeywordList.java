package com.bene.pictures.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class MKeywordList extends MBase {

    public static class Keyword implements Parcelable {

        public int id;                  // 키워드아이디
        public String name;             // 키워드명
        public int like;                // 1-좋아요
        public int parent;                // 대분류아이디, 대분류는 0
        public ArrayList<Keyword> child;   // 키워드리스트

        public Keyword(Parcel in) {
            id = in.readInt();
            name = in.readString();
            like = in.readInt();
            parent = in.readInt();
            child = in.createTypedArrayList(Keyword.CREATOR);
        }

        public static final Creator<Keyword> CREATOR = new Creator<Keyword>() {
            @Override
            public Keyword createFromParcel(Parcel in) {
                return new Keyword(in);
            }

            @Override
            public Keyword[] newArray(int size) {
                return new Keyword[size];
            }
        };

        public Keyword() {

        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(id);
            dest.writeString(name);
            dest.writeInt(like);
            dest.writeInt(parent);
            dest.writeTypedList(child);
        }
    }

    public ArrayList<Keyword> list;
}
