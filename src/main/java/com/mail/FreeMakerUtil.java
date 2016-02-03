package com.mail;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class FreeMakerUtil {

	private static Logger logger = LoggerFactory.getLogger(FreeMakerUtil.class);
	
	private static Configuration configuration = null;
	
	static {
		try {
			configuration = new Configuration();
			configuration.setDefaultEncoding("utf-8");
			String path = FreeMakerUtil.class.getResource("/templates").getFile();
			System.out.println(path);
			logger.debug(path);
			configuration.setDirectoryForTemplateLoading(new File(path));
		} catch (IOException e) {
			logger.error("freemarkder实例化出错");
		}
		configuration.setObjectWrapper(new DefaultObjectWrapper());
	}
	
	public static String loadContent(String file, Map<String, Object> values) throws IOException, TemplateException {
		Template temp = configuration.getTemplate(file);
		StringWriter w = new StringWriter();
		temp.process(values, w);
		String result = w.toString();
		w.close();
		return result;
	}
	
	public static boolean isWindow() {
		String osName  = System.getProperty("os.name"); 
		if (osName.toLowerCase().contains("window")) {
			return true;
		}
		return false;
	}
	
	public static boolean isLinux() {
		String osName  = System.getProperty("os.name"); 
		if (osName.toLowerCase().contains("linux")) {
			return true;
		}
		return false;
	}
	
	public static void main(String[] args) {
		String path = FreeMakerUtil.class.getResource("/").getFile();
		System.out.println(path);
		int index = path.indexOf("WEB-INF");
		path = path.substring(0, index);
		path = path.substring(0, path.lastIndexOf("/"));
		System.out.println(path);
		System.out.println(isWindow());
	}

}
