package uk.co.cub3d.simplexkcd;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ComicFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class ComicFragment extends Fragment {
    private OnFragmentInteractionListener mListener;

    public ComicFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View b = inflater.inflate(R.layout.fragment_comic, container, false);

        int comicID = getArguments().getInt("comicID");

        String url = "https://xkcd.com/" + comicID + "/info.0.json";

        new Thread(new BackgroundComicGrabber(url, new Callback<XKCDComic>() {
            @Override
            public void callback(final XKCDComic comic) {
                new Thread(new BackgroundImageGrabber(comic.imageURL, new Callback<Bitmap>() {
                    @Override
                    public void callback(final Bitmap data) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ((ImageView)b.findViewById(R.id.imageView)).setImageBitmap(data);
                                ((TextView)b.findViewById(R.id.textViewTitle)).setText(comic.title);
                                ((TextView)b.findViewById(R.id.textViewAltText)).setText(comic.altText);
                            }
                        });
                    }
                })).start();
            }
        })).start();

        return b;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
