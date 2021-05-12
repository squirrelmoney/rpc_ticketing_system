package com.lnsf.rpc.service;


import com.lnsf.rpc.domain.SysMovieToCategory;

import java.util.List;

public interface SysMovieToCategoryService {

    List<SysMovieToCategory> findAll(SysMovieToCategory sysMovieToCategory);

    int add(SysMovieToCategory sysMovieToCategory);

    int delete(SysMovieToCategory sysMovieToCategory);

}
