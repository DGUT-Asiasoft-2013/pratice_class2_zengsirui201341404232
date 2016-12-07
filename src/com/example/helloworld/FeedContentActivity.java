package com.example.helloworld;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class FeedContentActivity extends Activity {

	private TextView tv;//声明一个TextView控件
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.feed_listview_activity);
		
		tv=(TextView) findViewById(R.id.feed_liatview_textView);//通过资源ID：feed_liatview_textView获取文本条对象（获取后的因为是View类型，要用(TextView)强制转换为TextView类型并把值赋给tv）
		String text=getIntent().getStringExtra("cont");
		tv.setText(text);
	}
}
