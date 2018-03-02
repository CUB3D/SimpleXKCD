package uk.co.cub3d.simplexkcd;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.InputStream;

/**
 * Created by cub3d on 02/03/18.
 */

public class BackgroundImageGrabber extends BackgroundGrabber {

    public Callback<Bitmap> callback;

    public BackgroundImageGrabber(String url, Callback<Bitmap> callback) {
        super(url);
        this.callback = callback;
    }

    @Override
    public void onCallback(InputStream is) {
        callback.callback(BitmapFactory.decodeStream(is));
    }
}
