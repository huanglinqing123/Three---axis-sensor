package com.example.fament;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.oldlady.Myzhanghu;
import com.example.oldlady.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class MyMessage extends Fragment {
	private ListView listView;
	private String titles[] = { "账户信息" };
	private int Image[] = { R.drawable.teacher};
	private List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	private SimpleAdapter adapter;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.mymessgae, null);
		listView = (ListView) v.findViewById(R.id.listView1);
		final String name = getArguments().getString("name");
      
		final String pass = getArguments().getString("pass");
		for (int i = 0; i < 1; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("title", titles[i]);
			map.put("image", Image[i]);
			list.add(map);
		}
		adapter = new SimpleAdapter(getActivity(), list, R.layout.jiaoshiwode,
				new String[] { "title", "image" }, new int[] { R.id.text1,
						R.id.image1 });
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				if (arg2 == 0) {
					Intent intent = new Intent(getActivity(),
							Myzhanghu.class);
					intent.putExtra("name", name);
					intent.putExtra("pass", pass);
					startActivity(intent);

				} 
			}
		});
		return v;
	}
}
