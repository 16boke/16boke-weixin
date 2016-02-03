package com.utils;

import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import com.pojo.Constant;

public class EHCacheUtil {
	private static CacheManager cacheManager = null;
	private static Cache cache = null;

	static {
		EHCacheUtil.initCacheManager();
		EHCacheUtil.initCache(Constant.CACHENAME);
	}

	/**
	 * 
	 * 初始化缓存管理容器
	 */
	public static CacheManager initCacheManager() {
		try {
			if (cacheManager == null)
				cacheManager = CacheManager.getInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cacheManager;
	}

	/**
	 * 
	 * 初始化缓存管理容器
	 * 
	 * @param path
	 *            ehcache.xml存放的路徑
	 */
	public static CacheManager initCacheManager(String path) {
		try {
			if (cacheManager == null) {
				cacheManager = CacheManager.getInstance().create(path);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cacheManager;
	}

	/**
	 * 初始化cache
	 */
	public static Cache initCache(String cacheName) {
		checkCacheManager();
		if (null == cacheManager.getCache(cacheName)) {
			cacheManager.addCache(cacheName);
		}
		cache = cacheManager.getCache(cacheName);
		return cache;
	}

	/**
	 * 
	 * 添加缓存
	 * 
	 * @param key
	 *            关键字
	 * @param value
	 *            值
	 */
	public static void put(Object key, Object value, String cacheName) {
		if(value == null)
			return;
		if (!StringUtil.isEmpty(cacheName))
			EHCacheUtil.initCache(cacheName);
		checkCache();
		// 创建Element,然后放入Cache对象中
		Element element = new Element(key, value);
		cache.put(element);
		System.out.println("放入Ehcache中成功,key = " + key + ",value = " + value.toString());
	}

	/**
	 * 获取cache
	 * 
	 * @param key
	 *            关键字
	 * @return
	 */
	public static Object get(Object key, String cacheName) {
		if (!StringUtil.isEmpty(cacheName))
			EHCacheUtil.initCache(cacheName);
		checkCache();
		Element element = cache.get(key);
		if (null == element) {
			return null;
		}
		System.out.println("从Ehcache中获取对象，key = " + key + "，value = " + element.getObjectValue());
		return element.getObjectValue();
	}

	/**
	 * 释放CacheManage
	 */

	public static void shutdown() {
		cacheManager.shutdown();
	}

	/**
	 * 移除cache
	 * 
	 * @param cacheName
	 */

	public static void removeCache(String cacheName) {
		checkCacheManager();
		cache = cacheManager.getCache(cacheName);
		if (null != cache) {
			cacheManager.removeCache(cacheName);
		}
	}

	/**
	 * 移除cache中的key
	 * 
	 * @param cacheName
	 */

	public static void remove(String key, String cacheName) {
		if (!StringUtil.isEmpty(cacheName))
			EHCacheUtil.initCache(cacheName);
		checkCache();
		cache.remove(key);
		System.out.println("删除Ehcache中 key = " + key + "的对象!");
	}

	/**
	 * 移除所有cache
	 */

	public static void removeAllCache() {
		checkCacheManager();
		cacheManager.removalAll();
	}

	/**
	 * 
	 * 移除所有Element
	 */

	public static void removeAllKey() {
		checkCache();
		cache.removeAll();
	}

	/**
	 * 
	 * 获取所有的cache名称
	 * 
	 * @return
	 */

	public static String[] getAllCaches() {
		checkCacheManager();
		return cacheManager.getCacheNames();
	}

	/**
	 * 
	 * 获取Cache所有的Keys
	 * 
	 * @return
	 */

	public static List getKeys(String cacheName) {
		if (!StringUtil.isEmpty(cacheName))
			EHCacheUtil.initCache(cacheName);
		checkCache();
		return cache.getKeys();
	}

	/**
	 * 
	 * 检测cacheManager
	 */

	private static void checkCacheManager() {
		if (null == cacheManager) {
			throw new IllegalArgumentException("调用前请先初始化CacheManager值：EHCacheUtil.initCacheManager");
		}
	}

	private static void checkCache() {
		if (null == cache) {
			throw new IllegalArgumentException("调用前请先初始化Cache值：EHCacheUtil.initCache(参数)");
		}
	}

	public static void main(String[] arg) {
		// 初始化--必须
		EHCacheUtil.initCacheManager();
		EHCacheUtil.put("A", "AAAAA", "cache");
		EHCacheUtil.put("B", "BBBBB", "cache");
		// EHCacheUtil.initCache("cache");
		List keys = EHCacheUtil.getKeys("cache");
		for (int i = 0; i < keys.size(); i++) {
			System.out.println(keys.get(i));
		}
		EHCacheUtil.shutdown();
	}
}
