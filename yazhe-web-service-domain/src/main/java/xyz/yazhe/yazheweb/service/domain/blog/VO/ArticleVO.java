package xyz.yazhe.yazheweb.service.domain.blog.VO;

import java.util.Date;

/**
 * 文章
 * @author Yazhe
 * Created at 15:00 2018/12/14
 */
public class ArticleVO {

	private Integer id;

	/**
	 * 创建该文章的用户id
	 */
	private String createdUserId;

	/**
	 * 创建该文章的用户名
	 */
	private String createdUsername;

	/**
	 * 标题
	 */
	private String title;

	/**
	 * 正文
	 */
	private String content;

	/**
	 * 阅读量
	 */
	private Long readingAmount;

	/**
	 * 点赞量
	 */
	private Long praiseClicks;

	/**
	 * 是否原创
	 */
	private Boolean original;

	/**
	 * 原作者id
	 */
	private String originalAuthorId;

	/**
	 * 原作者用户名
	 */
	private String originalAuthorUsername;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 修改时间
	 */
	private Date updateTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCreatedUserId() {
		return createdUserId;
	}

	public void setCreatedUserId(String createdUserId) {
		this.createdUserId = createdUserId;
	}

	public String getCreatedUsername() {
		return createdUsername;
	}

	public void setCreatedUsername(String createdUsername) {
		this.createdUsername = createdUsername;
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

	public Long getReadingAmount() {
		return readingAmount;
	}

	public void setReadingAmount(Long readingAmount) {
		this.readingAmount = readingAmount;
	}

	public Long getPraiseClicks() {
		return praiseClicks;
	}

	public void setPraiseClicks(Long praiseClicks) {
		this.praiseClicks = praiseClicks;
	}

	public Boolean getOriginal() {
		return original;
	}

	public void setOriginal(Boolean original) {
		this.original = original;
	}

	public String getOriginalAuthorId() {
		return originalAuthorId;
	}

	public void setOriginalAuthorId(String originalAuthorId) {
		this.originalAuthorId = originalAuthorId;
	}

	public String getOriginalAuthorUsername() {
		return originalAuthorUsername;
	}

	public void setOriginalAuthorUsername(String originalAuthorUsername) {
		this.originalAuthorUsername = originalAuthorUsername;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public String toString() {
		return "ArticleVO{" +
				"id=" + id +
				", createdUserId='" + createdUserId + '\'' +
				", createdUsername='" + createdUsername + '\'' +
				", title='" + title + '\'' +
				", content='" + content + '\'' +
				", readingAmount=" + readingAmount +
				", praiseClicks=" + praiseClicks +
				", original=" + original +
				", originalAuthorId='" + originalAuthorId + '\'' +
				", originalAuthorUsername='" + originalAuthorUsername + '\'' +
				", createTime=" + createTime +
				", updateTime=" + updateTime +
				'}';
	}
}
