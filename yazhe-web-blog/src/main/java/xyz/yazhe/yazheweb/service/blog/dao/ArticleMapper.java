package xyz.yazhe.yazheweb.service.blog.dao;

import xyz.yazhe.yazheweb.service.domain.blog.DO.Article;
import xyz.yazhe.yazheweb.service.domain.blog.RO.ArticleRO;
import xyz.yazhe.yazheweb.service.domain.blog.VO.ArticleVO;

import java.util.List;

public interface ArticleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Article record);

    int insertSelective(Article record);

    Article selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Article record);

    int updateByPrimaryKey(Article record);

	/**
	 * 逻辑删除
	 * @param id 主键
	 * @return
	 */
	int logicalDelete(Integer id);

	/**
	 * 根据id查询文章信息
	 * @param id
	 * @return
	 */
	ArticleVO getArticleVOById(Integer id);

	/**
	 * 根据条件查询文章列表
	 * @param articleRO
	 * @return
	 */
	List<ArticleVO> getArticleList(ArticleRO articleRO);

	/**
	 * 点赞量加一
	 * @param articleId
	 * @return
	 */
	int praiseClicksInc(Integer articleId);

	/**
	 * 阅读量加一
	 * @param articleId
	 * @return
	 */
	int readingAmountInc(Integer articleId);
}