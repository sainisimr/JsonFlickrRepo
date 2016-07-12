package com.example.scs_user.jsonflickr;

import android.app.FragmentTransaction;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
ProgressBar mProgressbar;
    private ArrayList<FlickrPhoto> mPhotos = new ArrayList<FlickrPhoto>();
    public final static String API_KEY ="add API Key here";
    public final static String NUM_PHOTOS ="12";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mProgressbar = (ProgressBar) findViewById(R.id.progressBar1);
        if(isOnline()){
            LoadPhotos task = new LoadPhotos();
            task.execute();
        }
        else
        {
            mProgressbar.setVisibility(View.GONE);
            Toast.makeText(MainActivity.this.getApplicationContext(), "connect to retrieve photos", Toast.LENGTH_SHORT).show();
        }
    }
    public ArrayList<FlickrPhoto> getPhotos(){
    return mPhotos;
    }

    private boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo!=null && networkInfo.isConnected());
    }

    private class LoadPhotos extends AsyncTask<String,String,Long> {
       protected void onPreExecute(){

       }

        public void showlist(){
            PhotoListFragment photolistFragment = new PhotoListFragment();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.layout_container, photolistFragment);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();
        }

        protected void onPostExecute(Long result){
        if (result == 0){
            showlist();
        }
            else{
            Toast.makeText(MainActivity.this.getApplicationContext(),"something wrong",Toast.LENGTH_SHORT).show();
        }
            mProgressbar.setVisibility(View.GONE);

        }

        @Override
        protected Long doInBackground(String... params) {
            HttpURLConnection connection = null;
            try {
                URL dataUrl = new URL("https://api.flickr.com/services/rest/?method=flickr.photos.getRecent&api_key="+ API_KEY +"&per_page="+ NUM_PHOTOS +"&format=json&nojsoncallback=1");
                connection = (HttpURLConnection) dataUrl.openConnection();
                connection.connect();
                int status = connection.getResponseCode();
                Log.d("Photos",status + " " + connection.getResponseMessage());
                if (status==200){
                    InputStream is = connection.getInputStream();
                    BufferedReader mbufferedreader = new BufferedReader(new InputStreamReader(is));
                    String responseString;
                    StringBuilder sb = new StringBuilder();
                    while((responseString = mbufferedreader.readLine())!= null){
                        sb = sb.append(responseString);
                    }
                    String photodata = sb.toString();
                    mPhotos = FlickrPhoto.makePhotolist(photodata);
                    return (0l);
                }
                else{
                    return (1l);
                }
            }
            catch (java.io.IOException e) {
                e.printStackTrace();
                return (1l);
            } catch (NullPointerException e) {
                e.printStackTrace();
                return (1l);
            } catch (JSONException e) {
                e.printStackTrace();
                return (1l);
            }

        }
    }

}
