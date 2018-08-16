package com.example.naman.weatherapp.Helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class helper {
    static String stream=null;
    public helper(){

    }
    public String getHTTPData(String urlString){
        try {
            URL url=new URL(urlString);
            HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
            if (httpURLConnection.getResponseCode()==200)
            {
             BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
             StringBuilder stringBuilder=new StringBuilder();
             String line;
             while ((line=bufferedReader.readLine())!=null)
                 stringBuilder.append(line);
             stream=stringBuilder.toString();
             httpURLConnection.disconnect();
            }
        } catch (MalformedURLException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stream;
    }
}
