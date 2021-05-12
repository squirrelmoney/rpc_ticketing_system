package com.lnsf.rpc.service;


import com.lnsf.rpc.domain.SysSession;
import com.lnsf.rpc.domain.vo.SysSessionVo;

import java.util.List;

public interface SysSessionService {

    List<SysSession> findByVo(SysSessionVo sysSessionVo);

    SysSession findById(Long id);

    SysSession findOne(Long id);

    int add(SysSession sysSession);

    int update(SysSession sysSession);

    int delete(Long[] id);

    List<SysSession> findByCinemaAndMovie(Long cinemaId, Long movieId);

}
