package com.example.helloworld;

import java.io.IOException;
import java.security.PublicKey;

import com.example.helloworld.entity.Server;
import com.example.helloworld.view.MD5;
import com.fasterxml.jackson.databind.ObjectMapper;

import Fragment.PasswordRecoverStep1Fragment;
import Fragment.PasswordRecoverStep1Fragment.OnGoNextListener;
import Fragment.PasswordRecoverStep2Fragment;
import Fragment.PasswordRecoverStep2Fragment.OnSubmitClickedListener;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import inputcells.SimpleTextInputCellFragment;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.tls.OkHostnameVerifier;

public class PasswordRecoverActivity extends Activity {
	PasswordRecoverStep1Fragment step1 = new PasswordRecoverStep1Fragment();// 动态添加Fragment
	PasswordRecoverStep2Fragment step2 = new PasswordRecoverStep2Fragment();

	// SimpleTextInputCellFragment fragInputCellAccount;
	// SimpleTextInputCellFragment fragInputCellPassword;
	// SimpleTextInputCellFragment fragInputCellPasswordRepeat;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// fragInputCellAccount = (SimpleTextInputCellFragment)
		// getFragmentManager().findFragmentById(R.id.input_account);
		// fragInputCellPassword = (SimpleTextInputCellFragment)
		// getFragmentManager().findFragmentById(R.id.input_password);
		// fragInputCellPasswordRepeat=(SimpleTextInputCellFragment)getFragmentManager().findFragmentById(R.id.input_password_repeat);

		setContentView(R.layout.activity_password_recover);

		step1.setOnGoNextListener(new OnGoNextListener() {

			@Override
			public void onGoNext() {
				goStep2();
			}

		});

		step2.setOnSubmitClickedListener(new OnSubmitClickedListener() {

			public void onSubmitClicked() {
				goSubmit();
			}
		});
		getFragmentManager().beginTransaction().replace(R.id.container, step1).commit();
	}

	void goStep2() {

		getFragmentManager().beginTransaction() // 开始一个事务
				.setCustomAnimations(R.animator.slide_in_right, R.animator.slide_out_left, R.animator.slide_in_left,
						R.animator.slide_out_right)
				.replace(R.id.container, step2).addToBackStack(null)// 返回键
				.commit();// 提交事务
	}

	void goSubmit() {
		OkHttpClient client = Server.getOkHttpClient();
		MultipartBody body = new MultipartBody.Builder().addFormDataPart("email", step1.getText())
				// .addFormDataPart("passwordHash", MD5.getMD5(step2.getText()))
				.addFormDataPart("passwordHash", step2.getText()).build();

		Request request = Server.requestuildApi("passwordrecover").post(body).build();

		client.newCall(request).enqueue(new Callback() {

			@Override
			public void onResponse(final Call arg0, Response arg1) throws IOException {
				// TODO Auto-generated method stub
				try {
					final Boolean succeed = new ObjectMapper().readValue(arg1.body().bytes(), Boolean.class);
					runOnUiThread(new Runnable() {
						public void run() {
							if (succeed) {
								PasswordRecoverActivity.this.onResponse(arg0, "succeed is true");
							} else {

								PasswordRecoverActivity.this.onFailure(arg0, new Exception("succeed if false"));
							}
						}
					});
				} catch (final Exception e) {
					runOnUiThread(new Runnable() {
						public void run() {
							PasswordRecoverActivity.this.onFailure(arg0, e);
						}
					});
				}
				
			}

			@Override
			public void onFailure(Call arg0, IOException arg1) {
				// TODO Auto-generated method stub

			}
		});
	}

	protected void onFailure(Call arg0, Exception exception) {
		Toast.makeText(PasswordRecoverActivity.this, exception.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

	}

	protected void onResponse(Call arg0, String string) {
		Toast.makeText(PasswordRecoverActivity.this, "成功", Toast.LENGTH_SHORT).show();
		 Intent itnt = new Intent(PasswordRecoverActivity.this,
				 LoginActivity.class);
				 startActivity(itnt);
				 finish();
	}

}
