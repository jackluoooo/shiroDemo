package com.luo.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

public class IniRealmTest {
    @Test
    public void testAuthentication() {
        IniRealm iniRealm=new IniRealm("classpath:iniRealm.ini");

        /** 构建SecurityManager环境**/
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(iniRealm);
        /**提交认证请求**/
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("mark", "123456");
        subject.login(token);

        /**判断是否认证**/
        System.out.println("isAuthenticated： " + subject.isAuthenticated());
        System.out.println(subject.hasRole("user"));
        subject.checkRole("admin");
        subject.checkPermission("user:delete");

//        subject.logout();
//
//        /**判断是否认证**/
//        System.out.print("isAuthenticated： " + subject.isAuthenticated());

    }
}
