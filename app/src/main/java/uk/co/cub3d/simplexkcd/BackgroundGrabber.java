package uk.co.cub3d.simplexkcd;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by cub3d on 02/03/18.
 */

public class BackgroundGrabber implements Runnable {

    private final String url;

    public BackgroundGrabber(String url) {
        this.url = url;
    }

    public void onCallback(InputStream is) {
        // NOOP
    }

    @Override
    public void run() {
        URL url = null;
        try {
            url = new URL(this.url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        try {
            URLConnection connection = url.openConnection();
            onCallback(connection.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
