package com.lnsf.rpc.service;
import com.lnsf.rpc.domain.SysCinema;
import com.lnsf.rpc.domain.vo.SysCinemaVo;

import java.util.List;

public interface SysCinemaService {

    List<SysCinema> findAll(SysCinemaVo sysCinemaVo);

    SysCinema findById(Long id);

    int add(SysCinema sysCinema);

    int update(SysCinema sysCinema);

    int delete(Long[] ids);

    SysCinema findCinemaById(Long id);

}
