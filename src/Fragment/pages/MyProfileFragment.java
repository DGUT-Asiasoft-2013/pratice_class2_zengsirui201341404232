package Fragment.pages;

import java.io.IOException;

import com.example.helloworld.R;
import com.example.helloworld.User;
import com.example.helloworld.entity.Server;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MyProfileFragment extends Fragment {
	View view;

	private TextView tv;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (view==null){
			view = inflater.inflate(R.layout.fragment_page_my_profile, null);
			tv=(TextView) view.findViewById(R.id.account_mes);
		}
       
		return view;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		// �����ͻ���
		OkHttpClient client = Server.getOkHttpClient();
		// ���û���¼����Ϣ�����������б�Ǻõ�String������
//		MultipartBody.Builder body = new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart("num", num)
//				.addFormDataPart("password", password);
		// ��������
		/*
		 * Request request = new Request.Builder()
		 * .url("http://172.27.0.5:8080/membercenter/api/login")
		 * .post(body.build()) .build();
		 */
		Request request = Server.requestuildApi("me")
				//.post(body.build())
				.build();
		// �ͻ��˷���һ������newCall������Ȼ��enqueue()��ȥ���У����Callback()���ͻ����ӵĳɹ�������Ϣ
		client.newCall(request).enqueue(new Callback() {
			
			@Override
			public void onResponse(Call arg0, final Response arg1) throws IOException {
				getActivity().runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						final User user;
						ObjectMapper objectMapper=new ObjectMapper();
						try {
							user = objectMapper.readValue(arg1.body().string(), User.class);
							tv.setText("wwnwnnwnwnw");
							//tv.setText(user.getName()+","+user.getAccount());
						} catch (JsonParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (JsonMappingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
				});
			}
			
			@Override
			public void onFailure(Call arg0, IOException arg1) {
				
				Toast.makeText(getActivity(), "����", Toast.LENGTH_SHORT).show();
			}
		});
	}

/*	client.newCall(request).enqueue(new Callback() {
		
		@Override
		public void onResponse(final Call arg0, final Response arg1) throws IOException {
			try{
				final User user;
				user = new ObjectMapper().readValue(arg1.body().string(), User.class);
				//MyProfileFragment.this.onResponse(arg0,user);
				getActivity().runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						tv.setText("�����û����˺ź����룺"+user.getAccount()+","+user.getPasswordHash());
						
					}
				});
			}catch(final Exception e){
				getActivity().runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						MyProfileFragment.this.OnFailure(arg0,e);
					}
				});
			}
			
			
			final User user;
			user = new ObjectMapper().readValue(arg1.body().string(), User.class);
		    getActivity().runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					tv.setText("�����û����˺ź����룺"+user.getAccount()+","+user.getPasswordHash());
				}
			});
		}
		
		@Override
		public void onFailure(final Call arg0, final IOException arg1) {
			getActivity().runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					MyProfileFragment.this.OnFailure(arg0,arg1);
					
				}
			});
			
		}
	});
	}

	protected void onResponse(Call arg0, User user) {

		tv.setText("�����û����˺ź����룺"+user.getAccount()+","+user.getPasswordHash());
	}

	protected void OnFailure(Call arg0, Exception e) {

	}*/
	}
