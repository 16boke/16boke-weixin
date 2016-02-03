package com.config;

import java.io.FileInputStream;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Properties;

/**
 * 读取*.properties配置文件
 * 
 * @since 2014-9-1
 */
public class PropertiesConfig {

	// 配置文件的map key:propertiesName value:PropertiesUtil对象
	private static HashMap<String, PropertiesConfig> propertiesMap = new HashMap<String, PropertiesConfig>();

	// 属性文件
	private Properties properties;

	private PropertiesConfig() {

	}

	public synchronized static PropertiesConfig getInstance(String propertiesName) {

		PropertiesConfig configUtil = propertiesMap.get(propertiesName);

		if (configUtil == null) {
			configUtil = new PropertiesConfig();
			configUtil.analysisXml(propertiesName);
			propertiesMap.put(propertiesName, configUtil);
		}

		return configUtil;
	}

	private void analysisXml(String propertiesName) {
		properties = new Properties();
		try {
			properties.load(new FileInputStream(this.getClass().getResource(propertiesName).getFile()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getProperty(String key) {
		return properties.getProperty(key);
	}

	/**
	 * 读取属性文件，如果没有找到使用默认的值
	 * 
	 * @param key
	 * @param defaultValue
	 * @author wangyc
	 * @return
	 */
	public String getProperty(String key, String defaultValue) {
		return properties.getProperty(key, defaultValue);
	}

	/**
	 * 获取带占位符的配置
	 */
	public String getProperty(String key, Object[] param) {
		String value = properties.getProperty(key);
		return MessageFormat.format(value, param);
	}

}
