package xyz.yazhe.yazheweb.service.domain.user.auth.RO;

import org.hibernate.validator.constraints.Email;
import xyz.yazhe.yazheweb.service.domain.base.QueryPage;
import xyz.yazhe.yazheweb.service.domain.base.validation.group.UserValidatedGroup;
import xyz.yazhe.yazheweb.service.domain.base.validation.group.UserValidatedGroup.AddUser;
import xyz.yazhe.yazheweb.service.domain.base.validation.group.UserValidatedGroup.ListUser;
import xyz.yazhe.yazheweb.service.domain.base.validation.group.UserValidatedGroup.RegisterAccount;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author Yazhe
 * Created at 22:17 2018/12/24
 */
public class UserRO {
	/**
	 * 用户名
	 */
	@NotNull(message = "用户名不能为空",groups = {AddUser.class, RegisterAccount.class})
	private String username;

	/**
	 * 密码
	 */
	@NotNull(message = "密码不能为空",groups = {AddUser.class, RegisterAccount.class})
	private String password;

	/**
	 * 真实姓名
	 */
	@NotNull(message = "姓名不能为空",groups = {AddUser.class, RegisterAccount.class})
	private String realName;

	/**
	 * 邮箱
	 */
	@Email(groups = {AddUser.class, RegisterAccount.class})
	private String email;

	/**
	 * 电话
	 */
	@NotNull(message = "电话不能为空",groups = {AddUser.class, RegisterAccount.class})
	private String phone;

	/**
	 * 地址
	 */
	@Pattern(regexp = "[\\u2E80-\\u9FFFa-zA-Z0-9_,，-]+", groups = {AddUser.class, RegisterAccount.class})
	private String address;

	/**
	 * 角色id
	 */
	@NotNull(message = "角色不能为空",groups = {AddUser.class})
	private String roleId;

	@NotNull(message = "分页参数不能为空", groups = {ListUser.class})
	@Valid
	private QueryPage queryPage;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public QueryPage getQueryPage() {
		return queryPage;
	}

	public void setQueryPage(QueryPage queryPage) {
		this.queryPage = queryPage;
	}

	@Override
	public String toString() {
		return "UserRO{" +
				"username='" + username + '\'' +
				", password='" + password + '\'' +
				", realName='" + realName + '\'' +
				", email='" + email + '\'' +
				", phone='" + phone + '\'' +
				", address='" + address + '\'' +
				", roleId='" + roleId + '\'' +
				", queryPage=" + queryPage +
				'}';
	}
}
