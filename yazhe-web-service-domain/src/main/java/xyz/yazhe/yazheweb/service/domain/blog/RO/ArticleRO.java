package xyz.yazhe.yazheweb.service.domain.blog.RO;

import xyz.yazhe.yazheweb.service.domain.base.QueryPage;

import javax.validation.Valid;

/**
 * 文章，request对象
 * @author Yazhe
 * Created at 15:25 2018/12/14
 */
public class ArticleRO {

	private Integer id;

	/**
	 * 标题
	 */
	private String title;

	/**
	 * 正文
	 */
	private String content;

	@Valid
	private QueryPage queryPage;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public QueryPage getQueryPage() {
		return queryPage;
	}

	public void setQueryPage(QueryPage queryPage) {
		this.queryPage = queryPage;
	}

	@Override
	public String toString() {
		return "ArticleRO{" +
				"id=" + id +
				", title='" + title + '\'' +
				", content='" + content + '\'' +
				", queryPage=" + queryPage +
				'}';
	}
}
