package com.cqust.service.Impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cqust.enitiy.Role;
import com.cqust.enitiy.UserRole;
import com.cqust.mapper.IUserRoleMapper;
import com.cqust.service.IRoleService;
import com.cqust.service.IUserRoleService;

@Service
public class IUserRoleServiceImpl extends ServiceImpl<IUserRoleMapper,UserRole> implements IUserRoleService{
	
	@Autowired
    private IRoleService roleService;
	@Override
	public List<Role> getUserRoles(Long userId) {
	        List<Role> roles = new ArrayList<>();
	        if (userId != null) {
	            // 获取用户的所有角色
	            List<UserRole> userRoles = this.lambdaQuery()
	                    .select(UserRole::getRoleId)
	                    .eq(UserRole::getUserId, userId)
	                    .list();
	            //userRole是user-role对应表,通过遍历userRole,对角色添加,userRole:单体
	            userRoles.forEach(userRole -> {
	                Role role = roleService.getById(userRole.getRoleId());
	                if (role != null) {
	                    roles.add(role);
	                }
	            });
	        }
	        return roles;
	    }
    @Override
    public List<String> getUserRoleCodes(Long userId) {
        List<String> roleCodes = new ArrayList<>();
        this.getUserRoles(userId).forEach(role -> roleCodes.add(String.valueOf(role.getRoleId())));
        return roleCodes;
    }
}
