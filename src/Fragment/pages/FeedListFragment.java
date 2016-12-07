package Fragment.pages;

import java.util.Random;

import com.example.helloworld.FeedContentActivity;
import com.example.helloworld.R;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class FeedListFragment extends Fragment {
	
	View view;
	ListView listView;// 將listView取出來

	String[] data;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.fragment_page_feed_list, null);

			listView = (ListView) view.findViewById(R.id.list);
			listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					onfeedSelected(position);
				}
			});
			listView.setAdapter(listAdapter);
			
			Random rand=new Random();
			data=new String[10+rand.nextInt()%20];
			
			for(int i=0;i<data.length;i++){
				data[i]="THIS IS ROW"+rand.nextInt();
			}
		}
		return view;
		
		
	}

	public void onfeedSelected(int position) {
		String content=data[position];  //获得列表字符
		Intent intent=new Intent(getActivity(), FeedContentActivity.class);//Fragment通过调用getActivity()方法来得到和碎片相关联的活动实例
		//把字符传入intent里面去
		intent.putExtra("cont", content);
		
		startActivity(intent);
	}

	BaseAdapter listAdapter = new BaseAdapter() {// 適配器

		@SuppressLint("InflateParams")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = null;

			if (convertView == null) {
				LayoutInflater inflater = LayoutInflater.from(parent.getContext());
				view = inflater.inflate(android.R.layout.simple_list_item_1, null);
			} else {
				view = convertView;
			}
			
			TextView text1 = (TextView) view.findViewById(android.R.id.text1);
			text1.setText(data[position]);

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
			return data[position];
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return data==null ? 0 :data.length;//若data为空时，返回0个，否则返回data.length个listview
		}
	};
}
