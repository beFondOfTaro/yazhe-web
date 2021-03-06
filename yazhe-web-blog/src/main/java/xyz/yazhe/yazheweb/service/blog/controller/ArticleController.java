package xyz.yazhe.yazheweb.service.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xyz.yazhe.yazheweb.service.blog.service.ArticleService;
import xyz.yazhe.yazheweb.service.domain.base.QueryPage;
import xyz.yazhe.yazheweb.service.domain.base.ResultVO;
import xyz.yazhe.yazheweb.service.domain.base.validation.group.BlogValidatedGroup.AddArticle;
import xyz.yazhe.yazheweb.service.domain.base.validation.group.BlogValidatedGroup.GetArticleList;
import xyz.yazhe.yazheweb.service.domain.base.validation.group.BlogValidatedGroup.AddArticleComment;
import xyz.yazhe.yazheweb.service.domain.base.validation.group.BlogValidatedGroup.UpdateArticle;
import xyz.yazhe.yazheweb.service.domain.blog.RO.ArticleCommentRo;
import xyz.yazhe.yazheweb.service.domain.blog.RO.ArticleRO;
import xyz.yazhe.yazheweb.service.domain.common.constants.FileInfoType;
import xyz.yazhe.yazheweb.service.domain.exception.VerificationException;
import xyz.yazhe.yazheweb.service.util.web.result.ResultVOUtil;

import javax.validation.constraints.Pattern;
import java.util.List;

/**
 * 设备
 * @author Yazhe
 * Created at 16:54 2018/12/14
 */
@RestController
@RequestMapping("/article")
public class ArticleController {

	@Autowired
	private ArticleService articleService;

	/**
	 * 添加文章
	 * @param articleRO
	 * @return
	 */
	@PostMapping("/add-article")
	public ResultVO addArticle(@RequestBody @Validated(AddArticle.class) ArticleRO articleRO){
		articleService.addArticle(articleRO);
		return ResultVOUtil.success();
	}

	/**
	 * 删除文章
	 * @param articleId 文章id
	 */
	@DeleteMapping("/delete-article/{articleId}")
	public ResultVO deleteArticle(@PathVariable @Pattern(regexp = "[0-9]+") Integer articleId){
		articleService.deleteArticle(articleId);
		return ResultVOUtil.success();
	}

	/**
	 * 更新文章
	 * @param articleRO
	 */
	@PutMapping("/update-article")
	public ResultVO updateArticle(@RequestBody @Validated(UpdateArticle.class) ArticleRO articleRO){
		articleService.updateArticle(articleRO);
		return ResultVOUtil.success();
	}

	/**
	 * 根据id获取文章
	 * @param articleId
	 */
	@GetMapping("/get-article-by-id/{articleId}")
	public ResultVO getArticle(@PathVariable @Pattern(regexp = "[0-9]+") Integer articleId){
		return ResultVOUtil.success(articleService.getArticle(articleId));
	}

	/**
	 * 分页查询文章列表
	 * @param articleRO
	 * @return
	 */
	@PostMapping("/get-article-list")
	public ResultVO getArticleList(@RequestBody @Validated(GetArticleList.class) ArticleRO articleRO){
		return ResultVOUtil.success(articleService.getArticleList(articleRO));
	}

	/**
	 * 点赞文章
	 * @param articleId
	 * @return
	 */
	@PostMapping("/praise-article/{articleId}")
	public ResultVO praiseArticle(@PathVariable Integer articleId){
		articleService.praiseArticle(articleId);
		return ResultVOUtil.success();
	}

	/**
	 * 根据条件查询评论
	 * @param articleCommentRo
	 * @return
	 */
	@PostMapping("/query-comment-by-condition")
	public ResultVO getCommentByCondition(@RequestBody ArticleCommentRo articleCommentRo) throws VerificationException {
		QueryPage.validParam(articleCommentRo.getQueryPage());
		return ResultVOUtil.success(articleService.getCommentByCondition(articleCommentRo));
	}

	/**
	 * 添加评论，回复评论则需要评论id，回复文章需要文章id
	 * @param articleCommentRo
	 * @return
	 */
	@PostMapping("/add-article-comment")
	public ResultVO addArticleComment(@Validated(AddArticleComment.class)
										  @RequestBody ArticleCommentRo articleCommentRo) throws VerificationException {
		if (articleCommentRo.getArticleId()==null && articleCommentRo.getToCommentId()==null){
			throw new VerificationException("文章id和被回复的评论id不能同时为空");
		}
		if (articleCommentRo.getArticleId()!=null && articleCommentRo.getToCommentId()!=null){
			throw new VerificationException("文章id和被回复的评论id不能同时传递");
		}
		articleService.addComment(articleCommentRo);
		return ResultVOUtil.success();
	}

	/**
	 * 上传文章图片
	 * @param multipartFileList
	 * @return
	 * @throws VerificationException
	 */
	@PostMapping("/upload-article-picture")
	public ResultVO uploadArticlePicture(@RequestParam("file") List<MultipartFile> multipartFileList) throws VerificationException {
		for (MultipartFile file : multipartFileList){
			if (!FileInfoType.IMAGE.verifySuffix(file.getOriginalFilename())){
				throw new VerificationException("不支持的文件类型");
			}
		}
		return ResultVOUtil.success(articleService.uploadArticlePicture(multipartFileList));
	}

}
