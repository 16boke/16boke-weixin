package com.pojo;

/**
 * 文本消息
 * 
 * @author avic
 * @date 2013-11-19
 */
public class TextMessage extends BaseMessage {
	// 消息内容
	private String Content;
	private int funcFlag;

	public int getFuncFlag() {
		return funcFlag;
	}

	public void setFuncFlag(int funcFlag) {
		this.funcFlag = funcFlag;
	}

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}
}
