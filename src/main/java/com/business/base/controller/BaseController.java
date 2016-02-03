package com.business.base.controller;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.business.base.vo.PageModel;
import com.mail.ThreadService;

/**
 * controller基类
 * 
 * @param <T>
 */
public abstract class BaseController<T> {

	/**
	 * 统一的异常处理
	 * 
	 * @param request
	 * @param ex
	 * @return
	 */
	@ExceptionHandler
	public void exp(Exception ex, HttpServletRequest request) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ex.printStackTrace(new PrintStream(baos));
		String exception = baos.toString();
		ThreadService.sendErrorMail(exception);
		ex.printStackTrace();
	}

	/**
	 * 处理查询集合过程，主要是被子类重写
	 * 
	 * @param page
	 * @return
	 */
	protected List<? extends T> prepareListData(PageModel<T> page) {
		return new ArrayList<T>();
	}

	protected Map<String, Object> createModel(String type) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("active", type);
		return model;
	}

	protected Map<String, Object> createModel(String type, String subType) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("active", type);
		model.put("subType", subType);
		return model;
	}

	/**
	 * 装载分页参数
	 * 
	 * @param pageModel
	 * @param request
	 */
	protected void installParam(PageModel<? extends T> pageModel, HttpServletRequest request) {
		Map<String, Object> params = new HashMap<String, Object>();
		Enumeration<?> enu = request.getParameterNames();
		while (enu.hasMoreElements()) {
			String name = (String) enu.nextElement();
			params.put(name, request.getParameter(name));
		}
		pageModel.setParams(params);
	}
}
