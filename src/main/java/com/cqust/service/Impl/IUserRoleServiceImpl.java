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
	            // ��ȡ�û������н�ɫ
	            List<UserRole> userRoles = this.lambdaQuery()
	                    .select(UserRole::getRoleId)
	                    .eq(UserRole::getUserId, userId)
	                    .list();
	            //userRole��user-role��Ӧ��,ͨ������userRole,�Խ�ɫ���,userRole:����
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
