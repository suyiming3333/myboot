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


public class MyShiroRealm extends AuthorizingRealm {

    @Autowired
    private UserServiceI userServiceImpl;

    //用户授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        SysUser userInfo = (SysUser) principalCollection.getPrimaryPrincipal();
        String userName = userInfo.getUsername();
        List<SysRole> roleList = userServiceImpl.getSysRoleByUserName(userName);
        for(SysRole sysRole:roleList){
            authorizationInfo.addRole(sysRole.getRole());
            List<SysPermission> permissionList = userServiceImpl.getSysPermissionByRoleId(sysRole.getId());
            for(SysPermission sysPermission:permissionList){
                authorizationInfo.addStringPermission(sysPermission.getPermission());
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
