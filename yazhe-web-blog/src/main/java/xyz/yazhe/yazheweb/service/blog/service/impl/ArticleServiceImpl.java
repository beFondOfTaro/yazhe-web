package xyz.yazhe.yazheweb.service.blog.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.yazhe.yazheweb.service.blog.dao.ArticleMapper;
import xyz.yazhe.yazheweb.service.blog.service.ArticleService;
import xyz.yazhe.yazheweb.service.domain.blog.DO.Article;
import xyz.yazhe.yazheweb.service.domain.blog.RO.ArticleRO;
import xyz.yazhe.yazheweb.service.domain.blog.VO.ArticleVO;
import xyz.yazhe.yazheweb.service.util.web.RequestUtil;

/**
 * @author Yazhe
 * Created at 15:36 2018/12/14
 */
@Service
public class ArticleServiceImpl implements ArticleService {

	@Autowired
	private ArticleMapper articleMapper;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void addArticle(ArticleRO articleRO) {
		String currentUserId = RequestUtil.getCurrentUserId();
		Article article = new Article();
		article.setTitle(articleRO.getTitle());
		article.setContent(articleRO.getContent());
		article.setCreatedUserId(currentUserId);
		article.setOriginalAuthorId(currentUserId);
		articleMapper.insertSelective(article);
	}

	@Override
	public void deleteArticle(Integer articleId) {
		articleMapper.logicalDelete(articleId);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void updateArticle(ArticleRO articleRO) {
		Article article = new Article();
		article.setId(articleRO.getId());
		article.setTitle(articleRO.getTitle());
		article.setContent(articleRO.getContent());
		articleMapper.updateByPrimaryKeySelective(article);
	}

	@Override
	public ArticleVO getArticle(Integer articleId) {
		return articleMapper.getArticleVOById(articleId);
	}

	@Override
	public PageInfo<ArticleVO> getArticleList(ArticleRO articleRO) {
		PageHelper.startPage(articleRO.getQueryPage().toPageHelperParam());
		return new PageInfo<>(articleMapper.getArticleList(articleRO));
	}
}
