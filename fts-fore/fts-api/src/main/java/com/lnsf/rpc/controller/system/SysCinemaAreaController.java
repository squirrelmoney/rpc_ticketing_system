package com.lnsf.rpc.controller.system;


import com.lnsf.rpc.annotation.RpcReference;
import com.lnsf.rpc.controller.BaseController;
import com.lnsf.rpc.domain.SysCinemaArea;
import com.lnsf.rpc.response.ResponseResult;
import com.lnsf.rpc.service.SysCinemaAreaService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Cinema")
public class SysCinemaAreaController extends BaseController {

    @RpcReference(group = "Cinema",version = "V1.0")
    private SysCinemaAreaService sysCinemaAreaService;

    @GetMapping("/sysCinemaArea")
    public ResponseResult findAll(@RequestParam(required = false,defaultValue = "0") int pageSize,@RequestParam(required = false,defaultValue = "0") int pageNum){
        startPage();
        List<SysCinemaArea> data = sysCinemaAreaService.findAll();
        return getResult(data,pageSize,pageNum);
    }

    @GetMapping("/sysCinemaArea/{id}")
    public ResponseResult findById(@PathVariable Long id){
        return getResult(sysCinemaAreaService.findById(id));
    }

    @PostMapping("/sysCinemaArea")
    public ResponseResult add(@Validated @RequestBody SysCinemaArea sysCinemaArea){
        return getResult(sysCinemaAreaService.add(sysCinemaArea));
    }

    @PutMapping("/sysCinemaArea")
    public ResponseResult update(@Validated @RequestBody SysCinemaArea sysCinemaArea){
        return getResult(sysCinemaAreaService.update(sysCinemaArea));
    }

    @DeleteMapping("/sysCinemaArea/{ids}")
    public ResponseResult delete(@PathVariable Long[] ids){
        return getResult(sysCinemaAreaService.delete(ids));
    }

}
