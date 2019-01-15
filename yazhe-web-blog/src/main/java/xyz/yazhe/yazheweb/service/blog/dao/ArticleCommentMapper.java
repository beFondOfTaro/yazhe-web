package xyz.yazhe.yazheweb.service.blog.dao;

import xyz.yazhe.yazheweb.service.domain.blog.DO.ArticleComment;
import xyz.yazhe.yazheweb.service.domain.blog.RO.ArticleCommentRo;
import xyz.yazhe.yazheweb.service.domain.blog.VO.ArticleCommentVo;

import java.util.List;

public interface ArticleCommentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ArticleComment record);

    int insertSelective(ArticleComment record);

    ArticleComment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ArticleComment record);

    int updateByPrimaryKey(ArticleComment record);

	/**
	 * 根据条件查询评论
	 * @param articleCommentRo
	 * @return
	 */
	List<ArticleCommentVo> queryCommentByCondition(ArticleCommentRo articleCommentRo);
}