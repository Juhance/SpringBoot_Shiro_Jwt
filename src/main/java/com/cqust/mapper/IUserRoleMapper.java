package com.cqust.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cqust.enitiy.Role;
import com.cqust.enitiy.UserRole;
@Mapper
public interface IUserRoleMapper extends BaseMapper<UserRole>{

}
