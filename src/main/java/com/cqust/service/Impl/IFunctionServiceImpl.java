package com.cqust.service.Impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cqust.enitiy.Function;
import com.cqust.mapper.FunctionMapper;
import com.cqust.service.IFunctionService;

@Service
public class IFunctionServiceImpl extends ServiceImpl<FunctionMapper, Function> implements IFunctionService{

	@Override
	public List<Function> findFunctionById(int userId) {
		// TODO Auto-generated method stub
		return null;
	}
}
