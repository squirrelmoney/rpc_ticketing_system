package com.lnsf.rpc.controller.system;


import com.lnsf.rpc.annotation.RpcReference;
import com.lnsf.rpc.controller.BaseController;
import com.lnsf.rpc.domain.SysActorRole;
import com.lnsf.rpc.response.ResponseResult;
import com.lnsf.rpc.service.SysActorRoleService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 演员角色控制器
 */
@RestController
@RequestMapping("/Actor")
public class SysActorRoleController extends BaseController {

    @RpcReference(group = "Actor",version = "V1.0")
    private  SysActorRoleService sysActorRoleService;

    @GetMapping("/sysActorRole")
    public ResponseResult findAll(@RequestParam(required = false,defaultValue = "0") int pageSize,@RequestParam(required = false,defaultValue = "0") int pageNum){
        startPage();
        List<SysActorRole> data = sysActorRoleService.findAll();
        return getResult(data,pageSize,pageNum);
    }

    @GetMapping("/sysActorRole/{id}")
    public ResponseResult findById(@PathVariable Long id){
        return getResult(sysActorRoleService.findById(id));
    }

    @PostMapping("/sysActorRole")
    public ResponseResult add(@Validated @RequestBody SysActorRole sysActorRole){
        return getResult(sysActorRoleService.add(sysActorRole));
    }

    @PutMapping("/sysActorRole")
    public ResponseResult update(@Validated @RequestBody SysActorRole sysActorRole){
        return getResult(sysActorRoleService.update(sysActorRole));
    }

    @DeleteMapping("/sysActorRole/{ids}")
    public ResponseResult delete(@PathVariable Long[] ids){
        return getResult(sysActorRoleService.delete(ids));
    }

}
