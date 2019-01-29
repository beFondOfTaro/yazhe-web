package xyz.yazhe.yazheweb.service.blog.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import xyz.yazhe.yazheweb.service.blog.bean.entity.ArticleFileInfo;
import xyz.yazhe.yazheweb.service.blog.dao.ArticleCommentMapper;
import xyz.yazhe.yazheweb.service.blog.dao.ArticleFileInfoMapper;
import xyz.yazhe.yazheweb.service.blog.dao.ArticleMapper;
import xyz.yazhe.yazheweb.service.blog.service.ArticleService;
import xyz.yazhe.yazheweb.service.blog.service.FileService;
import xyz.yazhe.yazheweb.service.domain.base.FileInfoVo;
import xyz.yazhe.yazheweb.service.domain.blog.DO.Article;
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
	private ArticleFileInfoMapper articleFileInfoMapper;
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
		return new PageInfo<>(articleCommentMapper.queryCommentByCondition(articleCommentRo));
	}

	@Override
	public List<FileInfoVo> uploadArticlePicture(List<MultipartFile> multipartFileList, Integer articleId) throws VerificationException {
		//保存文件
		List<FileInfoVo> fileInfoVos = fileService.saveBatchFile(multipartFileList);
		//写入文章-文件关联信息
		fileInfoVos.parallelStream().forEach(fileInfoVo -> {
			ArticleFileInfo articleFileInfo = new ArticleFileInfo();
			articleFileInfo.setArticleId(articleId);
			articleFileInfo.setFileInfoId(fileInfoVo.getId());
			articleFileInfoMapper.insertSelective(articleFileInfo);
		});
		return fileInfoVos;
	}

}
