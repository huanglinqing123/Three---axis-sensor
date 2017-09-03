package com.example.server;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

public class HTTPutils {
	public static void getNewsJSON(final String url,final Handler handler) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				HttpURLConnection connection;
				InputStream is;
				try {
					connection = (HttpURLConnection) new URL(url)
							.openConnection();
					connection.setRequestMethod("GET");
					is=connection.getInputStream();
					BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(is));
					String line="";
					StringBuilder result = new StringBuilder();
					while((line=bufferedReader.readLine())!=null){
						result.append(line);
					}
					Message message=new Message();
					message.obj=result.toString();
					handler.sendMessage(message);
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}).start();
	}
	public static void setPicBitmap(final ImageView ima, final String pic_url){
		   new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					HttpURLConnection cono=(HttpURLConnection) new URL(pic_url).openConnection();
					cono.connect();
					InputStream is=cono.getInputStream();
					Bitmap  bitmap=BitmapFactory.decodeStream(is);
					ima.setImageBitmap(bitmap);
					is.close();

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				
			}
		}).start();
	   }
}
