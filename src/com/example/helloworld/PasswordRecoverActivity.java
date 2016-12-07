package com.example.helloworld;

import Fragment.PasswordRecoverStep1Fragment;
import Fragment.PasswordRecoverStep1Fragment.OnGoNextListener;
import Fragment.PasswordRecoverStep2Fragment;
import android.app.Activity;
import android.os.Bundle;
import inputcells.SimpleTextInputCellFragment;

public class PasswordRecoverActivity extends Activity {
    PasswordRecoverStep1Fragment step1 = new PasswordRecoverStep1Fragment();//动态添加Fragment
	PasswordRecoverStep2Fragment step2 = new PasswordRecoverStep2Fragment();
	
//	SimpleTextInputCellFragment fragInputCellAccount;
//	SimpleTextInputCellFragment fragInputCellPassword;
//	SimpleTextInputCellFragment fragInputCellPasswordRepeat;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
//		fragInputCellAccount = (SimpleTextInputCellFragment) getFragmentManager().findFragmentById(R.id.input_account);
//		fragInputCellPassword = (SimpleTextInputCellFragment) getFragmentManager().findFragmentById(R.id.input_password);
//		fragInputCellPasswordRepeat=(SimpleTextInputCellFragment)getFragmentManager().findFragmentById(R.id.input_password_repeat);
		
		setContentView(R.layout.activity_password_recover);
		
 		step1.setOnGoNextListener(new OnGoNextListener() {
			
			@Override
			public void onGoNext() {
				goStep2();
			}

		});
 		
 		getFragmentManager().beginTransaction().replace(R.id.container, step1).commit();
	}
	
	void goStep2(){
		
		getFragmentManager()
		.beginTransaction()	//开始一个事务
		.setCustomAnimations(
				R.animator.slide_in_right,
				R.animator.slide_out_left,
				R.animator.slide_in_left,
				R.animator.slide_out_right)
		.replace(R.id.container, step2)
		.addToBackStack(null)//返回键
		.commit();//提交事务
	}
	
	
	
}

