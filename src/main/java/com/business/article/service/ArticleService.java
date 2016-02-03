package com.business.article.service;

import java.util.LinkedList;
import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.business.article.dao.ArticleDao;
import com.business.article.model.Article;
import com.business.article.vo.ArticleVo;
import com.business.base.vo.PageModel;
import com.pojo.Constant;
import com.utils.DateUtil;
import com.utils.EHCacheUtil;
import com.utils.StringUtil;

@Service
public class ArticleService {
	@Autowired
	private ArticleDao articleDao;

	public Article getArticleById(Long id,boolean calcArticleBrowse) throws Exception {
		Object articleObj = EHCacheUtil.get(Constant.ARTICLE_DETAIL_PREFIX + id,Constant.CACHENAME); 
		if (articleObj != null){
			if(calcArticleBrowse){
				this.calcArticleBrowse((Article) articleObj, id);
			}
			return (Article) articleObj;
		}
		Article article = this.articleDao.selectByPrimaryKey(id);
		if(article == null)
			return null;
		if(article!=null){
			EHCacheUtil.put(Constant.ARTICLE_DETAIL_PREFIX + id, article,Constant.CACHENAME);
			if(calcArticleBrowse){
				this.calcArticleBrowse(article, id);
			}
		}
		return article;
	}
	/**
	 * 计算文章的浏览数
	 * @param article
	 * @param id
	 */
	public int calcArticleBrowse(Article article,Long id){
		int browse = Constant.getAndSaveArticleBrowse(id);
		if(browse > 0){
			article.setBrowsecount(browse);
			EHCacheUtil.put(Constant.ARTICLE_DETAIL_PREFIX + id, article,Constant.CACHENAME);
		}
		return browse;
	}

	public List<Article> getAllArticle() throws Exception {
		return this.articleDao.selectAll();
	}

	public List<Article> getArticleList(PageModel<Article> page) {
		return this.articleDao.getArticleList(page);
	}

	public Object getArticleListJson(PageModel<Article> page) {
		JSONObject json = JSONObject.fromObject("{}");
		if (page == null)
			return null;
		json.put("totalPage", page.getTotalPage());
		json.put("totalRecord", page.getTotalRecord());
		json.put("pageNo", page.getPageNo());
		List<ArticleVo> results = new LinkedList<ArticleVo>();
		List list = page.getResults();
		for (int i = 0; i < list.size(); i++) {
			Article article = (Article) list.get(i);
			ArticleVo articleVo = new ArticleVo();
			articleVo.setId(article.getId());
			articleVo.setCover(article.getCover());
			if (!StringUtil.isEmpty(article.getDetail()) && StringUtil.delHTMLTag(article.getDetail().trim()).length() > 300)
				articleVo.setDetail(StringUtil.delHTMLTag(article.getDetail().trim()).substring(0, 300));
			else
				articleVo.setDetail(StringUtil.delHTMLTag(article.getDetail().trim()));
			articleVo.setDescription(article.getDescription());
			//查缓存，如果有文章浏览数的值则取缓存的值，否则取数据库中的值
			if (EHCacheUtil.get(Constant.ARTICLE_BROWSE_PREFIX + article.getId(),Constant.ARTICLE_BROWSE_CACHENAME) != null) {
				articleVo.setBrowsecount(Integer.parseInt(String.valueOf(EHCacheUtil.get(Constant.ARTICLE_BROWSE_PREFIX + article.getId(),Constant.ARTICLE_BROWSE_CACHENAME))));
			}else{
				articleVo.setBrowsecount(article.getBrowsecount());
			}
			articleVo.setCancomment(article.getCancomment());
			articleVo.setCommentcount(article.getCommentcount());
			articleVo.setLovecount(article.getLovecount());
			articleVo.setRecommend(article.getRecommend());
			articleVo.setTitle(article.getTitle());
			articleVo.setUpdatetime(DateUtil.format(article.getUpdatetime(), "yyyy-MM-dd HH:mm:ss"));
			articleVo.setUpdateTimeCNDate(DateUtil.getDateCN(article.getUpdatetime()));
			articleVo.setUpdateTimeCNTime(DateUtil.getTimeCN(article.getUpdatetime()));
			results.add(articleVo);
		}
		json.put("results", results);
		return json;
	}
	
	public void updateArticleBrowse(long articleId, int browsecount) {
		this.articleDao.updateArticleBrowse(articleId,browsecount);
	}
}
