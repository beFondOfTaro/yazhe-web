package xyz.yazhe.yazheweb.service.blog.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import xyz.yazhe.yazheweb.service.blog.dao.ArticleCommentMapper;
import xyz.yazhe.yazheweb.service.blog.dao.ArticleMapper;
import xyz.yazhe.yazheweb.service.blog.service.ArticleService;
import xyz.yazhe.yazheweb.service.blog.service.FileService;
import xyz.yazhe.yazheweb.service.domain.base.FileInfoVo;
import xyz.yazhe.yazheweb.service.domain.blog.DO.Article;
import xyz.yazhe.yazheweb.service.domain.blog.DO.ArticleComment;
import xyz.yazhe.yazheweb.service.domain.blog.RO.ArticleCommentRo;
import xyz.yazhe.yazheweb.service.domain.blog.RO.ArticleRO;
import xyz.yazhe.yazheweb.service.domain.blog.VO.ArticleCommentVo;
import xyz.yazhe.yazheweb.service.domain.blog.VO.ArticleVO;
import xyz.yazhe.yazheweb.service.domain.exception.VerificationException;
import xyz.yazhe.yazheweb.service.util.web.RequestUtil;

import java.util.List;

/**
 * @author Yazhe
 * Created at 15:36 2018/12/14
 */
@Service
public class ArticleServiceImpl implements ArticleService {

	@Autowired
	private ArticleMapper articleMapper;
	@Autowired
	private ArticleCommentMapper articleCommentMapper;
	@Autowired
	private FileService fileService;

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
		//阅读量增加
		articleMapper.readingAmountInc(articleId);
		//查询文章信息
		return articleMapper.getArticleVOById(articleId);
	}

	@Override
	public PageInfo<ArticleVO> getArticleList(ArticleRO articleRO) {
		PageHelper.startPage(articleRO.getQueryPage().toPageHelperParam());
		return new PageInfo<>(articleMapper.getArticleList(articleRO));
	}

	@Override
	public void praiseArticle(Integer articleId) {
		articleMapper.praiseClicksInc(articleId);
	}

	@Override
	public PageInfo<ArticleCommentVo> getCommentByCondition(ArticleCommentRo articleCommentRo) {
		PageHelper.startPage(articleCommentRo.getQueryPage().toPageHelperParam());
		List<ArticleCommentVo> articleCommentVos = articleCommentMapper.queryCommentByCondition(articleCommentRo);
		//查询子评论
		ArticleCommentRo queryChildrenCommentRo = new ArticleCommentRo();
		articleCommentVos.forEach(articleCommentVo -> {
			queryChildrenCommentRo.setToCommentId(articleCommentVo.getArticleId());
			articleCommentVo.setChildrenComments(articleCommentMapper.queryCommentByCondition(queryChildrenCommentRo));
		});
		return new PageInfo<>(articleCommentVos);
	}

	@Override
	public void addComment(ArticleCommentRo articleCommentRo) {
		ArticleComment articleComment = new ArticleComment();

		Integer toCommentId = articleCommentRo.getToCommentId();
		//如果是回复别人的评论，文章id直接从被评论的评论获取
		if (toCommentId != null){
			articleComment.setArticleId(articleCommentMapper.selectByPrimaryKey(toCommentId).getArticleId());
		}else {
			articleComment.setArticleId(articleCommentRo.getArticleId());
		}
		articleComment.setToCommentId(articleCommentRo.getToCommentId());
		articleComment.setContent(articleCommentRo.getContent());
		articleCommentMapper.insertSelective(articleComment);
	}

	@Override
	public List<FileInfoVo> uploadArticlePicture(List<MultipartFile> multipartFileList) throws VerificationException {
		//保存文件
		return fileService.saveBatchFile(multipartFileList);
	}

}
