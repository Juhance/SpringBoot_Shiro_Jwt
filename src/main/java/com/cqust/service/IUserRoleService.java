package com.cqust.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cqust.enitiy.Role;
import com.cqust.enitiy.UserRole;

public interface IUserRoleService extends IService<UserRole>{

	List<Role> getUserRoles(Long userId);

	List<String> getUserRoleCodes(Long userId);

}
