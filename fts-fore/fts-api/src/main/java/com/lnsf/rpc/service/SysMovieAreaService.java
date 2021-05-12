package com.lnsf.rpc.service;


import com.lnsf.rpc.domain.SysMovieArea;

import java.util.List;

public interface SysMovieAreaService {
    List<SysMovieArea> findAll();

    SysMovieArea findById(Long id);

    int add(SysMovieArea sysMovieArea);

    int update(SysMovieArea sysMovieArea);

    int delete(Long[] ids);
}
