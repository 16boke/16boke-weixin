package com.pojo;

import java.util.concurrent.locks.ReentrantLock;

import com.business.article.model.Article;
import com.config.PropertiesConfig;
import com.config.PropertiesService;
import com.utils.EHCacheUtil;
import com.utils.WeixinUtil;

/**
 * 常量定义类
 * 
 * @author admin
 * 
 */
public class Constant {
	// 操作成功
	public static final String SUCCESS = "200";
	// 操作失败
	public static final String ERROR = "300";
	// 默认使用的缓存名
	public static final String CACHENAME = "cache";
	// 文章详情缓存的前缀
	public static final String ARTICLE_DETAIL_PREFIX = "ARTICLE_DETAIL_";
	// 文章浏览数缓存的前缀
	public static final String ARTICLE_BROWSE_PREFIX = "ARTICLE_BROWSER_";
	// 文章浏览数缓存名
	public static final String ARTICLE_BROWSE_CACHENAME = "articleBrowseCache";
	// 第三方用户唯一凭证
	public static String APPID = "";

	// 第三方用户唯一凭证密钥
	public static String APPSECRET = "";

	public final static AccessToken ACCESSTOKEN = WeixinUtil.getAccessToken(APPID, APPSECRET);// 调用接口获取access_token

	public final static String GET = "GET";// GET请求方式

	public final static String POST = "POST";// POST请求方式

	static {
		try {
			PropertiesConfig propertiesConfig = PropertiesService.getApplicationConfig();
			APPID = propertiesConfig.getProperty("APPID");
			APPSECRET = propertiesConfig.getProperty("APPSECRET");
		} catch (Exception e) {
			APPID = "wx417f3cbb953e74ec";
			APPSECRET = "55a9711f02cad0fa3134cb3080f28a37";
		}
	}
	private static ReentrantLock lockBrowseCount = new ReentrantLock();

	public static int getAndSaveArticleBrowse(Long articleId) {
		int browse = 0;
		try {
			lockBrowseCount.lock();
			Object browseObj = EHCacheUtil.get(Constant.ARTICLE_BROWSE_PREFIX + articleId, Constant.ARTICLE_BROWSE_CACHENAME);
			if (browseObj != null) {
				browse = Integer.parseInt(String.valueOf(browseObj)) + 1;
				EHCacheUtil.put(Constant.ARTICLE_BROWSE_PREFIX + articleId, browse, Constant.ARTICLE_BROWSE_CACHENAME);
			} else {
				// 如果此文章id详细对象存在缓存中，则直接从缓存中取对象的browsecount属性
				// 文章详情对象使用cache缓存名
				Object articleObj = EHCacheUtil.get(Constant.ARTICLE_DETAIL_PREFIX + articleId, Constant.CACHENAME);
				if (articleObj != null) {
					browse = ((Article) articleObj).getBrowsecount() + 1;
					EHCacheUtil.put(Constant.ARTICLE_BROWSE_PREFIX + articleId, browse, Constant.ARTICLE_BROWSE_CACHENAME);
				} else {
					return -1;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lockBrowseCount.unlock();
		}
		return browse;
	}

}
