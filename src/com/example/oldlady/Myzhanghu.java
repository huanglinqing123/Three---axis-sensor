package com.example.oldlady;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.server.HTTPutils;
import com.example.server.UpdateServer;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

public class Myzhanghu extends Activity {

	private EditText editText1, editText2, editText3, editText4;
	private  String tel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_jiaoshizhanghu);
		editText1 = (EditText) findViewById(R.id.name);
		editText2 = (EditText) findViewById(R.id.pass);
		editText3 = (EditText) findViewById(R.id.pass2);
		editText4 = (EditText) findViewById(R.id.tel);
		editText1.setFocusable(false);
		Intent intent = getIntent();
		String name = intent.getStringExtra("name");
		String pass = intent.getStringExtra("pass");
		String url = "http://www.huanglinqing.com/oldlady/gerenxinxi/getnewsJSON.php?name=";
		url = url + name;
		new HTTPutils().getNewsJSON(url, handler2);
	    
		editText1.setText(name);
		editText2.setText(pass);
		editText3.setText(pass);
		
		
		init();
	}

	private Handler handler2 = new Handler() {
		public void handleMessage(android.os.Message msg) {
			 String json=(String) msg.obj;
			 try {
				JSONArray array=new JSONArray(json);
				for (int i = 0; i < array.length(); i++) {
					JSONObject jsonObject=array.getJSONObject(i);
					tel=jsonObject.getString("tel");
					editText4.setText(tel);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		};
	};

	// 点击退出当前登录
	public void zhuxiao(View v) {
		// Login.sp.edit().putBoolean("IsAuto", false).commit();//取消自动登录
		// Login.sp.edit().putInt("status", 0);
		startActivity(new Intent(Myzhanghu.this, Login.class));
		finish();
	}

	// 点击修改密码
	public void update(View v) {
		editText2.setText("");
		editText2.setFocusableInTouchMode(true);
		editText3.setText("");
		editText3.setFocusableInTouchMode(true);
		

	}

	// 保存修改
	public void xiugai(View v) {
		Intent intent = getIntent();
		String pass = intent.getStringExtra("pass");
		if (editText2.getText().toString().isEmpty()
				|| editText3.getText().toString().isEmpty()) {
			Toast.makeText(Myzhanghu.this, "密码不能为空", 0).show();
		} else if (!editText2.getText().toString()
				.equals(editText3.getText().toString())) {
			Toast.makeText(Myzhanghu.this, "两次输入的密码不一致", 0).show();
		} else if (editText2.getText().toString().equals(pass)) {
			Toast.makeText(Myzhanghu.this, "新密码与当前密码相同", 0).show();
		} else {
			// 更新用户密码的功能
			String url = "http://www.huanglinqing.com/oldlady/Updatepassword/";
			String password = editText2.getText().toString();
			String name = intent.getStringExtra("name");
			 new UpdateServer(name, password, url, handler).start();
		}
	}

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String result = (String) msg.obj;
			int result1 = result.indexOf("修改成功");
			if (result1 > 0) {
				Toast.makeText(Myzhanghu.this, "密码修改成功，请重新登录", 0).show();
				startActivity(new Intent(Myzhanghu.this, Login.class));
				Myzhanghu.this.finish();
			}
		};
	};

	private void init() {
		editText2.setFocusable(false);
		editText3.setFocusable(false);
		editText4.setFocusable(false);
	}

}
