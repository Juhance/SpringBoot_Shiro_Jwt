package com.cqust.service.Impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cqust.enitiy.Role;
import com.cqust.mapper.RoleMapper;
import com.cqust.service.IRoleService;

@Service
public class IRoleServiceImpl extends ServiceImpl<RoleMapper,Role> implements IRoleService {

}
