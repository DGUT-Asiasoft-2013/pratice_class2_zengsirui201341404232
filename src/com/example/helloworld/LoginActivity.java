package com.example.helloworld;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
<<<<<<< HEAD
import inputcells.SimpleTextInputCellFragment;

public class LoginActivity extends Activity {
	
	SimpleTextInputCellFragment fragInputCellAccount;
	SimpleTextInputCellFragment fragInputCellPassword;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		fragInputCellAccount = (SimpleTextInputCellFragment) getFragmentManager().findFragmentById(R.id.input_account);
		fragInputCellPassword = (SimpleTextInputCellFragment) getFragmentManager().findFragmentById(R.id.input_password);

		findViewById(R.id.btn_register).setOnClickListener(new View.OnClickListener() {//注册事件 

			@Override
			public void onClick(View v) {
				goRegister();
			}
		});
		
		findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {//注册事件 

			@Override
			public void onClick(View v) {
				goLogin();
			}
		});
		
	}

	void goRegister() {
		Intent itnt = new Intent(this, RegisterActivity.class);
		startActivity(itnt);
	}
	
	void goLogin() {
		Intent itnt = new Intent(this, HelloWorldActivity.class);
		startActivity(itnt);
	}
	
	@Override
	protected void onResume() {
		super.onResume();

		fragInputCellAccount.setLabelText("账户名");
		fragInputCellAccount.setHintText("请输入账户名");
		
		fragInputCellPassword.setLabelText("密码");
		fragInputCellPassword.setHintText("请输入密码");



	}
=======

public class LoginActivity extends Activity {

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
    }
    void goRegister(){
        Intent itnt = new Intent(this,RegisterActivity.class);
        startActivity(itnt);
    }
>>>>>>> branch 'master' of https://github.com/DGUT-Asiasoft-2013/pratice_class2_zengsirui_201341404232.git
}
