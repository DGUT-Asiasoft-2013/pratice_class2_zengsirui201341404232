package com.example.helloworld;

import java.io.Serializable;
import java.util.Date;

public class Comment implements Serializable {
	String text;
	User author;
	Article article;
	Date createDate;// 文章创建时间
	Date editDate;
	
	Integer id;
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public Date getEditDate() {
		return editDate;
	}

	public void setEditDate(Date editDate) {
		this.editDate = editDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}



}
