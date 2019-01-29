package xyz.yazhe.yazheweb.service.blog.dao;

import xyz.yazhe.yazheweb.service.blog.bean.entity.ArticleFileInfo;

public interface ArticleFileInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ArticleFileInfo record);

    int insertSelective(ArticleFileInfo record);

    ArticleFileInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ArticleFileInfo record);

    int updateByPrimaryKey(ArticleFileInfo record);
}