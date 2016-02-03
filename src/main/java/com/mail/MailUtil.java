package com.mail;

import java.io.FileInputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.config.PropertiesService;

/**
 * 加载mail.properties配置文件
 */
public class MailUtil {
	protected static final Log log = LogFactory.getLog(MailUtil.class);
	private static final String SETTING_MAIL_PATH = PropertiesService.MAIL_PATH;
	public static final String MAIL_SMTP_PORT = "mail.smtp.port";
	public static final String MAIL_SMTP_HOST = "mail.smtp.host";
	public static final String MAIL_SMTP_AUTH = "mail.smtp.auth";
	public static final String MAIL_USERNAME = "mail.username";
	public static final String MAIL_PASSWORD = "mail.password";
	public static final String MAIL_FROM = "mail.from";
	public static final String MAIL_TOLIST = "mail.toList";
	private Properties props = new Properties();

	public MailUtil() {
		loadFile();
	}

	private void loadFile() {
		FileInputStream fis = null;
		String settingFilePath = "";
		try {
			System.out.println(SETTING_MAIL_PATH);
			settingFilePath = Thread.currentThread().getContextClassLoader().getResource(SETTING_MAIL_PATH).getPath();
			System.out.println(settingFilePath);
			fis = new FileInputStream(settingFilePath);
			props.load(fis);
		} catch (Exception e) {
			log.error("Fail to load properties from file \"" + settingFilePath + "\"", e);
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (Exception e) {
					log.error(e);
				}
			}
		}
	}

	public String getProperty(String key) {
		return props.getProperty(key);
	}

}
