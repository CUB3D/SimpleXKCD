package uk.co.cub3d.simplexkcd;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by cub3d on 02/03/18.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {

    public Fragment preload;
    public int preloadID = -1000;

    public int availableComics = 0;

    public PagerAdapter(FragmentManager fm, int availableComics) {
        super(fm);
        this.availableComics = availableComics;
    }

    public Fragment getComicFragement(int id) {
        Fragment f =  new ComicFragment();
        Bundle b = new Bundle();
        b.putInt("comicID", id);
        f.setArguments(b);

        return f;
    }

    @Override
    public Fragment getItem(int position) {
        if(preloadID == position) {
            Fragment f = preload;
            preloadID = availableComics - position - 1;
            preload = getComicFragement(availableComics - position - 1);
            return f;
        } else {
            preload = getComicFragement(availableComics - position - 1);
            preloadID = availableComics - position - 1;
            return getComicFragement(availableComics - position);
        }
    }

    @Override
    public int getCount() {
        return availableComics;
    }
}
