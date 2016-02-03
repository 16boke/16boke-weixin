package com.business.article.model;

import java.io.Serializable;
import java.util.Date;

public class Article implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;

	private String title;

	private String description;

	private String cover;

	private Integer cancomment;

	private Integer commentcount;

	private Integer browsecount;

	private Integer uvcount;

	private Integer lovecount;

	private Integer recommend;

	private Date inserttime;

	private Date updatetime;

	private Long userid;

	private String detail;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title == null ? null : title.trim();
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover == null ? null : cover.trim();
	}

	public Integer getCancomment() {
		return cancomment;
	}

	public void setCancomment(Integer cancomment) {
		this.cancomment = cancomment;
	}

	public Integer getCommentcount() {
		return commentcount;
	}

	public void setCommentcount(Integer commentcount) {
		this.commentcount = commentcount;
	}

	public Integer getBrowsecount() {
		return browsecount;
	}

	public void setBrowsecount(Integer browsecount) {
		this.browsecount = browsecount;
	}

	public Integer getLovecount() {
		return lovecount;
	}

	public void setLovecount(Integer lovecount) {
		this.lovecount = lovecount;
	}

	public Date getInserttime() {
		return inserttime;
	}

	public void setInserttime(Date inserttime) {
		this.inserttime = inserttime;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail == null ? null : detail.trim();
	}

	public Integer getRecommend() {
		return recommend;
	}

	public void setRecommend(Integer recommend) {
		this.recommend = recommend;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getUvcount() {
		return uvcount;
	}

	public void setUvcount(Integer uvcount) {
		this.uvcount = uvcount;
	}

}