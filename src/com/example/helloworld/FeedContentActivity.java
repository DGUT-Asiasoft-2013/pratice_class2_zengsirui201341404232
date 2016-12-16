package com.example.helloworld;

import java.io.IOException;
import java.util.List;

import com.example.helloworld.entity.Server;
import com.example.helloworld.view.AvatarView;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.Response;

public class FeedContentActivity extends Activity {
	Article article;

	Button btn_good, comment;

	int page = 0;

	ListView listView;
	// List<Comment> data = new ArrayList<Comment>();
	List<Comment> comments;

	// private TextView tv;//声明一个TextView控件
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_feed_content);

		// String text = getIntent().getStringExtra("text");

		listView = (ListView) findViewById(R.id.comment_list);

		listView.setAdapter(listAdapter);

		article = (Article) getIntent().getSerializableExtra("data");

		TextView textView = (TextView) findViewById(R.id.text);
		textView.setText(article.getAuthorName() + ":" + article.getText());

		/*
		 * View headerView =
		 * LayoutInflater.from(this).inflate(R.layout.feed_listview_activity,
		 * null); TextView textContent = (TextView)
		 * headerView.findViewById(R.id.text); TextView textTitle = (TextView)
		 * headerView.findViewById(R.id.title); TextView textAuthorName =
		 * (TextView) headerView.findViewById(R.id.username); TextView textDate
		 * = (TextView) headerView.findViewById(R.id.date); AvatarView avatar =
		 * (AvatarView) headerView.findViewById(R.id.avatar);
		 * 
		 * textContent.setText(article.getText());
		 * textTitle.setText(article.getTitle());
		 * textAuthorName.setText(article.getAuthor().getName());
		 * avatar.load(article.getAuthor());
		 * 
		 * String dateStr =DateFormat.format("yyyy-MM-dd hh:mm",
		 * article.getCreateDate()).toString(); textDate.setText(dateStr);
		 * 
		 * list.addHeaderView(headerView, null, false);
		 */

		comment = (Button) findViewById(R.id.btn_comment);
		findViewById(R.id.btn_comment).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				goComment();
			}
		});

		btn_good = (Button) findViewById(R.id.btn_good);
		findViewById(R.id.btn_good).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				goLikes();

			}
		});

	}

	private boolean isLiked;

	void checkLiked() {
		Request request = Server.requestuildApi("article/" + article.getId() + "/isliked").get().build();
		Server.getOkHttpClient().newCall(request).enqueue(new Callback() {
			@Override
			public void onResponse(Call arg0, Response arg1) throws IOException {
				try {
					final String responseString = arg1.body().string();
					final Boolean result = new ObjectMapper().readValue(responseString, Boolean.class);

					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							onCheckLikedResult(result);
						}
					});
				} catch (final Exception e) {
					e.printStackTrace();
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							onCheckLikedResult(false);
						}
					});
				}
			}

			@Override
			public void onFailure(Call arg0, IOException e) {
				e.printStackTrace();
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						onCheckLikedResult(false);
					}
				});
			}
		});
	}

	void onCheckLikedResult(boolean result) {
		isLiked = result;
		btn_good.setTextColor(result ? Color.BLUE : Color.BLACK);
	}

	void reloadLikes() {
		Request request = Server.requestuildApi("/article/" + article.getId() + "/likes").get().build();

		Server.getOkHttpClient().newCall(request).enqueue(new Callback() {

			@Override
			public void onResponse(Call arg0, Response arg1) throws IOException {
				try {
					String responseString = arg1.body().string();
					final Integer count = new ObjectMapper().readValue(responseString, Integer.class);

					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							onReloadLikesResult(count);
						}
					});
				} catch (Exception e) {
					e.printStackTrace();
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							onReloadLikesResult(0);
						}
					});
				}
			}

			@Override
			public void onFailure(Call arg0, IOException e) {
				e.printStackTrace();
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						onReloadLikesResult(0);
					}
				});
			}
		});
	}

	void onReloadLikesResult(int count) {
		if (count > 0) {
			btn_good.setText("点赞(" + count + ")");
		} else {
			btn_good.setText("点赞");
		}
	}

	protected void goLikes() {
		MultipartBody body = new MultipartBody.Builder().addFormDataPart("likes", String.valueOf(!isLiked)).build();

		Request request = Server.requestuildApi("article/" + article.getId() + "/likes").post(body).build();

		Server.getOkHttpClient().newCall(request).enqueue(new Callback() {

			@Override
			public void onResponse(Call arg0, Response arg1) throws IOException {
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						reload();

					}
				});
			}

			@Override
			public void onFailure(Call arg0, IOException arg1) {
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						reload();

					}
				});

			}
		});

	}

	void reload() {
		reloadLikes();
		checkLiked();

		Request request = Server.requestuildApi("/article/" + article.getId() + "/comments").get().build();

		Server.getOkHttpClient().newCall(request).enqueue(new Callback() {
			@Override
			public void onResponse(Call arg0, Response arg1) throws IOException {
				String string=arg1.body().string();
				try {
					final Page<Comment> data = new ObjectMapper().readValue(string,
							new TypeReference<Page<Comment>>() {});

					Log.d("--------------------asdsfdfdfg------------", string);
					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							FeedContentActivity.this.reloadData(data);
						}
					});
				} catch (final Exception e) {
					runOnUiThread(new Runnable() {
						public void run() {
							FeedContentActivity.this.onFailure(e);
						}
					});
				}
			}

			@Override
			public void onFailure(Call arg0, final IOException e) {
				runOnUiThread(new Runnable() {
					public void run() {
						FeedContentActivity.this.onFailure(e);
					}
				});
			}
		});
	}

	protected void onFailure(Exception e) {
		// TODO Auto-generated method stub

	}

	protected void reloadData(Page<Comment> data) {
		page = data.getNumber();
		comments = data.getContent();
		listAdapter.notifyDataSetInvalidated();
	}

	protected void appendData(Page<Comment> data) {
		if (data.getNumber() > page) {
			page = data.getNumber();

			if (comments == null) {
				comments = data.getContent();
			} else {
				comments.addAll(data.getContent());
			}
		}

		listAdapter.notifyDataSetChanged();
	}

	protected void goComment() {
		Intent intent = new Intent(FeedContentActivity.this, CommentContentActivity.class);
		intent.putExtra("data", article);
		startActivity(intent);
		overridePendingTransition(R.anim.slide_in_bottom, R.anim.none);

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		reload();
	}

	/*
	 * private void load() { OkHttpClient client = Server.getOkHttpClient();
	 * 
	 * Request request = Server.requestuildApi("article/" + article.getId() +
	 * "/comments").build();
	 * 
	 * client.newCall(request).enqueue(new Callback() {
	 * 
	 * @Override public void onResponse(Call arg0, Response arg1) throws
	 * IOException { final Comment comment; try { Page1<Comment> page1;
	 * ObjectMapper objectMapper = new ObjectMapper(); String ab =
	 * arg1.body().string(); page1 = objectMapper.readValue(ab, new
	 * TypeReference<Page1<Comment>>() { });
	 * 
	 * FeedContentActivity.this.page = page1.getNumber();
	 * 
	 * FeedContentActivity.this.data = page1.getContent();
	 * FeedContentActivity.this.runOnUiThread(new Runnable() {
	 * 
	 * @Override public void run() { listAdapter.notifyDataSetInvalidated();
	 * 
	 * } }); } catch (final Exception e) {
	 * FeedContentActivity.this.runOnUiThread(new Runnable() {
	 * 
	 * @Override public void run() { new
	 * AlertDialog.Builder(FeedContentActivity.this).setTitle("失败").setMessage(e
	 * .toString()) .show(); } }); }
	 * 
	 * }
	 * 
	 * @Override public void onFailure(Call arg0, final IOException arg1) {
	 * FeedContentActivity.this.runOnUiThread(new Runnable() {
	 * 
	 * @Override public void run() { new
	 * AlertDialog.Builder(FeedContentActivity.this).setTitle("失败").setMessage(
	 * arg1.getMessage()) .show();
	 * 
	 * } });
	 * 
	 * } }); }
	 */

	BaseAdapter listAdapter = new BaseAdapter() {
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = null;

			if (convertView == null) {
				LayoutInflater inflater = LayoutInflater.from(parent.getContext());
				view = inflater.inflate(R.layout.activity_list_comment, null);
			} else {
				view = convertView;
			}
			Comment comment = comments.get(position);

			TextView textContent = (TextView) view.findViewById(R.id.text);
			TextView textAuthorName = (TextView) view.findViewById(R.id.username);
			TextView textDate = (TextView) view.findViewById(R.id.date);
			AvatarView avatar = (AvatarView) view.findViewById(R.id.avatar);

			textContent.setText(comment.getText());
			textAuthorName.setText(comment.getAuthor().getName());
			avatar.load(comment.getAuthor());

			String dateStr = DateFormat.format("yyyy-MM-dd hh:mm", comment.getCreateDate()).toString();
			textDate.setText(dateStr);

			return view;
		}

		@Override
		public long getItemId(int position) {
			return comments.get(position).getId();
		}

		@Override
		public Object getItem(int position) {
			return comments.get(position);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return comments == null ? 0 : comments.size();
		}
	};

}
