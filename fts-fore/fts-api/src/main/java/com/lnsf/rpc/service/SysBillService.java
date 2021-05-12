package com.lnsf.rpc.service;


import com.lnsf.rpc.domain.SysBill;

import java.util.List;

public interface SysBillService {

    List<SysBill> findAll(SysBill sysBill);

    SysBill findById(Long id);

    Object add(SysBill sysBill);

    int update(SysBill sysBill);

    int delete(Long[] ids);

    Double todayBoxOffice();
    
}
