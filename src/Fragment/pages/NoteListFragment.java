package Fragment.pages;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.example.helloworld.Article;
import com.example.helloworld.Comment;
import com.example.helloworld.FeedContentActivity;
import com.example.helloworld.Page;
import com.example.helloworld.R;
import com.example.helloworld.entity.Server;
import com.example.helloworld.view.AvatarView;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract.Contacts.Data;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class NoteListFragment extends Fragment {
View view;
ListView noteList;
int page = 0;
	
List<Comment> data=new ArrayList<Comment>();
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (view==null){
			view = inflater.inflate(R.layout.fragment_page_note_list, null);
			noteList=(ListView) view.findViewById(R.id.note_listy);
			
			noteList.setAdapter(noteListAdapter);
		}

		return view;
	}


/*	protected void onItemClicked(int position) {
		Comment comment = data.get(position);

		String text = comment.getAuthor() + ":" + comment.getText();
		Intent itnt = new Intent(getActivity(), FeedContentActivity.class);
//		itnt.putExtra("text", text);

		itnt.putExtra("data", comment);
		startActivity(itnt);
		*/

	
	
	BaseAdapter noteListAdapter=new BaseAdapter() {
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = null;

			if (convertView == null) {
				LayoutInflater inflater = LayoutInflater.from(parent.getContext());
				view = inflater.inflate(R.layout.activity_list_comment, null);
			} else {
				view = convertView;
			}
			
			TextView textContent = (TextView) view.findViewById(R.id.text);//��������
			TextView textAuthorName = (TextView) view.findViewById(R.id.username);//�����˵�����
			TextView textDate = (TextView) view.findViewById(R.id.date);//����ʱ��
			AvatarView avatar = (AvatarView) view.findViewById(R.id.avatar);//����ͷ��

			Comment comment = data.get(position);

			textContent.setText(comment.getText());
			textAuthorName.setText(comment.getAuthor().getName());
			avatar.load(Server.serverAddress + comment.getAuthor().getAvatar());

			String dateStr = DateFormat.format("yyyy-MM-dd hh:mm", comment.getCreateDate()).toString();
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
			return data.get(position);
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return data == null ? 0 : data.size();// ��dataΪ��ʱ������0�������򷵻�data.length��listview
		}
	};
	
	@Override
		public void onResume() {
			// TODO Auto-generated method stub
			super.onResume();
			load();
		}

	public void load() {
		// ��ÿͻ���
		OkHttpClient client = Server.getOkHttpClient();
		// �������
		Request request = Server.requestuildApi("comments").get().build();

		// ��������
		client.newCall(request).enqueue(new Callback() {

			@Override
			public void onResponse(Call arg0, Response arg1) throws IOException {
				try {
					final Page<Comment> pageab;
					ObjectMapper objectMapper = new ObjectMapper();
					// �ѽ����������û����ݷŽ�article��
					pageab = objectMapper.readValue(arg1.body().string(), new TypeReference<Page<Comment>>() {
					});

					getActivity().runOnUiThread(new Runnable() {

						@Override
						public void run() {
							// �ѽ���������ҳ������Feed_Fragment
							page = pageab.getNumber();
							// �ѽ������������ݴ���list
							data= pageab.getContent();
							// ˢ��
							noteListAdapter.notifyDataSetInvalidated();

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
	