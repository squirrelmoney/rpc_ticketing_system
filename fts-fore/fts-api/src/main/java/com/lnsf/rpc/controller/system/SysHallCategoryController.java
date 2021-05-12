package com.lnsf.rpc.controller.system;


import com.lnsf.rpc.annotation.RpcReference;
import com.lnsf.rpc.controller.BaseController;
import com.lnsf.rpc.domain.SysHallCategory;
import com.lnsf.rpc.response.ResponseResult;
import com.lnsf.rpc.service.SysHallCategoryService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Hall")
public class SysHallCategoryController extends BaseController {
    @RpcReference(group = "Hall",version = "V1.0")
    private SysHallCategoryService sysHallCategoryService;

    @GetMapping("/sysHallCategory")
    public ResponseResult findAll(@RequestParam(required = false,defaultValue = "0") int pageSize,@RequestParam(required = false,defaultValue = "0") int pageNum){
        startPage();
        List<SysHallCategory> data = sysHallCategoryService.findAll();
        return getResult(data,pageSize,pageNum);
    }

    @GetMapping("/sysHallCategory/{id}")
    public ResponseResult findById(@PathVariable Long id){
        return getResult(sysHallCategoryService.findById(id));
    }

    @PostMapping("/sysHallCategory")
    public ResponseResult add(@Validated @RequestBody SysHallCategory sysHallCategory){
        return getResult(sysHallCategoryService.add(sysHallCategory));
    }


    @PutMapping("/sysHallCategory")
    public ResponseResult update(@Validated @RequestBody SysHallCategory sysHallCategory){
        return getResult(sysHallCategoryService.update(sysHallCategory));
    }

    @DeleteMapping("/sysHallCategory/{ids}")
    public ResponseResult delete(@PathVariable Long[] ids){
        return getResult(sysHallCategoryService.delete(ids));
    }
}
