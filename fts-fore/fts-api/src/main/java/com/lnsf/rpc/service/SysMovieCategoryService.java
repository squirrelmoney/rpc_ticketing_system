package com.lnsf.rpc.service;


import com.lnsf.rpc.domain.SysMovieCategory;

import java.util.List;

public interface SysMovieCategoryService {
    List<SysMovieCategory> findAll();

    SysMovieCategory findById(Long id);

    int add(SysMovieCategory sysMovieCategory);

    int update(SysMovieCategory sysMovieCategory);

    int delete(Long[] ids);
}
