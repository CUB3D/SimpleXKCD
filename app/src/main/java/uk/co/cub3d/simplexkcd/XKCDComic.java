package uk.co.cub3d.simplexkcd;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by cub3d on 02/03/18.
 */

public class XKCDComic implements Parcelable{
    public int id;
    public String title;
    public String altText;
    public String imageURL;

    public XKCDComic(int id, String title, String altText, String imageURL) {
        this.id = id;
        this.title = title;
        this.altText = altText;
        this.imageURL = imageURL;
    }

    protected XKCDComic(Parcel in) {
        id = in.readInt();
        title = in.readString();
        altText = in.readString();
        imageURL = in.readString();
    }

    public static final Creator<XKCDComic> CREATOR = new Creator<XKCDComic>() {
        @Override
        public XKCDComic createFromParcel(Parcel in) {
            return new XKCDComic(in);
        }

        @Override
        public XKCDComic[] newArray(int size) {
            return new XKCDComic[size];
        }
    };

    public static XKCDComic fromJsonObject(JSONObject object) throws JSONException {
        int id = object.getInt("num");
        String title = object.getString("safe_title");
        String altTex = object.getString("alt");
        String image = object.getString("img");
        return new XKCDComic(id, title, altTex, image);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.altText);
        dest.writeString(this.imageURL);
    }
}
