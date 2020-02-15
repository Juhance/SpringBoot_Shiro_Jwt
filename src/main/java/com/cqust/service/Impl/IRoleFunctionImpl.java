package com.cqust.service.Impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cqust.enitiy.Function;
import com.cqust.enitiy.Role;
import com.cqust.enitiy.RoleFunction;
import com.cqust.mapper.RoleFunctionMapper;
import com.cqust.service.IFunctionService;
import com.cqust.service.IRoleFunctionService;

@Service
public class IRoleFunctionImpl extends ServiceImpl<RoleFunctionMapper,RoleFunction> implements IRoleFunctionService {
	
	@Autowired
	private IFunctionService iFunctionService;
	@Override
	public List<Function> getRoleFunctions(List<String> userRoleCodes) {
		//1.����ͨ��RoleFunction,�ҵ���ӦRole��Function Id.
		List<Function> functions = new ArrayList<>();
		List<RoleFunction> RoleFunctions  = new ArrayList<>();
		if(userRoleCodes != null) {
			//������ɫ,�ҵ���Ӧ��RoleFunctions���list����
			for(String s:userRoleCodes) {
				//System.out.println(s);
				List<RoleFunction> rf = this.lambdaQuery().eq(RoleFunction::getRoleId, s).list();
				if(rf != null) {
					RoleFunctions.addAll(rf);
				}
			}
			//����RoleFunctions���list����,����functionID�ҵ���Ӧ��function����
			 RoleFunctions.forEach(RoleF ->{
			 Function function = iFunctionService.getById(RoleF.getFuncId());
			 if(function != null) {
					functions.add(function);
				}
			});
		}
		//2.ͨ��FunctionService����Ӧ��Ȩ���ҵ��������ӹ���.
		
		return functions;
	}
	@Override
	public List<String> getFunctions(List<String> userRoleCodes) {
		List<Function> list = this.getRoleFunctions(userRoleCodes);
		List<String> functions = new ArrayList<>();
		for(Function f : list) {
			if(f!=null) {
				functions.add(f.getFuncName());
			}
		}
		return functions;
	}

}
