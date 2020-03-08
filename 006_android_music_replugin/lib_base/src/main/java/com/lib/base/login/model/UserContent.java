package com.lib.base.login.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.lib.base.model.BaseModel;

/**
 * 用户真正实体数据
 */
public class UserContent extends BaseModel implements Parcelable {

    public String userId; //用户唯一标识符
    public String photoUrl;
    public String name;
    public String tick;
    public String mobile;
    public String platform;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userId);
        dest.writeString(this.photoUrl);
        dest.writeString(this.name);
        dest.writeString(this.tick);
        dest.writeString(this.mobile);
        dest.writeString(this.platform);
    }

    public UserContent() {
    }

    protected UserContent(Parcel in) {
        this.userId = in.readString();
        this.photoUrl = in.readString();
        this.name = in.readString();
        this.tick = in.readString();
        this.mobile = in.readString();
        this.platform = in.readString();
    }

    public static final Parcelable.Creator<UserContent> CREATOR = new Parcelable.Creator<UserContent>() {
        @Override
        public UserContent createFromParcel(Parcel source) {
            return new UserContent(source);
        }

        @Override
        public UserContent[] newArray(int size) {
            return new UserContent[size];
        }
    };
}
