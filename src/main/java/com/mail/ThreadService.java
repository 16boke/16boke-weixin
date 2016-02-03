package com.mail;

/**
 * 线程池暴露接口
 */
public class ThreadService {

	/**
	 * 发邮件
	 * 
	 * @param title
	 *            邮件的题目
	 * @param theme
	 *            邮件的主题
	 * @param msg
	 *            邮件的信息
	 * @param file
	 *            邮件的模板文件
	 */
	public static void sendMail(String title, String msg, String file) {
		ThreadPool pool = ThreadPool.getInstance();
		pool.threadToPool(new MailThread(title, msg, file));
	}

	public static void sendErrorMail(String content) {
		ThreadPool pool = ThreadPool.getInstance();
		pool.threadToPool(new MailThread("系统异常", content, "exception.ftl"));
	}

}
