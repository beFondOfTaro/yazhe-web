package xyz.yazhe.yazheweb.service.domain.user.auth.DTO;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 添加角色时传输的数据
 * @author BeFondOfTaro
 * Created at 22:52 2018/5/17
 */
@Data
public class RoleAddDTO {

    /**
     * 角色名
     */
    @NotNull
    private String name;

    /**
     * 权限id列表
     */
    private List<String> permissionIdList;
}
