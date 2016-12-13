package Fragment.pages;

import java.io.IOException;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.example.helloworld.Article;
import com.example.helloworld.FeedContentActivity;
import com.example.helloworld.Page;
import com.example.helloworld.R;
import com.example.helloworld.entity.Server;
import com.example.helloworld.view.AvatarView;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FeedListFragment extends Fragment {

	View view;
	ListView listView;// ��listViewȡ����

	List<Article> data = new ArrayList<Article>();
	int page = 0;
	// String[] data;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.fragment_page_feed_list, null);

			listView = (ListView) view.findViewById(R.id.list);

			listView.setAdapter(listAdapter);

			listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					onItemClicked(position);
				}
			});
			// listView.setAdapter(listAdapter);

			// Random rand = new Random();
			// data = new String[20 + rand.nextInt() % 20];
			//
			// for (int i = 0; i < data.length; i++) {
			// data[i] = "THIS IS ROW" + rand.nextInt();
			// }
		}
		return view;

	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		load();
	}

	// public void onfeedSelected(int position) {
	// String content = data[position]; // ����б��ַ�
	// Intent intent = new Intent(getActivity(), FeedContentActivity.class);//
	// Fragmentͨ������getActivity()�������õ�����Ƭ������Ļʵ��
	// // ���ַ�����intent����ȥ
	// intent.putExtra("cont", content);
	//
	// startActivity(intent);
	// }

	BaseAdapter listAdapter = new BaseAdapter() {// ������

		@SuppressLint("InflateParams")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = null;

			if (convertView == null) {
				LayoutInflater inflater = LayoutInflater.from(parent.getContext());
				view = inflater.inflate(R.layout.feed_listview_activity, null);
			} else {
				view = convertView;
			}

			// TextView text1 = (TextView)
			// view.findViewById(R.id.feed_liatview_textView);
			// Article article = data.get(position);
			// // text1.setText(data[position]);
			// text1.setText(article.getAuthorImage()+":"+article.getAuthorName()
			// + ":" + article.getText());
			TextView textContent = (TextView) view.findViewById(R.id.text);
			TextView textTitle = (TextView) view.findViewById(R.id.title);
			TextView textAuthorName = (TextView) view.findViewById(R.id.username);
			TextView textDate = (TextView) view.findViewById(R.id.date);
			AvatarView avatar = (AvatarView) view.findViewById(R.id.avatar);

			Article article = data.get(position);

			textContent.setText(article.getText());
			textTitle.setText(article.getTitle());
			textAuthorName.setText(article.getAuthorName());
			avatar.load(Server.serverAddress + article.getAuthorImage());

			String dateStr = DateFormat.format("yyyy-MM-dd hh:mm", article.getCreateDate()).toString();
			textDate.setText(dateStr);

			return view;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			// return data[position];
			return data.get(position);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return data == null ? 0 : data.size();// ��dataΪ��ʱ������0�������򷵻�data.length��listview
		}
	};

	void onItemClicked(int position) {
		Article article = data.get(position);

		String text = article.getAuthorName() + ":" + article.getText();
		Intent itnt = new Intent(getActivity(), FeedContentActivity.class);
		itnt.putExtra("text", text);

		startActivity(itnt);
	}

	public void load() {
		// ��ÿͻ���
		OkHttpClient client = Server.getOkHttpClient();
		// �������
		Request request = Server.requestuildApi("feeds").get().build();

		// ��������
		client.newCall(request).enqueue(new Callback() {

			@Override
			public void onResponse(Call arg0, Response arg1) throws IOException {
				final Article article;
				try {
					Page<Article> page;
					ObjectMapper objectMapper = new ObjectMapper();
					// �ѽ����������û����ݷŽ�article��
					page = objectMapper.readValue(arg1.body().string(), new TypeReference<Page<Article>>() {
					});

					// �ѽ���������ҳ������Feed_Fragment
					FeedListFragment.this.page = page.getNumber();
					// �ѽ������������ݴ���list
					FeedListFragment.this.data = page.getContent();
					getActivity().runOnUiThread(new Runnable() {

						@Override
						public void run() {
							// ˢ��
							listAdapter.notifyDataSetInvalidated();

						}
					});
				} catch (final Exception e) {
					getActivity().runOnUiThread(new Runnable() {

						@Override
						public void run() {
							new AlertDialog.Builder(getActivity()).setTitle("ʧ��").setMessage(e.toString()).show();
						}
					});
				}

			}

			@Override
			public void onFailure(Call arg0, final IOException arg1) {
				getActivity().runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						new AlertDialog.Builder(getActivity())
						.setMessage(arg1.getMessage())
						.show();
						
					}
				});

			}
		});
	}
}