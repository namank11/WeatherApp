package com.example.naman.weatherapp.Common;

import android.support.annotation.NonNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class common {
    public static String API_KEY="a312bde703722f03a471c90e2c1fdaf1";
    public static String API_LINK="http://api.openweathermap.org/data/2.5/weatgher";

    @NonNull
    public static String apiRequest(String lat, String lng){
        StringBuilder stringBuilder=new StringBuilder(API_LINK);
        stringBuilder.append(String.format("?lat=%s&lon=%s&units=metric",lat,lng,API_KEY));
        return  stringBuilder.toString();
    }
    public static String unixTimeStampToDateTime(double unixTimeStamp){
        DateFormat dateFormat=new SimpleDateFormat("HH:mm");
        Date date=new Date();
        date.setTime((long)unixTimeStamp*1000);
        return dateFormat.format(date);
    }
    public static String getImage(String icon){
        return String.format("http://openweather.org/img/w/%s.png",icon);
    }
    public static String getDateNow(){
        DateFormat dateFormat=new SimpleDateFormat("dd MMMMM YYYY HH;mm");
        Date date=new Date();
        return dateFormat.format(date);
    }
}
