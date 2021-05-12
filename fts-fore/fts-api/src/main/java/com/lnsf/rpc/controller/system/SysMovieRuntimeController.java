package com.lnsf.rpc.controller.system;


import com.lnsf.rpc.annotation.RpcReference;
import com.lnsf.rpc.controller.BaseController;
import com.lnsf.rpc.domain.SysMovieRuntime;
import com.lnsf.rpc.response.ResponseResult;
import com.lnsf.rpc.service.SysMovieRuntimeService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Movie")
public class SysMovieRuntimeController extends BaseController {

    @RpcReference(group = "Movie",version = "V1.0")
    private SysMovieRuntimeService sysMovieRuntimeService;

    @GetMapping("/sysMovieRuntime")
    public ResponseResult findAll(@RequestParam(required = false,defaultValue = "0") int pageSize,@RequestParam(required = false,defaultValue = "0") int pageNum){
        startPage();
        List<SysMovieRuntime> data = sysMovieRuntimeService.findAll();
        return getResult(data,pageSize,pageNum);
    }

    @GetMapping("/sysMovieRuntime/{id}")
    public ResponseResult findById(@PathVariable Long id){
        return getResult(sysMovieRuntimeService.findById(id));
    }

    @PostMapping("/sysMovieRuntime")
    public ResponseResult add(@Validated @RequestBody SysMovieRuntime sysMovieRuntime){
        return getResult(sysMovieRuntimeService.add(sysMovieRuntime));
    }

    @PutMapping("/sysMovieRuntime")
    public ResponseResult update(@Validated @RequestBody SysMovieRuntime sysMovieRuntime){
        return getResult(sysMovieRuntimeService.update(sysMovieRuntime));
    }

    @DeleteMapping("/sysMovieRuntime/{ids}")
    public ResponseResult delete(@PathVariable Long[] ids){
        return getResult(sysMovieRuntimeService.delete(ids));
    }

}
