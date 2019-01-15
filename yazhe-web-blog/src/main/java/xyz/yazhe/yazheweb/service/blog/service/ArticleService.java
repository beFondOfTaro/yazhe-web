package xyz.yazhe.yazheweb.service.blog.service;

import com.github.pagehelper.PageInfo;
import xyz.yazhe.yazheweb.service.domain.blog.RO.ArticleCommentRo;
import xyz.yazhe.yazheweb.service.domain.blog.RO.ArticleRO;
import xyz.yazhe.yazheweb.service.domain.blog.VO.ArticleCommentVo;
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

	/**
	 * 分页查询文章列表
	 * @param articleRO
	 * @return
	 */
	PageInfo<ArticleVO> getArticleList(ArticleRO articleRO);

	/**
	 * 点赞文章
	 * @param articleId
	 */
	void praiseArticle(Integer articleId);

	/**
	 * 获取最新评论
	 * @param articleCommentRo
	 * @return
	 */
	PageInfo<ArticleCommentVo> getCommentByCondition(ArticleCommentRo articleCommentRo);
}
