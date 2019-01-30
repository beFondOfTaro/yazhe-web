package xyz.yazhe.yazheweb.service.domain.blog.RO;

import lombok.Data;
import xyz.yazhe.yazheweb.service.domain.base.QueryPage;
import xyz.yazhe.yazheweb.service.domain.base.validation.group.BlogValidatedGroup;
import xyz.yazhe.yazheweb.service.domain.base.validation.group.BlogValidatedGroup.AddArticle;
import xyz.yazhe.yazheweb.service.domain.base.validation.group.BlogValidatedGroup.UpdateArticle;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 文章，request对象
 * @author Yazhe
 * Created at 15:25 2018/12/14
 */
@Data
public class ArticleRO {

	@NotNull(message = "文章id不能为空",groups = {UpdateArticle.class})
	private Integer id;

	/**
	 * 标题
	 */
	@NotNull(message = "文章标题不能为空",groups = {AddArticle.class,UpdateArticle.class})
	private String title;

	/**
	 * 正文
	 */
	@NotNull(message = "正文内容不能为空",groups = {AddArticle.class,UpdateArticle.class})
	private String content;

	@Valid
	private QueryPage queryPage;

	/**
	 * 上传的文件id
	 */
	private List<String> fileIds;

}
