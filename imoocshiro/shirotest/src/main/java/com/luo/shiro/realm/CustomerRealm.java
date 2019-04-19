package com.luo.shiro.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class CustomerRealm extends AuthorizingRealm {
    public static void main(String[] args) {
        System.out.println(new Md5Hash("123456","mark").toString());
    }

    HashMap<String,String> userMap=new HashMap<String, String>(16);
    {
        userMap.put("mark","73bea81c6c06bacab41a995495239545");
        super.setName("customerReaml");
    }
    /**
     * 授权
     * @param principalCollection
     * @return
     */
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String userName=(String) principalCollection.getPrimaryPrincipal();
        Set<String> roles=getRolesByUserName(userName);
        Set<String> permissions=getPerssionByUserName(userName);

        SimpleAuthorizationInfo authenticationInfo=new SimpleAuthorizationInfo();
        authenticationInfo.setRoles(roles);
        authenticationInfo.setStringPermissions(permissions);
        return authenticationInfo;
    }

    private Set<String> getPerssionByUserName(String userName) {
        Set<String> permissions=new HashSet<String>();
        permissions.add("user:delete");
        permissions.add("user:update");
        return permissions;
    }

    private Set<String> getRolesByUserName(String userName) {
        Set<String> roles=new HashSet<String>();
        roles.add("admin");
        roles.add("user");
        return roles;
    }

    /**
     * 认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String userName=(String) authenticationToken.getPrincipal();
        String password=getPasswordByUserName(userName);
        if(password==null){
            return  null;
        }

        SimpleAuthenticationInfo authenticationInfo=new SimpleAuthenticationInfo("mark",password,"customerReaml");
        authenticationInfo.setCredentialsSalt(ByteSource.Util.bytes("mark"));
        return authenticationInfo;
    }

    private String getPasswordByUserName(String userName){
     return  userMap.get(userName);
    }


}
