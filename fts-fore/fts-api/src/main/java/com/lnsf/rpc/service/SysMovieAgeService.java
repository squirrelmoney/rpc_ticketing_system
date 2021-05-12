package com.lnsf.rpc.service;


import com.lnsf.rpc.domain.SysMovieAge;

import java.util.List;

public interface SysMovieAgeService {

    List<SysMovieAge> findAll();

    SysMovieAge findById(Long id);

    int add(SysMovieAge sysMovieAge);

    int update(SysMovieAge sysMovieAge);

    int delete(Long[] ids);

}
