package Fragment.pages;

import java.io.IOException;

import com.example.helloworld.R;
import com.example.helloworld.User;
import com.example.helloworld.entity.Server;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import com.example.helloworld.view.AvatarView;

public class MyProfileFragment extends Fragment {
	View view;

	TextView tv;
	ProgressBar progress;
	AvatarView avatar;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.fragment_page_my_profile, null);
			tv = (TextView) view.findViewById(R.id.account_mes);// �û�����Ϣ
			progress = (ProgressBar) view.findViewById(R.id.progress);// ������
			avatar = (AvatarView) view.findViewById(R.id.avatar);// �û�ͷ��
		}

		return view;
	}

	@Override
	public void onResume() {
		super.onResume();

		tv.setVisibility(View.GONE);
		progress.setVisibility(View.VISIBLE);

		// ���û���¼����Ϣ�����������б�Ǻõ�String������
		// MultipartBody.Builder body = new
		// MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart("num",
		// num)
		// .addFormDataPart("password", password);
		// ��������
		/*
		 * Request request = new Request.Builder()
		 * .url("http://172.27.0.5:8080/membercenter/api/login")
		 * .post(body.build()) .build();
		 */

		// �����ͻ���
		OkHttpClient client = Server.getOkHttpClient();

		Request request = Server.requestuildApi("me").method("get", null).build();
		// �ͻ��˷���һ������newCall������Ȼ��enqueue()��ȥ���У����Callback()���ͻ����ӵĳɹ�������Ϣ
		client.newCall(request).enqueue(new Callback() {
			@Override
			public void onResponse(final Call arg0, Response arg1) throws IOException {
				try {
					final User user;
					ObjectMapper objectMapper = new ObjectMapper();
					user = objectMapper.readValue(arg1.body().string(), User.class);
					getActivity().runOnUiThread(new Runnable() {

						@Override
						public void run() {
							MyProfileFragment.this.onResponse(arg0, user);

						}
					});
				} catch (final Exception e) {
					getActivity().runOnUiThread(new Runnable() {

						@Override
						public void run() {
							MyProfileFragment.this.onFailuer(arg0, e);

						}
					});
				}

			}

			@Override
			public void onFailure(final Call arg0, final IOException arg1) {
				getActivity().runOnUiThread(new Runnable() {

					@Override
					public void run() {
						MyProfileFragment.this.onFailuer(arg0, arg1);

					}
				});
			}

		});
	}

	protected void onFailuer(Call arg0, Exception e) {
		progress.setVisibility(View.GONE);
		tv.setVisibility(View.VISIBLE);
		tv.setTextColor(Color.RED);
		tv.setText(e.getMessage());

	}

	protected void onResponse(Call arg0, User user) {
		progress.setVisibility(View.GONE);
		avatar.load(user);
		tv.setVisibility(View.VISIBLE);
		tv.setTextColor(Color.BLACK);
		tv.setText("Hello," + user.getName());

	}
}
