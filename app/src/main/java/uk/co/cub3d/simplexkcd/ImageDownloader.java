package uk.co.cub3d.simplexkcd;

import android.os.AsyncTask;
import android.os.Environment;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by cub3d on 05/03/18.
 */

public class ImageDownloader extends AsyncTask<XKCDComic, Integer, Integer> {

    @Override
    protected Integer doInBackground(XKCDComic... xkcdComics) {
        try {
            URL url = new URL(xkcdComics[0].imageURL);
            URLConnection connection = url.openConnection();
            BufferedInputStream buf = new BufferedInputStream(connection.getInputStream());

            String basePath = Environment.getExternalStorageDirectory().getPath() + "/Download/SimpleXKCD/";

            File f = new File(basePath);
            if(!f.exists()) {
                f.mkdirs();
            }

            FileOutputStream fos =  new FileOutputStream(basePath + xkcdComics[0].id + ".png");

            byte[] b = new byte[1024];
            int count;

            while((count = buf.read(b, 0, b.length)) != -1) {
                fos.write(b, 0, count);
            }

            fos.flush();
            fos.close();

            buf.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 1;
    }
}
