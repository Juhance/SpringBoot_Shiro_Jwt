package com.cqust.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.cqust.Vo.ResponseVo;
import com.cqust.enitiy.Role;
import com.cqust.enitiy.User;
import com.cqust.enitiy.UserRole;
import com.cqust.jwt.JWTUtil;
import com.cqust.service.IUserRoleService;
import com.cqust.service.RecordService;
import com.cqust.service.UserService;
import com.cqust.util.JedisUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("/user")
public class UserController {

    Logger log = Logger.getLogger(UserController.class);
    @Autowired
	private UserService userService;
    @Autowired
    private IUserRoleService userRoleservice;
    @Resource
    private RecordService recordService;
	
	@RequestMapping("toLogin")
	public String toLogin() {
		
		return "login";
	}

	@RequestMapping(value = "login")
	@ResponseBody
	public ResponseVo login(@RequestBody User user,HttpServletResponse response) throws UnsupportedEncodingException {
		//֧�����ַ�ʽ��½��1.userName;2.loginName;3.email
		User u = null;
		u = userService.lambdaQuery()
				.eq(User::getLoginName, user.getLoginName())
				.eq(User::getPassword, user.getPassword())
				.one();
		int flag = 0;
		if(u == null) {
			u = userService.lambdaQuery()
					.eq(User::getUserName, user.getLoginName())
					.eq(User::getPassword, user.getPassword())
					.one();
			flag++;
			if(u == null) {
				u = userService.lambdaQuery()
						.eq(User::getEmail, user.getLoginName())
						.eq(User::getPassword, user.getPassword())
						.one();
				flag++;
			}
		}
		if(u != null && u.getStatus() == 1) {
			//System.out.println(flag);
			if(flag == 0) {
				u.setLoginMethod(u.getLoginName());
			}
			if(flag == 1) {
				u.setLoginMethod(u.getUserName());
			}
			if(flag == 2) {
				u.setLoginMethod(u.getEmail());			
			}
			userService.saveOrUpdate(u);
			//System.out.println(u.getLoginMethod());
			//����һ���û���ר��token
			String token = JWTUtil.sign(u.getLoginMethod(),u.getSalt());
			//����Redis����,��Ч��Ϊ��Сʱ��tokenΪkey��session_keyΪvalue;
			JedisUtil.set("username", token, JWTUtil.EXPIRE_SECONDS, TimeUnit.SECONDS);
			String value = JedisUtil.get("username");
			//����ǰ��
			return ResponseVo.success(token,u);
		}else {
			if(u == null) {
				return ResponseVo.error("�˺Ż��������");
			}
			if(u.getStatus() == 0) {
				return ResponseVo.error("���û��ѱ�������");
			}
			return null;
		}
		
	}
	@RequestMapping("/toindex")
	public String toindex(@RequestParam("token") String token,Model model,HttpServletRequest request) {
		String loginName = null;
		try {
			loginName = JWTUtil.getUsername(token);
		}catch(Exception e) {
			return null;
		}
		User user = new User();
		
		user.setLoginMethod(loginName);//����½ֵ����ǰ��
		model.addAttribute("user", user);
		model.addAttribute("token",token.toString());
		return "index";
	}
	@RequestMapping("test")
    public String hello(Model m,
            @RequestParam(value = "start",defaultValue = "1")int start,
            @RequestParam(value = "size",defaultValue = "5")int size) {
     // ����ѯ�������Է�ҳ����ʽչʾ
        PageHelper.startPage(start,size);// ������ָ���������
        List<User> list = userService.list();
        PageInfo<User> page = new PageInfo<User>(list);
        
        m.addAttribute("page",page);
        return "test";
    }
	@RequestMapping("/findPage")
	@ResponseBody
	public ResponseVo list(
			@RequestParam(value = "start",defaultValue = "1")int start,
            @RequestParam(value = "size",defaultValue = "5")int size,
            String str,HttpServletRequest request) {
		// �����ҳ��ѯ����
		// ����ѯ�������Է�ҳ����ʽչʾ
		//System.out.println("start:"+start+" size:"+size);
        PageHelper.startPage(start,size);// ������ָ���������
        List<User> list = userService.list();
        PageInfo<User> pageUser = new PageInfo<User>(list);
        PageHelper.startPage(start,size);// ������ָ���������
        List<UserRole> userRoles = userRoleservice.list();
        PageInfo<UserRole> pageUserRole = new PageInfo<UserRole>(userRoles);
        //List<PageInfo> page = new ArrayList<>();
        Map<String,PageInfo> page  = new HashMap<>();
        page.put("pageUser", pageUser);
        page.put("pageUserRole",pageUserRole);
		return ResponseVo.success(page);
	}
	@RequiresRoles(value={"1","2"},logical = Logical.OR)
	@RequestMapping("/findById")
	@ResponseBody
	public ResponseVo findById(@RequestParam("uid") String id) {
		String token = (String) SecurityUtils.getSubject().getPrincipal();
		if(token==null) {
			return null;
		}
		User user = userService.lambdaQuery().eq(User::getUid, id).one();
		//System.out.println(user);
		if(user == null) {
			return ResponseVo.error("δ�ҵ����û���");
		}
		return ResponseVo.success(user);
	}
	@RequiresRoles(value={"1","2"},logical = Logical.OR)
	@RequestMapping("/updateUser")
	@ResponseBody
	public ResponseVo updateUser(@RequestBody User u) {
		String token = (String) SecurityUtils.getSubject().getPrincipal();
		if(token==null || u== null) {
			return null;
		}
		userService.saveOrUpdate(u);
		User r = userService.lambdaQuery().eq(User::getUid, u.getUid()).one();
		return ResponseVo.success();
		
	}
	@RequestMapping("/findUserByPoint")
	@ResponseBody
	public ResponseVo findUserByPoint(@RequestParam("id") String uid) {
		List<User> userList = new ArrayList<User>();
		User  u = userService.lambdaQuery().eq(User::getUid, uid).one();
		 //System.out.println(u.toString());
		 if(u != null) {
			 userList.add(u);
			 return ResponseVo.success(userList);
		 }
		 return ResponseVo.error("δ�ҵ�ƥ���");
	}
	@RequestMapping("/findUserRoleByPoint")
	@ResponseBody
	public ResponseVo findUserRoleByPoint(@RequestParam("id") String uid) {
		List<UserRole> userRoleList = new ArrayList<UserRole>();
		userRoleList = userRoleservice.lambdaQuery().eq(UserRole::getUserId, uid).list();
		 //System.out.println(u.toString());
		 if(userRoleList.size() > 0) {
			 return ResponseVo.success(userRoleList);
		 }
		 return ResponseVo.error("δ�ҵ�ƥ���");
	}
	@RequiresRoles(value={"1","2"},logical = Logical.OR)
	@RequestMapping("/deleteUser")
	@ResponseBody
	public ResponseVo deleteUser(@RequestBody String[] arr) {
		List<String> idlist = java.util.Arrays.asList(arr);
		/*for(String i : idlist) {
			System.out.println(i);
		}*/
 		try{
 			userService.removeByIds(idlist);
 		}catch(Exception e) {
 			e.printStackTrace();
 			return ResponseVo.error("ɾ��ʧ�ܣ�");
 		}
		return ResponseVo.success();
	}
	@RequiresRoles(value={"1","2"},logical = Logical.OR)
	@RequestMapping("/deleteUserRole")
	@ResponseBody
	public ResponseVo deleteUserRole(@RequestBody String[] arr) {
		List<String> idlist = java.util.Arrays.asList(arr);
		for(String i : idlist) {
			System.out.println(i);
		}
 		try{
 			userRoleservice.removeByIds(idlist);
 		}catch(Exception e) {
 			return ResponseVo.error("ɾ��ʧ�ܣ�");
 		}
		return ResponseVo.success();
	}
	@RequiresRoles(value={"1","2"},logical = Logical.OR)
	@RequestMapping("/createUser")
	@ResponseBody
	public ResponseVo createUser(@RequestBody User u) {
		//System.out.println(u.toString());
		//System.out.println(u.toString());
		User user = null;
		if(u.equals(user)){
			return ResponseVo.error("�����û�Ϊ�գ�");
		}
		String userName = u.getUserName();
		String loginName = u.getLoginName();
		if(userName != null && !" ".equals(userName)) {
			List<User> list = null;
			list = userService.lambdaQuery().eq(User::getUserName, userName).list();
			if(list.size() != 0) {
				return ResponseVo.error("�û����Ѵ��ڣ��������ta�û�����");
			}
		}
		if(loginName != null && !" ".equals(loginName)) {
			List<User> list = null;
			list = userService.lambdaQuery().eq(User::getLoginName, loginName).list();
			if(list.size() != 0) {
				return ResponseVo.error("��¼���Ѵ��ڣ��������ta��¼����");
			}
		}
		long l = System.currentTimeMillis();
		//new���ڶ�
		Date date = new Date(l);
		u.setCreateTime(date);
		u.setStatus(1);
		u.setUpdateTime(date);
		//Ĭ��������ֵΪabcd
		u.setSalt("abcd");
		boolean rs = userService.save(u);
		if(rs == true) {
			return ResponseVo.success();
		}else {
			return ResponseVo.error("����ʧ�ܣ�");
		}
	}
	@RequiresRoles(value={"1","2"},logical = Logical.OR)
	@RequestMapping("/createUserRole")
	@ResponseBody
	public ResponseVo createUserRole(@RequestBody UserRole ur) {
		//System.out.println(u.toString());
		//System.out.println(u.toString());
		UserRole userRole = null;
		if(ur.equals(userRole)){
			return ResponseVo.error("�����û�Ϊ�գ�");
		}
		int userId = ur.getUserId();
		int roleId = ur.getRoleId();
		if(userId != 0 && !" ".equals(userId) && roleId != 0 && !" ".equals(roleId)) {
			List<UserRole> list = null;
			list = userRoleservice.lambdaQuery()
					.eq(UserRole::getUserId, userId)
					.eq(UserRole::getRoleId, roleId)
					.list();
			if(list.size() != 0) {
				return ResponseVo.error("���û��Ѵ��ڴ˽�ɫ���������ta��ɫ��");
			}
		}
		long l = System.currentTimeMillis();
		boolean rs = userRoleservice.save(ur);
		if(rs == true) {
			return ResponseVo.success();
		}else {
			return ResponseVo.error("����ʧ�ܣ�");
		}
	}
}
