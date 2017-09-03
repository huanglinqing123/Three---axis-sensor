package com.example.oldlady;

import com.example.server.LoginServer;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity {

	private EditText editText1, editText2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);
		editText1 = (EditText) findViewById(R.id.name);
		editText2 = (EditText) findViewById(R.id.pass);

	}

	// 登录按钮
	public void denglu(View v) {
		String name = editText1.getText().toString();
		String pass = editText2.getText().toString();
		if (name.isEmpty() || pass.isEmpty()) {
			Toast.makeText(Login.this, "用户名或密码为空", 0).show();
		} else {
			String url = "http://www.huanglinqing.com/oldlady/denglu/";
			// 用户登录验证
			new LoginServer(name, pass, url, handler).start();
		}
	}

	// 登录验证线程机制
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			String json = (String) msg.obj;
			int index = json.indexOf("登陆成功");
			int index1 = json.indexOf("密码错误");
			int index2 = json.indexOf("服务器繁忙");
			int index3 = json.indexOf("用户名不存在");
			if (index > 0) {
				Toast.makeText(Login.this, "登录成功", 0).show();
				Intent intent = new Intent(Login.this, MainActivity.class);
				String name = editText1.getText().toString();
				String pass = editText2.getText().toString();
				intent.putExtra("name", name);
				intent.putExtra("pass", pass);
				startActivity(intent);
				Login.this.finish();

			} else if (index1 > 0) {
				Toast.makeText(Login.this, "密码错误", 0).show();
			} else if (index2 > 0) {
				Toast.makeText(Login.this, "服务器繁忙", 0).show();
			} else if (index3 > 0) {
				Toast.makeText(Login.this, "用户名不存在", 0).show();
			}
		};
	};

	// 新用户注册按钮
	public void zhuce(View v) {
		Intent intent = new Intent(this, Regeister.class);
		startActivity(intent);
		Login.this.finish();
	}

	// 取消按钮

	// 再按一次退出
	private static final int MSG_EXIT = 1;
	private static final int MSG_EXIT_WAIT = 2;
	private static final long EXIT_DELAY_TIME = 2000;
	private Handler mHandle = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MSG_EXIT:
				if (mHandle.hasMessages(MSG_EXIT_WAIT)) {
					finish();
				} else {
					Toast.makeText(Login.this, "再按一次返回键退出", Toast.LENGTH_SHORT)
							.show();
					mHandle.sendEmptyMessageDelayed(MSG_EXIT_WAIT,
							EXIT_DELAY_TIME);
				}
				break;
			case MSG_EXIT_WAIT:
				break;
			}
		}
	};

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (KeyEvent.KEYCODE_BACK == keyCode) {
			mHandle.sendEmptyMessage(MSG_EXIT);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
