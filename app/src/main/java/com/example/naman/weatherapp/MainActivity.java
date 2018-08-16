package com.example.naman.weatherapp;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.constraint.solver.widgets.Helper;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.PrecomputedText;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.naman.weatherapp.Common.common;
import com.example.naman.weatherapp.Helper.helper;
import com.example.naman.weatherapp.Model.OpenWeatherApp;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.PicassoProvider;

import java.lang.reflect.Type;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;

public class MainActivity extends AppCompatActivity implements LocationListener {
    TextView txtcity, txtLastUpdate, txtDescription, txthumidity, txtTime, txtcelcius;
    ImageView imageView;

    LocationManager locationManager;
    String provider;
    static double lat, lng;
    OpenWeatherApp openWeatherApp = new OpenWeatherApp();
    int MY_PERSMISSION = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //control
        txtcity = (TextView) findViewById(R.id.txtcity);
        txtLastUpdate = (TextView) findViewById(R.id.txtLastUpdate);
        txtDescription = (TextView) findViewById(R.id.txtDescription);
        txthumidity = (TextView) findViewById(R.id.txthumidity);
        txtTime = (TextView) findViewById(R.id.txtTime);
        txtcelcius = (TextView) findViewById(R.id.txtcelcius);
        imageView = (ImageView) findViewById(R.id.imageView);

        //get ccordinates
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        provider = locationManager.getBestProvider(new Criteria(), false);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                    Manifest.permission.INTERNET,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_NETWORK_STATE,
                    Manifest.permission.SYSTEM_ALERT_WINDOW,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, MY_PERSMISSION);
        }
        Location location = locationManager.getLastKnownLocation(provider);
        if (location == null)
            Log.e("TAG", "No Location");
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                    Manifest.permission.INTERNET,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_NETWORK_STATE,
                    Manifest.permission.SYSTEM_ALERT_WINDOW,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, MY_PERSMISSION);
        }
        locationManager.removeUpdates(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{
                    Manifest.permission.INTERNET,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_NETWORK_STATE,
                    Manifest.permission.SYSTEM_ALERT_WINDOW,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            },MY_PERSMISSION);
        }
        locationManager.requestLocationUpdates(provider, 400, 1, this);
    }

    @Override
    public void onLocationChanged(Location location) {
        lat=location.getLatitude();
        lng=location.getLongitude();
        new GetWeather().execute(common.apiRequest(String.valueOf(lat),String.valueOf(lng)));

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
    private class GetWeather extends AsyncTask<String,Void,String>{
        ProgressDialog progressDialog=new ProgressDialog(MainActivity.this);
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setTitle("Please wait....");
            progressDialog.show();
        }


        @Override
        protected String doInBackground(String... strings) {
            String stream=null;
            String urlString= strings[0];

            helper http=new helper();
            stream=http.getHTTPData(urlString);
            return stream;

    }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s.contains("Error: Not found city")){
                progressDialog.dismiss();
                return;
            }
            Gson gson=new Gson();
            Type mtype=new TypeToken<OpenWeatherApp>(){}.getType();
            openWeatherApp=gson.fromJson(s,mtype);
            progressDialog.dismiss();
            txtcity.setText(String.format("%s,%s",openWeatherApp.getName(),openWeatherApp.getSy().getCountry()));
            txtLastUpdate.setText(String.format("Last Updated: %s", common.getDateNow()));
            txtDescription.setText(String.format("%s",openWeatherApp.getWeather().get(0).getDescription()));
            txthumidity.setText(String.format("%d",openWeatherApp.getMa().getHumidity()));
            txtTime.setText(String.format("%s/%s",common.unixTimeStampToDateTime(openWeatherApp.getSy().getSunrise()),common.unixTimeStampToDateTime(openWeatherApp.getSy().getSunset())));
            txtcelcius.setText(String.format("%.2f C",openWeatherApp.getMa().getTemp()));
            Picasso.with(MainActivity.this)
                    .load(common.getImage(openWeatherApp.getWeather().get(0).getIcon()))
                    .into(imageView);

        }
    }
}
