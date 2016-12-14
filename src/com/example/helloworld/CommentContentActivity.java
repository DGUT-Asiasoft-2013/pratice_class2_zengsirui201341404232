package com.example.helloworld;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.example.helloworld.entity.Server;
import com.example.helloworld.view.AvatarView;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CommentContentActivity extends Activity{
	EditText edit;
	Article article;
	int page1=0;
	
	ListView listView;
	List<Comment> data=new ArrayList<Comment>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feed_comment);
		
		listView = (ListView)findViewById(R.id.comment_list);
		
		listView.setAdapter(listAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				onItemClicked(position);
				
			}
		});
		
		edit=(EditText) findViewById(R.id.edit);
		
		article=(Article)getIntent().getSerializableExtra("data");
		
		findViewById(R.id.btn_send).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				sendContent();
				
			}
		});
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		load();
	}
	
	private void load() {
	OkHttpClient client=Server.getOkHttpClient();
	
	Request request=Server.requestuildApi("article/"+article.getId()+"/comments").build();
		
	client.newCall(request).enqueue(new Callback() {
		
		@Override
		public void onResponse(Call arg0, Response arg1) throws IOException {
			final Comment comment;
			try{
				Page1<Comment> page1;
				ObjectMapper objectMapper=new ObjectMapper();
				String ab=arg1.body().string();
				page1=objectMapper.readValue(ab,new TypeReference<Page1<Comment>>() {
				});
				
				CommentContentActivity.this.page1=page1.getNumber();
				
				CommentContentActivity.this.data=page1.getContent();
				CommentContentActivity.this.runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						listAdapter.notifyDataSetInvalidated();
						
					}
				});
			}catch (final Exception e) {
				CommentContentActivity.this.runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						new AlertDialog.Builder(CommentContentActivity.this).setTitle("失败").setMessage(e.toString()).show();
					}
				});
			}
			
		}
		
		@Override
		public void onFailure(Call arg0, final IOException arg1) {
			CommentContentActivity.this.runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					new AlertDialog.Builder(CommentContentActivity.this).setTitle("失败").setMessage(arg1.getMessage()).show();
					
				}
			});
			
		}
	});
	}

	protected void onItemClicked(int position) {
		Comment comment = data.get(position);

		String text = comment.getAuthor() + ":" + comment.getText();
		Intent itnt = new Intent(CommentContentActivity.this, FeedContentActivity.class);
//		itnt.putExtra("text", text);

		itnt.putExtra("data", comment);
		startActivity(itnt);
	}

	BaseAdapter listAdapter=new BaseAdapter() {
		
		@SuppressLint("InflateParams")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view=null;
			
			if(convertView==null){
				LayoutInflater inflater=LayoutInflater.from(parent.getContext());
				view=inflater.inflate(R.layout.activity_list_comment, null);//把layout改掉
			}else{
				view=convertView;
			}
			
               TextView textContent=(TextView) findViewById(R.id.comment_content);
               TextView textName=(TextView) findViewById(R.id.name);
               TextView textDate=(TextView) findViewById(R.id.date);
               
               Comment comment=data.get(position);
                
               textContent.setText(comment.getText());
               textName.setText(comment.getAuthor().getName());
               
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
			return data.get(position);
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return data == null ? 0 : data.size();// 若data为空时，返回0个，否则返回data.length个listview
		}
	};

	protected void sendContent() {
		String text=edit.getText().toString();
		
		MultipartBody body=new MultipartBody.Builder()
				.addFormDataPart("text", text)
				.build();
		
		Request request=Server.requestuildApi("article/"+article.getId()+"/comments")
		.post(body)
		.build();
		
		Server.getOkHttpClient().newCall(request).enqueue(new Callback() {
			
			@Override
			public void onResponse(Call arg0, Response arg1) throws IOException {
				final String responseBody=arg1.body().string();
				
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						CommentContentActivity.this.onSucceed(responseBody);
						
					}
				});
				
			}
			
			@Override
			public void onFailure(Call arg0, final IOException arg1) {
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						CommentContentActivity.this.onFailure(arg1);
						
					}
				});
				
			}
		});
	}

	protected void onFailure(IOException arg1) {
		new AlertDialog.Builder(this).setMessage(arg1.getMessage()).show();
		
	}

	protected void onSucceed(String text) {
		new AlertDialog.Builder(this).setMessage(text)
		.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				finish();
				overridePendingTransition(R.anim.none, R.anim.slide_out_bottom);
			}
		}).show();
	}
}
