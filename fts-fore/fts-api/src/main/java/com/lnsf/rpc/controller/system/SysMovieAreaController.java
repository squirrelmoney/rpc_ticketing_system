package com.lnsf.rpc.controller.system;


import com.lnsf.rpc.annotation.RpcReference;
import com.lnsf.rpc.controller.BaseController;
import com.lnsf.rpc.domain.SysMovieArea;
import com.lnsf.rpc.response.ResponseResult;
import com.lnsf.rpc.service.SysMovieAreaService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Movie")
public class SysMovieAreaController extends BaseController {
    @RpcReference(group = "Movie",version = "V1.0")
    private SysMovieAreaService sysMovieAreaService;

    @GetMapping("/sysMovieArea")
    public ResponseResult findAll(@RequestParam(required = false,defaultValue = "0") int pageSize,@RequestParam(required = false,defaultValue = "0") int pageNum){
        startPage();
        List<SysMovieArea> data = sysMovieAreaService.findAll();
        return getResult(data,pageSize,pageNum);
    }

    @GetMapping("/sysMovieArea/{id}")
    public ResponseResult findById(@PathVariable Long id){
        return getResult(sysMovieAreaService.findById(id));
    }

    @PostMapping("/sysMovieArea")
    public ResponseResult add(@Validated @RequestBody SysMovieArea sysMovieArea){
        return getResult(sysMovieAreaService.add(sysMovieArea));
    }


    @PutMapping("/sysMovieArea")
    public ResponseResult update(@Validated @RequestBody SysMovieArea sysMovieArea){
        return getResult(sysMovieAreaService.update(sysMovieArea));
    }

    @DeleteMapping("/sysMovieArea/{ids}")
    public ResponseResult delete(@PathVariable Long[] ids){
        return getResult(sysMovieAreaService.delete(ids));
    }
}
