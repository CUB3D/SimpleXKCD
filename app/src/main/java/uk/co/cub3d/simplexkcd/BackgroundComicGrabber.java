package uk.co.cub3d.simplexkcd;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by cub3d on 02/03/18.
 */

public class BackgroundComicGrabber extends BackgroundGrabber {

    public Callback<XKCDComic> callback;

    public BackgroundComicGrabber(String url, Callback<XKCDComic> callback) {
        super(url);
        this.callback = callback;
    }

    @Override
    public void onCallback(InputStream is) {
        try {
            JSONObject object = new JSONObject(new BufferedReader(new InputStreamReader(is)).readLine());
            XKCDComic comic = XKCDComic.fromJsonObject(object);
            callback.callback(comic);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
