package uk.co.cub3d.simplexkcd;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class Main2Activity extends AppCompatActivity implements ComicFragment.OnFragmentInteractionListener{

    public ViewPager pager;
    public PagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        pager = findViewById(R.id.viewPager);

        adapter = new PagerAdapter(getSupportFragmentManager(), 0);

        pager.setAdapter(adapter);

        final Runnable onDataChange = new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        };

        final Handler h = new Handler();

        loadLatestComic(new Callback<XKCDComic>() {
            @Override
            public void callback(XKCDComic data) {
                adapter.availableComics = data.id;
                h.postDelayed(onDataChange, 0);
            }
        });
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
