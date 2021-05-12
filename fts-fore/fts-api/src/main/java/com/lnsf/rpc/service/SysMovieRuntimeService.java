package com.lnsf.rpc.service;


import com.lnsf.rpc.domain.SysMovieRuntime;

import java.util.List;

public interface SysMovieRuntimeService {

    List<SysMovieRuntime> findAll();

    SysMovieRuntime findById(Long id);

    int add(SysMovieRuntime sysMovieRuntime);

    int update(SysMovieRuntime sysMovieRuntime);

    int delete(Long[] ids);

}
