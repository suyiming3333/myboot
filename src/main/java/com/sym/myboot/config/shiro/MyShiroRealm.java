package com.sym.myboot.config.shiro;


import com.sym.myboot.entity.SysPermission;
import com.sym.myboot.entity.SysRole;
import com.sym.myboot.entity.SysUser;
import com.sym.myboot.service.UserServiceI;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;


public class MyShiroRealm extends AuthorizingRealm {

    @Autowired
    private UserServiceI userServiceImpl;

    //用户授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        String userName = (String) principalCollection.getPrimaryPrincipal();
        List<Map> roleList = userServiceImpl.getSysRoleByUserName(userName);
        for(Map sysRole:roleList){
            authorizationInfo.addRole((String) sysRole.get("role"));
            List<Map> permissionList = userServiceImpl.getSysPermissionByRoleId((Integer) sysRole.get("role_id"));
            for(Map sysPermission:permissionList){
                authorizationInfo.addStringPermission((String) sysPermission.get("permission"));
            }
        }
        return authorizationInfo;
    }


    //用户认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String username = (String) authenticationToken.getPrincipal();
        SysUser userInfo = userServiceImpl.findUserByUserName(username);
        if(userInfo==null){
            return null;
        }

        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                userInfo.getAccount(),
                userInfo.getPassword(),
                userInfo.getUsername()
        );
        return authenticationInfo;
    }
}
