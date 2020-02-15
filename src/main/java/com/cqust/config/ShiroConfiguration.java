package com.cqust.config;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.DefaultWebSubjectFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import com.cqust.jwt.JWTFilter;
import com.cqust.jwt.ShiroDatabaseRealm;
import com.cqust.jwt.StatelessDefaultSubjectFactory;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;

@Configuration
public class ShiroConfiguration {
    /**
     * ע��Shiro�Դ����ڴ滺����
     */
    @Bean(name = "shiroCacheManager")
    public CacheManager cacheManager() {
        return new MemoryConstrainedCacheManager();
    }

    /**
     * Subject����������
     */
    @Bean
    public DefaultWebSubjectFactory subjectFactory() {
        return new StatelessDefaultSubjectFactory();
    }

    @Bean
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(jwtRealm());
        securityManager.setCacheManager(cacheManager());

        // �滻Ĭ�ϵ�DefaultSubjectFactory�����ڹر�session����
        securityManager.setSubjectFactory(subjectFactory());

        DefaultSessionManager sessionManager = new DefaultSessionManager();
        // �ر�session��ʱ��飬ͨ��setSessionValidationSchedulerEnabled���õ��Ự������
        sessionManager.setSessionValidationSchedulerEnabled(false);
        securityManager.setSessionManager(sessionManager);

        // �ر�session�洢������Session��Ϊ�洢���Ե�ʵ�֣�����û����ȫ�ؽ���Session������Ҫ���SubjectFactory�е�context.setSessionCreationEnabled(false)
        DefaultSubjectDAO subjectDAO = (DefaultSubjectDAO) securityManager.getSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = (DefaultSessionStorageEvaluator) subjectDAO.getSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);

        return securityManager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean filterFactoryBean = new ShiroFilterFactoryBean();

        // ����Լ��Ĺ���������ȡ��Ϊjwt
        Map<String, Filter> filterMap = new LinkedHashMap<>();
        //���������Զ����JWT������
        filterMap.put("jwt", new JWTFilter());
        filterFactoryBean.setFilters(filterMap);

        // �������� SecurityManager
        filterFactoryBean.setSecurityManager(securityManager);

        //filterFactoryBean.setLoginUrl("/user/toLogin");
        //filterFactoryBean.setSuccessUrl("/user/toindex");
        //filterFactoryBean.setUnauthorizedUrl("/unAuthc");
        // ��˳���map�����ش��ϵ���
        Map<String, String> filterRuleMap = new LinkedHashMap<>();
        // ���� /unauthorized/** ��ͨ��JWTFilter
        filterRuleMap.put("/static/**", "anon");
        //filterRuleMap.put("/user/toindex", "anon");
        filterRuleMap.put("/user/test", "anon");
        // ��������ͨ�������Լ���JWT Filter
        filterRuleMap.put("/**", "jwt");
        filterFactoryBean.setFilterChainDefinitionMap(filterRuleMap);
        return filterFactoryBean;
    }

    @Bean
    public ShiroDatabaseRealm jwtRealm() {
        return new ShiroDatabaseRealm();
    }

    /**
     * ���ע��֧��
     */
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator bean = new DefaultAdvisorAutoProxyCreator();
        // ǿ��ʹ��cglib����ֹ�ظ�����Ϳ������������������
        // https://zhuanlan.zhihu.com/p/29161098
        bean.setProxyTargetClass(true);
        return bean;
    }

    /**
     * Shiro �������ڴ�������ʵ�ֳ�ʼ�������ٻص�
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * ����Shrio��Ȩע�����ط�ʽ��AOPʽ������Ȩ�޼��
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor bean = new AuthorizationAttributeSourceAdvisor();
        bean.setSecurityManager(securityManager);
        return bean;
    }
		@Bean 
		//�ṩ��thymeleafģ�������ҳ���е�shiro�Զ����ǩ��֧��
		public ShiroDialect getShiroDialect(){
			return new ShiroDialect();
		}
}
