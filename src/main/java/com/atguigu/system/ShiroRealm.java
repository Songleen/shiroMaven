package com.atguigu.system;

import com.atguigu.model.User;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.HashSet;
import java.util.Set;

/**
 * @ClassName shiro
 * @Author Songleen
 * @Date 2020/08/02/22:20
 */
public class ShiroRealm extends AuthorizingRealm {

    // 认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("认证信息……");
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;

        String username = token.getUsername();
        String password = new String(token.getPassword());

        User user = new User("zhangsan", "123456");
        if (!user.getUsername().equals(username)) {
            throw new UnknownAccountException("用户名不存在！");
        }
        if (!user.getPassword().equals(password)) {
            throw new CredentialsException("密码错误");
        }

        System.out.println("认证成功");

        // 返回的这个信息，就是给shiro用的，可以理解为登陆成功后把用户信息设置到了session会话中
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(token.getPrincipal(), token.getCredentials(), getName());

        return info;
    }

    /**
     * 将认证通过的用户角色和权限信息设置到对应用户主题上
     *
     * @Author lisonglin
     * @Date  2020/08/02
     * @Param [principals] 和上面验证最后传进入的Principal对应
     * @return AuthorizationInfo 返回的这个授权对象中，就携带了相关的角色和权限信息
     **/
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        // 获取主身份信息，和上面验证传进来的一样
        String username = principals.getPrimaryPrincipal().toString();



        // 模拟从数据库中获取当前用户角色
        Set<String> roleNameSet = new HashSet<>();
        roleNameSet.add("系统管理员");
        roleNameSet.add("系统运维");

        // 模拟从数据库中获取当前用户权限，通过角色查询用户拥有的权限
        Set<String> permissionSet = new HashSet<>();
        permissionSet.add("sys:user:list"); // 查看列表,字符串没有特殊含义，只是为了方便标识
        permissionSet.add("sys:user:info"); // 查看详情
        permissionSet.add("sys:user:create"); // 创建用户
        permissionSet.add("sys:user:update"); // 修改用户
        permissionSet.add("sys:user:delete"); // 删除用户

        // 简单授权信息对象，包含用户的角色和权限信息
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addRoles(roleNameSet);
        info.addStringPermissions(permissionSet);

        System.out.println("授权完成！");
        return info;

    }


}
