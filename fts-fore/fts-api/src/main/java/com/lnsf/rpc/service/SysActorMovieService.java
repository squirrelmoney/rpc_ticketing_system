package com.lnsf.rpc.service;

import com.lnsf.rpc.domain.SysActorMovie;

import java.util.List;

/**
 * @description:
 * @author: money
 * @time: 2021/3/27 10:25
 */
public interface SysActorMovieService {
    List<SysActorMovie> findAll(SysActorMovie sysActorMovie);

    int add(SysActorMovie sysActorMovie);

    int delete(SysActorMovie sysActorMovie);
}
