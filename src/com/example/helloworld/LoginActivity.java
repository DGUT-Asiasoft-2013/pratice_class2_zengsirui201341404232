package com.example.helloworld;

import java.io.IOException;

import com.example.helloworld.entity.Server;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import inputcells.SimpleTextInputCellFragment;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginActivity extends Activity {
	SimpleTextInputCellFragment fragAccount,fragPassword;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		findViewById(R.id.btn_register).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				goRegister();
			}
		});
		
		findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				goLogin();
			}
		});
		
		findViewById(R.id.btn_forgot_password).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				goRecoverPassword();
			}
		});
		
		fragAccount = (SimpleTextInputCellFragment) getFragmentManager().findFragmentById(R.id.input_account);
		fragPassword = (SimpleTextInputCellFragment) getFragmentManager().findFragmentById(R.id.input_password);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		fragAccount.setLabelText("�˻���");
		fragAccount.setHintText("�������˻���");
		fragPassword.setLabelText("����");
		fragPassword.setHintText("����������");
		fragPassword.setIsPassword(true);
	}
	
	void goRegister(){
		Intent itnt = new Intent(this,RegisterActivity.class);
		startActivity(itnt);
	}
	
	void goLogin(){
		
		String current_user=fragAccount.getText();
		String current_password=fragPassword.getText();
		
		
		
//		OkHttpClient client = new OkHttpClient();

		MultipartBody.Builder requestBodyBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM)
				.addFormDataPart("account", current_user)
				.addFormDataPart("passwordHash", current_password);
		
//		Request request = new Request.Builder()
//				.url("http://172.27.0.18:8080/membercenter/api/login")
//				.method("post", null)
//				.post(requestBodyBuilder.build())
//				.build();
		
		OkHttpClient client=Server.getOkHttpClient();
	      //����һ��Request����ȡurl,method����
	        Request request=Server.requestuildApi("login")
	        		.method("post", null)
	        		.post(requestBodyBuilder.build())
	        		.build();//������������URL
	
		final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
		progressDialog.setMessage("���Ժ�");
		progressDialog.setCancelable(false);
		progressDialog.setCanceledOnTouchOutside(false);
		progressDialog.show();

		client.newCall(request).enqueue(new Callback() {

			@Override
			public void onResponse(final Call arg0, final Response arg1) throws IOException {
				
				
				
				LoginActivity.this.runOnUiThread(new Runnable() {

					@Override
					public void run() {
						User user;
						progressDialog.dismiss();
						try {
							ObjectMapper objectMapper=new ObjectMapper();
							user=objectMapper.readValue(arg1.body().string(), User.class);
							new AlertDialog.Builder(LoginActivity.this).setTitle("�ɹ�")
							.setMessage(user.getName()+","+user.getAccount())
							.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
								
								@Override
								public void onClick(DialogInterface dialog, int which) {
									Intent itnt = new Intent(LoginActivity.this, HelloWorldActivity.class);
									startActivity(itnt);	
								}
							}).show();
							
							
						} catch (JsonParseException e) {
							e.printStackTrace();
						} catch (JsonMappingException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}
						
												
						
					}
				});
			}

			@Override
			public void onFailure(final Call arg0, final IOException arg1) {
				LoginActivity.this.runOnUiThread(new Runnable() {

					@Override
					public void run() {
						progressDialog.dismiss();
							//LoginActivity.this.onFailure(arg0, arg1);
							new AlertDialog.Builder(LoginActivity.this).setTitle("ʧ��").setMessage("����ʧ��")
							.setNegativeButton("ȷ��",null)
							.show();
						

						
					}
				});

			}
		});
	}
/*
	void onResponse(Call arg0, String response) {
		try {		
			new AlertDialog.Builder(this).setTitle("�ɹ�").setMessage("��¼�ɹ�")
			.setNegativeButton("ȷ��", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Intent itnt = new Intent(LoginActivity.this, HelloWorldActivity.class);
					startActivity(itnt);	
				}
			})
			.show();
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			onFailure(arg0, e);
		}
	}
	
	void onFailure(Call arg0, Exception arg1) {
		new AlertDialog.Builder(this).setTitle("��¼ʧ��")
		.setMessage(arg1.getLocalizedMessage())
				.setNegativeButton("��", null).show();
	}*/

	void goRecoverPassword(){
		Intent itnt = new Intent(this, PasswordRecoverActivity.class);
		startActivity(itnt);
	}
}