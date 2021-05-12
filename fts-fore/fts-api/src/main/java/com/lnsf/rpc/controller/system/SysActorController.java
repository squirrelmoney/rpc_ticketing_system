package com.lnsf.rpc.controller.system;


import com.lnsf.rpc.annotation.RpcReference;
import com.lnsf.rpc.controller.BaseController;
import com.lnsf.rpc.domain.SysActor;
import com.lnsf.rpc.response.ResponseResult;
import com.lnsf.rpc.service.SysActorService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/Actor")
public class SysActorController extends BaseController {
    @RpcReference(group = "Actor",version = "V1.0")
    private SysActorService sysActorService;

    @GetMapping("/sysActor")
    public ResponseResult findAll(SysActor sysActor,@RequestParam(required = false,defaultValue = "0") int pageSize,@RequestParam(required = false,defaultValue = "0") int pageNum ){
        startPage();
        List<SysActor> data = sysActorService.findAll(sysActor);
        return getResult(data,pageSize,pageNum);
    }

    @GetMapping("/sysActor/{id}")
    public ResponseResult findById(@PathVariable Long id){
        return getResult(sysActorService.findById(id));
    }

    @PostMapping("/sysActor")
    public ResponseResult add(@Validated @RequestBody SysActor sysActor){
        return getResult(sysActorService.add(sysActor));
    }

    @PutMapping("/sysActor")
    public ResponseResult update(@Validated @RequestBody SysActor sysActor){
        return getResult(sysActorService.update(sysActor));
    }

    @DeleteMapping("/sysActor/{ids}")
    public ResponseResult delete(@PathVariable Long[] ids){
        return getResult(sysActorService.delete(ids));
    }

    @GetMapping("/sysActor/find/{id}")
    public ResponseResult findActorById(@PathVariable Long id){
        return getResult(sysActorService.findActorById(id));
    }
}
