package com.lnsf.rpc.controller.system;


import com.lnsf.rpc.annotation.RpcReference;
import com.lnsf.rpc.controller.BaseController;
import com.lnsf.rpc.domain.SysCinemaBrand;
import com.lnsf.rpc.response.ResponseResult;
import com.lnsf.rpc.service.SysCinemaBrandService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Cinema")
public class SysCinemaBrandController extends BaseController {
    @RpcReference(group = "Cinema",version = "V1.0")
    private SysCinemaBrandService sysCinemaBrandService;

    @GetMapping("/sysCinemaBrand")
    public ResponseResult findAll(@RequestParam(required = false,defaultValue = "0") int pageSize,@RequestParam(required = false,defaultValue = "0") int pageNum){
        startPage();
        List<SysCinemaBrand> data = sysCinemaBrandService.findAll();
        return getResult(data,pageSize,pageNum);
    }

    @GetMapping("/sysCinemaBrand/{id}")
    public ResponseResult findById(@PathVariable Long id){
        return getResult(sysCinemaBrandService.findById(id));
    }

    @PostMapping("/sysCinemaBrand")
    public ResponseResult add(@Validated @RequestBody SysCinemaBrand sysCinemaBrand){
        return getResult(sysCinemaBrandService.add(sysCinemaBrand));
    }


    @PutMapping("/sysCinemaBrand")
    public ResponseResult update(@Validated @RequestBody SysCinemaBrand sysCinemaBrand){
        return getResult(sysCinemaBrandService.update(sysCinemaBrand));
    }

    @DeleteMapping("/sysCinemaBrand/{ids}")
    public ResponseResult delete(@PathVariable Long[] ids){
        return getResult(sysCinemaBrandService.delete(ids));
    }
}
