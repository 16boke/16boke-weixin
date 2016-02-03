package com.business.article.vo;

import java.io.Serializable;

public class ArticleVo implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;

	private String title;

	private String description;

	private String cover;

	private String detail;

	private Integer cancomment;

	private Integer commentcount;

	private Integer browsecount;

	private Integer uvcount;

	private Integer lovecount;

	private Integer recommend;

	private String updatetime;

	private String updateTimeCNDate;

	private String updateTimeCNTime;

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
		this.title = title;
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

	public String getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}

	public Integer getRecommend() {
		return recommend;
	}

	public void setRecommend(Integer recommend) {
		this.recommend = recommend;
	}

	public String getUpdateTimeCNDate() {
		return updateTimeCNDate;
	}

	public void setUpdateTimeCNDate(String updateTimeCNDate) {
		this.updateTimeCNDate = updateTimeCNDate;
	}

	public String getUpdateTimeCNTime() {
		return updateTimeCNTime;
	}

	public void setUpdateTimeCNTime(String updateTimeCNTime) {
		this.updateTimeCNTime = updateTimeCNTime;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
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
