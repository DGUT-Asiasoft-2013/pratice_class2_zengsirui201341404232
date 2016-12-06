package com.example.helloworld;

import android.app.Activity;
import android.os.Bundle;
import inputcells.SimpleTextInputCellFragment;

public class RegisterActivity extends Activity {
	SimpleTextInputCellFragment fragInputCellAccount;
	SimpleTextInputCellFragment fragInputCellPassword;
	SimpleTextInputCellFragment fragInputCellPasswordRepeat;
<<<<<<< HEAD
	SimpleTextInputCellFragment fragInputCellEmail;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_register);

		fragInputCellAccount = (SimpleTextInputCellFragment) getFragmentManager().findFragmentById(R.id.input_account);
		fragInputCellPassword = (SimpleTextInputCellFragment) getFragmentManager().findFragmentById(R.id.input_password);
		fragInputCellPasswordRepeat = (SimpleTextInputCellFragment) getFragmentManager().findFragmentById(R.id.input_password_repeat);
		fragInputCellEmail= (SimpleTextInputCellFragment) getFragmentManager().findFragmentById(R.id.input_email);
	}

	@Override
	protected void onResume() {
		super.onResume();

		fragInputCellAccount.setLabelText("ÕË»§Ãû");
		fragInputCellAccount.setHintText("ÇëÊäÈëÕË»§Ãû");
		
		fragInputCellPassword.setLabelText("ÃÜÂë");
		fragInputCellPassword.setHintText("ÇëÊäÈëÃÜÂë");
		fragInputCellPassword.setIsPassword(true);
		
		fragInputCellPasswordRepeat.setLabelText("ÖØ¸´ÃÜÂë");
		fragInputCellPasswordRepeat.setHintText("ÇëÖØ¸´ÊäÈëÃÜÂë");
		fragInputCellPasswordRepeat.setIsPassword(true);
		
		fragInputCellAccount.setLabelText("Email");
		fragInputCellAccount.setHintText("ÇëÊäÈëÓÊÏä");
=======

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_register);

		fragInputCellAccount = (SimpleTextInputCellFragment) getFragmentManager().findFragmentById(R.id.input_account);
		fragInputCellPassword = (SimpleTextInputCellFragment) getFragmentManager().findFragmentById(R.id.input_password);
		fragInputCellPasswordRepeat = (SimpleTextInputCellFragment) getFragmentManager().findFragmentById(R.id.input_password_repeat);
	}

	@Override
	protected void onResume() {
		super.onResume();

		fragInputCellAccount.setLabelText("ÕË»§Ãû");
		fragInputCellAccount.setHintText("ÇëÊäÈëÕË»§Ãû");
		fragInputCellPassword.setLabelText("ÃÜÂë");
		fragInputCellPassword.setHintText("ÇëÊäÈëÃÜÂë");
		fragInputCellPassword.setIsPassword(true);
		fragInputCellPasswordRepeat.setLabelText("ÖØ¸´ÃÜÂë");
		fragInputCellPasswordRepeat.setHintText("ÇëÖØ¸´ÊäÈëÃÜÂë");
		fragInputCellPasswordRepeat.setIsPassword(true);
>>>>>>> branch 'master' of https://github.com/DGUT-Asiasoft-2013/pratice_class2_zengsirui_201341404232.git

	}
}
