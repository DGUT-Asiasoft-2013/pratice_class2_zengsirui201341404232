package com.example.helloworld;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Page1<Comment> {
List<Comment> content;
Integer number;
public List<Comment> getContent(){
	return content;
}

public void setContent(List<Comment> content){
	this.content=content;
}

public Integer getNumber(){
	return number;
}

public void setNumber(Integer number){
	this.number=number;
}
}
