package xyz.yazhe.yazheweb.service.user.auth.service.impl;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.yazhe.yazheweb.service.domain.base.QueryPage;
import xyz.yazhe.yazheweb.service.domain.common.constants.ResourceConstants;
import xyz.yazhe.yazheweb.service.domain.common.constants.exception.ResourceExceptionEnum;
import xyz.yazhe.yazheweb.service.domain.exception.ResourceException;
import xyz.yazhe.yazheweb.service.domain.exception.SystemException;
import xyz.yazhe.yazheweb.service.domain.user.auth.DO.User;
import xyz.yazhe.yazheweb.service.domain.user.auth.DO.UserAuth;
import xyz.yazhe.yazheweb.service.domain.user.auth.DO.UserRole;
import xyz.yazhe.yazheweb.service.domain.user.auth.VO.UserVO;
import xyz.yazhe.yazheweb.service.domain.user.auth.RO.UserRO;
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
    public UserVO getUserById(String userId) {
        UserVO userVO = userMapper.getUserById(userId);
        if (userVO == null){
            throw new ResourceException(ResourceExceptionEnum.RESOURCE_NOT_FOUND, ResourceConstants.USER);
        }
        return userVO;
    }

    @Override
    public List<UserVO> listUser(QueryPage queryPage) {
        PageHelper.startPage(queryPage);
        return userMapper.listUser();
    }

    @Override
    public String getPasswordByUserId(String userId) {
        return userAuthMapper.getPasswordByUserId(userId);
    }

    @Override
    public void addUser(UserRO userRO) throws SystemException {
		String userId = insertUser(userRO);
		insertUserAuthTable(userRO, userId);
		insertUserRole(userRO, userId);
	}

	/**
	 * 写入用户角色
	 * @param userRO
	 * @param userId 用户id
	 */
	private void insertUserRole(UserRO userRO, String userId) throws SystemException {
		UserRole userRole = new UserRole();
		userRole.setId(KeyUtil.genUniqueKey());
		userRole.setUserId(userId);
		userRole.setRoleId(userRO.getRoleId());
		if (1 != userRoleMapper.insertUserRole(userRole)){
			throw new SystemException("插入user_role表失败");
		}
	}

	/**
	 * 用户身份认证信息表写入
	 * @param userRO
	 * @param userId
	 */
	private void insertUserAuthTable(UserRO userRO, String userId) throws SystemException {
		UserAuth userAuth = new UserAuth();
		userAuth.setId(KeyUtil.genUniqueKey());
		userAuth.setUserId(userId);
		userAuth.setIdentifyType(0);
		userAuth.setIdentifier(userRO.getUsername());
		userAuth.setCredential(userRO.getPassword());
		if (1 != userMapper.insertUserAuth(userAuth)){
			throw new SystemException("插入user_auth表失败");
		}
	}

	/**
	 * 用户信息表写入
	 * @param userRO
	 * @return 用户id
	 */
	private String insertUser(UserRO userRO) throws SystemException {
		User user = new User();
		BeanUtils.copyProperties(userRO,user);
		String userId = KeyUtil.genUniqueKey();
		user.setId(userId);
		if ( null == user.getAddress()){
			user.setAddress("");
		}
		if (1 != userMapper.insertUser(user)){
			throw new SystemException("插入user表失败");
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
