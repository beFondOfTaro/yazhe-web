package xyz.yazhe.yazheweb.service.user.auth.service.impl;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.yazhe.yazheweb.service.domain.base.QueryPage;
import xyz.yazhe.yazheweb.service.domain.common.constants.ResourceConstants;
import xyz.yazhe.yazheweb.service.domain.common.constants.exception.ResourceExceptionEnum;
import xyz.yazhe.yazheweb.service.domain.common.constants.exception.ResultEnum;
import xyz.yazhe.yazheweb.service.domain.exception.CommonException;
import xyz.yazhe.yazheweb.service.domain.exception.ResourceException;
import xyz.yazhe.yazheweb.service.domain.user.auth.DO.User;
import xyz.yazhe.yazheweb.service.domain.user.auth.DO.UserAuth;
import xyz.yazhe.yazheweb.service.domain.user.auth.DO.UserRole;
import xyz.yazhe.yazheweb.service.domain.user.auth.DTO.UserDTO;
import xyz.yazhe.yazheweb.service.domain.user.auth.DTO.UserRegisterDTO;
import xyz.yazhe.yazheweb.service.user.auth.dao.UserAuthMapper;
import xyz.yazhe.yazheweb.service.user.auth.dao.UserMapper;
import xyz.yazhe.yazheweb.service.user.auth.dao.UserRoleMapper;
import xyz.yazhe.yazheweb.service.user.auth.service.UserService;
import xyz.yazhe.yazheweb.service.util.KeyUtil;

import java.util.List;

/**
 * @author BeFondOfTaro
 * Created at 13:05 2018/5/14
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private UserAuthMapper userAuthMapper;

    @Override
    public UserDTO getUserById(String userId) {
        UserDTO userDTO = userMapper.getUserById(userId);
        if (userDTO == null){
            throw new ResourceException(ResourceExceptionEnum.RESOURCE_NOT_FOUND, ResourceConstants.USER);
        }
        return userDTO;
    }

    @Override
    public List<UserDTO> listUser(QueryPage queryPage) {
        PageHelper.startPage(queryPage);
        return userMapper.listUser();
    }

    @Override
    public String getPasswordByUserId(String userId) {
        return userAuthMapper.getPasswordByUserId(userId);
    }

    @Override
    public void addUser(UserRegisterDTO userRegisterDTO) {
		String userId = insertUser(userRegisterDTO);
		insertUserAuthTable(userRegisterDTO, userId);
		insertUserRole(userRegisterDTO, userId);
	}

	/**
	 * 写入用户角色
	 * @param userRegisterDTO
	 * @param userId 用户id
	 */
	private void insertUserRole(UserRegisterDTO userRegisterDTO, String userId) {
		UserRole userRole = new UserRole();
		userRole.setId(KeyUtil.genUniqueKey());
		userRole.setUserId(userId);
		userRole.setRoleId(userRegisterDTO.getRoleId());
		if (1 != userRoleMapper.insertUserRole(userRole)){
			throw new CommonException(ResultEnum.UNKNOWN_ERROR);
		}
	}

	/**
	 * 用户身份认证信息表写入
	 * @param userRegisterDTO
	 * @param userId
	 */
	private void insertUserAuthTable(UserRegisterDTO userRegisterDTO, String userId) {
		UserAuth userAuth = new UserAuth();
		userAuth.setId(KeyUtil.genUniqueKey());
		userAuth.setUserId(userId);
		userAuth.setIdentifyType(0);
		userAuth.setIdentifier(userRegisterDTO.getUsername());
		userAuth.setCredential(userRegisterDTO.getPassword());
		if (1 != userMapper.insertUserAuth(userAuth)){
			throw new CommonException(ResultEnum.UNKNOWN_ERROR);
		}
	}

	/**
	 * 用户信息表写入
	 * @param userRegisterDTO
	 * @return 用户id
	 */
	private String insertUser(UserRegisterDTO userRegisterDTO) {

		User user = new User();
		BeanUtils.copyProperties(userRegisterDTO,user);
		String userId = KeyUtil.genUniqueKey();
		user.setId(userId);
		if (1 != userMapper.insertUser(user)){
			throw new CommonException(ResultEnum.UNKNOWN_ERROR);
		}
		return userId;
	}

	@Override
    public void updateUserRole(String userId, List<String> roleIdList) {
        //删除原来的角色
        userRoleMapper.deleteUserRoleByUserId(userId);
        //添加要更新的角色
        UserRole userRole = new UserRole();
        for (String roleId : roleIdList){
            userRole.setId(KeyUtil.genUniqueKey());
            userRole.setUserId(userId);
            userRole.setRoleId(roleId);
            userRoleMapper.insertUserRole(userRole);
        }
    }

    @Override
    public void deleteUserById(String userId) {
        userMapper.deleteUserById(userId);
        userMapper.deleteUserAuthByUserId(userId);
        userRoleMapper.deleteUserRoleByUserId(userId);
    }
}
