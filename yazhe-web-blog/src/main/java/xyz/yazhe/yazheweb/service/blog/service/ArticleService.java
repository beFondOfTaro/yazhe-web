package xyz.yazhe.yazheweb.service.blog.service;

import xyz.yazhe.yazheweb.service.domain.blog.RO.ArticleRO;
import xyz.yazhe.yazheweb.service.domain.blog.VO.ArticleVO;

/**
 * 文章服务
 * @author Yazhe
 * Created at 14:56 2018/12/14
 */
public interface ArticleService {

	/**
	 * 添加文章
	 * @param articleRO
	 */
	void addArticle(ArticleRO articleRO);

	/**
	 * 删除文章
	 * @param articleId 文章id
	 */
	void deleteArticle(Integer articleId);

	/**
	 * 更新文章
	 * @param articleRO
	 */
	void updateArticle(ArticleRO articleRO);

	/**
	 * 根据id获取文章
	 * @param articleId
	 * @return
	 */
	ArticleVO getArticle(Integer articleId);
}
