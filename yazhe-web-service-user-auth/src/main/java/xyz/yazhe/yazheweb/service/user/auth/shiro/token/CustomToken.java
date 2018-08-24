package xyz.yazhe.yazheweb.service.user.auth.shiro.token;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * @author Yazhe
 * Created at 17:00 2018/8/24
 */
public class CustomToken extends UsernamePasswordToken {

	/**
	 * 时间戳
	 */
	private long timestamp;

	/**
	 * 身份认证类型，0：用户名密码登录，1：微信登录
	 */
	private Integer identifyType;

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public Integer getIdentifyType() {
		return identifyType;
	}

	public void setIdentifyType(Integer identifyType) {
		this.identifyType = identifyType;
	}

	public CustomToken(String username, String password, long timestamp, Integer identifyType) {
		super(username, password);
		this.timestamp = timestamp;
		this.identifyType = identifyType;
	}
}
