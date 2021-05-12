package com.lnsf.rpc.service;


import com.lnsf.rpc.domain.SysActorRole;

import java.util.List;

/**
 * @description:
 * @author: money
 * @time: 2021/3/27 10:27
 */
public interface SysActorRoleService {
    List<SysActorRole> findAll();

    SysActorRole findById(Long id);

    int add(SysActorRole sysActorRole);

    int update(SysActorRole sysActorRole);

    int delete(Long[] ids);
}
