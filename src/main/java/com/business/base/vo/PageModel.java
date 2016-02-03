/******************************************************************************************************
 * Project Name: ad-common
 * File Name: PageModel.java
 * Creation Date: 2013年9月6日下午4:44:41
 * 
 * Copyright (c) 2013-, Funshion Online Technologies Ltd. All Rights Reserved.
 * 版权 2013-，北京风行在线技术有限公司  所有版权保护
 *
 * This is UNPUBLISHED PROPRIETARY SOURCE CODE of Funshion Online Technologies Ltd.;
 * The content of this file can not be disclosed to third parties, by copied or duplicated in any
 * form, in whole or in part, without the prior written permission of Funshion Online Technologies Ltd.
 * 这是北京风行在线技术有限公司未公开的私有源代码。本文件及相关内容未经风行在线技术有 限公司事先书面同意，不允许通过任何手段泄露给第三方机构.
 /******************************************************************************************************/

package com.business.base.vo;

import java.util.List;
import java.util.Map;

/**
 * Description:分页参数
 * 
 * @author yaorf
 * @since version
 */
public class PageModel<T> {

	private Integer pageNo = 1;// 页数（默认第一页）

	private Integer pageSize = 10;// 每页显示数据条数（默认10）

	private String order;// 排序条件

	private String sort;// 排序顺序(desc asc)

	private Integer totalRecord;// 总记录数

	private Integer totalPage;// 总页数

	private Map<String, Object> params;// 参数条件

	private List<? extends T> results;// 分页结果

	private Boolean isIntercept = true;// 是否拦截

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(Integer totalRecord) {
		this.totalRecord = totalRecord;
		setTotalPage(totalRecord % pageSize == 0 ? totalRecord / pageSize : totalRecord / pageSize + 1);
	}

	public Integer getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}

	public List<? extends T> getResults() {
		return results;
	}

	public void setResults(List<? extends T> list) {
		this.results = list;
	}

	public Boolean getIsIntercept() {
		return isIntercept;
	}

	public void setIsIntercept(Boolean isIntercept) {
		this.isIntercept = isIntercept;
	}

}
