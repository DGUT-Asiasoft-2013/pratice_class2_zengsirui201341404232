package com.example.helloworld;

import java.io.IOException;

import com.example.helloworld.entity.Server;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.preference.Preference.OnPreferenceClickListener;
import android.view.View;
import android.widget.ProgressBar;
import inputcells.PictureInputCellFragment;
import inputcells.SimpleTextInputCellFragment;
import okhttp3.*;

public class RegisterActivity extends Activity {
	SimpleTextInputCellFragment fragInputCellAccount;
	SimpleTextInputCellFragment fragInputEmailAddress;
	SimpleTextInputCellFragment fragInputCellPassword;
	SimpleTextInputCellFragment fragInputCellPasswordRepeat;
	SimpleTextInputCellFragment fragInputCellName;
	
	PictureInputCellFragment fragment_image;
	
	

	ProgressDialog ProgressDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_register);

		fragInputCellAccount = (SimpleTextInputCellFragment) getFragmentManager().findFragmentById(R.id.input_account);
		fragInputEmailAddress = (SimpleTextInputCellFragment) getFragmentManager().findFragmentById(R.id.input_email);
		fragInputCellPassword = (SimpleTextInputCellFragment) getFragmentManager()
				.findFragmentById(R.id.input_password);
		fragInputCellPasswordRepeat = (SimpleTextInputCellFragment) getFragmentManager()
				.findFragmentById(R.id.input_password_repeat);
		fragInputCellName = (SimpleTextInputCellFragment) getFragmentManager().findFragmentById(R.id.input_name);
		
		fragment_image=(PictureInputCellFragment) getFragmentManager().findFragmentById(R.id.input_avatar);

		findViewById(R.id.btn_submit).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Submit();
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();

		fragInputCellAccount.setLabelText("�˻���");
		{
			fragInputCellAccount.setHintText("�������˻���");
		}

		fragInputCellName.setLabelText("�ǳ�");
		{
			fragInputCellName.setHintText("�������ǳ�");
		}

		fragInputCellPassword.setLabelText("����");
		{
			fragInputCellPassword.setHintText("����������");
			fragInputCellPassword.setIsPassword(true);
		}

		fragInputCellPasswordRepeat.setLabelText("�ظ�����");
		{
			fragInputCellPasswordRepeat.setHintText("���ظ���������");
			fragInputCellPasswordRepeat.setIsPassword(true);
		}

		fragInputEmailAddress.setLabelText("�����ʼ�");
		{
			fragInputEmailAddress.setHintText("�������������");
		}
	}

	void Submit() {
		String password = fragInputCellPassword.getText();
		String passwordRepeat = fragInputCellPasswordRepeat.getText();

		if (!password.equals(passwordRepeat)) {// ��password��passwordRepeat��һ��
			new AlertDialog.Builder(RegisterActivity.this)
					.setMessage("�ظ����벻һ��")
					.setIcon(android.R.drawable.ic_dialog_alert)
					.setNegativeButton("��", null)
					.show();

			return;
		}
		
	

		String account = fragInputCellAccount.getText();
		String name = fragInputCellName.getText();
		String email = fragInputEmailAddress.getText();

//		OkHttpClient client = new OkHttpClient();
OkHttpClient client=Server.getOkHttpClient();
		MultipartBody.Builder requestBodyBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM)
				.addFormDataPart("account", account)
				.addFormDataPart("name", name)
				.addFormDataPart("email", email)
				.addFormDataPart("passwordHash", password);

	if (fragment_image.getPngData()!=null) {
			requestBodyBuilder.addFormDataPart("avatar", "avatar",RequestBody.create(MediaType.parse("image/png")
					, fragment_image.getPngData()));
			
		}
		
//		Request request = new Request.Builder()
//				.url("http://172.27.0.18:8080/membercenter/api/register")
//				.method("post", null)
//				.post(requestBodyBuilder.build())
//				.build();

	Request request=Server.requestuildApi("register").method("post", null).post(requestBodyBuilder.build()).build();
		final ProgressDialog progressDialog = new ProgressDialog(RegisterActivity.this);
		progressDialog.setMessage("���Ժ�");
		progressDialog.setCancelable(false);
		progressDialog.setCanceledOnTouchOutside(false);
		progressDialog.show();

		client.newCall(request).enqueue(new Callback() {

			@Override
			public void onResponse(final Call arg0, final Response arg1) throws IOException {
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						progressDialog.dismiss();
						
						RegisterActivity.this.onResponse(arg0, arg1.body().toString());
					}
				});
			}

			@Override
			public void onFailure(final Call arg0, final IOException arg1) {
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						progressDialog.dismiss();// ʹProgressDialog��ʧ

						RegisterActivity.this.onFailure(arg0, arg1);
					}
				});

			}
		});
	}

	void onFailure(Call arg0, Exception arg1) {
		new AlertDialog.Builder(this).setTitle("����ʧ��").setMessage(arg1.getLocalizedMessage())
				.setNegativeButton("��", null).show();
	}

	void onResponse(Call arg0, String response) {
		try {
			new AlertDialog.Builder(this).setTitle("����ɹ�").setMessage("��������ɹ�")
			.setNegativeButton("ȷ��", null)
			.show();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			onFailure(arg0, e);
		}
	}
}
