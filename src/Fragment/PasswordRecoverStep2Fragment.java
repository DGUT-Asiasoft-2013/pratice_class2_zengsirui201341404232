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
		
		fragVerify.setLabelText("��֤��");
		fragVerify.setHintText("������֤��");
		
		fragPassword.setLabelText("������");
		fragPassword.setHintText("����������");
		
		fragPasswordRepeat.setLabelText("�ظ�������");
		fragPasswordRepeat.setHintText("���ٴ�����������");
	}

	public static interface OnSubmitClickedListener {
		void onSubmitClicked();
	}

	OnSubmitClickedListener onSubmitClickedListener;

	public void setOnSubmitClickedListener(OnSubmitClickedListener onSubmitClickedListener) {
		this.onSubmitClickedListener = onSubmitClickedListener;
	}

	public void onSubmitClicked() {
		if (fragPassword.getText().equals(fragPasswordRepeat.getText())) {//���ж����������ظ��������Ƿ�һ��
			if (onSubmitClickedListener != null) {//����������Ϊ��ʱ��
				onSubmitClickedListener.onSubmitClicked();
			}
		} else {//��һ��ʱ��ʾ��ʾ�Ի���
			new AlertDialog.Builder(getActivity()).setMessage("���벻һ��").setNegativeButton("ȷ��",null).show();
		}
	}

	public String getText() {
		return fragPassword.getText();
	}

}
