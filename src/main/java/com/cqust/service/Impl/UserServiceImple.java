package com.cqust.service.Impl;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cqust.Vo.ResponseVo;
import com.cqust.enitiy.User;
import com.cqust.jwt.JWTUtil;
import com.cqust.mapper.UserMapper;
import com.cqust.service.UserService;
@Service
public class UserServiceImple extends ServiceImpl<UserMapper,User> implements UserService{

	@Override
	public IPage<User> getUserList(Page page) {
		return baseMapper.selectUserList(page);
	}
	@Override
    public ResponseVo getUserByToken(String token) {
        if (StringUtils.isEmpty(token)) {
            return ResponseVo.error("传入参数为空");
        }
        String uid = JWTUtil.getUsername(token);
        System.out.println(uid);
        if (StringUtils.isEmpty(uid)) {
            return ResponseVo.error("登录信息有误");
        }
        User user = this.lambdaQuery()
                .eq(User::getLoginMethod, uid)
                .one();
        if (user == null) {
            return ResponseVo.error("用户不存在");
        }
        return ResponseVo.success(user);
    }
}
