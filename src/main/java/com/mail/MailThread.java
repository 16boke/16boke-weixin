package com.mail;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.utils.DateUtil;

public class MailThread extends Thread {
	private String title;
	private String file;
	private Map<String, Object> data = new HashMap<String, Object>();

	public MailThread(String title, String msg, String file) {
		this.title = title;
		this.file = file;
		this.data.put("time",DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		this.data.put("msg", msg);
	}

	public void run() {
		MailSender sms = new MailSender();
		sms.sendHtmlMail(title, data, file);
	}

}
