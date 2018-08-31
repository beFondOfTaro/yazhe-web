package xyz.yazhe.yazheweb.service.domain.user.auth.DO;

import java.math.BigInteger;

/**
 * 网站统计
 * @author Yazhe
 * Created at 16:44 2018/8/31
 */
public class WebStatistics {

	private Integer id;

	/**
	 * 点击量
	 */
	private BigInteger clicks;

	public WebStatistics() {
	}

	public WebStatistics(Integer id, BigInteger clicks) {
		this.id = id;
		this.clicks = clicks;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public BigInteger getClicks() {
		return clicks;
	}

	public void setClicks(BigInteger clicks) {
		this.clicks = clicks;
	}
}
