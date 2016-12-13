package com.example.helloworld;

import java.io.IOException;

import com.example.helloworld.entity.Server;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.Response;

public class MessagesSendingActivity extends Activity {

EditText editTitle,editText;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_messages_sending);
		
		editTitle=(EditText)findViewById(R.id.title);
		editText=(EditText)findViewById(R.id.edit);
		
		findViewById(R.id.btn_send).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				sendContent();
//				finish();
//				overridePendingTransition(R.anim.none, R.anim.slide_out_bottom);
				
			}
		});
	}
	 void sendContent() {
		String text=editText.getText().toString();
		String title=editTitle.getText().toString();
		
		MultipartBody body=new MultipartBody.Builder()
				.addFormDataPart("title", title)
				.addFormDataPart("text", text)
				.build();
		
		Request request =Server.requestuildApi("article")
				.post(body)
				.build();
		
		Server.getOkHttpClient().newCall(request).enqueue(new Callback() {
			
			@Override
			public void onResponse(Call arg0, Response arg1) throws IOException {
				final String responseBody=arg1.body().string();
				
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						MessagesSendingActivity.this.onSucceed(responseBody);
						
					}
				});
				
			}
			
			@Override
			public void onFailure(Call arg0, final IOException arg1) {
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						MessagesSendingActivity.this.onFailure(arg1);
						
					}
				});
				
			}
		});
		
		
	}
	protected void onFailure(IOException arg1) {
		new AlertDialog.Builder(this).setMessage(arg1.getMessage()).show();
		
	}
	 void onSucceed(String text) {
		new AlertDialog.Builder(this).setMessage(text)
		.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				finish();
				overridePendingTransition(R.anim.none, R.anim.slide_out_bottom);
			}
		}).show();
		
	
	}
}
