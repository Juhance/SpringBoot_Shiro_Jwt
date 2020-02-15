package com.cqust.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cqust.enitiy.Function;;

public interface IFunctionService extends IService<Function>{
	public List<Function> findFunctionById(int userId);
}
