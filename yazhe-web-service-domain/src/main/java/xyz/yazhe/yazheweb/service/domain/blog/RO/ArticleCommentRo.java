package xyz.yazhe.yazheweb.service.domain.blog.RO;

import javax.validation.constraints.NotNull;

import lombok.Data;
import xyz.yazhe.yazheweb.service.domain.base.QueryPage;
import xyz.yazhe.yazheweb.service.domain.base.validation.group.BlogValidatedGroup;
import xyz.yazhe.yazheweb.service.domain.base.validation.group.BlogValidatedGroup.AddArticle;

/**
 * @author Yazhe
 * Created at 22:36 2019/1/15
 */
@Data
public class ArticleCommentRo {

	private QueryPage queryPage;

	/**
	 * 评论的正文
	 */
	@NotNull(message = "评论不能为空！", groups = {AddArticle.class})
	private String content;

	/**
	 * 文章id
	 */
	@NotNull(message = "文章id不能为空！", groups = {AddArticle.class})
	private Integer articleId;

	/**
	 * 被回复的评论id
	 */
	private Integer toCommentId;
}
