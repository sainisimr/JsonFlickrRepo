package com.example.scs_user.jsonflickr;

import android.app.ListFragment;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by SCS_USER on 7/8/2016.
 */
public class PhotoListFragment extends ListFragment{
    String[] mTitles;
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MainActivity currentActivity = (MainActivity) this.getActivity();
        ArrayList<FlickrPhoto> photos = currentActivity.getPhotos();
        mTitles = new String[photos.size()];
        for (int i=0;i<photos.size();i++){
            mTitles[i] =photos.get(i).title;
        }
        setListAdapter(new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_list_item_1,mTitles));
    }
}
