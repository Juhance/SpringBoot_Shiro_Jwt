package com.cqust.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cqust.enitiy.Function;
import com.cqust.enitiy.Role;
import com.cqust.enitiy.RoleFunction;

public interface IRoleFunctionService extends IService<RoleFunction>{

	List<Function> getRoleFunctions(List<String> userRoleCodes);

	List<String> getFunctions(List<String> userRoleCodes);

}
