package com.config;

/**
 * properties服务类
 */
public class PropertiesService {

	public static String MAIL_PATH = "/config/mail.properties";

	/**
	 * call.properties配置文件
	 * 
	 * @return
	 */
	public static PropertiesConfig getCallConfig() {
		return PropertiesConfig.getInstance("/config/call.properties");
	}

	/**
	 * job.properties配置文件
	 * 
	 * @return
	 */
	public static PropertiesConfig getJobConfig() {
		return PropertiesConfig.getInstance("/config/job.properties");
	}

	/**
	 * application.properties配置文件
	 * 
	 * @return
	 */
	public static PropertiesConfig getApplicationConfig() {
		return PropertiesConfig.getInstance("/config/application.properties");
	}

	/**
	 * mail.properties配置文件
	 * 
	 * @return
	 */
	public static PropertiesConfig getMailConfig() {
		return PropertiesConfig.getInstance(MAIL_PATH);
	}

}
