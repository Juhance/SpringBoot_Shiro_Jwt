package com.cqust.jwt;

import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.cqust.Vo.ResponseVo;
import com.cqust.enitiy.Function;
import com.cqust.enitiy.Role;
import com.cqust.enitiy.User;
import com.cqust.service.IFunctionService;
import com.cqust.service.IRoleFunctionService;
import com.cqust.service.IUserRoleService;
import com.cqust.service.UserService;
import com.cqust.util.JedisUtil;

public class ShiroDatabaseRealm extends AuthorizingRealm {

	@Autowired
	private IFunctionService iFunctionService;
	@Autowired
	private IRoleFunctionService roleFunctionService;
	@Autowired
	private UserService userService;
	@Autowired
	private IUserRoleService userRoleService;
	/**
	 * ��Ȩ
	 */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
    	//��ȡ��ǰ�û���Ϣ
    	String token = principals.toString();
    	ResponseVo userByToken = userService.getUserByToken(token);
    	SimpleAuthorizationInfo saif = new SimpleAuthorizationInfo();
    	//�ж�token�Ƿ���ȷ
    	if(userByToken.getStatusCode() == 200) {
    		User user = userByToken.getUser();
    		//��ȡtoken�ڵĽ�ɫ��Ϣ
        	List<String> roles = userRoleService.getUserRoleCodes(Long.valueOf(user.getUid()));
        	//��ȡ��ɫ��Ӧ��Ȩ����Ϣ
        	List<String> perms = roleFunctionService.getFunctions(roles);
        	/*for(String string : roles) {
        		System.out.println(string);
        	}
        	for(String string : perms) {
        		System.out.println(string);
        	}*/
        	saif.addRoles(roles);
        	saif.addStringPermissions(perms);
    	} 	
  
    	return saif;
    }

    /**
     * ʹ��JWT���ԭ��Token
     *
     * @param token
     * @return
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    /**
     * ��֤
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        String token = (String) authcToken.getCredentials();
        
        String username = JWTUtil.getUsername(token);
        if (StringUtils.isEmpty(username)) {
            throw new IncorrectCredentialsException("��¼ƾ�ݴ���");
        }

        // �ӻ�����ȡֵ ȡ������ token,session_key
        String value = JedisUtil.get("username");
        if (StringUtils.isEmpty(value)) {
            throw new ExpiredCredentialsException("��¼�ѹ��ڣ������µ�¼");
        }

        return new SimpleAuthenticationInfo(token, token, getName());
    }
}
