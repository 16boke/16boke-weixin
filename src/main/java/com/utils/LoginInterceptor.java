package com.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoginInterceptor extends HandlerInterceptorAdapter {
	private static final Logger log = LoggerFactory.getLogger(LoginInterceptor.class);

	public String[] allowUrlStart;// 请求中为以下路径开头，则不进行拦截
	public String[] allowUrlMatch;// 请求中匹配以下路径，则不进行拦截

	public void setAllowUrlStart(String[] allowUrlStart) {
		this.allowUrlStart = allowUrlStart;
	}

	public void setAllowUrlMatch(String[] allowUrlMatch) {
		this.allowUrlMatch = allowUrlMatch;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		String requestUrl = request.getRequestURI().replace(request.getContextPath(), "");
		// 特殊情况"/"跳过
		if ("/".equals(requestUrl)) {
			return true;
		}

		if (null != allowUrlStart && allowUrlStart.length >= 1) {
			for (String url : allowUrlStart) {
				if (requestUrl.startsWith(url)) {
					return true;
				}
			}
		}

		if (null != allowUrlMatch && allowUrlMatch.length >= 1) {
			for (String url : allowUrlMatch) {
				if (requestUrl.equals(url)) {
					return true;
				}
			}
		}
		return true;
	}
}
