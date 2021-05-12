package com.lnsf.rpc.service;


import com.lnsf.rpc.domain.LoginUser;
import com.lnsf.rpc.domain.SysUser;
import com.lnsf.rpc.domain.vo.SysUserVo;

import java.util.List;

public interface SysUserService {
    List<SysUser> findAll(SysUser sysUser);

    SysUser findById(Long id);

    SysUser findByName(String userName);

    int add(SysUser sysUser);

    int update(SysUser sysUser);

    int delete(Long[] ids);

    LoginUser login(SysUserVo sysUserVo);

    LoginUser findLoginUser(SysUserVo sysUserVo);

    boolean isUserNameUnique(String userName, Long userId);
}
