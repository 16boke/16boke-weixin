package com.business.quartz;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.business.article.service.ArticleService;
import com.pojo.Constant;
import com.utils.EHCacheUtil;

@Component
@Lazy(false)
public class ArticlePushJob {
	protected static Logger logger = LoggerFactory.getLogger(ArticlePushJob.class);
	@Autowired
	private ArticleService articleService;
	/**
	 * 定时推送文章
	 */
	@Scheduled(cron = "${16boke.article.push}")
	public void pushArticle() {
		logger.info("begin push article !");
		long beginTime = System.currentTimeMillis();
		try {
			
		} catch (Exception e) {
			logger.error("push article has error:" + e.getMessage(), e);
		}
		logger.info("end push article , time = " + (System.currentTimeMillis() - beginTime));
	}
	/**
	 * 定时同步文章浏览数
	 */
	@Scheduled(cron = "${16boke.article.browse}")
	public void saveArticleBrowse() {
		logger.info("begin save article browse!");
		long beginTime = System.currentTimeMillis();
		try {
			List<String>keys = EHCacheUtil.getKeys(Constant.ARTICLE_BROWSE_CACHENAME);
			for(String key:keys){
				if(key.replace(Constant.ARTICLE_BROWSE_PREFIX, "")!=null && EHCacheUtil.get(key, Constant.ARTICLE_BROWSE_CACHENAME)!=null){
					this.articleService.updateArticleBrowse(Long.parseLong(key.replace(Constant.ARTICLE_BROWSE_PREFIX, "")),Integer.parseInt(String.valueOf(EHCacheUtil.get(key, Constant.ARTICLE_BROWSE_CACHENAME))));
				}
			}
		} catch (Exception e) {
			logger.error("save article browse has error:" + e.getMessage(), e);
		}
		logger.info("end save article browse, time = " + (System.currentTimeMillis() - beginTime));
	}
}
