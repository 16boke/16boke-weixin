package com.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;

import com.pojo.Constant;

public class StringUtil {

	public static Boolean isEmpty(String targetStr) {
		return (targetStr == null || targetStr.trim().length() <= 0 || targetStr.equalsIgnoreCase("undefined") || targetStr
				.equalsIgnoreCase("null"));
	}

	public static String upperFirstLetter(String targetStr) {
		String firstLetter = targetStr.substring(0, 1);
		return targetStr.replaceFirst(firstLetter, firstLetter.toUpperCase());
	}

	public static String lowerFirstLetter(String targetStr) {
		String firstLetter = targetStr.substring(0, 1);
		return targetStr.replaceFirst(firstLetter, firstLetter.toLowerCase());
	}

	public static String formatCommaExpression(String targetStr) {
		if (targetStr.length() > 0) {
			return targetStr.substring(0, targetStr.length() - 1);
		}
		return targetStr;
	}

	public static String formatList(List<String> list) {
		String result = "";
		for (String value : list) {
			result += value + ",";
		}
		return formatCommaExpression(result);
	}

	public static String convertToJson(Object object) {
		String[] excludes = new String[] { "id", "timestamp", "updateTime" };
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(excludes);
		return JSONSerializer.toJSON(object, jsonConfig).toString();
	}

	public static String convertToJson(Object object, String[] excludes) {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(excludes);
		return JSONSerializer.toJSON(object, jsonConfig).toString();
	}

	public static boolean isLong(String str) {
		if (isEmpty(str))
			return false;
		boolean isLong = false;
		try {
			Long.parseLong(str);
			isLong = true;
		} catch (Exception e) {
		}
		return isLong;
	}

	public static int strToInt(Object obj) {
		if (isEmpty(String.valueOf(obj)))
			return 0;
		try {
			return Integer.parseInt(String.valueOf(obj));
		} catch (Exception e) {
			return 0;
		}
	}

	public static String nullToString(Object str) {
		if (str == null)
			return "";
		return String.valueOf(str).trim();
	}

	/**
	 * 把日期List转化成SQL子语句.
	 * 
	 * @param dateList
	 * @return
	 */
	public static String getSqlWhereForDateList(List<String> dateList) {
		String sqlWhere = "'-1'";
		for (String date : dateList) {
			sqlWhere += ", '" + date + "'";
		}
		return sqlWhere;
	}

	/**
	 * 将结果转化为json
	 * 
	 * @param code
	 * @param msg
	 * @return
	 */
	public static String convertToJson(String code, String msg) {
		JSONObject jsonObject = JSONObject.fromObject("{}");
		jsonObject.put("code", code);
		jsonObject.put("msg", msg);
		return jsonObject.toString();
	}

	/**
	 * 将结果转化为json
	 * 
	 * @param code
	 * @param msg
	 * @return
	 */
	public static String convertToErrorJson() {
		JSONObject jsonObject = JSONObject.fromObject("{}");
		jsonObject.put("code", Constant.ERROR);
		jsonObject.put("msg", "操作失败");
		return jsonObject.toString();
	}

	/**
	 * 将结果转化为json
	 * 
	 * @param code
	 * @param msg
	 * @return
	 */
	public static String convertToSuccessJson() {
		JSONObject jsonObject = JSONObject.fromObject("{}");
		jsonObject.put("code", Constant.SUCCESS);
		jsonObject.put("msg", "操作成功");
		return jsonObject.toString();
	}

	public static String urlEncode(String str) {
		try {
			str = URLEncoder.encode(str, "UTF-8");
			return str;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return str;
		}
	}

	public static String delHTMLTag(String htmlStr) {
		String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式
		String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式
		String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式

		Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
		Matcher m_script = p_script.matcher(htmlStr);
		htmlStr = m_script.replaceAll(""); // 过滤script标签

		Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
		Matcher m_style = p_style.matcher(htmlStr);
		htmlStr = m_style.replaceAll(""); // 过滤style标签

		Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
		Matcher m_html = p_html.matcher(htmlStr);
		htmlStr = m_html.replaceAll(""); // 过滤html标签

		return htmlStr.trim(); // 返回文本字符串
	}
}
