package com.business.article.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.business.article.model.Article;
import com.business.article.service.ArticleService;
import com.business.base.controller.BaseController;
import com.business.base.vo.PageModel;

@Controller
@RequestMapping("/article")
public class ArticleController extends BaseController<Article>{
	@Autowired
	private ArticleService articleService;

	@RequestMapping("")
	public ModelAndView index(@ModelAttribute("page") PageModel<Article> page) throws Exception {
		Map<String, Object> map = createModel("article","index");
		return new ModelAndView("article/index",map);
	}

	@RequestMapping(value = "/detail/{id}")
	public ModelAndView detail(@PathVariable Long id) throws Exception {
		Map<String, Object> map = new HashMap<String,Object>();
		if (id != null && id.longValue() > 0) {
			Article article = this.articleService.getArticleById(id,true);
			map.put("article", article);
		}
		return new ModelAndView("article/detail", map);
	}
}
