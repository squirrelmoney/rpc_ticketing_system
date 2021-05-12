package com.lnsf.rpc.shiro.realms;


import com.lnsf.rpc.annotation.RpcReference;
import com.lnsf.rpc.domain.SysUser;
import com.lnsf.rpc.service.SysUserService;
import com.lnsf.rpc.shiro.JwtToken;
import com.lnsf.rpc.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * 自定义realm
 */
@Slf4j
public class CustomerRealm extends AuthorizingRealm {


    /**
     * 重写此方法避免shiro报错
     * @param token
     * @return
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    //身份认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String token = (String) authenticationToken.getCredentials();
        // 解密获得username，用于和数据库进行对比
        String username = null;
        try {
            username = JwtUtil.getUsername(token);
        } catch (Exception e) {
            throw new AuthenticationException("token拼写错误或者值为空");
        }
        if (username == null) {
            log.error("token无效(空''或者null都不行!)");
            throw new AuthenticationException("token无效");
        }
        return new SimpleAuthenticationInfo(token, token, this.getName());
    }
}
