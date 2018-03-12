package uk.co.cub3d.simplexkcd;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.Random;

public class Main2Activity extends AppCompatActivity implements ComicFragment.OnFragmentInteractionListener {

    public ViewPager pager;
    public PagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        pager = findViewById(R.id.viewPager);

        adapter = new PagerAdapter(getSupportFragmentManager(), 0);

        pager.setAdapter(adapter);

        final Handler h = new Handler();

        loadLatestComic(new Callback<XKCDComic>() {
            @Override
            public void callback(final XKCDComic data) {

                h.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adapter.availableComics = data.id;
                        adapter.notifyDataSetChanged();
                    }
                }, 0);
            }
        });

        requestStoragePermission();
    }

    public void requestStoragePermission() {
        ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (!(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    Toast.makeText(getApplicationContext(), "Downloading may not work", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.download:
                int commicID = this.adapter.getComicFragement(this.adapter.availableComics - this.pager.getCurrentItem()).getArguments().getInt("comicID");
                downloadComic(commicID);
                break;
            case R.id.share:
                Toast.makeText(getApplicationContext(), "Share is not implemented yet", Toast.LENGTH_LONG).show();
                break;
            case R.id.random:
                this.pager.setCurrentItem(this.randomCommicID());
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    public int randomCommicID() {
        int maxValue = this.adapter.getCount();
        return new Random().nextInt(maxValue);
    }

    public void downloadComic(int comicID) {
        new Thread(new BackgroundComicGrabber("https://xkcd.com/" + comicID + "/info.0.json", new Callback<XKCDComic>() {
            @Override
            public void callback(XKCDComic data) {
                new ImageDownloader().execute(data);
            }
        })).start();
    }

    public void loadLatestComic(Callback<XKCDComic> callback) {
        String url = "https://xkcd.com/info.0.json";
        new Thread(new BackgroundComicGrabber(url, callback)).start();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        System.out.println(uri);
    }
}
