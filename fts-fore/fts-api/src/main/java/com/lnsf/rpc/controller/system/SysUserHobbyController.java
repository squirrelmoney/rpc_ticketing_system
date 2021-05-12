package com.lnsf.rpc.controller.system;


import com.lnsf.rpc.annotation.RpcReference;
import com.lnsf.rpc.controller.BaseController;
import com.lnsf.rpc.domain.SysUserHobby;
import com.lnsf.rpc.response.ResponseResult;
import com.lnsf.rpc.service.SysUserHobbyService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SysUserHobbyController extends BaseController {

    @RpcReference(group = "Hobby",version = "V1.0")
    private SysUserHobbyService sysUserHobbyService;

    @GetMapping("/sysUserHobby")
    public ResponseResult findAll(@RequestParam(required = false,defaultValue = "0") int pageSize,@RequestParam(required = false,defaultValue = "0") int pageNum){
        startPage();
        List<SysUserHobby> data = sysUserHobbyService.findAll();
        return getResult(data,pageSize,pageNum);
    }

    @GetMapping("/sysUserHobby/{id}")
    public ResponseResult findById(@PathVariable Long id){
        return getResult(sysUserHobbyService.findById(id));
    }

    @PostMapping("/sysUserHobby")
    public ResponseResult add(@Validated @RequestBody SysUserHobby sysUserHobby){
        return getResult(sysUserHobbyService.add(sysUserHobby));
    }

    @PutMapping("/sysUserHobby")
    public ResponseResult update(@Validated @RequestBody SysUserHobby sysUserHobby){
        return getResult(sysUserHobbyService.update(sysUserHobby));
    }

    @DeleteMapping("/sysUserHobby/{ids}")
    public ResponseResult delete(@PathVariable Long[] ids){
        return getResult(sysUserHobbyService.delete(ids));
    }

}
