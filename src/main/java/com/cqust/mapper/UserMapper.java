package com.cqust.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cqust.enitiy.User;

@Mapper
public interface UserMapper extends BaseMapper<User> {	
	
	@Select("select Max(UID) from tab_user")
	int selectUserByLast();
	@Select("select * from tab_user u ORDER BY u.uid LIMIT 1")
	IPage<User> selectUserList(Page page);

}
