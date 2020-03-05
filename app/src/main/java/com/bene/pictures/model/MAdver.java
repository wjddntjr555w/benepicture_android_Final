package com.bene.pictures.model;

import android.os.Parcel;
import android.os.Parcelable;

public class MAdver extends MBase {

    public static class Info implements Parcelable {

        public int id; //광고아아디
        public String name; //광고명
        public float cost;          // 1회노출시적립금
        public int ad_type;          // 1-영상광고, 2: 스위칭퍼즐, 3:짝맞추기퍼즐
        public String ad_image;         //광고이미지의 url(동영상광고인경우: 배경이미지，　기타：　광고이미지）
        public String ad_video; //광고동영상 url
        public String url; //링크 url
        public int log; //통계아이디

        public Info() {

        }

        protected Info(Parcel in) {
            id = in.readInt();
            name = in.readString();
            cost = in.readFloat();
            ad_type = in.readInt();
            ad_image = in.readString();
            ad_video = in.readString();
            url = in.readString();
            log = in.readInt();
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
            dest.writeFloat(cost);
            dest.writeInt(ad_type);
            dest.writeString(ad_image);
            dest.writeString(ad_video);
            dest.writeString(url);
            dest.writeInt(log);
        }
    }

    public MAdver.Info info;
}
