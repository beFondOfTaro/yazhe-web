package xyz.yazhe.yazheweb.service.domain.blog.VO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;
import xyz.yazhe.yazheweb.service.domain.user.auth.VO.UserVO;

import java.util.Date;
import java.util.List;

/**
 * @author Yazhe
 * Created at 21:30 2019/1/15
 */
@Data
@JsonInclude(Include.NON_NULL)
public class ArticleCommentVo {

	private Integer id;

	private Integer articleId;

	/**
	 * 子评论
	 */
	private List<ArticleCommentVo> childrenComments;

	private String content;

	private UserVO createUser;

	private Date createTime;
}
