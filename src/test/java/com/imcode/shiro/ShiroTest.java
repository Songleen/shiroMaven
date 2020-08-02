package com.imcode.shiro;

import com.common.ShiroUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * @ClassName shiro
 * @Author Songleen
 * @Date 2020/08/02/21:18
 */
public class ShiroTest {

    @Test
    public void test(){
        // 1、初始化shiro的安全管理器
        DefaultSecurityManager dsm = new DefaultSecurityManager();

        // 2、设置用户的权限信息到安全管理器
        IniRealm realm = new IniRealm("classpath:shiro.ini");

        dsm.setRealm(realm);

        // 3、将securityManager设置到运行环境中
        SecurityUtils.setSecurityManager(dsm);

        // 前三步构建环境，后面进行认证
        // 4、创建Subject实例
        Subject subject = SecurityUtils.getSubject();

        // 5、创建用于认证的token令牌，记录用户认证的身份和凭证，即账号和密码
        AuthenticationToken token = new UsernamePasswordToken("zhangsan", "123456");

        // 6、主题要进行登陆，登陆时进行认证检查，如果账号或者密码出错都会报错
        subject.login(token);

        // 7、检查用户登陆状态
        System.out.println("用户认证状态："+subject.isAuthenticated());

        //8、检查用户的授权状态
        System.out.println("是否拥有admin角色："+subject.hasRole("admin"));

        //9、检查是否有权限
        System.out.println("是否有权限："+subject.isPermitted("product:view"));

        //10、获取到主角
        System.out.println("获取到主角："+subject.getPrincipal());

        //11、退出
        subject.logout();
    }

    @Test
    public void test02(){
        // 登陆认证
        Subject subject = ShiroUtil.login("zhangsan", "123456");

        // 检查角色和权限
        System.out.println("是否是系统管理员？："+subject.hasRole("系统管理员"));
        System.out.println("是否有修改权限？："+subject.isPermitted("sys:user:update"));

        subject.logout();
    }
}
