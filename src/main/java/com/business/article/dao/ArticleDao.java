package com.business.article.dao;

import java.util.LinkedList;
import java.util.List;

import com.business.article.model.Article;
import com.business.base.vo.PageModel;

public interface ArticleDao {
    int deleteByPrimaryKey(Long id)throws Exception;

    int insert(Article record)throws Exception;

    int insertSelective(Article record)throws Exception;

    Article selectByPrimaryKey(Long id);
    
    Article getPreArticle(Long id);
    
    Article getNextArticle(Long id);

    int updateByPrimaryKeySelective(Article record);

    int updateByPrimaryKeyWithBLOBs(Article record);

    int updateByPrimaryKey(Article record);

	List<Article> selectAll();

	List<Article> getArticleList(PageModel<Article> page);

	List<Article> getTop10Article();

	LinkedList<Article> getTop5Article();

	int updateArticleBrowse(long articleId, int browsecount);

	List<Integer> getArticleIds();

	int updateArticleUv(long articleId, int uvcount);

	int updateArticleComment(String articleId, int comment);
}