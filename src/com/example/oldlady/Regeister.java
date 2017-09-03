package com.example.oldlady;

import com.example.server.RegeisterServer;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

public class Regeister extends Activity {

	private EditText editText1, editText2, editText3, editText4;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_regeister);
		editText1 = (EditText) findViewById(R.id.user);
		editText2 = (EditText) findViewById(R.id.pass1);
		editText3 = (EditText) findViewById(R.id.pass2);
		editText4 = (EditText) findViewById(R.id.phone);
	}

	// 取消按钮
	public void back(View v) {
		Intent intent = new Intent(Regeister.this, Login.class);
		startActivity(intent);
		Regeister.this.finish();
	}

	// 注册按钮
	public void zhuce(View v) {
		String name = editText1.getText().toString();
		String pass1 = editText2.getText().toString();
		String pass2 = editText3.getText().toString();
		String phone = editText4.getText().toString();

		if (name.isEmpty() || pass1.isEmpty() || pass2.isEmpty()
				|| phone.isEmpty()) {
			Toast.makeText(Regeister.this, "您输入的信息不完整", 0).show();
		} else if (!pass1.equals(pass2)) {
			Toast.makeText(Regeister.this, "两次输入的密码不一致", 0).show();

		} else {
			String url = "http://www.huanglinqing.com/oldlady/zhuce/";
			// 存储用户信息
			new RegeisterServer(name, pass1, phone, url, handler).start();
		}

	}

	// 注册线程机制
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String json = (String) msg.obj;
			int index = json.indexOf("注册成功");
			if (index > 0) {
				Toast.makeText(Regeister.this, "注册成功", 0).show();
				startActivity(new Intent(Regeister.this, Login.class));
				Regeister.this.finish();
			} else {
				Toast.makeText(Regeister.this, "注册失败，请稍后重试", 0).show();
			}

		};
	};
}
