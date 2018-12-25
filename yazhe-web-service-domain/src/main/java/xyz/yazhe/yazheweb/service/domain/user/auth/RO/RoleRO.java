package xyz.yazhe.yazheweb.service.domain.user.auth.RO;

import lombok.Data;
import xyz.yazhe.yazheweb.service.domain.base.QueryPage;
import xyz.yazhe.yazheweb.service.domain.base.validation.group.RoleValidatedGroup.ListRole;
import xyz.yazhe.yazheweb.service.domain.base.validation.group.RoleValidatedGroup.RoleADD;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 添加角色时传输的数据
 * @author BeFondOfTaro
 * Created at 22:52 2018/5/17
 */
@Data
public class RoleRO {

    /**
     * 角色名
     */
    @NotNull(message = "角色名不能为空",groups = {RoleADD.class})
    private String name;

    /**
     * 权限id列表
     */
	@NotNull(message = "权限列表不能为空",groups = {RoleADD.class})
    private List<String> permissionIdList;

	@NotNull(message = "分页参数不能为空",groups = {ListRole.class})
	@Valid
	private QueryPage queryPage;
}
