package com.cqust.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cqust.Vo.ResponseVo;
import com.cqust.enitiy.User;
import com.cqust.exception.cachException;

public interface UserService extends IService<User> {

	IPage<User> getUserList(Page page);

	ResponseVo getUserByToken(String token);

}