package xyz.yazhe.yazheweb.service.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.yazhe.yazheweb.service.blog.service.ArticleService;
import xyz.yazhe.yazheweb.service.domain.base.ResultVO;
import xyz.yazhe.yazheweb.service.domain.blog.RO.ArticleRO;
import xyz.yazhe.yazheweb.service.util.web.result.ResultVOUtil;

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
	public ResultVO addArticle(@RequestBody ArticleRO articleRO){
		articleService.addArticle(articleRO);
		return ResultVOUtil.success();
	}

	/**
	 * 删除文章
	 * @param articleId 文章id
	 */
	@DeleteMapping("/delete-article/{articleId}")
	public ResultVO deleteArticle(@PathVariable Integer articleId){
		articleService.deleteArticle(articleId);
		return ResultVOUtil.success();
	}

	/**
	 * 更新文章
	 * @param articleRO
	 */
	@PutMapping("/update-article")
	public ResultVO updateArticle(@RequestBody ArticleRO articleRO){
		articleService.updateArticle(articleRO);
		return ResultVOUtil.success();
	}

	/**
	 * 根据id获取文章
	 * @param articleId
	 */
	@GetMapping("/get-article-by-id/{articleId}")
	public ResultVO getArticle(@PathVariable Integer articleId){
		return ResultVOUtil.success(articleService.getArticle(articleId));
	}

	/**
	 * 分页查询文章列表
	 * @param articleRO
	 * @return
	 */
	@PostMapping("/get-article-list")
	public ResultVO getArticleList(@RequestBody ArticleRO articleRO){
		return ResultVOUtil.success(articleService.getArticleList(articleRO));
	}
}
