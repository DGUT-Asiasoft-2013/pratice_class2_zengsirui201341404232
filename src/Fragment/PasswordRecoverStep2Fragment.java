package Fragment;

//import com.example.helloworld.OnSubmitClickedListener;
import com.example.helloworld.R;
import com.example.helloworld.R.id;

import android.app.AlertDialog;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import inputcells.SimpleTextInputCellFragment;

public class PasswordRecoverStep2Fragment extends Fragment {
	View view;
	SimpleTextInputCellFragment fragVerify;
	SimpleTextInputCellFragment fragPassword;
	SimpleTextInputCellFragment fragPasswordRepeat;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		if (view == null) {
			view = inflater.inflate(R.layout.fragment_password_recover_step2, null);

			fragVerify=(SimpleTextInputCellFragment)getFragmentManager().findFragmentById(R.id.input_verify);
			fragPassword = (SimpleTextInputCellFragment) getFragmentManager().findFragmentById(R.id.input_password);
			fragPasswordRepeat=(SimpleTextInputCellFragment)getFragmentManager().findFragmentById(R.id.input_password_repeat);
			view.findViewById(R.id.btn_submit).setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					onSubmitClicked();
				}
			});
		}

		return view;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		fragVerify.setLabelText("验证码");
		fragVerify.setHintText("输入验证码");
		
		fragPassword.setLabelText("新密码");
		fragPassword.setHintText("输入新密码");
		
		fragPasswordRepeat.setLabelText("重复新密码");
		fragPasswordRepeat.setHintText("请再次输入新密码");
	}

	public static interface OnSubmitClickedListener {
		void onSubmitClicked();
	}

	OnSubmitClickedListener onSubmitClickedListener;

	public void setOnSubmitClickedListener(OnSubmitClickedListener onSubmitClickedListener) {
		this.onSubmitClickedListener = onSubmitClickedListener;
	}

	public void onSubmitClicked() {
		if (fragPassword.getText().equals(fragPasswordRepeat.getText())) {//先判断新密码与重复新密码是否一致
			if (onSubmitClickedListener != null) {//若监听器不为空时？
				onSubmitClickedListener.onSubmitClicked();
			}
		} else {//不一致时显示提示对话框
			new AlertDialog.Builder(getActivity()).setMessage("密码不一致").setNegativeButton("确定",null).show();
		}
	}

	public String getText() {
		return fragPassword.getText();
	}

}
