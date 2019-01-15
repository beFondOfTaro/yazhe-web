package xyz.yazhe.yazheweb.service.domain.blog.VO;

import lombok.Data;
import xyz.yazhe.yazheweb.service.domain.user.auth.VO.UserVO;

import java.util.Date;

/**
 * @author Yazhe
 * Created at 21:30 2019/1/15
 */
@Data
public class ArticleCommentVo {

	private Integer id;

	private Integer articleId;

	private UserVO toUser;

	private String content;

	private UserVO createUser;

	private Date createTime;
}
