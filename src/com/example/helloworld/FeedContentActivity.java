package com.example.helloworld;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class FeedContentActivity extends Activity {

	private TextView tv;//����һ��TextView�ؼ�
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.feed_listview_activity);
		
		tv=(TextView) findViewById(R.id.feed_liatview_textView);//ͨ����ԴID��feed_liatview_textView��ȡ�ı������󣨻�ȡ�����Ϊ��View���ͣ�Ҫ��(TextView)ǿ��ת��ΪTextView���Ͳ���ֵ����tv��
		String text=getIntent().getStringExtra("cont");
		tv.setText(text);
	}
}
