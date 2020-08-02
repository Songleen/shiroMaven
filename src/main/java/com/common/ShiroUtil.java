package com.common;

import com.atguigu.system.ShiroRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;

/**
 *
 * @ClassName shiro
 * @Author Songleen
 * @Date 2020/08/02/21:59
 */
public class ShiroUtil {

    // 初始化shiro的运行环境
    static {
        // 1、初始化shiro的安全管理器
        DefaultSecurityManager dsm = new DefaultSecurityManager();

        // 2、设置用户的权限信息到安全管理器
        // 从配置文件中获取认证角色信息
        // IniRealm realm = new IniRealm("classpath:shiro.ini");

        // 从自定义realm中
        ShiroRealm realm = new ShiroRealm();

        dsm.setRealm(realm);

        // 3、将securityManager设置到运行环境中
        SecurityUtils.setSecurityManager(dsm);
    }

    public static Subject login(String username,String password){
        // 4、创建Subject实例
        Subject subject = SecurityUtils.getSubject();

        // 5、创建用于认证的token令牌，记录用户认证的身份和凭证，即账号和密码
        AuthenticationToken token = new UsernamePasswordToken(username, password);

        // 6、主题要进行登陆，登陆时进行认证检查，如果账号或者密码出错都会报错
        subject.login(token);

        // 7、检查用户登陆状态
        System.out.println("用户认证状态："+subject.isAuthenticated());

        return subject;

    }
}
