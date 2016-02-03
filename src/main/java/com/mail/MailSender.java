package com.mail;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import freemarker.template.TemplateException;

public class MailSender {
	private static final Log log = LogFactory.getLog(MailSender.class);
	/**
	 * 以文本格式发送邮件
	 * @param mailInfo
	 *       待发送的邮件的信息
	 */
	public boolean sendTextMail(MailSenderInfo mailInfo) {
		// 判断是否需要身份认证
		MyAuthenticator authenticator = null;
		Properties pro = mailInfo.getProperties();
		if (mailInfo.isValidate()) {
			// 如果需要身份认证，则创建一个密码验证器
			authenticator = new MyAuthenticator(mailInfo.getUserName(),mailInfo.getPassword());
		}
		// 根据邮件会话属性和密码验证器构造一个发送邮件的session
		Session sendMailSession = Session.getDefaultInstance(pro, authenticator);
		try {
			// 根据session创建一个邮件消息
			Message mailMessage = new MimeMessage(sendMailSession);
			setMailMsg(mailMessage, mailInfo);
			
			// 设置邮件消息的主题
			mailMessage.setSubject(mailInfo.getSubject());
			// 设置邮件消息的主要内容
			mailMessage.setText(mailInfo.getContent());
			
			// 发送邮件
			Transport.send(mailMessage);
			return true;
		} catch (MessagingException ex) {
			log.error("发送邮件失败",ex);
		}
		return false;
	}
	/**
	 * 以HTML格式发送邮件
	 * @param mailInfo
	 *            待发送的邮件信息
	 */
	public boolean sendHtmlMail(MailSenderInfo mailInfo) {
		// 判断是否需要身份认证
		MyAuthenticator authenticator = null;
		Properties pro = mailInfo.getProperties();
		// 如果需要身份认证，则创建一个密码验证器
		if (mailInfo.isValidate()) {
			authenticator = new MyAuthenticator(mailInfo.getUserName(),mailInfo.getPassword());
		}
		// 根据邮件会话属性和密码验证器构造一个发送邮件的session
		Session sendMailSession = Session.getDefaultInstance(pro, authenticator);
		try {
			// 根据session创建一个邮件消息
			Message mailMessage = new MimeMessage(sendMailSession);
			setMailMsg(mailMessage, mailInfo);
			
			// 设置邮件消息的主题
			mailMessage.setSubject(mailInfo.getSubject());
			// MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象
			
			Multipart mainPart = new MimeMultipart();
			// 创建一个包含HTML内容的MimeBodyPart
			BodyPart html = new MimeBodyPart();
			// 设置HTML内容
			html.setContent(mailInfo.getContent(), "text/html; charset=utf-8");
			mainPart.addBodyPart(html);
			// 将MiniMultipart对象设置为邮件内容
			mailMessage.setContent(mainPart);
			mailMessage.saveChanges(); 
			
			// 发送邮件
			Transport.send(mailMessage);
			return true;
		} catch (MessagingException ex) {
			log.error("发送邮件失败",ex);
		}
		return false;
	}

	private void setMailMsg(Message mailMessage, MailSenderInfo mailInfo) throws MessagingException{
		// 创建邮件发送者地址
		Address from = new InternetAddress(mailInfo.getFromAddress());
		// 设置邮件消息的发送者
		mailMessage.setFrom(from);
		// 创建邮件的接收者地址，并设置到邮件消息中
		int toSize = mailInfo.getToAddressList().size();
		Address[] toS = new Address[toSize];
		for(int i=0; i<toSize; i++){
			toS[i] = new InternetAddress(mailInfo.getToAddressList().get(i) );
		}
		mailMessage.setRecipients(Message.RecipientType.TO, toS);
		// 设置邮件消息发送的时间
		mailMessage.setSentDate(new Date());			
	}
	
	public MailSenderInfo createMailSenderInfo(String title, String content){
		return createMailSenderInfo(title, content, null);
	}
	
	public MailSenderInfo createMailSenderInfo(String title, String content, String mailToList){
		 MailSenderInfo mailInfo = new MailSenderInfo();
		 MailUtil mu = new MailUtil();
	     mailInfo.setMailServerHost(mu.getProperty(MailUtil.MAIL_SMTP_HOST));    
	     mailInfo.setMailServerPort(mu.getProperty(MailUtil.MAIL_SMTP_PORT));    
	     mailInfo.setValidate(Boolean.parseBoolean(mu.getProperty(MailUtil.MAIL_SMTP_AUTH)));    
	     mailInfo.setUserName(mu.getProperty(MailUtil.MAIL_USERNAME));    
	     mailInfo.setPassword(mu.getProperty(MailUtil.MAIL_PASSWORD));//您的邮箱密码    
	     mailInfo.setFromAddress(mu.getProperty(MailUtil.MAIL_FROM));
	     String toS = null;
	     if (mailToList != null) {
	    	 toS = mu.getProperty(mailToList);
	     }
	     if (toS == null || toS.equals("")) {
	    	 toS = mu.getProperty(MailUtil.MAIL_TOLIST);
	     }
	     List<String> toList = Arrays.asList(toS.split(";"));
	     mailInfo.setToAddressList(toList);  
	     
	     mailInfo.setSubject(title);
	     mailInfo.setContent(content);
		return mailInfo;
	}
	
	public void sendTextEmail(String title, String content){
		 MailSenderInfo mailInfo = createMailSenderInfo(title, content);
	     sendTextMail(mailInfo);//发送文体格式    
	}
	
	public void sendHtmlMail(String title, String content){
		 MailSenderInfo mailInfo = createMailSenderInfo(title, content);
		 sendHtmlMail(mailInfo);//发送文体格式    
	}
	
	public void sendHtmlMail(String title, Map<String, Object> data, String file){
		try {
			String content = FreeMakerUtil.loadContent(file, data);
			log.info(content);
			String criusType = "";
			if(data.get("criusType")!=null){
				criusType = "【"+String.valueOf(data.get("criusType"))+"."+String.valueOf(data.get("env"))+"环境】";
			}
			sendHtmlMail(criusType+title, content);
			log.info(title + "，已经发送一封邮件");
		} catch (Exception e) {
			log.error(title+"，邮件发送失败");
			log.error(e.getMessage(), e);
		}
	}
	
	public void sendForgetMail(String content){
		//这个类主要来发送邮件
		String email = "forget_pass.ftl";
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("title", "找回密码");
		data.put("content", content);
		try {
			content = FreeMakerUtil.loadContent(email, data);
			MailSender sms = new MailSender();   
			sms.sendHtmlMail("找回密码", content);//发送文体格式    
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
	}
	public void sendSignMail(String content){
		//这个类主要来发送邮件
		String email = "sign_in.ftl";
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("title", "用户激活");
		data.put("content", content);
		try {
			content = FreeMakerUtil.loadContent(email, data);
			MailSender sms = new MailSender();   
			sms.sendHtmlMail("用户激活", content);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws Exception{
        //这个类主要来发送邮件   
		String email = "forget_pass.ftl";
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("id", 1231321);
		data.put("name", "test");
		data.put("adUrl", "asfdas");
		String content = FreeMakerUtil.loadContent(email, data);
		MailSender sms = new MailSender();   
		sms.sendHtmlMail("测试", content);//发送文体格式    
    }
}
