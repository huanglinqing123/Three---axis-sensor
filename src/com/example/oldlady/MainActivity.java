      package com.example.oldlady;

import java.util.List;

import com.example.fament.CloudeData;
import com.example.fament.MyMessage;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements OnClickListener {
	private ImageButton left, right;
	private TextView textView1, textView2;
	private Fragment fragmentleft, fragmentright;
	public void onAttachFragment(Fragment fragment) {
		// TODO Auto-generated method stub
		super.onAttachFragment(fragment);
		if (fragmentleft == null && fragment instanceof CloudeData) {
			fragmentleft = (CloudeData) fragment;
		} else if (fragmentright == null && fragment instanceof MyMessage) {
			fragmentright = (MyMessage) fragment;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		init();// 系统组件初始化函数
		jianting();
	}
	
	

	// 系统组件初始化函数
	private void init() {
		left = (ImageButton) findViewById(R.id.left);
		right = (ImageButton) findViewById(R.id.right);
		textView1 = (TextView) findViewById(R.id.t1);
		textView2 = (TextView) findViewById(R.id.t2);
		jianting();
		resetimage();
		select(0);
	}

	// 初始化监听事件
	private void jianting() {
		left.setOnClickListener(MainActivity.this);
		right.setOnClickListener(MainActivity.this);

	}

	// 底部菜单选择事件
	public void select(int i) {
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		hideframent(transaction);
		switch (i) {
		case 0:
//			if (fragmentleft == null) {
//				fragmentleft = new CloudeData();
//				Intent intent = getIntent();
//				String name = intent.getStringExtra("name");
//				Bundle bundle = new Bundle();
//				bundle.putString("name", name);
//				fragmentleft.setArguments(bundle);
//
//				transaction.add(R.id.framengt1, fragmentleft);
//			} else {
//				transaction.show(fragmentleft);
//			}
//			transaction.commit();
			fragmentleft = new CloudeData();
			Intent intent1 = getIntent();
			String name1 = intent1.getStringExtra("name");
			Bundle bundle1 = new Bundle();
			bundle1.putString("name", name1);
			fragmentleft.setArguments(bundle1);
			transaction.add(R.id.framengt1, fragmentleft);
			left.setImageResource(R.drawable.gxuesheng);
			textView1.setTextColor(Color.BLUE);
			transaction.commit();
			break;
		case 1:
			if (fragmentright == null) {
				fragmentright = new MyMessage();
				Intent intent = getIntent();
				String name = intent.getStringExtra("name");
				String pass = intent.getStringExtra("pass");
				Bundle bundle = new Bundle();
				bundle.putString("name", name);
				bundle.putString("pass", pass);
				fragmentright.setArguments(bundle);
				transaction.add(R.id.framengt1, fragmentright);
			} else {
				transaction.show(fragmentright);
			}
			transaction.commit();
			right.setImageResource(R.drawable.gjiaoshi);
			textView2.setTextColor(Color.BLUE);
			break;

		}
	}

	// 隐藏底部菜单
	private void hideframent(FragmentTransaction transaction) {
		if (fragmentleft != null) {
			transaction.hide(fragmentleft);
		}
		if (fragmentright != null) {
			transaction.hide(fragmentright);
		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		resetimage();
		switch (v.getId()) {
		case R.id.left:
			select(0);
			break;
		case R.id.right:
			select(1);
			break;

		}
	}

	private void resetimage() {
		// TODO Auto-generated method stub
		textView1.setTextColor(Color.BLACK);
		textView2.setTextColor(Color.BLACK);
        left.setImageResource(R.drawable.guanjiaoshi0);
        right.setImageResource(R.drawable.guanjiaoshi0);
	}
}
