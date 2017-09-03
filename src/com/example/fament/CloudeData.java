package com.example.fament;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.telephony.SmsManager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.oldlady.R;
import com.example.server.HTTPutils;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendPosition;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

public class CloudeData extends Fragment {
	private String tel;
	private SensorManager manager ;
	private PieChart mChart;
	private String messContent = "您的老人可能摔倒了，请及时查看";
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.cloudedata, null);
		mChart = (PieChart) view.findViewById(R.id.sp);
		final String name = getArguments().getString("name");
		final String pass = getArguments().getString("pass");
		String url = "http://www.huanglinqing.com/oldlady/gerenxinxi/getnewsJSON.php?name=";
		url = url + name;
		new HTTPutils().getNewsJSON(url, handler2); 
		manager = (SensorManager) getActivity().getSystemService(getActivity().SENSOR_SERVICE);
		Sensor sensor = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		manager.registerListener(listener, sensor, manager.SENSOR_DELAY_NORMAL);
		PieData mData = getPieData(5, 100);
		showChart(mChart, mData);
		return view;
	}
	private void showChart(PieChart mChart, PieData mData) {
		// TODO Auto-generated method stub
		mChart.setHoleColorTransparent(true);
		mChart.setHoleRadius(60f);// 
		mChart.setTransparentCircleRadius(64f);// 
		mChart.setDescription("跌倒地点分布图");
		mChart.setDrawCenterText(true);// 
		mChart.setDrawHoleEnabled(true);
		mChart.setTouchEnabled(true);
		mChart.setRotationAngle(90);// 
		mChart.setRotationEnabled(true);//
		mChart.setUsePercentValues(true);// 
		mChart.setCenterText("跌到地点");
		
		mChart.setData(mData);
		Legend mLegend = mChart.getLegend();//
		mLegend.setPosition(LegendPosition.RIGHT_OF_CHART);//
		mLegend.setXEntrySpace(7f);
		mLegend.setYEntrySpace(5f);
		mChart.animateXY(1000, 1000);// 
	}

	private PieData getPieData(int count, float range) {
		// TODO Auto-generated method stub
		ArrayList<String> xValues = new ArrayList<String>();// 
		for (int i = 0; i < count; i++) {
			xValues.add("厨房");// 
			xValues.add("卧室");
			xValues.add("卫生间");
			xValues.add("过道");
			xValues.add("客厅");
		}
		ArrayList<Entry> yValues = new ArrayList<Entry>();// 
		

		float ggy1 = 4;
		float ggy2 = 14;
		float ggy3 = 34;
		float ggy4 = 38;
		float ggy5 = 10;
		yValues.add(new Entry(ggy1, 0));
		yValues.add(new Entry(ggy2, 0));
		yValues.add(new Entry(ggy3, 0));
		yValues.add(new Entry(ggy4, 0));
		yValues.add(new Entry(ggy5, 0));
		
		PieDataSet dataSet = new PieDataSet(yValues, "跌到地点");
		dataSet.setSliceSpace(0f);
		ArrayList<Integer> colors = new ArrayList<Integer>();
		
		colors.add(Color.rgb(205, 205, 205));
		colors.add(Color.rgb(114, 188, 223));
		colors.add(Color.rgb(255, 123, 124));
		colors.add(Color.rgb(57, 135, 200));
		colors.add(Color.rgb(67, 105, 200));
		dataSet.setColors(colors);
		DisplayMetrics metrics = getResources().getDisplayMetrics();
		float px = 5 * (metrics.densityDpi / 160f);
		dataSet.setSelectionShift(px);
		PieData pieData = new PieData(xValues, dataSet);
		return pieData;
	}
	public void sendMessage(String phoneNum, String mess) {
		SmsManager manager = SmsManager.getDefault();
		List<String> divideContents = manager.divideMessage(mess);
		for (int i = 0; i < divideContents.size(); i++) {
			manager.sendTextMessage(phoneNum, null, mess, null, null);
		}
		
	}
	private SensorEventListener listener = new SensorEventListener() {
		@Override
		public void onAccuracyChanged(Sensor arg0, int arg1) {
			// TODO Auto-generated method stub

		}
		@Override
		public void onSensorChanged(SensorEvent event) {
			// TODO Auto-generated method stub
			float x = Math.abs(event.values[0]);
			float y = Math.abs(event.values[1]);
			float z = Math.abs(event.values[2]);
			if (x > 25 || y >25 || z > 28) {
				sendMessage(tel, messContent);
				Toast.makeText(getActivity(), "报警信息已成功发送", 0).show();
			}
		}
	};
	private Handler handler2 = new Handler() {
		public void handleMessage(android.os.Message msg) {
			 String json=(String) msg.obj;
			 try {
				JSONArray array=new JSONArray(json);
				for (int i = 0; i < array.length(); i++) {
					JSONObject jsonObject=array.getJSONObject(i);
					tel=jsonObject.getString("tel");
					
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		};
	};
}
