package com.luo.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

public class AuthenticationTest {

    SimpleAccountRealm simpleAccountRealm = new SimpleAccountRealm();

    @Before
    public void addUser() {
        simpleAccountRealm.addAccount("mark", "123456","admin","user");
    }

    @Test
    public void testAuthentication() {
        /** 构建SecurityManager环境**/
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(simpleAccountRealm);
        /**提交认证请求**/
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("mark", "123456");
        subject.login(token);

        /**判断是否认证**/
        System.out.println("isAuthenticated： " + subject.isAuthenticated());
        System.out.println(subject.hasRole("user"));

//        subject.logout();
//
//        /**判断是否认证**/
//        System.out.print("isAuthenticated： " + subject.isAuthenticated());

    }
}
