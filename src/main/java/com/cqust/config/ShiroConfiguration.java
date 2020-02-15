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
     * 注入Shiro自带的内存缓存类
     */
    @Bean(name = "shiroCacheManager")
    public CacheManager cacheManager() {
        return new MemoryConstrainedCacheManager();
    }

    /**
     * Subject工厂管理器
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

        // 替换默认的DefaultSubjectFactory，用于关闭session功能
        securityManager.setSubjectFactory(subjectFactory());

        DefaultSessionManager sessionManager = new DefaultSessionManager();
        // 关闭session定时检查，通过setSessionValidationSchedulerEnabled禁用掉会话调度器
        sessionManager.setSessionValidationSchedulerEnabled(false);
        securityManager.setSessionManager(sessionManager);

        // 关闭session存储，禁用Session作为存储策略的实现，但它没有完全地禁用Session所以需要配合SubjectFactory中的context.setSessionCreationEnabled(false)
        DefaultSubjectDAO subjectDAO = (DefaultSubjectDAO) securityManager.getSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = (DefaultSessionStorageEvaluator) subjectDAO.getSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);

        return securityManager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean filterFactoryBean = new ShiroFilterFactoryBean();

        // 添加自己的过滤器并且取名为jwt
        Map<String, Filter> filterMap = new LinkedHashMap<>();
        //设置我们自定义的JWT过滤器
        filterMap.put("jwt", new JWTFilter());
        filterFactoryBean.setFilters(filterMap);

        // 必须设置 SecurityManager
        filterFactoryBean.setSecurityManager(securityManager);

        //filterFactoryBean.setLoginUrl("/user/toLogin");
        //filterFactoryBean.setSuccessUrl("/user/toindex");
        //filterFactoryBean.setUnauthorizedUrl("/unAuthc");
        // 有顺序的map，拦截从上到下
        Map<String, String> filterRuleMap = new LinkedHashMap<>();
        // 访问 /unauthorized/** 不通过JWTFilter
        filterRuleMap.put("/static/**", "anon");
        //filterRuleMap.put("/user/toindex", "anon");
        filterRuleMap.put("/user/test", "anon");
        // 所有请求通过我们自己的JWT Filter
        filterRuleMap.put("/**", "jwt");
        filterFactoryBean.setFilterChainDefinitionMap(filterRuleMap);
        return filterFactoryBean;
    }

    @Bean
    public ShiroDatabaseRealm jwtRealm() {
        return new ShiroDatabaseRealm();
    }

    /**
     * 添加注解支持
     */
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator bean = new DefaultAdvisorAutoProxyCreator();
        // 强制使用cglib，防止重复代理和可能引起代理出错的问题
        // https://zhuanlan.zhihu.com/p/29161098
        bean.setProxyTargetClass(true);
        return bean;
    }

    /**
     * Shiro 生命周期处理器，实现初始化和销毁回调
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 启用Shrio授权注解拦截方式，AOP式方法级权限检查
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor bean = new AuthorizationAttributeSourceAdvisor();
        bean.setSecurityManager(securityManager);
        return bean;
    }
		@Bean 
		//提供对thymeleaf模板引擎的页面中的shiro自定义标签的支持
		public ShiroDialect getShiroDialect(){
			return new ShiroDialect();
		}
}
