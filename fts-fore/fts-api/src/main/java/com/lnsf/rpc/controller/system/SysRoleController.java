package com.lnsf.rpc.controller.system;


import com.lnsf.rpc.annotation.RpcReference;
import com.lnsf.rpc.controller.BaseController;
import com.lnsf.rpc.domain.SysRole;
import com.lnsf.rpc.response.ResponseResult;
import com.lnsf.rpc.service.SysRoleService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SysRoleController extends BaseController {

    @RpcReference(group = "Role",version = "V1.0")
    private SysRoleService sysRoleService;

    @GetMapping("/sysRole")
    public ResponseResult findAll(@RequestParam(required = false,defaultValue = "0") int pageSize,@RequestParam(required = false,defaultValue = "0") int pageNum){
        startPage();
        List<SysRole> data = sysRoleService.findAll();
        return getResult(data,pageSize,pageNum);
    }

    @GetMapping("/sysRole/{id}")
    public ResponseResult findById(@PathVariable Long id){
        return getResult(sysRoleService.findById(id));
    }

    @PostMapping("/sysRole")
    public ResponseResult add(@Validated @RequestBody SysRole sysRole){
        return getResult(sysRoleService.add(sysRole));
    }

    @PutMapping("/sysRole")
    public ResponseResult update(@Validated @RequestBody SysRole sysRole){
        return getResult(sysRoleService.update(sysRole));
    }

    @DeleteMapping("/sysRole/{ids}")
    public ResponseResult delete(@PathVariable Long[] ids){
        return getResult(sysRoleService.delete(ids));
    }

    /**
     * 给指定 id 的角色分配权限，包括增加或者删除权限
     * @param roleId
     * @param keys
     * @return
     */
    @PostMapping("/sysRole/{roleId}")
    public ResponseResult allotRight(@PathVariable Long roleId, @RequestBody Long[] keys){
        return getResult(sysRoleService.allotRight(roleId, keys));
    }

}
