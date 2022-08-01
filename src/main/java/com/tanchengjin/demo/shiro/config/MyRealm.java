package com.tanchengjin.demo.shiro.config;

import lombok.Data;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.Arrays;
import java.util.List;

public class MyRealm extends AuthorizingRealm {
    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取主身份信息
        String primaryPrincipal = (String) principalCollection.getPrimaryPrincipal();
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        //添加用户角色
        simpleAuthorizationInfo.addRole("user");
        //添加资源权限
        List<String> strings = Arrays.asList("sys:user:add", "sys:user:del");
        simpleAuthorizationInfo.addStringPermissions(strings);
        return simpleAuthorizationInfo;
    }

    //验证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String principal = (String) authenticationToken.getPrincipal();

        User user = new User();
        user.setUsername("admin");
        user.setPassword("admin");
        user.setSalt("123456");
        if (null == principal || "".equals(principal) || principal.length() == 0) {
            throw new RuntimeException("Token缺失!");
        }

        if (null == user || user.getUsername() == null) {
            throw new RuntimeException("用户信息错误");
        }
        return new SimpleAuthenticationInfo(principal, user.getPassword(), ByteSource.Util.bytes(user.getSalt()), user.getUsername());
    }

    @Data
    private static class User {
        private String username;
        private String password;
        private String salt;
    }
}
