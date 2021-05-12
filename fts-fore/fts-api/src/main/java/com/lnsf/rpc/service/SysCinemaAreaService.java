package com.lnsf.rpc.service;


import com.lnsf.rpc.domain.SysCinemaArea;

import java.util.List;

public interface SysCinemaAreaService {

    List<SysCinemaArea> findAll();

    SysCinemaArea findById(Long id);

    int add(SysCinemaArea sysCinemaArea);

    int update(SysCinemaArea sysCinemaArea);

    int delete(Long[] ids);

}
