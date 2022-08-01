package com.tanchengjin.demo.shiro.config;

import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;

@Configuration
public class ShiroConfig {
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager defaultWebSecurityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        //anno 不需要授权即可访问资源
        map.put("/auth/login", "anon");
        map.put("/auth/register", "anon");
        map.put("/auth/captcha", "anon");
        //authc 需要认证后才可访问的url路由
        map.put("/home/**", "authc");
        shiroFilterFactoryBean.setLoginUrl("/auth/login");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        return shiroFilterFactoryBean;
    }

    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager(Realm realm) {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        //安全管理器设置realm
        manager.setRealm(realm);
        return manager;
    }

    @Bean("realm")
    public Realm realm() {
        MyRealm myRealm = new MyRealm();
        //添加凭证校验匹配器
//        myRealm.setCredentialsMatcher(hashedCredentialsMatcher);
        return myRealm;
    }
}
