package com.lnsf.rpc.controller.system;


import com.lnsf.rpc.annotation.RpcReference;
import com.lnsf.rpc.controller.BaseController;
import com.lnsf.rpc.domain.SysResource;
import com.lnsf.rpc.response.ResponseResult;
import com.lnsf.rpc.service.SysResourceService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SysResourceController extends BaseController {

    @RpcReference(group = "Resource",version = "V1.0")
    private SysResourceService sysResourceService;

    @GetMapping("/sysResource")
    public ResponseResult findAll(@RequestParam(required = false,defaultValue = "0") int pageSize,@RequestParam(required = false,defaultValue = "0") int pageNum){
        startPage();
        List<SysResource> data = sysResourceService.findAll();
        return getResult(data,pageSize,pageNum);
    }

    @GetMapping("/sysResource/children")
    public ResponseResult findWithChildren(){
        return getResult((Object)sysResourceService.findWithChildren());
    }

    @GetMapping("/sysResource/{id}")
    public ResponseResult findById(@PathVariable Long id){
        return getResult(sysResourceService.findById(id));
    }

    @GetMapping("/sysResource/tree")
    public ResponseResult findAllWithAllChildren(){
        return getResult(sysResourceService.findAllWithAllChildren());
    }

    @PostMapping("/sysResource")
    public ResponseResult add(@Validated @RequestBody SysResource sysResource){
        return getResult(sysResourceService.add(sysResource));
    }

    @PutMapping("/sysResource")
    public ResponseResult update(@Validated @RequestBody SysResource sysResource){
        return getResult(sysResourceService.update(sysResource));
    }

    @DeleteMapping("/sysResource/{ids}")
    public ResponseResult delete(@PathVariable Long[] ids){
        return getResult(sysResourceService.delete(ids));
    }

}
