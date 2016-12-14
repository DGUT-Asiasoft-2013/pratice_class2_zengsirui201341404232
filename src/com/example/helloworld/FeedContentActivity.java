package com.example.helloworld;

import java.util.List;

import org.w3c.dom.Comment;

import com.example.helloworld.view.AvatarView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract.Data;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class FeedContentActivity extends Activity {
	Article article;

	List<Comment> q;

	// private TextView tv;//声明一个TextView控件
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_feed_content);

		//String text = getIntent().getStringExtra("text");

		ListView list = (ListView) findViewById(R.id.list);

		article = (Article) getIntent().getSerializableExtra("data");

		TextView textView = (TextView) findViewById(R.id.text);
		textView.setText(article.getAuthorName()+":"+article.getText());
		
		
		/*View headerView = LayoutInflater.from(this).inflate(R.layout.feed_listview_activity, null);
		TextView textContent = (TextView) headerView.findViewById(R.id.text);
		TextView textTitle = (TextView) headerView.findViewById(R.id.title);
		TextView textAuthorName = (TextView) headerView.findViewById(R.id.username);
		TextView textDate = (TextView) headerView.findViewById(R.id.date);
		AvatarView avatar = (AvatarView) headerView.findViewById(R.id.avatar);

		textContent.setText(article.getText());
		textTitle.setText(article.getTitle());
		textAuthorName.setText(article.getAuthor().getName());
		avatar.load(article.getAuthor());

		String dateStr =DateFormat.format("yyyy-MM-dd hh:mm", article.getCreateDate()).toString();
		textDate.setText(dateStr);
		
		list.addHeaderView(headerView, null, false);*/

		findViewById(R.id.btn_comment).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				goComment();
			}
		});
	}
	
	

	protected void goComment() {
		Intent intent = new Intent(FeedContentActivity.this, CommentContentActivity.class);
		intent.putExtra("data", article);
		startActivity(intent);
		overridePendingTransition(R.anim.slide_in_bottom, R.anim.none);

	}
}
